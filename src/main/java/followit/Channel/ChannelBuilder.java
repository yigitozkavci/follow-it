package followit.Channel;

import facebook4j.Facebook;
import twitter4j.Twitter;

public class ChannelBuilder {
	private Facebook facebook;
	private Twitter twitter;
	
	public ChannelBuilder(Facebook facebook, Twitter twitter) {
		this.facebook = facebook;
		this.twitter = twitter;
	}
	
	public FacebookChannel buildFacebook() {
		return new FacebookChannel(facebook);
	}
	
	public TwitterChannel buildTwitter() {
		return new TwitterChannel(twitter);
	}
}