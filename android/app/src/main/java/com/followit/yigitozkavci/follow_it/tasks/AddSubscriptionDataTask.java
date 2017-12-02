package com.followit.yigitozkavci.follow_it.tasks;

import android.os.AsyncTask;

import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.models.SubscriptionData;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDataDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class AddSubscriptionDataTask extends AsyncTask<Void, Void, Void> {
    private SubscriptionDataDao dao;
    private List<SubscriptionData> subData;

    public AddSubscriptionDataTask(SubscriptionDataDao dao, Subscription sub, List<String> data) {
        this.dao = dao;
        this.subData = new ArrayList<>();
        for(String datum : data) {
            this.subData.add(new SubscriptionData(sub.getId(), datum));
        }
    }

    protected Void doInBackground(Void... params) {
        for(SubscriptionData data : this.subData) {
            this.dao.insertSubscriptionData(data);
        }
        return null;
    }
}