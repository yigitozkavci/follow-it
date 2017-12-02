package com.followit.yigitozkavci.follow_it.tasks;

import android.os.AsyncTask;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.network.Channel;

import java.util.List;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class AddSubscriptionTask extends AsyncTask<Void, Void, Void> {
    private SubscriptionDao dao;
    private Subscription sub;

    public AddSubscriptionTask(SubscriptionDao dao, Subscription sub) {
        this.dao = dao;
        this.sub = sub;
    }

    protected Void doInBackground(Void... params) {
        this.dao.insertSubscription(sub);
        return null;
    }
}