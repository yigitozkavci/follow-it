package com.followit.yigitozkavci.follow_it.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.network.Channel;

/**
 * Created by yigitozkavci on 30.11.2017.
 */
@Database(entities = { Subscription.class }, exportSchema = false, version = 1)
@TypeConverters({ ChannelTypeConverter.class })
abstract public class FIDatabase extends RoomDatabase {
    public abstract SubscriptionDao subscriptionDao();

}
