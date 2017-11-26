package followit.command;

import followit.Client;
import followit.Channel.Channel;

public class SubscriptionCommand extends Command {
  private Client client;
  private Channel chan;
  private String username;
  
  public SubscriptionCommand(Client client, Channel chan, String username) {
    this.client = client;
    this.chan = chan;
    this.username = username;
  }
  
  public void perform() {
    System.out.println("Opening a subscription");
    chan.subscribe(client, username);
  }
  
  public void notifyClient(Client c) {
    c.getOutputStream().println("true");
  }
}
