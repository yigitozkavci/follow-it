package com.followit.yigitozkavci.follow_it.tasks;

import android.os.AsyncTask;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class DeleteSubscriptionsTask extends AsyncTask<Void, Void, Void> {
    private SubscriptionDao dao;

    public DeleteSubscriptionsTask(SubscriptionDao dao) {
        this.dao = dao;
    }

    protected Void doInBackground(Void... params) {
        this.dao.deleteSubscriptions();
        return null;
    }
}