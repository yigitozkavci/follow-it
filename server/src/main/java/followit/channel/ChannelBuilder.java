package followit.channel;

import facebook4j.Facebook;
import twitter4j.Twitter;

public class ChannelBuilder {
  private TwitterChannel twitterChan;

  public ChannelBuilder(Facebook facebook, Twitter twitter) {
    this.twitterChan = new TwitterChannel(twitter);
  }

  public TwitterChannel getTwitterChannel() {
    return this.twitterChan;
  }
}