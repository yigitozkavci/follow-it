package followit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.google.gson.Gson;

import followit.Channel.Channel;
import followit.Channel.FacebookChannel;
import followit.Channel.TwitterChannel;
import followit.command.Command;
import followit.command.RegisterCommand;
import followit.command.SubscriptionCommand;
import twitter4j.Status;

enum Tag {
  REGISTER, SUBSCRIBE
}
public class Client extends Thread {
  private BufferedReader in;
  private PrintWriter out;
  private FacebookChannel facebookChannel;
  private TwitterChannel twitterChannel;

  public Client(BufferedReader in, PrintWriter out, FacebookChannel facebookChannel,
      TwitterChannel twitterChannel) {
    this.in = in;
    this.out = out;
    this.facebookChannel = facebookChannel;
    this.twitterChannel = twitterChannel;
  }

  public void run() {
    String inputLine;
    try {
      while ((inputLine = in.readLine()) != null) {
        processInput(inputLine).ifPresent((command) -> { command.perform(); command.notifyClient(this); });
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Optional<Command> processInput(String inputLine) {
    System.out.println("Got message: " + inputLine);
    Matcher registerPat = Pattern.compile("register: (\\w+)").matcher(inputLine);
    Function<Channel, Matcher> subsPat = (chan) -> Pattern.compile("subscribe: " + chan.toString() + " (\\w+)").matcher(inputLine);
    if(registerPat.find()) {
      return Optional.of(new RegisterCommand(registerPat.group(0)));
    } else if (subsPat.apply(facebookChannel).find()) {
      return Optional.of(new SubscriptionCommand(this, facebookChannel, "Yigit"));
    } else if (subsPat.apply(twitterChannel).find()) {
      return Optional.of(new SubscriptionCommand(this, twitterChannel, "Yigit Ozkavci"));
    } else {
      System.out.println("No matches for message " + inputLine);
      return Optional.empty();
    }
  }

  public void notifyUpdate(List<Status> tweets) {
    out.println(
      prepareResult("tweets", tweets)
    );
  }

  public void notifyDebug() {
    out.println("{ \"tag\": \"debug\" }");
  }
  
  public PrintWriter getOutputStream() {
    return this.out;
  }
  
  private JSONObject prepareResult(String key, Object val) {
    HashMap<String, Object> vals = new HashMap<>();
    vals.put("tag", key);
    vals.put("data", new Gson().toJson(val));
    return new JSONObject(vals);
  }
}

class ClientResult<T> extends JSONObject {
  public ClientResult(String key, T value) {
    HashMap<String, T> result = new HashMap<>();
    result.put(key, value);
  }
}