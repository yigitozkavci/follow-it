package com.followit.yigitozkavci.follow_it.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by yigitozkavci on 30.11.2017.
 */

@Dao
public interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions")
    List<Subscription> getAll();

    @Insert
    public void insertSubscription(Subscription sub);

    @Delete
    public void deleteSubscription(Subscription sub);

    @Query("DELETE FROM subscriptions")
    public void deleteSubscriptions();
}
