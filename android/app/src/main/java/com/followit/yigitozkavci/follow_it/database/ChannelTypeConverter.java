package com.followit.yigitozkavci.follow_it.database;

import android.arch.persistence.room.TypeConverter;

import com.followit.yigitozkavci.follow_it.network.Channel;

/**
 * Created by yigitozkavci on 30.11.2017.
 */

public class ChannelTypeConverter {
    @TypeConverter
    public Channel fromString(String string) {
        switch(string) {
            case "Facebook":
                return Channel.FACEBOOK;
            case "Twitter":
                return Channel.TWITTER;
            default:
                return null;
        }

    }

    @TypeConverter
    public String toString(Channel channel) {
        return channel.toString();
    }
}
