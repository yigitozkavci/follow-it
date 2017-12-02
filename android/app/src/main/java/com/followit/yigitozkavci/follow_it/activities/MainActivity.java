package com.followit.yigitozkavci.follow_it.activities;

import android.arch.persistence.room.Delete;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.activities.DashboardActivity;
import com.followit.yigitozkavci.follow_it.network.FIConnection;
import com.followit.yigitozkavci.follow_it.tasks.DeleteSubscriptionsTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static final String CONNECTION_USERNAME = "CONNECTION_USERNAME";
    protected static FIConnection conn;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Button registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setEnabled(false);

        EditText editText = (EditText) findViewById(R.id.editText);
        String username = editText.getText().toString();

        final Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        // Initialize socket connection
        TaskListener<Void> listener = new TaskListener<Void>() {
            @Override
            public void onFinished(Void _param) {
                startActivity(dashboardIntent);
            }
        };

        this.conn = createSocketConn();
        this.conn.register(username, listener);
    }

    private FIConnection createSocketConn() {
        try {
            Socket socket = new Socket("192.168.0.23", 4444);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            return new FIConnection(in, out);
        } catch (IOException e) {
            Log.e("ERROR3", "Socket exception", e);
            return null;
        }
    }
}
