package com.szabto.lazacetlapp.api.responses;

/**
 * Created by kubu on 4/4/2017.
 */

public class ResponseBase {
    private boolean success;

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBroadcastMessage() {
        return message;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.message = broadcastMessage;
    }

}
