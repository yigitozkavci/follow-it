package com.followit.yigitozkavci.follow_it.network;

import android.util.Log;

import com.followit.yigitozkavci.follow_it.exceptions.ProtocolException;
import com.followit.yigitozkavci.follow_it.utils.Tuple;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yigitozkavci on 30.11.2017.
 */
public class FromServerMessage {
    public enum Tag {
        ERROR, REGISTER_ACCEPT, SUBSCRIPTION_ACCEPT, TWEETS, FB_POSTS;

        public boolean isSubscribedDataTag() {
            return this == TWEETS || this == FB_POSTS;
        }
    }

    private transient final String TWEETS_KEY = "tweets";
    private transient final String USER_KEY = "user";

    private Tag tag;
    private HashMap<String, Object> data;

    public Tag getTag() {
        return this.tag;
    }

    public HashMap<String, Object> getData() {
        return this.data;
    }

    public String getErrorMsg() throws ProtocolException {
        if(this.data == null || !this.data.containsKey("message")) {
            throw new ProtocolException("Tag is ERROR but data does not contain `message` key");
        }
        return (String) this.data.get("message");
    }

    public Tuple<String, ArrayList<String>> getTweetData() throws ProtocolException {
        HashMap<String, Object> data = this.getData();
        Log.d("TWEET_DATA_NULL", (String) data.get(USER_KEY));
        if(!data.containsKey("user") || !data.containsKey("tweets")) {
            throw new ProtocolException("Received wrong tweet message: " + data.toString());
        }
        return new Tuple((String) data.get(USER_KEY), (ArrayList<String>) data.get(TWEETS_KEY));
    }

    public String toString() {
        String result = "Tag: " + this.tag;
        if(data != null && data.containsKey("user") && data.containsKey("tweets")) {
            result += ",\nUser: " + this.data.get("user").toString();
            result += ",\nTweets: " + this.data.get("tweets").toString();
        }
        return result;
    }
}

