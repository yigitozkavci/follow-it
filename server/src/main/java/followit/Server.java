package followit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import followit.Channel.ChannelBuilder;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Server {
  public static void main(String[] args) throws FacebookException, TwitterException, IOException {
    // TODO Auto-generated method stub
    Facebook facebook = new FacebookFactory().getInstance();
    // System.out.println(facebook.searchUsers("Yiğit Özkavcı").get(0).getBio());

    Twitter twitter = new TwitterFactory().getInstance();
    ChannelBuilder channelBuilder = new ChannelBuilder(facebook, twitter);
    try (ServerSocket server = new ServerSocket(4444);) {
      while (true) {
        Socket clientSocket = server.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        new Client(in, out, channelBuilder.getFacebookChannel(), channelBuilder.getTwitterChannel()).start();
      }
    }
  }

}
