package com.followit.yigitozkavci.follow_it;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.io.PrintWriter;

public class DisplayMessageActivity extends AppCompatActivity {
    private FIConnection fc;
    private String username;
    private Button registerBtn;
    private Button subscribeFacebookButton;
    private Button subscribeTwitterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        this.username = intent.getStringExtra(MainActivity.CONNECTION_USERNAME);

        registerBtn = (Button) findViewById(R.id.register_button);
        subscribeFacebookButton = (Button) findViewById(R.id.subscribe_facebook_button);
        subscribeFacebookButton.setEnabled(false);

        subscribeTwitterButton = (Button) findViewById(R.id.subscribe_twitter_button);
        subscribeTwitterButton.setEnabled(false);

        TextView textView = findViewById(R.id.textView);
        textView.setText(this.username);

        try {
            Socket socket = new Socket("192.168.0.23", 4444);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            fc = new FIConnection(in, out);
            registerBtn.setEnabled(true);
        } catch (IOException e) {
            Log.d("ERROR3", "Socket exception", e);
        }
    }

    public void handleRegister(View view) {
        this.fc.register(this.username);
        this.registerBtn.setEnabled(false);
        this.subscribeFacebookButton.setEnabled(true);
        this.subscribeTwitterButton.setEnabled(true);
    }

    public void handleFacebookSubscription(View view) {
        this.fc.subscribe(Channel.FACEBOOK);
        this.subscribeFacebookButton.setEnabled(false);
    }

    public void handleTwitterSubscription(View view) {
        this.fc.subscribe(Channel.TWITTER);
        this.subscribeTwitterButton.setEnabled(false);
    }
}
