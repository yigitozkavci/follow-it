package com.followit.yigitozkavci.follow_it.activities;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.followit.yigitozkavci.follow_it.database.FIDatabase;
import com.followit.yigitozkavci.follow_it.models.SubscriptionDao;
import com.followit.yigitozkavci.follow_it.network.Channel;
import com.followit.yigitozkavci.follow_it.network.FIConnection;
import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.followit.yigitozkavci.follow_it.adapters.SubscriptionList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private FIConnection fc;
    private String username;
    private Button registerBtn;
    private Button subscribeFacebookButton;
    private Button subscribeTwitterButton;
    private ArrayList<Subscription> subscriptions;
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

        try {
            FIDatabase db = Room.databaseBuilder(getApplicationContext(), FIDatabase.class, "fi").build();
            this.subscriptionDao = db.subscriptionDao();
            this.fc = createConnection();
        } catch (IOException e) {
            Log.d("ERROR3", "Socket exception", e);
        }
    }

    private FIConnection createConnection() throws IOException {
        Socket socket = new Socket("192.168.0.23", 4444);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        return new FIConnection(in, out, this.subscriptions);
    }

    // Ask this to Irmak, this is a bad practice
    private void addSubscription() {
        Subscription sub = new Subscription(Channel.TWITTER, "Yiggy");
        subscriptionDao.insertSubscription(sub);
        this.subscriptions.add(sub);
        this.adapter = new SubscriptionList(this, this.subscriptions);
        subscriptionList.setAdapter(this.adapter);
    }

    public void handleAddSubscription(View view) {
        Intent intent = new Intent(this, AddSubscriptionActivity.class);

        intent.putExtra(SUBSCRIPTIONS_EXTRA_KEY, this.subscriptions);
        startActivity(intent);
    }
}