package followit;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import twitter4j.*;

public class App {

	public static void main(String[] args) throws FacebookException, TwitterException {
		// TODO Auto-generated method stub
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.searchUsers("Yiğit Özkavcı").get(0);
		
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.search(new Query("Yigit"));
	}

}
