package followit.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import errors.SubscriptionError;
import facebook4j.Facebook;
import facebook4j.User;
import followit.Client;

public class FacebookChannel extends Channel {
  private Facebook facebook;
  private HashMap<User, List<Client>> subscriptions;
  
  public FacebookChannel(Facebook facebook) {
      this.facebook = facebook;
      subscriptions = new HashMap<>();
  }

  public Optional<SubscriptionError> subscribe(Client client, String username) {
    // TODO: Subscription logic here
    return Optional.empty();
  }
  
  public String toString() {
    return "Facebook";
  }
}