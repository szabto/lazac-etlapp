package com.szabto.lazacetlapp.helpers;

import android.content.Context;
import android.util.Log;

import com.szabto.lazacetlapp.api.LazacApi;
import com.szabto.lazacetlapp.helpers.Utils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kubu on 4/5/2017.
 */

public class ApiHelper {
    private final LazacApi api;
    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = null;
    private static final String TAG = ApiHelper.class.getName();

    public ApiHelper(final Context ctx) {
        REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                HttpUrl url = originalResponse.request().url();
                Log.i(TAG, "Requesting: " + url);
                if( "gettoday".equals(url.queryParameter("action")) ||
                        "getday".equals(url.queryParameter("action")) ||
                        "getbroadcast".equals(url.queryParameter("action")) ) {
                    Log.d(TAG, "Overriding cache policy.");
                    if (Utils.isNetworkAvailable(ctx)) {
                        int maxAge = 60;
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, max-age=" + maxAge)
                                .build();
                    } else {
                        int maxStale = 60 * 60 * 24;
                        return originalResponse.newBuilder()
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    }
                }
                else {
                    Log.d(TAG, "Not overriding cache policy.");
                    return originalResponse;
                }
            }
        };

        File httpCacheDirectory = new File(ctx.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();

                            Request.Builder builder = originalRequest.newBuilder();

                            Request newRequest = builder.build();
                            okhttp3.Response r = chain.proceed(newRequest);
                            return r;
                        }
                    })
                    .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                    .cache(cache)
                    .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.szabto.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.api = retrofit.create(LazacApi.class);
    }

    public LazacApi getService() {
        return this.api;
    }
}
