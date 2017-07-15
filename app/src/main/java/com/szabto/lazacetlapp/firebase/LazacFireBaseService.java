package com.szabto.lazacetlapp.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.szabto.lazacetlapp.api.responses.ResponseBase;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.helpers.FavoriteHelper;
import com.szabto.lazacetlapp.structures.Command;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 3/24/17.
 */

public class LazacFireBaseService extends FirebaseInstanceIdService {
    private static final String TAG = LazacFireBaseService.class.toString();

    @Override
    public void onCreate() {
        super.onCreate();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        ApiHelper api = new ApiHelper(this);
        api.getService().registerUUID(FavoriteHelper.getInstance().getUserToken(), refreshedToken).enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {

            }
        });
    }
}
