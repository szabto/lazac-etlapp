package com.szabto.lazacetlapp.api.responses;

/**
 * Created by kubu on 6/25/2017.
 */

public class BroadcastResponse extends ResponseBase {
    private String message;
    private boolean hasBroadcast;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasBroadcast() {
        return hasBroadcast;
    }

    public void setHasBroadcast(boolean hasBroadcast) {
        this.hasBroadcast = hasBroadcast;
    }
}
