package com.followit.yigitozkavci.follow_it.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.followit.yigitozkavci.follow_it.database.FIDatabase;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.adapters.SubscriptionList;
import com.followit.yigitozkavci.follow_it.tasks.GetSubscriptionsTask;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private String username;
    private List<Subscription> subscriptions;
    private ArrayAdapter<String> adapter;
    private ListView subscriptionList;
    protected static final String SUBSCRIPTIONS_EXTRA_KEY = "subscriptions";
    private SubscriptionDao subscriptionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        Intent intent = getIntent();
        this.username = intent.getStringExtra(MainActivity.CONNECTION_USERNAME);

        this.subscriptions = new ArrayList<>();
        this.subscriptionList = (ListView) findViewById(R.id.dashboardListView);
        this.adapter = new SubscriptionList(this, this.subscriptions);
        this.subscriptionList.setAdapter(adapter);

        // Initialize persistent DB
        FIDatabase db = Room.databaseBuilder(getApplicationContext(), FIDatabase.class, "fi").build();
        this.subscriptionDao = db.subscriptionDao();

        // Enable this to flush the database
        // new DeleteSubscriptionsTask(subscriptionDao).execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Fetch subscription asynchronously
        fetchSubscriptions();
    }

    /**
     * Fetch subscriptions from the database and update the @R.layout.dashboard@
     */
    private void fetchSubscriptions() {
        final Activity self = this; // Is this Javascript or what?

        new GetSubscriptionsTask(subscriptionDao) {
            @Override
            protected void onPostExecute(List<Subscription> subs) {
                subscriptions = new ArrayList<>(subs);
                adapter = new SubscriptionList(self, subscriptions);
                subscriptionList.setAdapter(adapter);
            }
        }.execute();
    }

    public void handleAddSubscription(View view) {
        Intent intent = new Intent(this, AddSubscriptionActivity.class);
        startActivity(intent);
    }
}