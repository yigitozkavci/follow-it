package errors;

import facebook4j.FacebookException;
import twitter4j.TwitterException;

public class SubscriptionException extends Exception {
  private static final long serialVersionUID = 1L;
  private String message;
  
  public SubscriptionException(String details) {
    this.message = "A subscription error has occured: " + details;
  }

  public SubscriptionException(TwitterException e) {
    this.message = "Twitter exception occured: " + e.getMessage();
  }

  public SubscriptionException(FacebookException e) {
    this.message = "Facebook exception occured: " + e.getMessage();
  }
  
  public String getMessage() {
    return this.message;
  }
}