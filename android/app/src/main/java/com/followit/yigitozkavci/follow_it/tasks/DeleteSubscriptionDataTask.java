package com.followit.yigitozkavci.follow_it.tasks;

import android.os.AsyncTask;

import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDataDao;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class DeleteSubscriptionDataTask extends AsyncTask<Void, Void, Void> {
    private SubscriptionDataDao dao;

    public DeleteSubscriptionDataTask(SubscriptionDataDao dao) {
        this.dao = dao;
    }

    protected Void doInBackground(Void... params) {
        this.dao.deleteSubscriptionData();
        return null;
    }
}