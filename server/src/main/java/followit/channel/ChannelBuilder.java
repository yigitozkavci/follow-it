package followit.channel;

import twitter4j.Twitter;

/**
 * Keeps the instance of twitter channel, since we only instansiate it once.
 *  
 * @author yigitozkavci
 */
public class ChannelBuilder {
  private TwitterChannel twitterChan;

  public ChannelBuilder(Twitter twitter) {
    this.twitterChan = new TwitterChannel(twitter);
  }

  public TwitterChannel getTwitterChannel() {
    return this.twitterChan;
  }
}