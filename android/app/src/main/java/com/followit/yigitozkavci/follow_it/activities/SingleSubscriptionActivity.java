package com.followit.yigitozkavci.follow_it.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.widget.ListView;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.adapters.SubscriptionDataList;
import com.followit.yigitozkavci.follow_it.adapters.SubscriptionList;
import com.followit.yigitozkavci.follow_it.database.FIDatabase;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionData;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDataDao;
import com.followit.yigitozkavci.follow_it.tasks.GetSubscriptionDataTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class SingleSubscriptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_subscription);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final ListView subscriptionDataView = findViewById(R.id.subscription_data_list);
        final Activity self = this;

        SubscriptionDataDao dao = DashboardActivity.db.subscriptionDataDao();

        new GetSubscriptionDataTask(dao, getIntent().getIntExtra("sub_id", 1)) {
            @Override
            protected void onPostExecute(List<SubscriptionData> subscriptionData) { // TODO: Where query with id parameter came from Intent.
                SubscriptionDataList adapter = new SubscriptionDataList(self, subscriptionData);
                subscriptionDataView.setAdapter(adapter);
            }
        }.execute();
    }
}
