package followit.command;

import java.util.HashMap;
import java.util.Optional;

import errors.SubscriptionException;
import followit.Client;
import followit.Server;
import followit.channel.Channel;
import followit.channel.ChannelBuilder;

/**
 * An abstraction over what to do with a valid client message.
 * Details regarding commands are in the project report.
 * 
 * @author yigitozkavci
 */
public class Command {
  /**
   * Tag of this message
   */
  private Client.Tag tag;
  
  /**
   * Data contained within this message
   */
  private HashMap<String, String> data;
  
  /**
   * Client that this command has came by
   */
  private transient Client client;
  
  /**
   * The ChannelBuilder instance for using Twitter channel
   */
  private transient ChannelBuilder channelBuilder;
  
  /**
   * These are the keys when we are performing search in incoming data (see {@link followit.command.Command#data})
   */
  private final String USERNAME_KEY = "username";
  private final String DATA_CHAN_KEY = "channel";
  private final String TWITTER_NAME = "Twitter";
  private final String CHAN_USER_KEY = "user";
  
  public Command() {}
  
  /**
   * Perform the action contained in the command
   * @param client Client to perform action for
   * @param channelBuilder see {@link followit.command.Command#channelBuilder}
   */
  public void perform(Client client, ChannelBuilder channelBuilder) {
    this.client = client;
    this.channelBuilder = channelBuilder;
    
    if(tag == null) sendErrToClient("Cannot resolve message tag.");
    
    switch(this.tag) {
    case REGISTER:
      findDataFromKey(Client.Tag.REGISTER, USERNAME_KEY)
        .ifPresent((username) -> {
          client.register(username);
          client.sendMessage(Server.Tag.REGISTER_ACCEPT);
        });
      break;
    case SUBSCRIBE:
      findSubscriptionChannel()
        .ifPresent((chan) ->
          findDataFromKey(Client.Tag.SUBSCRIBE, CHAN_USER_KEY)
            .ifPresent((user) ->
              {
                try {
                  final Server.Tag msgTag = chan.subscribe(client, user) ? Server.Tag.SUBSCRIPTION_ACCEPT : Server.Tag.SUBSCRIPTION_REJECT;
                  client.sendMessage(msgTag, "channel", chan.getClass().getName());
                } catch (SubscriptionException e) {
                  System.out.println(e.getMessage());
                  sendErrToClient(e.getMessage());
                }
              }
            )
        );
      break;
    default:
      sendErrToClient("Tag " + this.tag + " is not understood.");
    }
  }
  
  /**
   * Find data by key contained in this instance's data
   * 
   * @param tag Tag of the message
   * @param key Key to look for value of
   * @return
   */
  private Optional<String> findDataFromKey(Client.Tag tag, String key) {
    if(this.data == null || !this.data.containsKey(key)) {
      sendErrToClient("Message tag is " + tag + ", but data doesn't contain key " + key);
      return Optional.empty();
    } else {
      return Optional.of(this.data.get(key));
    }
  }
  
  /**
   * Find the subscription channel just by looking at internal state
   * 
   * @return Channel if it's found, empty optional else
   */
  private Optional<Channel> findSubscriptionChannel() {
    // Uuu, monadic bind, love it
    return findDataFromKey(Client.Tag.SUBSCRIBE, DATA_CHAN_KEY).flatMap((chanName) -> {
      switch(chanName) {
      case TWITTER_NAME:
        return Optional.of(channelBuilder.getTwitterChannel());
      default:
        sendErrToClient("Message tag is SUBSCRIBE, but value of key " + DATA_CHAN_KEY + " doesn't match any of the channels.");
        return Optional.empty();
      }
    });
  }
  
  private void sendErrToClient(String message) {
    client.sendMessage(Server.Tag.ERROR, "message", message);
  }
}
