package followit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import followit.channel.ChannelBuilder;
import followit.command.Command;
import twitter4j.Status;

/**
 * Client is a big thread which is responsible of only one client.
 * 
 * @author yigitozkavci
 */
public class Client extends Thread {
  public enum Tag {
    /**
     * Client wants to register
     */
    REGISTER,
    /**
     * Client wants to subscribe
     */
    SUBSCRIBE
  }
  
  /**
   * Reader for client's input stream
   */
  private BufferedReader in;
  
  /**
   * Writer for client's output stream
   */
  private PrintWriter out;
  
  /**
   * ChannelBuilder gives access to different channel instances, since
   * we only instansiate those channels once
   */
  private ChannelBuilder channelBuilder;
  
  /**
   * If this client is registered or not. Certain actions cannot be taken
   * while not registered
   */
  private boolean registered;
  
  /**
   * Even though provided username doesn't involve in business logic, we still take
   * value of this field while user registers
   */
  private String username;

  public Client(BufferedReader in, PrintWriter out, ChannelBuilder channelBuilder) {
    this.in = in;
    this.out = out;
    this.channelBuilder = channelBuilder;
    this.registered = false;
  }

  public void run() {
    String inputLine;
    try {
      while ((inputLine = in.readLine()) != null) {
        processInput(inputLine).ifPresent((command) -> command.perform(this, this.channelBuilder) );
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Parser of the client message
   * @param inputLine Message to parse
   * @return Whether a command can be created from this message or not
   */
  private Optional<Command> processInput(String inputLine)  {
    System.out.println("Got message: " + inputLine);
    Gson gson = new Gson();
    try {
      return Optional.of(gson.fromJson(inputLine, Command.class));
    } catch (JsonSyntaxException e) {
      return Optional.empty();
    }
  }

  /**
   * Called when there are data available to notify the client. This is the only interface
   * that is exposed in terms of sending data to client.
   * 
   * @param user username that is related to those tweets
   * @param tweets List of tweets to notify client with
   */
  public void notifyUpdate(String user, List<Status> tweets) {
    sendMessage(Server.Tag.TWEETS, "tweets", tweets.stream().map(t -> t.getText()).collect(Collectors.toList()), "user", user);
  }
  
  /**
   * Helper for sending a message with 2 key-values
   * 
   * @param tag Tag of the message
   * @param key1 Key of the first key-value
   * @param value1 Value of the first key-value
   * @param key2 Key of the second key-value
   * @param value2 Value of the second key-value
   */
  public void sendMessage(Server.Tag tag, String key1, Object value1, String key2, Object value2) {
    HashMap<String, Object> message = new HashMap<>();
    HashMap<String, Object> data = new HashMap<>();
    data.put(key1, value1);
    data.put(key2, value2);
    
    message.put("tag", tag);
    message.put("data", data);
    sendMessageData(message);
  }
  
  /**
   * Helper for sending a message with 1 key-value
   * 
   * @param tag Tag of the message
   * @param key Key of the key-value
   * @param value Value of the key-value
   */
  public void sendMessage(Server.Tag tag, String key, Object value) {
    HashMap<String, Object> message = new HashMap<>();
    HashMap<String, Object> data = new HashMap<>();
    data.put(key, value);
    
    message.put("tag", tag);
    message.put("data", data);
    sendMessageData(message);
  }
  
  /**
   * Helper for sending a message with no data associated with it, for
   * example, a {@link followit.Server.Tag#REGISTER_ACCEPT} message.
   * 
   * @param tag Tag of the message
   */
  public void sendMessage(Server.Tag tag) {
    HashMap<String, Object> message = new HashMap<>();
    message.put("tag", tag);
    sendMessageData(message);
  }
  
  /**
   * Private helper that is used while sending message
   * 
   * @param data Data to send message of
   */
  private void sendMessageData(HashMap<String, Object> data) {
    String stringifiedData = new Gson().toJson(data);
    this.out.println(stringifiedData);
    System.out.println("Sent message: " + stringifiedData);
  }
  
  /**
   * Register the client
   * 
   * @param username Username of the client who is registrating
   */
  public void register(String username) {
    this.registered = true;
    this.username = username;
  }
  
  /**
   * Getter of the {@link followit.Client#username}
   * 
   * @return Username of this client
   */
  public String getUsername() {
    return this.username;
  }
  
  /**
   * Getter of the {@link followit.Client#registered}
   * 
   * @return
   */
  public boolean isRegistered() {
    return registered;
  }
}