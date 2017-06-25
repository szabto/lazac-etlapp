package com.szabto.lazacetlapp.api.responses;

/**
 * Created by kubu on 6/25/2017.
 */

public class BroadcastResponse extends ResponseBase {
    private String broadcastMessage;
    private boolean hasBroadcast;

    public String getBroadcastMessage() {
        return broadcastMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    public boolean isHasBroadcast() {
        return hasBroadcast;
    }

    public void setHasBroadcast(boolean hasBroadcast) {
        this.hasBroadcast = hasBroadcast;
    }
}
