package com.followit.yigitozkavci.follow_it.models;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

@Dao
public interface SubscriptionDataDao {
    @Query("SELECT * FROM subscription_data")
    List<SubscriptionData> getAll();


    @Query("SELECT * FROM subscription_data WHERE subscription_id = :sub_id")
    List<SubscriptionData> getWhere(int sub_id);

    @Insert
    void insertSubscriptionData(SubscriptionData sd);

    @Query("DELETE FROM subscription_data")
    void deleteSubscriptionData();
}
