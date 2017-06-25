package com.szabto.lazacetlapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.activities.MenuActivity;
import com.szabto.lazacetlapp.api.responses.MenusResponse;
import com.szabto.lazacetlapp.api.responses.ResponseBase;
import com.szabto.lazacetlapp.api.structures.FavoriteItem;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.api.structures.MenuItem;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.helpers.FavoriteHelper;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.EndlessScrollHelper;
import com.szabto.lazacetlapp.structures.ItemFavoritedListener;
import com.szabto.lazacetlapp.structures.favorite.FavoriteAdapter;
import com.szabto.lazacetlapp.structures.menu.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment implements ItemFavoritedListener {
    private static final String TAG = Fragment.class.toString();

    private ArrayList<Object> dataModels;
    private FavoriteAdapter adapter;

    private RecyclerView listView;
    private ProgressBar progressBar;

    private boolean isLoading = false;

    private ApiHelper api;

    public FavoritesFragment() {
    }

    public static FavoritesFragment newInstance() {
        FavoritesFragment fragment = new FavoritesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiHelper(this.getContext());

        dataModels = new ArrayList<Object>();
        adapter = new FavoriteAdapter(dataModels);

        adapter.setItemFavoritedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        loadFavorites();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);


        final Activity act = this.getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        progressBar = (ProgressBar) view.findViewById(R.id.menu_list_progress);
        listView = (RecyclerView) view.findViewById(R.id.menu_list_listview);

        listView.setAdapter(adapter);

        listView.setLayoutManager(layoutManager);

        loadFavorites();
        return view;
    }

    @Override
    public void onItemFavorited(View view, final int position) {
        final FavoriteItem item = (FavoriteItem) dataModels.get(position);
        if( item != null ) {
            item.setLoading(true);
            adapter.notifyItemChanged(position);

            api.getService().setFavoriteState(item.getId(), FavoriteHelper.getInstance().getUserToken(), false).enqueue(new Callback<ResponseBase>() {
                @Override
                public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
                    item.setLoading(false);
                    dataModels.remove(item);
                    adapter.notifyItemRemoved(position);
                    FavoriteHelper.getInstance().removeFromFavorites(item.getId());
                }

                @Override
                public void onFailure(Call<ResponseBase> call, Throwable t) {
                    item.setLoading(false);
                    adapter.notifyItemChanged(position);
                }
            });
        }
    }

    private void loadFavorites() {
        if (isLoading) return;

        isLoading = true;

        progressBar.setVisibility(View.VISIBLE);

        api.getService().getFavoritedFoods(FavoriteHelper.getInstance().getUserToken()).enqueue(new Callback<List<FavoriteItem>>() {
            @Override
            public void onResponse(Call<List<FavoriteItem>> call, Response<List<FavoriteItem>> response) {
                Log.d(TAG, "Loaded favorites");

                dataModels.clear();

                isLoading = false;
                List<FavoriteItem> favs = response.body();

                for(FavoriteItem it : favs ) {
                    dataModels.add(it);
                }


                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<FavoriteItem>> call, Throwable t) {
                isLoading = false;
                Log.e(TAG, "Error", t);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
