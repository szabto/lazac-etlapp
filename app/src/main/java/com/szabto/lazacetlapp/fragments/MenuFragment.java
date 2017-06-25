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
import com.szabto.lazacetlapp.api.structures.FoodCategory;
import com.szabto.lazacetlapp.api.structures.FoodItem;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.helpers.ApiHelper;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.item.ItemAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private static final String TAG = MenuFragment.class.getSimpleName();

    private ArrayList<Object> dataModels;
    private RecyclerView listView;
    private ItemAdapter adapter;
    private OnFragmentInteractionListener mListener;

    private RelativeLayout noMenuLayout;
    private LinearLayout progressBar;

    private View view;
    private ApiHelper api;

    private MenuListFragment.FragmentNavigation mFragmentNavigation;

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
                /*Snackbar.make(view.findViewById(getSnackBarParentId()), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        checkNetwork();
                    }
                }).show();*/
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
                /*Snackbar.make(view.findViewById(getSnackBarParentId()), getString(R.string.error_occurred), Snackbar.LENGTH_LONG).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        checkNetwork();
                    }
                }).show();*/
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuListFragment.FragmentNavigation) {
            mFragmentNavigation = (MenuListFragment.FragmentNavigation) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FragmentNavigation {
        public void pushFragment(Fragment fragment);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
