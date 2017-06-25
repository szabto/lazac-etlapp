package com.szabto.lazacetlapp.structures.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.structures.ClickListener;
import com.szabto.lazacetlapp.structures.ItemFavoritedListener;

/**
 * Created by kubu on 4/5/2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView priceHighView;
    private TextView priceLowView;
    private TextView itemNameView;
    private ToggleButton favoriteButton;
    private ProgressBar loadingProgressView;
    private RelativeLayout favoriteHolderView;

    private ClickListener clicklistener = null;
    private ItemFavoritedListener favoritedListener = null;

    public ItemViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        priceHighView = (TextView) itemView.findViewById(R.id.price_high);
        priceLowView = (TextView) itemView.findViewById(R.id.price_low);
        itemNameView = (TextView) itemView.findViewById(R.id.item_name);
        favoriteButton = (ToggleButton) itemView.findViewById(R.id.fav_button);
        loadingProgressView = (ProgressBar) itemView.findViewById(R.id.fav_pb);
        favoriteHolderView = (RelativeLayout) itemView.findViewById(R.id.favorite_holder);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( favoritedListener != null ) {
                    favoritedListener.onItemFavorited(view, getAdapterPosition());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (clicklistener != null) {
            clicklistener.itemClicked(v, getAdapterPosition());
        }
    }

    public void setFavoritedListener(ItemFavoritedListener lst) {
        this.favoritedListener = lst;
    }

    public void setClickListener(ClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }

    public RelativeLayout getFavoriteHolderView() {
        return this.favoriteHolderView;
    }

    public TextView getPriceHighView() {
        return priceHighView;
    }

    public ToggleButton getFavoriteButtonView() {
        return favoriteButton;
    }


    public TextView getPriceLowView() {
        return priceLowView;
    }


    public TextView getItemNameView() {
        return itemNameView;
    }


    public ProgressBar getLoadingProgressView() {
        return this.loadingProgressView;
    }
}
