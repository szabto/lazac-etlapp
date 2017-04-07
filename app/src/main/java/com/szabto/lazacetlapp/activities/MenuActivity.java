package com.szabto.lazacetlapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.DayResponse;
import com.szabto.lazacetlapp.api.FoodCategory;
import com.szabto.lazacetlapp.api.FoodItem;
import com.szabto.lazacetlapp.api.HeaderItem;
import com.szabto.lazacetlapp.api.MenuItem;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.helpers.SqliteHelper;
import com.szabto.lazacetlapp.helpers.Utils;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.item.ItemAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.toString();

    private ArrayList<Object> dataModels;
    private RecyclerView listView;
    private static ItemAdapter adapter;

    private Snackbar noNetworkIndicator = null;

    private ApiHelper api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        Bundle b = getIntent().getExtras();
        final String value; // or other values
        if (b != null)
            value = b.getString("menu_id");
        else
            value = null;

        setTitle(b.getString("date"));

        if (value == null) {
            Snackbar.make(findViewById(R.id.main_listview), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
            finish();
            return;
        }

        api = new ApiHelper(this);

        listView = (RecyclerView) findViewById(R.id.item_listview);
        dataModels = new ArrayList<>();
        adapter = new ItemAdapter(dataModels);

        final Activity act = this;

        adapter.setItemClickListener(new ClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                FoodItem dataModel = (FoodItem) dataModels.get(position);
                if (dataModel != null) {
                    Intent intent = new Intent(act, FoodActivity.class);
                    startActivity(intent);
                }
            }
        });

        final StickyLayoutManager layoutManager = new StickyLayoutManager(this, adapter);

        listView.setLayoutManager(layoutManager);

        listView.setAdapter(adapter);

        ReactiveNetwork.observeNetworkConnectivity(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Connectivity>() {
                    @Override public void call(Connectivity connectivity) {
                        checkNetwork();
                    }
                });

        checkNetwork();
        loadMenu(value);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkNetwork();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void checkNetwork() {
        if( !Utils.isNetworkAvailable(this) ) {
            noNetworkIndicator = Snackbar.make(findViewById(R.id.activity_main_swipe_refresh_layout), R.string.no_network, Snackbar.LENGTH_INDEFINITE);
            noNetworkIndicator.show();
        }
        else {
            if( noNetworkIndicator != null ) {
                noNetworkIndicator.dismiss();
                noNetworkIndicator = null;
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadMenu(final String menuId) {
        final SqliteHelper database = new SqliteHelper(this);
        final Activity _this = this;
        api.getService().getDay(Integer.valueOf(menuId)).enqueue(new Callback<DayResponse>() {
            @Override
            public void onResponse(Call<DayResponse> call, Response<DayResponse> response) {
                DayResponse dr = response.body();
                if (dr != null) {
                    dataModels.clear();
                    database.viewMenu(menuId);

                    LinearLayout pb = (LinearLayout) findViewById(R.id.progressbar_view);
                    pb.setVisibility(View.GONE);

                    try {
                        for (FoodCategory cat : dr.getData()) {
                            dataModels.add(new HeaderItem(cat.getName()));

                            for (FoodItem food : cat.getItems()) {
                                dataModels.add(food);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Error occurred while loading menu", e);
                    }

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DayResponse> call, Throwable t) {

                Snackbar.make(findViewById(R.id.main_listview), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                    super.onDismissed(transientBottomBar, event);
                    checkNetwork();
                    }
                }).show();
                Log.e(TAG, "Error", t);
            }
        });
    }

}
