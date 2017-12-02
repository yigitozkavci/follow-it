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

public class Client extends Thread {
  public enum Tag {
    REGISTER, SUBSCRIBE
  }
  
  private BufferedReader in;
  private PrintWriter out;
  private ChannelBuilder channelBuilder;
  private boolean registered;
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

  private Optional<Command> processInput(String inputLine)  {
    System.out.println("Got message: " + inputLine);
    Gson gson = new Gson();
    try {
      return Optional.of(gson.fromJson(inputLine, Command.class));
    } catch (JsonSyntaxException e) {
      return Optional.empty();
    }
  }

  public void notifyUpdate(String user, List<Status> tweets) {
    sendMessage(Server.Tag.TWEETS, "tweets", tweets.stream().map(t -> t.getText()).collect(Collectors.toList()), "user", user);
  }
  
  public void sendMessage(Server.Tag tag, String key1, Object value1, String key2, Object value2) {
    HashMap<String, Object> message = new HashMap<>();
    HashMap<String, Object> data = new HashMap<>();
    data.put(key1, value1);
    data.put(key2, value2);
    
    message.put("tag", tag);
    message.put("data", data);
    sendMessageData(message);
  }
  
  public void sendMessage(Server.Tag tag, String key, Object value) {
    HashMap<String, Object> message = new HashMap<>();
    HashMap<String, Object> data = new HashMap<>();
    data.put(key, value);
    
    message.put("tag", tag);
    message.put("data", data);
    sendMessageData(message);
  }

  public void sendMessage(Server.Tag tag) {
    HashMap<String, Object> message = new HashMap<>();
    message.put("tag", tag);
    sendMessageData(message);
  }
  
  private void sendMessageData(HashMap<String, Object> data) {
    String stringifiedData = new Gson().toJson(data);
    this.out.println(stringifiedData);
    System.out.println("Sent message: " + stringifiedData);
  }
  
  public void register(String username) {
    this.registered = true;
    this.username = username;
  }
  
  public String getUsername() {
    return this.username;
  }
  
  public boolean isRegistered() {
    return registered;
  }
}