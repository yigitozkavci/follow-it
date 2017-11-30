package followit.channel;

import facebook4j.Facebook;
import twitter4j.Twitter;

public class ChannelBuilder {
  private FacebookChannel facebookChan;
  private TwitterChannel twitterChan;

  public ChannelBuilder(Facebook facebook, Twitter twitter) {
    this.facebookChan = new FacebookChannel(facebook);
    this.twitterChan = new TwitterChannel(twitter);
  }

  public FacebookChannel getFacebookChannel() {
    return this.facebookChan;
  }

  public TwitterChannel getTwitterChannel() {
    return this.twitterChan;
  }
}