package followit.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import errors.SubscriptionException;
import followit.Client;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

abstract class Agent extends TimerTask {}

class TwitterAgent extends Agent {
  private User user;
  private List<Client> watchers;
  private Twitter twitter;
  private Optional<Long> sinceId;

  public TwitterAgent(User user, ArrayList<Client> watchers, Twitter twitter) {
    this.user = user;
    this.watchers = watchers;
    this.twitter = twitter;
    this.sinceId = Optional.empty();
  }

  @Override
  public void run() {
    // TODO Auto-generated method stub
    try {
      Paging paging = this.sinceId.isPresent() ? new Paging(this.sinceId.get()) : new Paging();
      List<Status> tweets = twitter.getUserTimeline(user.getName(), paging);
      if (tweets.isEmpty())
        return;
      this.sinceId = Optional.of(tweets.get(0).getId());
      watchers.forEach((watcher) -> {
        watcher.notifyUpdate(user.getScreenName(), tweets);
      });
    } catch (TwitterException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

public class TwitterChannel extends Channel {
  private Twitter twitter;
  private HashMap<User, List<Client>> subscriptions;

  public TwitterChannel(Twitter twitter) {
    this.twitter = twitter;
    subscriptions = new HashMap<>();
  }

  public void subscribe(Client client, String username) throws SubscriptionException {
    super.clientCheck(client);
    try {
      Optional<User> mb_user = findUser(username);
      if (mb_user.isPresent()) {
        User user = mb_user.get();
        if (!subscriptions.containsKey(user)) {
          ArrayList<Client> watcherClients = new ArrayList<>();
          subscriptions.put(user, watcherClients);
          new Timer().scheduleAtFixedRate(new TwitterAgent(user, watcherClients, twitter), 0, 5000);
        }
        subscriptions.get(user).add(client);
      } else {
        throw new SubscriptionException("User cannot be found");
      }
    } catch (TwitterException e) {
      throw new SubscriptionException(e);
    }
  }

  private Optional<User> findUser(String username) throws TwitterException {
    return twitter.searchUsers(username, 5).stream().findFirst();
  }
  
  public static final String getName() {
    return "Twitter";
  }
}