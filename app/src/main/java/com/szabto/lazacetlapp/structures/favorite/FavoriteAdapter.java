package com.szabto.lazacetlapp.structures.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.api.structures.FavoriteItem;
import com.szabto.lazacetlapp.structures.BaseAdapter;
import com.szabto.lazacetlapp.structures.ItemFavoritedListener;

import java.util.List;

/**
 * Created by kubu on 6/25/2017.
 */

public class FavoriteAdapter extends BaseAdapter implements ItemFavoritedListener {
    private ItemFavoritedListener itemFavoritedListener = null;

    public FavoriteAdapter(List<Object> items) {
        super(items);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FavoriteViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v1 = inflater.inflate(R.layout.list_favorite_row, parent, false);
        viewHolder = new FavoriteViewHolder(v1);
        viewHolder.setFavoritedListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FavoriteItem item = (FavoriteItem)items.get(position);
        FavoriteViewHolder fvh = (FavoriteViewHolder) holder;
        if( item != null ) {
            fvh.getItemNameView().setText(item.getName());

            fvh.setLoading(item.isLoading());
        }
    }

    public void setItemFavoritedListener( ItemFavoritedListener list ) {
        this.itemFavoritedListener = list;
    }

    @Override
    public void onItemFavorited(View view, int position) {
        if( this.itemFavoritedListener != null ) {
            this.itemFavoritedListener.onItemFavorited(view, position);
        }
    }
}
