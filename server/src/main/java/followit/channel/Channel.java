package followit.channel;

import errors.SubscriptionException;
import followit.Client;

public abstract class Channel {
  abstract public void subscribe(Client client, String username) throws SubscriptionException;
  protected void clientCheck(Client client) throws SubscriptionException {
    if(!client.isRegistered()) {
      throw new SubscriptionException("Client is not registered");
    }
  }
}
