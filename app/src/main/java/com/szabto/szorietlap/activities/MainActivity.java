package com.szabto.szorietlap.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.szabto.szorietlap.api.Api;
import com.szabto.szorietlap.R;
import com.szabto.szorietlap.helpers.SqliteHelper;
import com.szabto.szorietlap.structures.menu.MenuAdapter;
import com.szabto.szorietlap.structures.ResponseHandler;
import com.szabto.szorietlap.structures.menu.MenuDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();

    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<MenuDataModel> dataModels;
    ListView listView;
    private static MenuAdapter adapter;
    boolean isLoading = true;
    int start = 0;
    boolean hasMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("newmenu");

        listView = (ListView)findViewById(R.id.main_listview);
        dataModels = new ArrayList<>();
        adapter= new MenuAdapter(dataModels,getApplicationContext());

        final MainActivity act = this;

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuDataModel dataModel= dataModels.get(position);

                Intent intent = new Intent(act, MenuActivity.class);
                intent.putExtra("menu_id", String.valueOf(dataModel.getId()));
                startActivity(intent);
            }
        });



        //setting  listener on scroll event of the list
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //what is the bottom item that is visible
                int lastInScreen = firstVisibleItem + visibleItemCount;

                Log.d(TAG, String.valueOf(lastInScreen) + ":"+String.valueOf(totalItemCount));

                if ((lastInScreen == totalItemCount) && !isLoading && hasMore) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    isLoading = true;
                    start = totalItemCount;

                    loadMenus(true);
                }
            }
        });


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = 0;
                hasMore = true;
                loadMenus(false);
            }
        });

        mSwipeRefreshLayout.setRefreshing(true);
        loadMenus(false);
    }

    @Override
    protected void onPause() {
        // Unregister since the activity is not visible
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

    // Handling the received Intents for the "my-integer" event
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            start = 0;
            mSwipeRefreshLayout.setRefreshing(true);
            loadMenus(false);
        }
    };

    @Override
    protected void onResume() {
        final SqliteHelper database = new SqliteHelper(this);
        super.onResume();


        // This registers mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,
                        new IntentFilter("menu-arrive"));

        for(MenuDataModel md : dataModels ) {
            int id = md.getId();
            md.setNew(!database.isViewedMenu(String.valueOf(id)));
        }
        adapter.notifyDataSetChanged();
    }

    private void loadMenus(final boolean more) {
        final Activity act = this;
        final SqliteHelper database = new SqliteHelper(this);
        Api a = new Api();
        a.getMenus(new ResponseHandler() {
            @Override
            public void onComplete(JSONObject resp) {
                mSwipeRefreshLayout.setRefreshing(false);

                if( !more )
                    dataModels.clear();
                isLoading = false;
                if( resp == null ) {
                    Snackbar.make(findViewById(R.id.main_listview), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
                    return;
                }
                Log.d(TAG, "Loaded menus");
                try {
                    hasMore = resp.getBoolean("there_more");
                    if( resp.getBoolean("success") ) {
                        JSONArray arr = resp.getJSONArray("list");
                        Log.d(TAG, "Menu count: " + String.valueOf(arr.length()));

                        for(int i=0;i<arr.length();i++) {
                            JSONObject row = arr.getJSONObject(i);

                            int id = row.getInt("id");

                            dataModels.add(new MenuDataModel(id, row.getString("date"), row.getString("posted"), row.getInt("item_count"), !database.isViewedMenu(String.valueOf(id))));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Log.d(TAG, "Success: false");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, start);
    }

}
