package com.followit.yigitozkavci.follow_it.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import com.followit.yigitozkavci.follow_it.network.Channel;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

@Entity( tableName = "subscription_data"
       , foreignKeys = @ForeignKey( entity = Subscription.class
                                  , parentColumns = "id"
                                  , childColumns = "subscription_id"))
public class SubscriptionData {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "subscription_id")
    private int subscriptionId;

    @ColumnInfo(name = "data")
    private String data;

    private transient boolean isNew;

    public SubscriptionData(int subscriptionId, String data) {
        this.subscriptionId = subscriptionId;
        this.data = data;
        this.isNew = true;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getSubscriptionId() {
        return this.subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public boolean getIsNew() {
        return this.isNew;
    }

    public void isSeen() {
        this.isNew = false;
    }
}
