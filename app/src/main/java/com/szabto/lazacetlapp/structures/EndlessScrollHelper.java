package com.szabto.lazacetlapp.structures;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.View;

/**
 * Created by kubu on 4/8/2017.
 */

public abstract class EndlessScrollHelper extends RecyclerView.OnScrollListener {
    private final LayoutManager manager;
    private final RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;

    public EndlessScrollHelper(LayoutManager m, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.manager = m;
        this.adapter = adapter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (manager.getChildCount() > 0) {
            int indexOfLastItemViewVisible = manager.getChildCount() - 1;
            View lastItemViewVisible = manager.getChildAt(indexOfLastItemViewVisible);
            int adapterPosition = manager.getPosition(lastItemViewVisible);
            boolean isLastItemVisible = (adapterPosition == this.adapter.getItemCount() - 1);

            if (isLastItemVisible) {
                this.onScrolledEnd();
            }
        }
    }

    public abstract void onScrolledEnd();
}