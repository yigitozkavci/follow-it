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

/**
 * Watcher of the tweets. This object is spawned for each individual Twitter user
 * we want to watch. Performs its action in per 5 seconds.
 * 
 * @author yigitozkavci
 */
class TwitterAgent extends TimerTask {
  /**
   * Twitter user that this agent tracks
   */
  private User user;
  
  /**
   * Clients that this agent will notify occasionally
   */
  private List<Client> watchers;
  
  /**
   * Twitter API instance
   */
  private Twitter twitter;
  
  /**
   * Tweet ID for caching tweets
   */
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
      List<Status> tweets = twitter.getUserTimeline(user.getId(), paging);
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

/**
 * Twitter channel. See {@link followit.channel.Channel} for more on channels
 * 
 * @author yigitozkavci
 */
public class TwitterChannel extends Channel {
  private Twitter twitter;
  private HashMap<User, List<Client>> subscriptions;

  public TwitterChannel(Twitter twitter) {
    this.twitter = twitter;
    subscriptions = new HashMap<>();
  }

  public boolean subscribe(Client client, String username) throws SubscriptionException {
    super.clientCheck(client);
    System.out.println("Subscribing to twitter user " + username);
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
        return true;
      } else {
        return false;
      }
    } catch (TwitterException e) {
      throw new SubscriptionException(e);
    }
  }

  /**
   * Find Twitter user with the given username
   * 
   * @param username Username of the Twitter user we are looking for
   * @return A user if we can find him/her, or an empty optional if we cannot
   * @throws TwitterException
   */
  private Optional<User> findUser(String username) throws TwitterException {
    return twitter.searchUsers(username, 5).stream().findFirst();
  }
  
  public static final String getName() {
    return "Twitter";
  }
}