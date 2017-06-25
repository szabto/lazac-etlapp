package com.szabto.lazacetlapp.structures;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.brandongogetap.stickyheaders.exposed.StickyHeader;
import com.szabto.lazacetlapp.R;

/**
 * Created by kubu on 4/5/2017.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView headerTextView;

    public HeaderViewHolder(View v) {
        super(v);

        headerTextView = (TextView) v.findViewById(R.id.header_text);
    }

    public TextView getHeaderTextView() {
        return headerTextView;
    }

    public void setHeaderTextView(TextView view) {
        this.headerTextView = view;
    }
}
