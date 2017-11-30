package followit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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

  public void notifyUpdate(List<Status> tweets) {
    HashMap<String, Object> data = new HashMap<>();
    data.put("tweets", tweets);
    sendMessage(Server.Tag.TWEETS, data);
  }
  
  public void sendMessage(Server.Tag tag, HashMap<String, Object> data) {
    HashMap<String, Object> message = new HashMap<>();
    message.put("tag", tag);
    message.put("data", data);
    this.out.println(new Gson().toJson(message));
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