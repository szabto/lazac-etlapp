package com.szabto.lazacetlapp.activities;

import android.os.Bundle;

import com.szabto.lazacetlapp.R;

public class MenuActivity extends NetworkActivity {
    private static final String TAG = MenuActivity.class.toString();

    private int menu_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        Bundle b = getIntent().getExtras();
        if (b != null)
            menu_id = b.getInt("menu_id");

        setTitle(b.getString("date"));

        /*if (value == null) {
            Snackbar.make(findViewById(R.id.main_container), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
            finish();
            return;
        }*/
    }

    public int getMenuId() {
        return this.menu_id;
    }

    @Override
    int getSnackBarParentId() {
        return R.id.activity_menu_refresh_layout;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
