package followit.Channel;

import java.util.Optional;

import errors.SubscriptionError;
import followit.Client;

public abstract class Channel {
  abstract public Optional<SubscriptionError> subscribe(Client client, String username);
}
