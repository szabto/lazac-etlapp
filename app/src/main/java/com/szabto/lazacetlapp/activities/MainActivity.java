package com.szabto.lazacetlapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.google.firebase.messaging.FirebaseMessaging;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.HeaderItem;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.helpers.SqliteHelper;
import com.szabto.lazacetlapp.api.MenusResponse;
import com.szabto.lazacetlapp.helpers.Utils;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.menu.MenuAdapter;
import com.szabto.lazacetlapp.api.MenuItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Object> dataModels;
    private RecyclerView listView;
    private static MenuAdapter adapter;
    private boolean isLoading = false;
    private int start = 0;
    private boolean hasMore = false;
    private int currentWeek = -1;

    private Snackbar noNetworkIndicator = null;

    private ApiHelper api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.api = new ApiHelper(this);

        FirebaseMessaging.getInstance().subscribeToTopic("newmenu");

        listView = (RecyclerView) findViewById(R.id.main_listview);
        dataModels = new ArrayList<Object>();

        adapter = new MenuAdapter(dataModels);

        final MainActivity act = this;

        listView.setAdapter(adapter);

        final StickyLayoutManager layoutManager = new StickyLayoutManager(this, adapter);

        listView.setLayoutManager(layoutManager);

        checkNetwork();

        adapter.setItemClickListener(new ClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                MenuItem dataModel = (MenuItem)dataModels.get(position);
                if( dataModel != null ) {
                    Intent intent = new Intent(act, MenuActivity.class);
                    intent.putExtra("menu_id", String.valueOf(dataModel.getId()));
                    intent.putExtra("date", dataModel.getDate());
                    startActivity(intent);
                }
            }
        });

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (layoutManager.getChildCount() > 0) {
                    int indexOfLastItemViewVisible = layoutManager.getChildCount() -1;
                    View lastItemViewVisible = layoutManager.getChildAt(indexOfLastItemViewVisible);
                    int adapterPosition = layoutManager.getPosition(lastItemViewVisible);
                    boolean isLastItemVisible = (adapterPosition == adapter.getItemCount() -1);

                    if (isLastItemVisible && !isLoading && hasMore) {
                        loadMenus(true);
                    }
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadMenus(false);
            }
        });

        ReactiveNetwork.observeNetworkConnectivity(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Connectivity>() {
                @Override public void call(Connectivity connectivity) {
                    checkNetwork();
                }
            });

        loadMenus(false);
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        loadMenus(false);
        }
    };

    @Override
    protected void onResume() {
        final SqliteHelper database = new SqliteHelper(this);
        super.onResume();

        checkNetwork();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,
                        new IntentFilter("menu-arrive"));

        for (Object md : dataModels) {
            if( md instanceof MenuItem ) {
                MenuItem mi = (MenuItem)md;
                int id = mi.getId();
                //mi.setNew(!database.isViewedMenu(String.valueOf(id)));
            }
        }
        adapter.notifyDataSetChanged();
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
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_about:
                startActivity(new Intent(this, AboutAcitivity.class));
                break;

            /*case R.id.menu_item_place:
                startActivity(new Intent(this, PlaceActivity.class));
                break;*/

            /*case R.id.menu_item_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;*/
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.list_menu, menu);
        return true;
    }

    private void loadMenus(final boolean more) {
        if (isLoading) return;

        mSwipeRefreshLayout.setRefreshing(true);
        isLoading = true;
        if (!more) {
            start = 0;
            currentWeek = -1;
            hasMore = true;
        }

        final SqliteHelper database = new SqliteHelper(this);

        api.getService().getMenuList(start).enqueue(new Callback<MenusResponse>() {
            @Override
            public void onResponse(Call<MenusResponse> call, Response<MenusResponse> response) {
                Log.d(TAG, "Loaded menus");

                if (!more)
                    dataModels.clear();

                isLoading = false;
                mSwipeRefreshLayout.setRefreshing(false);
                MenusResponse mr = response.body();

                hasMore = mr.isThere_more();

                for (com.szabto.lazacetlapp.api.MenuItem m : mr.getList()) {

                    if (m.getWeekNum() != currentWeek)  {
                        String date = m.getDate();
                        date = date.substring(0, 4);
                        dataModels.add(new HeaderItem(date + " - " + m.getWeekNum() + ". hét"));
                        currentWeek = m.getWeekNum();
                    }

                    dataModels.add(m);
                }
                start += mr.getList().size();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MenusResponse> call, Throwable t) {
                isLoading = false;
                hasMore = true;
                mSwipeRefreshLayout.setRefreshing(false);
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
