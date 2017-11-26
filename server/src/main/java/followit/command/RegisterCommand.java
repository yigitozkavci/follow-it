package followit.command;

import followit.Client;

public class RegisterCommand extends Command {
  private String username;
  
  public RegisterCommand(String username) {
    this.username = username;
  }
  
  public void perform() {
    // TODO: Implement
    System.out.println("Registrated user: " + username);
  }
  
  public void notifyClient(Client c) {
    c.getOutputStream().println("true");
  }
}
