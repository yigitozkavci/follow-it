package com.followit.yigitozkavci.follow_it.network;

/**
 * Created by yigitozkavci on 30.11.2017.
 */
public enum Tag {
    REGISTER, REGISTER_ACCEPT, ERROR, SUBSCRIBE, SUBSCRIPTION_ACCEPT, TWEETS, FB_POSTS;

    public boolean isSubscribedDataTag() {
        return this == TWEETS || this == FB_POSTS;
    }
}
