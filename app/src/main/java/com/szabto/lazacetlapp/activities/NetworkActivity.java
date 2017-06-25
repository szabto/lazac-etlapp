package com.szabto.lazacetlapp.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.helpers.Utils;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by kubu on 4/8/2017.
 */

public abstract class NetworkActivity extends AppCompatActivity {
    private static final String TAG = NetworkActivity.class.getSimpleName();

    private Snackbar noNetworkIndicator = null;
    private Subscription networkConn;

    protected ApiHelper api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = new ApiHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        networkConn = ReactiveNetwork.observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Connectivity>() {
                    @Override
                    public void call(Connectivity connectivity) {
                        Log.d(TAG, "Connectivity change received: " + connectivity.getState().name());
                        checkNetwork();
                    }
                });
    }

    protected void checkNetwork() {
        if (!Utils.isNetworkAvailable(this)) {
            noNetworkIndicator = Snackbar.make(findViewById(getSnackBarParentId()), R.string.no_network, Snackbar.LENGTH_INDEFINITE);
            noNetworkIndicator.show();
        } else {
            if (noNetworkIndicator != null) {
                noNetworkIndicator.dismiss();
                noNetworkIndicator = null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (networkConn != null && !networkConn.isUnsubscribed()) {
            networkConn.unsubscribe();
        }
    }

    abstract int getSnackBarParentId();
}
