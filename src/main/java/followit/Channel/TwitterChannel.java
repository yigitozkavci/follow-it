package followit.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import errors.SubscriptionError;
import followit.Client;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

class UserWatcher extends TimerTask {
	private User user;
	private List<Client> watchers;
	private Twitter twitter;
	private Optional<Long> sinceId;
	
	public UserWatcher(User user, ArrayList<Client> watchers, Twitter twitter) {
		this.user = user;
		this.watchers = watchers;
		this.twitter = twitter;
		this.sinceId = Optional.empty();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Checking updates for user " + user.getName() + " for Twitter channel.");
		try {
			Paging paging = this.sinceId.isPresent() ? new Paging(this.sinceId.get()) : new Paging();
			List<Status> tweets = twitter.getUserTimeline(user.getName(), paging);
			this.sinceId = Optional.of(tweets.get(tweets.size() - 1).getId());
			watchers.forEach((watcher) -> {
				watcher.notifyUpdate(tweets);
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
	
	public Optional<SubscriptionError> subscribe(Client client, String username) {
		try {
			Optional<User> mb_user = findUser(username);
			if(mb_user.isPresent()) {
				User user = mb_user.get();
				System.out.println(user.toString());
				if(!subscriptions.containsKey(user)) {
					ArrayList<Client> watcherClients = new ArrayList<>();
					subscriptions.put(user, watcherClients);
					new Timer().scheduleAtFixedRate(new UserWatcher(user, watcherClients, twitter), 0, 5000);
				}
				subscriptions.get(user).add(client);
				return Optional.empty();
			} else {
				return Optional.of(new SubscriptionError("User cannot be found"));
			}
		} catch(TwitterException e) {
			return Optional.of(new SubscriptionError(e));
		}
	}
	
	private Optional<User> findUser(String username) throws TwitterException {
	  return twitter.searchUsers(username, 5)
				 .stream()
				 .findFirst();
	}
}