package com.szabto.lazacetlapp.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.responses.BroadcastResponse;
import com.szabto.lazacetlapp.api.responses.ResponseBase;
import com.szabto.lazacetlapp.api.structures.FavoriteItem;
import com.szabto.lazacetlapp.helpers.FavoriteHelper;
import com.szabto.lazacetlapp.helpers.UUIDHelper;
import com.szabto.lazacetlapp.structures.Command;

import java.util.List;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends NetworkActivity implements Callback<BroadcastResponse> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FavoriteHelper.getInstance().setUserToken(UUIDHelper.id(this));

        ImageView mImageViewFilling = (ImageView) findViewById(R.id.imageview_animation_list_filling);
        ((AnimationDrawable) mImageViewFilling.getBackground()).start();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                registerUUID(new Command() {
                    @Override
                    public void execute() {
                        getFavorites(new Command() {
                            @Override
                            public void execute() {
                                getBroadcastMessage();
                            }
                        });
                    }
                });
            }
        });
    }

    private void getBroadcastMessage() {
        final SplashScreenActivity act = this;
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                api.getService().getBroadcast().enqueue(act);
            }
        });
    }

    private void registerUUID(final Command callback) {
        api.getService().registerUUID(FavoriteHelper.getInstance().getUserToken(), FirebaseInstanceId.getInstance().getToken()).enqueue(new Callback<ResponseBase>() {
            @Override
            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                callback.execute();
            }

            @Override
            public void onFailure(Call<ResponseBase> call, Throwable t) {
                callback.execute();
            }
        });
    }

    private void getFavorites(final Command callback) {
        api.getService().getFavoritedFoods(FavoriteHelper.getInstance().getUserToken()).enqueue(new Callback<List<FavoriteItem>>() {
            @Override
            public void onResponse(Call<List<FavoriteItem>> call, Response<List<FavoriteItem>> response) {
                List<FavoriteItem> resp = response.body();
                if(resp != null ) {
                    for(FavoriteItem i : resp) {
                        FavoriteHelper.getInstance().addToFavorites(i.getId());
                    }
                }

                callback.execute();
            }

            @Override
            public void onFailure(Call<List<FavoriteItem>> call, Throwable t) {
                callback.execute();
            }
        });
    }

    @Override
    int getSnackBarParentId() {
        return R.id.splash_root;
    }

    @Override
    public void onResponse(Call<BroadcastResponse> call, Response<BroadcastResponse> response) {
        BroadcastResponse resp = response.body();

        Intent i = new Intent(this, MainActivity.class);
        if( resp != null ) {
            i.putExtra("message", resp.getBroadcastMessage());
            i.putExtra("hasBroadcast", resp.isHasBroadcast());
        }
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    public void onFailure(Call<BroadcastResponse> call, Throwable t) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
