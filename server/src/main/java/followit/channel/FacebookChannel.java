package followit.channel;

import java.util.HashMap;
import java.util.List;

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

  public void subscribe(Client client, String username) {
    // TODO: Subscription logic here
    
  }
  
  public static final String getName() {
    return "Facebook";
  }
}