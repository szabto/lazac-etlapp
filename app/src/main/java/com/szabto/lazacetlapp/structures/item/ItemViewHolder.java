package com.szabto.lazacetlapp.structures.item;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.structures.ClickListener;

/**
 * Created by kubu on 4/5/2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView priceHighView;
    private TextView priceLowView;
    private TextView itemNameView;

    private ClickListener clicklistener = null;

    public ItemViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);
        priceHighView = (TextView) itemView.findViewById(R.id.price_high);
        priceLowView = (TextView) itemView.findViewById(R.id.price_low);
        itemNameView = (TextView) itemView.findViewById(R.id.item_name);
    }

    @Override
    public void onClick(View v) {
        if (clicklistener != null) {
            clicklistener.itemClicked(v, getAdapterPosition());
        }
    }

    public void setClickListener(ClickListener clicklistener) {
        this.clicklistener = clicklistener;
    }
    public TextView getPriceHighView() {
        return priceHighView;
    }

    public void setPriceHighView(TextView priceHighView) {
        this.priceHighView = priceHighView;
    }

    public TextView getPriceLowView() {
        return priceLowView;
    }

    public void setPriceLowView(TextView priceLowView) {
        this.priceLowView = priceLowView;
    }

    public TextView getItemNameView() {
        return itemNameView;
    }

    public void setItemNameView(TextView itemNameView) {
        this.itemNameView = itemNameView;
    }
}
