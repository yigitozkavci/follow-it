package com.followit.yigitozkavci.follow_it.network;

import android.util.Log;

import com.followit.yigitozkavci.follow_it.exceptions.ProtocolException;
import com.followit.yigitozkavci.follow_it.models.Subscription;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FIConnection {
    private BufferedReader in;
    private PrintWriter out;
    private String username;
    private boolean isRegistered;
    private ArrayList<Subscription> subscriptions;

    public FIConnection(BufferedReader in, PrintWriter out, ArrayList<Subscription> subscriptions) {
        this.in = in;
        this.out = out;
        this.isRegistered = false;
        this.subscriptions = subscriptions;
    }

    public boolean register(String username) {
        sendMsg(Tag.REGISTER, "username", username);
        try {
            recvTag(Tag.REGISTER_ACCEPT);
            this.isRegistered = true;
            this.username = username;
            return true;
        } catch (IOException e) {
            Log.e("REGISTRATION", "IOException", e);
        } catch(ProtocolException e) {
            Log.e("REGISTRATION", "ProtocolException", e);
        }
        return false;
    }

    public boolean subscribe(Channel channel, String user) {
        sendMsg(Tag.SUBSCRIBE, "channel", channel.toString(), "user", user);
        try {
            recvTag(Tag.SUBSCRIPTION_ACCEPT);
            this.subscriptions.add(new Subscription(channel, user));
        } catch(ProtocolException e) {
            Log.e("SUBSCRIPTION", "ProtocolException", e);
        } catch(IOException e) {
            Log.e("SUBSCRIPTION", "IOException", e);
            return false;
        }
        return false;
    }

    private String readLine() throws IOException {
        String line;
        while((line = this.in.readLine()) != null) {
            return line;
        }
        return null;
    }

    private void recvTag(Tag expectedTag) throws IOException, ProtocolException {
        String msg = readLine();
        Log.d("RECEIVED_MESSAGE", msg);
        ServerMessage respMsg = new Gson().fromJson(msg, ServerMessage.class);
        if(respMsg.getTag() == expectedTag) {
            return;
        } else if(respMsg.getTag() == Tag.ERROR) {
            throw new ProtocolException("Received error from server: " + respMsg.getErrorMsg());
        } else if(respMsg.getTag().isSubscribedDataTag()) {
            processDataMessage(respMsg);
        } else {
            throw new ProtocolException("Unexpected message with tag " + respMsg.getTag() + " and data " + respMsg.getData());
        }
    }

    private void processDataMessage(ServerMessage msg) {
        // TODO: Implement
    }

    private void sendMsg(Tag tag) {
        HashMap<String, Object> msgObj = new HashMap<>();
        msgObj.put("tag", tag);
        sendMessageData(msgObj);
    }

    private void sendMsg(Tag tag, String key, Object val) {
        HashMap<String, Object> msgObj = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        data.put(key, val);
        msgObj.put("tag", tag);
        msgObj.put("data", data);
        sendMessageData(msgObj);
    }

    private void sendMsg(Tag tag, String key, Object val, String key2, Object val2) {
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
}
