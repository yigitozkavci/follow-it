package com.followit.yigitozkavci.follow_it.activities;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.database.FIDatabase;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yigitozkavci on 30.11.2017.
 */

public class AddSubscriptionActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subscription);



        final SubscriptionDao dao = Room.databaseBuilder(getApplicationContext(), FIDatabase.class, "fi").build().subscriptionDao();
        new AsyncTask<Void, Void, List<Subscription>>() {
            @Override
            protected List<Subscription> doInBackground(Void... params) {
                return dao.getAll();
            }

            protected void onPostExecute(ArrayList<Subscription> subs) {
                Log.d("SUB", subs.toString());

            }
        }.execute();
    }
}
