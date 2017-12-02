package com.followit.yigitozkavci.follow_it.network;

import com.followit.yigitozkavci.follow_it.exceptions.ProtocolException;

import java.util.HashMap;

/**
 * Created by yigitozkavci on 2.12.2017.
 */

public class ToServerMessage {
    public enum Tag {
        REGISTER, SUBSCRIBE;
    }

    private Tag tag;
    private HashMap<String, Object> data;

    public Tag getTag() {
        return this.tag;
    }

    public Object getData() {
        return this.data;
    }

    public String getErrorMsg() throws ProtocolException {
        if(this.data == null || !this.data.containsKey("message")) {
            throw new ProtocolException("Tag is ERROR but data does not contain `message` key");
        }
        return this.data.get("message").toString();
    }
}

