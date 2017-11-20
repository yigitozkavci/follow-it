package errors;

import facebook4j.FacebookException;
import twitter4j.TwitterException;

public class SubscriptionError extends Exception {
	public SubscriptionError(String details) {
		System.out.println("A subscription error has occured: " + details);
	}
	
	public SubscriptionError(TwitterException e) {
		System.out.println("Twitter exception occured:");
		e.printStackTrace();
	}
	
	public SubscriptionError(FacebookException e) {
		System.out.println("Facebook exception occured:");
		e.printStackTrace();
	}
}