package com.szabto.lazacetlapp.activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.Api;
import com.szabto.lazacetlapp.helpers.SqliteHelper;
import com.szabto.lazacetlapp.structures.ResponseHandler;
import com.szabto.lazacetlapp.structures.menu.MenuDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView iv = (ImageView)findViewById(R.id.food_header);
        new DownloadImageTask(iv).execute("http://www.nosalty.hu/files/imagecache/recept/recept_kepek/harmati_lecso.jpg");
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Drawable doInBackground(String... urls) {
            String urldisplay = urls[0];
            Drawable mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = Drawable.createFromStream(in, "src");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Drawable result) {
            bmImage.setImageDrawable(result);
        }
    }


}
