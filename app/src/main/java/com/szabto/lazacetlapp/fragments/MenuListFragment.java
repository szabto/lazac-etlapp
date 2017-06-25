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
import android.widget.ProgressBar;

import com.brandongogetap.stickyheaders.StickyLayoutManager;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.activities.MenuActivity;
import com.szabto.lazacetlapp.api.responses.MenusResponse;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.api.structures.MenuItem;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.EndlessScrollHelper;
import com.szabto.lazacetlapp.structures.menu.MenuAdapter;

import java.util.ArrayList;

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
public class MenuListFragment extends Fragment {
    private static final String TAG = Fragment.class.toString();

    private ArrayList<Object> dataModels;
    private MenuAdapter adapter;

    private RecyclerView listView;
    private ProgressBar progressBar;

    private boolean isLoading = false;
    private int start = 0;
    private boolean hasMore = false;
    private int currentWeek = -1;
    private OnFragmentInteractionListener mListener;

    private ApiHelper api;

    FragmentNavigation mFragmentNavigation;

    public MenuListFragment() {
    }

    public static MenuListFragment newInstance() {
        MenuListFragment fragment = new MenuListFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiHelper(this.getContext());

        dataModels = new ArrayList<Object>();
        adapter = new MenuAdapter(dataModels);
    }

    @Override
    public void onStart() {
        super.onStart();

        loadMenus(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_list, container, false);


        final Activity act = this.getActivity();
        final StickyLayoutManager layoutManager = new StickyLayoutManager(this.getActivity(), adapter);

        progressBar = (ProgressBar) view.findViewById(R.id.fragment_menu_list_progress);
        listView = (RecyclerView) view.findViewById(R.id.fragment_menu_list_listview);

        listView.setAdapter(adapter);

        listView.setLayoutManager(layoutManager);

        adapter.setItemClickListener(new ClickListener() {
            @Override
            public void itemClicked(View view, int position) {
                MenuItem dataModel = (MenuItem) dataModels.get(position);
                if (dataModel != null) {
                    Intent intent = new Intent(act, MenuActivity.class);
                    intent.putExtra("menu_id", dataModel.getId());
                    intent.putExtra("date", dataModel.getDate());
                    startActivity(intent);
                }
            }
        });

        listView.addOnScrollListener(new EndlessScrollHelper(layoutManager, adapter) {
            @Override
            public void onScrolledEnd() {
                if (!isLoading && hasMore) {
                    loadMenus(true);
                }
            }
        });

        loadMenus(false);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public interface FragmentNavigation {
        public void pushFragment(Fragment fragment);
    }


    private void loadMenus(final boolean more) {
        if (isLoading) return;

        isLoading = true;
        if (!more) {
            start = 0;
            currentWeek = -1;
            hasMore = true;
        }

        progressBar.setVisibility(View.VISIBLE);

        api.getService().getMenuList(start).enqueue(new Callback<MenusResponse>() {
            @Override
            public void onResponse(Call<MenusResponse> call, Response<MenusResponse> response) {
                Log.d(TAG, "Loaded menus");

                if (!more)
                    dataModels.clear();

                isLoading = false;
                MenusResponse mr = response.body();

                hasMore = mr.isThereMore();

                for (MenuItem m : mr.getList()) {

                    if (m.getWeekNum() != currentWeek) {
                        String date = m.getDate();
                        date = date.substring(0, 4);
                        dataModels.add(new HeaderItem(date + " - " + m.getWeekNum() + ". hét"));
                        currentWeek = m.getWeekNum();
                    }

                    dataModels.add(m);
                }
                start += mr.getList().size();
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MenusResponse> call, Throwable t) {
                isLoading = false;
                hasMore = true;
                /*Snackbar.make(findViewById(getSnackBarParentId()), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        //checkNetwork();
                    }
                }).show();*/
                Log.e(TAG, "Error", t);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
