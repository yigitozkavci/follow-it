package com.followit.yigitozkavci.follow_it.exceptions;

import com.followit.yigitozkavci.follow_it.activities.DashboardActivity;

/**
 * Created by yigitozkavci on 30.11.2017.
 */
public class ProtocolException extends Exception {
    private String message;

    public ProtocolException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
