package com.followit.yigitozkavci.follow_it;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;

public class FIConnection {
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private boolean isRegistered;

    public FIConnection(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        this.isRegistered = false;
    }

    public boolean register(String username) {
        this.out.println("register: " + username);
        try {
            if(readLine().equals("true")) {
                this.username = username;
                this.isRegistered = true;
                Log.d("REGISTRATION", "Registration completed");
                return true;
            }
        } catch (IOException e) {
            Log.e("REGISTRATION", "IOException", e);
            return false;
        }
        return false;
    }

    public boolean subscribe(Channel channel) {
        if(!this.isRegistered) {
            Log.e("SUBSCRIPTION", "User is not registered while attempting to subscribe");
            return false;
        }
        this.out.println("subscribe: " + channel + " " + this.username);
        try {
            if (readLine().equals("true")) {
                return true;
            }
        } catch(IOException e) {
            Log.e("SUBSCRIPTION", "IOException", e);
            return false;
        }
        return false;
    }
    private String readLine () throws IOException {
        String line;

        while((line = this.in.readLine()) != null) {
            return line;
        }
        return null;
    }
}
