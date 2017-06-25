package com.szabto.lazacetlapp.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.responses.BroadcastResponse;

import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends NetworkActivity implements Callback<BroadcastResponse> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

        final SplashScreenActivity act = this;
        new Thread(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                api.getService().getBroadcast().enqueue(act);
            }
        }).start();
    }

    @Override
    int getSnackBarParentId() {
        return 0;
    }

    @Override
    public void onResponse(Call<BroadcastResponse> call, Response<BroadcastResponse> response) {
        BroadcastResponse resp = response.body();

        Intent i = new Intent(this, MainActivity.class);
        if( resp != null ) {
            i.putExtra("message", resp.getMessage());
            i.putExtra("hasBroadcast", resp.isHasBroadcast());
        }
        startActivity(i);
    }

    @Override
    public void onFailure(Call<BroadcastResponse> call, Throwable t) {

    }
}
