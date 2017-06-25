package com.szabto.lazacetlapp.structures.favorite;

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

public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView itemNameView;
    private ProgressBar loadingProgressView;
    private Button deleteButtonView;

    private ItemFavoritedListener favoritedListener = null;

    public FavoriteViewHolder(View itemView) {
        super(itemView);

        itemNameView = (TextView) itemView.findViewById(R.id.item_name);
        loadingProgressView = (ProgressBar) itemView.findViewById(R.id.fav_pb);
        deleteButtonView = (Button) itemView.findViewById(R.id.fav_button);
        deleteButtonView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (favoritedListener != null) {
            favoritedListener.onItemFavorited(v, getAdapterPosition());
        }
    }

    public void setFavoritedListener(ItemFavoritedListener lst) {
        this.favoritedListener = lst;
    }

    public TextView getItemNameView() {
        return itemNameView;
    }

    public void setLoading(boolean loading ) {
        if( loading ) {
            loadingProgressView.setVisibility(View.VISIBLE);
            deleteButtonView.setVisibility(View.GONE);
        }
        else {
            loadingProgressView.setVisibility(View.GONE);
            deleteButtonView.setVisibility(View.VISIBLE);
        }
    }
}
