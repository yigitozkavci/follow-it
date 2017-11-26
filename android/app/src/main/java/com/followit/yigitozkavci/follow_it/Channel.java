package com.followit.yigitozkavci.follow_it;

/**
 * Created by yigitozkavci on 26.11.2017.
 */

enum Channel {
    FACEBOOK("Facebook"), TWITTER("Twitter");

    private String name;

    Channel(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}