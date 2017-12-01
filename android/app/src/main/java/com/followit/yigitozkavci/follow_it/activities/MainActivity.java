package com.followit.yigitozkavci.follow_it.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.followit.yigitozkavci.follow_it.R;
import com.followit.yigitozkavci.follow_it.activities.DashboardActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public static final String CONNECTION_USERNAME = "CONNECTION_USERNAME";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(CONNECTION_USERNAME, message);
        startActivity(intent);
    }
}
