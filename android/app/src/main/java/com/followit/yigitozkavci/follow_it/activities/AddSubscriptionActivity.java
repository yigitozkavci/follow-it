package com.followit.yigitozkavci.follow_it.activities;

import android.app.Activity;
import android.app.AlertDialog;
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

    public void handleAddTwitterSubscription(View view) {
        final Activity self = this;
        final String user = ((EditText) findViewById(R.id.add_twitter_sub_usr_txt)).getText().toString();
        MainActivity.conn.subscribe(Channel.TWITTER, user, new TaskListener<Boolean>() {
            @Override
            public void onFinished(Boolean result) {
                if(!result) {
                    setResult(0);
                    finish();
                }
                else {
                    new AddSubscriptionTask(subscriptionDao, new Subscription(Channel.TWITTER, user)) {
                        @Override
                        protected void onPostExecute(Void _v) {
                            setResult(1);
                            finish();
                        }
                    }.execute();
                }
            }
        });
    }
}
