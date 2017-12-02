package com.followit.yigitozkavci.follow_it.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.models.SubscriptionData;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDataDao;
import com.followit.yigitozkavci.follow_it.network.Channel;

/**
 * Created by yigitozkavci on 30.11.2017.
 */
@Database(entities = { Subscription.class, SubscriptionData.class }, exportSchema = false, version = 3)
@TypeConverters({ ChannelTypeConverter.class })
abstract public class FIDatabase extends RoomDatabase {
    public abstract SubscriptionDao subscriptionDao();
    public abstract SubscriptionDataDao subscriptionDataDao();
}
