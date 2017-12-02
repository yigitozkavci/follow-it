package com.followit.yigitozkavci.follow_it.tasks;

import android.os.AsyncTask;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.models.SubscriptionData;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDataDao;

import java.util.List;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class GetSubscriptionDataTask extends AsyncTask<Void, Void, List<SubscriptionData>> {
    private SubscriptionDataDao dao;

    public GetSubscriptionDataTask(SubscriptionDataDao dao) {
        this.dao = dao;
    }

    protected List<SubscriptionData> doInBackground(Void... params) {
        return dao.getAll();
    }
}