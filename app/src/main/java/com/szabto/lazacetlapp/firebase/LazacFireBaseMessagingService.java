package com.szabto.lazacetlapp.firebase;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class LazacFireBaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = LazacFireBaseService.class.toString();

    public LazacFireBaseMessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Intent inte = new Intent("menu-arrive");
        inte.putExtra("menu_id", remoteMessage.getData().get("menu_id"));
        LocalBroadcastManager.getInstance(this).sendBroadcast(inte);
    }
}
