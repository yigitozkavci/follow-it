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
    private Integer sub_id;

    public GetSubscriptionDataTask(SubscriptionDataDao dao) {
        this.dao = dao;
    }

    public GetSubscriptionDataTask(SubscriptionDataDao dao, int sub_id) {
        this.dao = dao;
        this.sub_id = sub_id;
    }

    protected List<SubscriptionData> doInBackground(Void... params) {
        return this.sub_id == null ? dao.getAll() : dao.getWhere(sub_id);
    }
}