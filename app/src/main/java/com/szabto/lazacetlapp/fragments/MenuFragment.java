package com.szabto.lazacetlapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.activities.FoodActivity;
import com.szabto.lazacetlapp.activities.MenuActivity;
import com.szabto.lazacetlapp.api.responses.DayResponse;
import com.szabto.lazacetlapp.api.responses.ResponseBase;
import com.szabto.lazacetlapp.api.structures.FoodCategory;
import com.szabto.lazacetlapp.api.structures.FoodItem;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.helpers.FavoriteHelper;
import com.szabto.lazacetlapp.helpers.UUIDHelper;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.ItemFavoritedListener;
import com.szabto.lazacetlapp.structures.item.ItemAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {
    private static final String TAG = MenuFragment.class.getSimpleName();

    private ArrayList<Object> dataModels;
    private RecyclerView listView;
    private ItemAdapter adapter;

    private RelativeLayout noMenuLayout;
    private LinearLayout progressBar;

    private View view;
    private ApiHelper api;

    public MenuFragment() {
    }

    public static MenuFragment newInstance() {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        listView = (RecyclerView) view.findViewById(R.id.fragment_menu_listview);
        noMenuLayout = (RelativeLayout) view.findViewById(R.id.fragment_menu_no_menu);
        progressBar = (LinearLayout) view.findViewById(R.id.fragment_menu_progress);
        dataModels = new ArrayList<>();
        adapter = new ItemAdapter(dataModels);

        final Activity act = this.getActivity();

        //TODO implement later
        /*
        adapter.setItemClickListener(new ClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                FoodItem dataModel = (FoodItem) dataModels.get(position);
                if (dataModel != null) {
                    Intent intent = new Intent(act, FoodActivity.class);
                    intent.putExtra("food_id", dataModel.getId());
                    intent.putExtra("food_name", dataModel.getName());
                    startActivity(intent);
                }
            }
        });*/

        adapter.setItemFavoritedListener(new ItemFavoritedListener() {
            @Override
            public void onItemFavorited(View view, final int position) {
                final FoodItem dataModel = (FoodItem) dataModels.get(position);
                if( dataModel != null && !dataModel.getLoading() ) {
                    final boolean state = !dataModel.getIsFavorite();
                    dataModel.setLoading(true);
                    adapter.notifyItemChanged(position);

                    api.getService().setFavoriteState(dataModel.getId(), FavoriteHelper.getInstance().getUserToken(), state).enqueue(new Callback<ResponseBase>() {
                        @Override
                        public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                            dataModel.setIsFavorite(state);
                            dataModel.setLoading(false);
                            adapter.notifyItemChanged(position);
                        }

                        @Override
                        public void onFailure(Call<ResponseBase> call, Throwable t) {
                            dataModel.setLoading(false);
                            adapter.notifyItemChanged(position);
                        }
                    });
                }
            }
        });

        final StickyLayoutManager layoutManager = new StickyLayoutManager(this.getActivity(), adapter);

        listView.setLayoutManager(layoutManager);

        listView.setAdapter(adapter);

        if (getActivity() instanceof MenuActivity) {

            MenuActivity myActivity = (MenuActivity) getActivity();
            Bundle b = getActivity().getIntent().getExtras();
            loadMenu(b.getInt("menu_id"));
        } else {
            getTodayMenu();
        }

        return view;
    }

    private void getTodayMenu() {
        api.getService().getToday().enqueue(new Callback<DayResponse>() {
            @Override
            public void onResponse(Call<DayResponse> call, Response<DayResponse> response) {
                DayResponse body = response.body();
                if (body.isSuccess()) {
                    noMenuLayout.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    onGotMenu(body);
                } else {
                    noMenuLayout.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<DayResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error", t);
            }
        });
    }

    private void loadMenu(final int menuId) {
        api.getService().getDay(menuId).enqueue(new Callback<DayResponse>() {
            @Override
            public void onResponse(Call<DayResponse> call, Response<DayResponse> response) {
                onGotMenu(response.body());
            }

            @Override
            public void onFailure(Call<DayResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error", t);
            }
        });
    }

    private void onGotMenu(DayResponse dr) {
        if (dr != null) {
            dataModels.clear();

            progressBar.setVisibility(View.GONE);

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
}
