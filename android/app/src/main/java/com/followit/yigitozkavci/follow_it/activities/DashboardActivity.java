package com.followit.yigitozkavci.follow_it.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.followit.yigitozkavci.follow_it.database.FIDatabase;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.adapters.SubscriptionList;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDataDao;
import com.followit.yigitozkavci.follow_it.network.Channel;
import com.followit.yigitozkavci.follow_it.tasks.AddSubscriptionDataTask;
import com.followit.yigitozkavci.follow_it.tasks.DeleteSubscriptionDataTask;
import com.followit.yigitozkavci.follow_it.tasks.DeleteSubscriptionsTask;
import com.followit.yigitozkavci.follow_it.tasks.GetSubscriptionsTask;
import com.followit.yigitozkavci.follow_it.utils.Triple;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    private String username;
    private List<Subscription> subscriptions;
    private ArrayAdapter<String> adapter;
    private ListView subscriptionList;
    protected static final String SUBSCRIPTIONS_EXTRA_KEY = "subscriptions";

    private SubscriptionDao subscriptionDao;
    private SubscriptionDataDao subscriptionDataDao;

    protected static FIDatabase db; // Wow, this is a dangerous act for sure.

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

        final Activity self = this;

        subscriptionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Subscription entry = ((SubscriptionList) parent.getAdapter()).getItemAtPos(position);
                Intent intent = new Intent(self, SingleSubscriptionActivity.class);
                intent.putExtra("sub_id", entry.getId());
                startActivity(intent);
            }
        });
        // Initialize persistent DB
        db = Room.databaseBuilder(getApplicationContext(), FIDatabase.class, "fi")
                .fallbackToDestructiveMigration()
                .build();
        this.subscriptionDao = db.subscriptionDao();
        this.subscriptionDataDao = db.subscriptionDataDao();

        // Enable this to flush the database
        new DeleteSubscriptionsTask(subscriptionDao).execute();
        new DeleteSubscriptionDataTask(subscriptionDataDao).execute();

        MainActivity.conn.setDataListener(new TaskListener<Triple<Channel, String, ArrayList<String>>>() {
            @Override
            public void onFinished(Triple<Channel, String, ArrayList<String>> response) {
                final Channel respChan = response.left;
                final String respUser = response.middle;
                final List<String> respData = response.right;

                new GetSubscriptionsTask(subscriptionDao) {
                    @Override
                    protected void onPostExecute(List<Subscription> subs) {
                        Log.d("WOW", "Received a data block from channel " + respChan + " for the user " + respUser);
                        for(Subscription s : subs) {
                            if(s.getChannel() == respChan && s.getUsername().equals(respUser)) {
                                new AddSubscriptionDataTask(subscriptionDataDao, s, respData) {
                                    @Override
                                    protected void onPostExecute(Void _params) {
                                        Log.d("WOW", "Added subscription data to database...");
                                    }
                                }.execute();
                            }
                        }
                    }
                }.execute();
            }
        });
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