package com.followit.yigitozkavci.follow_it.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.database.FIDatabase;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.network.Channel;
import com.followit.yigitozkavci.follow_it.tasks.AddSubscriptionTask;

/**
 * Created by yigitozkavci on 30.11.2017.
 */

public class AddSubscriptionActivity extends Activity {
    private View view;
    private SubscriptionDao subscriptionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subscription);
        subscriptionDao = Room.databaseBuilder(getApplicationContext(), FIDatabase.class, "fi").build().subscriptionDao();
    }

    public void handleAddFacebookSubscription(View view) {
        final String user = ((EditText) findViewById(R.id.add_fb_sub_usr_txt)).getText().toString();
        MainActivity.conn.subscribe(Channel.FACEBOOK, user, new TaskListener<Void>() {
            @Override
            public void onFinished(Void _v) {
                new AddSubscriptionTask(subscriptionDao, new Subscription(Channel.FACEBOOK, user)) {
                    @Override
                    protected void onPostExecute(Void aVoid) {
                        finish();
                    }
                }.execute();
            }
        });
    }

    public void handleAddTwitterSubscription(View view) {
        final String user = ((EditText) findViewById(R.id.add_twitter_sub_usr_txt)).getText().toString();
        MainActivity.conn.subscribe(Channel.TWITTER, user, new TaskListener<Void>() {
            @Override
            public void onFinished(Void _v) {
                new AddSubscriptionTask(subscriptionDao, new Subscription(Channel.TWITTER, user)) {
                    @Override
                    protected void onPostExecute(Void _v) {
                        finish();
                    }
                }.execute();
            }
        });
    }
}
