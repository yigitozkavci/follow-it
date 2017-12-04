package followit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import facebook4j.FacebookException;
import followit.channel.ChannelBuilder;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * The entry point for this distributed application. This class is responsible of listening
 * clients and spawning client threads.
 * 
 * @author yigitozkavci
 */
public class Server {
  /**
   * Message types from server to client.
   */
  public enum Tag {
    /**
     * Accept the registration of the client
     */
    REGISTER_ACCEPT,
    /**
     * Accept the subscription attempt of the client
     */
    SUBSCRIPTION_ACCEPT,
    /**
     * Reject the subscription attempt of the client
     */
    SUBSCRIPTION_REJECT,
    /**
     * An error has occurred on the server. Details will be given in .data.message node
     */
    ERROR, TWEETS
  }
  
  public static void main(String[] args) throws FacebookException, TwitterException, IOException {
    // TODO Auto-generated method stub

    Twitter twitter = new TwitterFactory().getInstance();
    ChannelBuilder channelBuilder = new ChannelBuilder(twitter);
    try (ServerSocket server = new ServerSocket(4444);) {
      while (true) {
        Socket clientSocket = server.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        new Client(in, out, channelBuilder).start();
      }
    }
  }

}
