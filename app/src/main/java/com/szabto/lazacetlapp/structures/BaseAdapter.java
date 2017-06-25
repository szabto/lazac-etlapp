package com.szabto.lazacetlapp.structures;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.brandongogetap.stickyheaders.exposed.StickyHeaderHandler;

import java.util.List;

/**
 * Created by kubu on 4/7/2017.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyHeaderHandler, ClickListener {
    protected final List<Object> items;

    private ClickListener clickListener = null;

    public BaseAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public abstract int getItemViewType(int position);

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

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
