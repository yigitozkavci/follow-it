package followit.command;

import followit.Client;

abstract public class Command {
  public abstract void perform();
  public abstract void notifyClient(Client client);
}
