package followit.channel;

import errors.SubscriptionException;
import followit.Client;

/**
 * A declaration of what should a channel look like.
 * 
 * @author yigitozkavci
 */
public abstract class Channel {
  /**
   * Subscribe the client to the channel.
   * 
   * @param client Client to subscribe
   * @param username Username that client is subscribing for
   * @return
   * @throws SubscriptionException Thrown when a complication happens while subscribing 
   */
  abstract public boolean subscribe(Client client, String username) throws SubscriptionException;
  protected void clientCheck(Client client) throws SubscriptionException {
    if(!client.isRegistered()) {
      throw new SubscriptionException("Client is not registered");
    }
  }
}