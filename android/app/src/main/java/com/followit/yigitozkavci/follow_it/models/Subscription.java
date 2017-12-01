package com.followit.yigitozkavci.follow_it.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;

import com.followit.yigitozkavci.follow_it.network.Channel;

/**
 * Created by yigitozkavci on 30.11.2017.
 */

@Entity(tableName = "subscriptions")
public class Subscription {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "channel")
    private Channel channel;

    @ColumnInfo(name = "username")
    private String username;

    public Subscription(Channel channel, String username) {
        this.channel = channel;
        this.username = username;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public String getUsername() {
        return this.username;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}