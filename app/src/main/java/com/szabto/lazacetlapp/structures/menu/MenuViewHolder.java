package com.szabto.lazacetlapp.structures.menu;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.szabto.lazacetlapp.R;
import com.szabto.lazacetlapp.structures.ClickListener;

/**
 * Created by kubu on 4/5/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView dayNameView;
    private TextView validityView;
    private TextView postedAtView;
    private TextView itemCountView;
    private CardView backgroundView;

    private ClickListener clicklistener = null;

    public MenuViewHolder(View itemView) {
        super(itemView);

        dayNameView = (TextView) itemView.findViewById(R.id.day_name);
        validityView = (TextView) itemView.findViewById(R.id.validity);
        postedAtView = (TextView) itemView.findViewById(R.id.posted_at);
        itemCountView = (TextView) itemView.findViewById(R.id.item_count);
        backgroundView = (CardView) itemView.findViewById(R.id.menu_card);
        backgroundView.setOnClickListener(this);
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

    public TextView getDayNameView() {
        return dayNameView;
    }

    public void setDayNameView(TextView dayNameView) {
        this.dayNameView = dayNameView;
    }

    public TextView getValidityView() {
        return validityView;
    }

    public void setValidityView(TextView validityView) {
        this.validityView = validityView;
    }

    public TextView getPostedAtView() {
        return postedAtView;
    }

    public void setPostedAtView(TextView postedAtView) {
        this.postedAtView = postedAtView;
    }

    public TextView getItemCountView() {
        return itemCountView;
    }

    public void setItemCountView(TextView itemCountView) {
        this.itemCountView = itemCountView;
    }
}
