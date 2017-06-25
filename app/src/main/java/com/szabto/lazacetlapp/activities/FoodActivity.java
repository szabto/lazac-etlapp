package com.szabto.lazacetlapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.responses.FoodResponse;
import com.szabto.lazacetlapp.api.responses.MenusResponse;
import com.szabto.lazacetlapp.helpers.ApiHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends NetworkActivity {
    private static final String TAG = FoodActivity.class.getSimpleName();

    private CoordinatorLayout progressLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.food_header);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        Bundle b = getIntent().getExtras();
        String name;
        int id;

        try {
            name = b.getString("food_name");
        } catch (Exception e) {
            name = "";
            Log.e(TAG, "Error occurred while getting name from intent.", e);
        }
        try {
            id = b.getInt("food_id");
        } catch (Exception e) {
            id = -1;
            Log.e(TAG, "Error occurred while getting id from intent.", e);
        }

        progressLayout = (CoordinatorLayout) findViewById(R.id.food_menu_progress);

        setTitle(name);
        loadFood(id);
    }

    @Override
    int getSnackBarParentId() {
        return R.id.food_activity_layout;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(R.anim.slide_in_back, R.anim.slide_out_back);
    }

    private void loadFood(int id) {
        final Activity _this = this;
        api.getService().getFood(id).enqueue(new Callback<FoodResponse>() {
            @Override
            public void onResponse(Call<FoodResponse> call, Response<FoodResponse> response) {
                FoodResponse fr = response.body();

                if( fr != null )  {
                    Log.d(TAG, "Food arrived");
                    progressLayout.setVisibility(View.GONE);

                    if( fr.getDetails().getImageUrl() != null ) {
                        Picasso.with(_this).load(fr.getDetails().getImageUrl()).into(imageView);
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodResponse> call, Throwable t) {
                Log.d(TAG, "Food failed");
                progressLayout.setVisibility(View.GONE);
            }
        });
    }
}
