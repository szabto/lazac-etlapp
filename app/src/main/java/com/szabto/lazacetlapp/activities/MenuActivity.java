package com.szabto.lazacetlapp.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.szabto.lazacetlapp.api.Api;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.helpers.SqliteHelper;
import com.szabto.lazacetlapp.structures.item.ItemAdapter;
import com.szabto.lazacetlapp.structures.item.ItemDataModel;
import com.szabto.lazacetlapp.structures.ResponseHandler;
import com.szabto.lazacetlapp.structures.menu.MenuDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = MenuActivity.class.toString();

    ArrayList<ItemDataModel> dataModels;
    ListView listView;
    private static ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        setTitle(getString(R.string.loading_menu));

        Bundle b = getIntent().getExtras();
        final String value; // or other values
        if(b != null)
            value = b.getString("menu_id");
        else
            value = null;

        if( value == null ) {
            Snackbar.make(findViewById(R.id.main_listview), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).show();
            finish();
        }

        listView = (ListView)findViewById(R.id.item_listview);
        dataModels = new ArrayList<>();
        adapter = new ItemAdapter(dataModels,getApplicationContext());

        final Activity act = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemDataModel dataModel= dataModels.get(position);
                if( dataModel.isCategory() ) {
                    return;
                }
                Intent intent = new Intent(act, FoodActivity.class);
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);
        loadMenu(value);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadMenu(final String menuId ) {
        final SqliteHelper database = new SqliteHelper(this);
        Api a = new Api();
        a.getMenu(new ResponseHandler() {
            @Override
            public void onComplete(JSONObject resp) {
                dataModels.clear();

                database.viewMenu(menuId);

                LinearLayout pb = (LinearLayout)findViewById(R.id.progressbar_view);
                pb.setVisibility(View.GONE);

                try {
                    String dt = resp.getString("date");

                    setTitle(dt);

                    JSONArray data = resp.getJSONArray("data");
                    for( int i=0;i<data.length(); i++ ) {
                        JSONObject row = data.getJSONObject(i);
                        dataModels.add(new ItemDataModel(row.getString("name"), true, 0, 0));

                        JSONArray items = row.getJSONArray("items");

                        for( int a=0;a<items.length();a++) {
                            JSONObject item = items.getJSONObject(a);

                            dataModels.add(new ItemDataModel(item.getString("name"), false, item.getInt("price_high"), item.getInt("price_low")));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, menuId);
    }

}
