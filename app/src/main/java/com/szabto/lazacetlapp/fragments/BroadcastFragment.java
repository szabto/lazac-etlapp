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

    private ApiHelper api;

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
}
