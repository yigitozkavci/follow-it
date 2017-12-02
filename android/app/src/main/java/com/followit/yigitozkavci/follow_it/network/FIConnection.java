package com.followit.yigitozkavci.follow_it.network;

import android.util.Log;

import com.followit.yigitozkavci.follow_it.activities.TaskListener;
import com.followit.yigitozkavci.follow_it.exceptions.ProtocolException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.HashMap;

class SocketWatcher extends Thread {
    private BufferedReader in;
    private FIConnection conn;

    public SocketWatcher(BufferedReader in, FIConnection conn) {
        this.in = in;
        this.conn = conn;
    }

    public void run() {
        String line;
        while((line = this.readUnsafe()) != null) {
            try {
                this.processMessage(new Gson().fromJson(line, FromServerMessage.class));
            } catch(ProtocolException e) {
                Log.e("PROTOCOL_ERROR", "Error while processing protocol messages", e);
            }
        }
    }

    private void processMessage(FromServerMessage respMsg) throws ProtocolException {
        switch(respMsg.getTag()) {
            case ERROR:
                throw new ProtocolException("Received error from server: " + respMsg.getErrorMsg());
            case REGISTER_ACCEPT:
                this.conn.completeRegistration();
                break;
            case SUBSCRIPTION_ACCEPT:
                this.conn.setCanSubscribe(true);
                break;
            case TWEETS:
                break;
            case FB_POSTS:
                break;
            default:
                throw new ProtocolException("Unexpected message with tag " + respMsg.getTag() + " and data " + respMsg.getData());
        }
    }

    private String readUnsafe() {
        try {
            return this.in.readLine();
        } catch(IOException e) {
            Log.e("CRITICAL", "Exception while reading from socket", e);
            return null;
        }
    }
}

public class FIConnection {
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private boolean isRegistered;
    private boolean canSubscribe;

    // Task listeners
    private TaskListener registerListener;

    public FIConnection(BufferedReader in, PrintWriter out) {
        this.in = in;
        this.out = out;
        this.isRegistered = false;
        this.canSubscribe = true;
        new SocketWatcher(in, this).start();
    }

    public void register(String username, TaskListener listener) {
        sendMsg(ToServerMessage.Tag.REGISTER, "username", username);
        this.registerListener = listener;
    }

    public void subscribe(Channel channel, String user) {
        if(this.canSubscribe) {
            sendMsg(ToServerMessage.Tag.SUBSCRIBE, "channel", channel.toString(), "user", user);
            this.canSubscribe = false;
        } else {
            // TODO: Should this.class or the UI handle this case?
        }
    }

    private String readLine() throws IOException {
        String line;
        while((line = this.in.readLine()) != null) {
            return line;
        }
        return null;
    }

    private void sendMsg(ToServerMessage.Tag tag) {
        HashMap<String, Object> msgObj = new HashMap<>();
        msgObj.put("tag", tag);
        sendMessageData(msgObj);
    }

    private void sendMsg(ToServerMessage.Tag tag, String key, Object val) {
        HashMap<String, Object> msgObj = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put(key, val);
        msgObj.put("tag", tag);
        msgObj.put("data", data);
        sendMessageData(msgObj);
    }

    private void sendMsg(ToServerMessage.Tag tag, String key, Object val, String key2, Object val2) {
        HashMap<String, Object> msgObj = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put(key, val);
        data.put(key2, val2);
        msgObj.put("tag", tag);
        msgObj.put("data", data);
        sendMessageData(msgObj);
    }

    private void sendMessageData(HashMap<String, Object> msgData) {
        this.out.println(new Gson().toJson(msgData));
        Log.d("SENDING_MESSAGE", msgData.toString());
    }

    public void setCanSubscribe(boolean canSubscribe) {
        this.canSubscribe = canSubscribe;
    }

    public void completeRegistration() {
        Log.d("REGISTRATION", "Completed");
        this.isRegistered = true;
        this.registerListener.onFinished();
    }
}
