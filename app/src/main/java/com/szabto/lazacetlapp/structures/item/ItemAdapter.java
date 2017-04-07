package com.szabto.lazacetlapp.structures.item;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;
import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.FoodItem;
import com.szabto.lazacetlapp.api.HeaderItem;
import com.szabto.lazacetlapp.api.MenuItem;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.HeaderViewHolder;
import com.szabto.lazacetlapp.structures.menu.MenuViewHolder;

import java.util.List;

/**
 * Created by root on 3/24/17.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderHandler, ClickListener {
    private List<Object> items;
    private ClickListener clickListener = null;

    private final int ITEM = 0, HEADER = 1;

    public ItemAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof FoodItem) {
            return ITEM;
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
                View v1 = inflater.inflate(R.layout.item_row, parent, false);
                viewHolder = new ItemViewHolder(v1);
                ((ItemViewHolder)viewHolder).setClickListener(this);
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
                ItemViewHolder vh = (ItemViewHolder) holder;
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

    private void configureMenuItemViewHolder(ItemViewHolder mvh, int pos) {
        FoodItem item = (FoodItem) items.get(pos);
        if (item != null) {
            mvh.getItemNameView().setText(item.getName());
            mvh.getPriceHighView().setText(String.valueOf(item.getPriceHigh()));
            if( item.getPriceLow() > 0 ) {
                mvh.getPriceLowView().setText(String.valueOf(item.getPriceLow()));
                mvh.getPriceLowView().setVisibility(View.VISIBLE);
                mvh.getPriceLowView().setGravity(Gravity.TOP | Gravity.RIGHT);
                mvh.getPriceHighView().setGravity(Gravity.BOTTOM | Gravity.RIGHT);
            }
            else {
                mvh.getPriceLowView().setVisibility(View.GONE);
                mvh.getPriceHighView().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            }
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public List<?> getAdapterData() {
        return this.items;
    }

    public void setItemClickListener(ClickListener lst) {
        this.clickListener = lst;
    }

    @Override
    public final void itemClicked(View view, int position) {
        if( this.clickListener != null ) {
            this.clickListener.itemClicked(view, position);
        }
    }
}