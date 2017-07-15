package com.szabto.lazacetlapp.structures.menu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.api.structures.MenuItem;
import com.szabto.lazacetlapp.structures.BaseAdapter;
import com.szabto.lazacetlapp.structures.HeaderViewHolder;

import java.util.List;

/**
 * Created by root on 3/24/17.
 */

public class MenuAdapter extends BaseAdapter {
    private final int MENU_ITEM = 0, HEADER = 1;

    public MenuAdapter(List<Object> items) {
        super(items);
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof MenuItem) {
            return MENU_ITEM;
        } else if (items.get(position) instanceof HeaderItem) {
            return HEADER;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case HEADER:
                View v2 = inflater.inflate(R.layout.list_header, parent, false);
                viewHolder = new HeaderViewHolder(v2);
                break;
            default:
                View v1 = inflater.inflate(R.layout.list_menu_row, parent, false);
                viewHolder = new MenuViewHolder(v1);
                ((MenuViewHolder)viewHolder).setClickListener(this);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER:
                HeaderViewHolder vh1 = (HeaderViewHolder) holder;
                configureHeaderViewHolder(vh1, position);
                break;
            default:
                MenuViewHolder vh = (MenuViewHolder) holder;
                configureMenuItemViewHolder(vh, position);
                break;
        }
    }

    private void configureHeaderViewHolder(HeaderViewHolder hvh, int position) {
        HeaderItem item = (HeaderItem) items.get(position);
        if (item != null) {
            hvh.getHeaderTextView().setText(item.getValue());
        }
    }

    private void configureMenuItemViewHolder(MenuViewHolder mvh, int pos) {
        MenuItem item = (MenuItem) items.get(pos);
        if (item != null) {
            mvh.getDayNameView().setText(item.getDayName());
            mvh.getPostedAtView().setText(item.getPosted());
            mvh.getValidityView().setText(item.getDate());
        }
    }
}