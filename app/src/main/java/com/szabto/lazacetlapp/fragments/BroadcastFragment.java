package com.szabto.lazacetlapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.helpers.ApiHelper;

public class BroadcastFragment extends Fragment {
    private static final String TAG = Fragment.class.toString();

    private TextView messageView;
    private OnFragmentInteractionListener mListener;

    private ApiHelper api;

    FragmentNavigation mFragmentNavigation;

    public BroadcastFragment() {
    }

    public static BroadcastFragment newInstance() {
        BroadcastFragment fragment = new BroadcastFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = new ApiHelper(this.getContext());
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_broadcast, container, false);

        String strtext = getArguments().getString("message");
        messageView = (TextView) view.findViewById(R.id.broadcast_message);

        messageView.setText(strtext);

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
}
