package com.followit.yigitozkavci.follow_it.network;

import android.arch.persistence.room.TypeConverter;

import com.followit.yigitozkavci.follow_it.R;

/**
 * Created by yigitozkavci on 26.11.2017.
 */

public enum Channel {
    FACEBOOK("Facebook", R.drawable.facebook),
    TWITTER("Twitter", R.drawable.twitter);

    private String name;
    private Integer imageId;

    Channel(String name, Integer imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String toString() {
        return this.name;
    }

    public Integer getImageId() {
        return this.imageId;
    }
}