package com.szabto.lazacetlapp.structures.item;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.structures.FoodItem;
import com.szabto.lazacetlapp.api.structures.HeaderItem;
import com.szabto.lazacetlapp.structures.BaseAdapter;
import com.szabto.lazacetlapp.structures.HeaderViewHolder;
import com.szabto.lazacetlapp.structures.ItemFavoritedListener;

import java.util.List;

/**
 * Created by root on 3/24/17.
 */

public class ItemAdapter extends BaseAdapter implements ItemFavoritedListener {
    private final int ITEM = 0, HEADER = 1;

    private ItemFavoritedListener itemFavoritedListener = null;

    public ItemAdapter(List<Object> items) {
        super(items);
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

    public void setItemFavoritedListener(ItemFavoritedListener listener) {
        this.itemFavoritedListener = listener;
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
                View v1 = inflater.inflate(R.layout.list_item_row, parent, false);
                viewHolder = new ItemViewHolder(v1);
                ((ItemViewHolder) viewHolder).setClickListener(this);
                ((ItemViewHolder)viewHolder).setFavoritedListener(this);
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
            if( item.getCanfavorited() ) {
                mvh.getFavoriteHolderView().setVisibility(View.VISIBLE);
                if( item.getLoading() ) {
                    mvh.getLoadingProgressView().setVisibility(View.VISIBLE);
                    mvh.getFavoriteButtonView().setVisibility(View.GONE);
                }
                else {
                    mvh.getFavoriteButtonView().setChecked(item.getIsFavorite());

                    mvh.getLoadingProgressView().setVisibility(View.GONE);
                    mvh.getFavoriteButtonView().setVisibility(View.VISIBLE);
                }
            }
            else {
                mvh.getFavoriteHolderView().setVisibility(View.GONE);
            }
            if (item.getPriceLow() > 0) {
                mvh.getPriceLowView().setText(String.valueOf(item.getPriceLow()));
                mvh.getPriceLowView().setVisibility(View.VISIBLE);
                mvh.getPriceLowView().setGravity(Gravity.TOP | Gravity.RIGHT);
                mvh.getPriceHighView().setGravity(Gravity.BOTTOM | Gravity.RIGHT);
            } else {
                mvh.getPriceLowView().setVisibility(View.GONE);
                mvh.getPriceHighView().setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            }
        }
    }

    @Override
    public void onItemFavorited(View view, int position) {
        if( this.itemFavoritedListener != null ) {
            this.itemFavoritedListener.onItemFavorited(view, position);
        }
    }
}