package com.followit.yigitozkavci.follow_it.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class GetSubscriptionsTask extends AsyncTask<Void, Void, List<Subscription>> {
    private SubscriptionDao dao;

    public GetSubscriptionsTask(SubscriptionDao dao) {
        this.dao = dao;
    }

    protected List<Subscription> doInBackground(Void... params) {
        return dao.getAll();
    }
}
