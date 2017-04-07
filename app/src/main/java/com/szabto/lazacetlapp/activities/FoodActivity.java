package com.szabto.lazacetlapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.szabto.lazacetlapp.R;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView iv = (ImageView)findViewById(R.id.food_header);

        Picasso.with(this).load("http://www.nosalty.hu/files/imagecache/recept/recept_kepek/harmati_lecso.jpg").into(iv);
    }

}
