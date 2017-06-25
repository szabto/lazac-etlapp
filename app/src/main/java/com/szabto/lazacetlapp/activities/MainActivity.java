package com.szabto.lazacetlapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.api.responses.MenusResponse;
import com.szabto.lazacetlapp.fragments.BroadcastFragment;
import com.szabto.lazacetlapp.fragments.MenuFragment;
import com.szabto.lazacetlapp.fragments.MenuListFragment;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.EndlessScrollHelper;
import com.szabto.lazacetlapp.structures.menu.MenuAdapter;
import com.szabto.lazacetlapp.api.structures.MenuItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends NetworkActivity implements FragNavController.RootFragmentListener {
    private static final String TAG = MainActivity.class.toString();

    private FragNavController mNavController;
    private BottomNavigationView bottomView;
    private FragNavController.Builder builder;

    private boolean hasBroadcast = false;
    private String broadcastMessage = "";

    private final int INDEX_MENU_LIST = FragNavController.TAB2;
    private final int INDEX_TODAY_MENU = FragNavController.TAB1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("newmenu");

        bottomView = (BottomNavigationView) findViewById(R.id.navigation);

        builder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_container);
        builder.rootFragmentListener(this, 2);

        this.broadcastMessage = getIntent().getStringExtra("message");
        this.hasBroadcast = getIntent().getBooleanExtra("hasBroadcast", false);

        List<Fragment> fragments = new ArrayList<>(2);

        fragments.add(getMainFragment());
        fragments.add(MenuListFragment.newInstance());

        builder.rootFragments(fragments);
        mNavController = builder.build();

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_menu_list:
                        mNavController.switchTab(INDEX_MENU_LIST);
                        return true;
                    case R.id.navigation_today_menu:
                        mNavController.switchTab(INDEX_TODAY_MENU);
                        return true;

                }
                return false;
            }
        });
    }

    private Fragment getMainFragment() {
        if( hasBroadcast ) {
            Bundle bundle = new Bundle();
            bundle.putString("message", broadcastMessage);
            Fragment f = BroadcastFragment.newInstance();
            f.setArguments(bundle);
            return f;
        }
        else return MenuFragment.newInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    @Override
    int getSnackBarParentId() {
        return R.id.main_container;
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //loadMenus(false);
        }
    };


    @Override
    public void onBackPressed() {
        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("menu-arrive"));
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


    @Override
    public Fragment getRootFragment(int i) {
        switch (i) {
            case INDEX_MENU_LIST:
                return MenuListFragment.newInstance();
            case INDEX_TODAY_MENU:
                return getMainFragment();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }
}
