package followit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import errors.SubscriptionError;
import followit.Channel.Channel;
import followit.Channel.FacebookChannel;
import followit.Channel.TwitterChannel;
import followit.Utils.Tuple;
import twitter4j.Status;

public class Client extends Thread {
	private int clientId;
	private BufferedReader in;
	private PrintWriter out;
	private FacebookChannel facebookChannel;
	private TwitterChannel twitterChannel;
	
	public Client(int id, BufferedReader in, PrintWriter out, FacebookChannel facebookChannel, TwitterChannel twitterChannel) {
		this.in = in;
		this.out = out;
		this.facebookChannel = facebookChannel;
		this.twitterChannel = twitterChannel;
	}
	
	public void run() {
		System.out.println("Client thread is registered. I got the streams!");
		String inputLine;
		try {
		    while ((inputLine = in.readLine()) != null) {
		    		processInput(inputLine).ifPresent((subsRequest) -> {
		    			Optional<SubscriptionError> result = subsRequest.fst.subscribe(this, subsRequest.snd);
		    			System.out.println(result.toString());
				});
				out.println("I am client " + clientId + " and I got your message: " + inputLine);
		    }
		} catch(IOException e) {
	    		e.printStackTrace();
	    }
	}
	
	private Optional<Tuple<Channel, String>> processInput(String inputLine) {
		if(inputLine == "subscribe Facebook") {
			return Optional.of(new Tuple<Channel, String>(facebookChannel, "Yigit"));
		} else if(inputLine.equalsIgnoreCase("subscribe Twitter")) {
			return Optional.of(new Tuple<Channel, String>(twitterChannel, "Yigit Ozkavci"));
		} else {
			return Optional.empty();
		}
	}
	
	public void notifyUpdate(List<Status> tweets) {
		out.println("Got an update with tweets: " + tweets);
	}

	public void notifyDebug() {
		out.println("Got a debug update");
	}
}