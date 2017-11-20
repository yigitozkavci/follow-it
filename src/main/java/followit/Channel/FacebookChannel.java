package followit.Channel;

import java.util.Optional;

import errors.SubscriptionError;
import facebook4j.Facebook;
import followit.Client;

public class FacebookChannel extends Channel {
	public FacebookChannel(Facebook facebook) {
		
	}
	
	public Optional<SubscriptionError> subscribe(Client client, String username) {
		// TODO: Subscription logic here
		return Optional.empty();
	}
}