package com.szabto.szorietlap.structures.item;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.szabto.szorietlap.R;

import java.util.ArrayList;

/**
 * Created by root on 3/24/17.
 */

public class ItemAdapter extends ArrayAdapter<ItemDataModel> implements View.OnClickListener{
    private static final String TAG = ItemAdapter.class.getSimpleName();

    private ArrayList<ItemDataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtPriceLow;
        TextView txtPriceHigh;
        TextView txtCategoryName;
    }

    public ItemAdapter(ArrayList<ItemDataModel> data, Context context) {
        super(context, R.layout.menu_row, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        ItemDataModel dataModel=(ItemDataModel)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_row, parent, false);

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.txtPriceHigh = (TextView) convertView.findViewById(R.id.price_high);
            viewHolder.txtPriceLow = (TextView) convertView.findViewById(R.id.price_low);
            viewHolder.txtCategoryName = (TextView) convertView.findViewById(R.id.category_name);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        if( dataModel.isCategory() ) {
            viewHolder.txtPriceHigh.setVisibility(View.GONE);
            viewHolder.txtPriceLow.setVisibility(View.GONE);
            viewHolder.txtName.setVisibility(View.GONE);
            viewHolder.txtCategoryName.setVisibility(View.VISIBLE);

            viewHolder.txtCategoryName.setText(dataModel.getName());
        }
        else {
            viewHolder.txtPriceHigh.setVisibility(View.VISIBLE);
            viewHolder.txtName.setVisibility(View.VISIBLE);
            viewHolder.txtCategoryName.setVisibility(View.GONE);

            final float scale = getContext().getResources().getDisplayMetrics().density;

            if( dataModel.getPrice_low() == 0 ) {
                viewHolder.txtPriceLow.setVisibility(View.GONE);
                viewHolder.txtPriceHigh.getLayoutParams().height = (int) (40 * scale + 0.5f);
            }
            else {
                viewHolder.txtPriceLow.setVisibility(View.VISIBLE);
                viewHolder.txtPriceHigh.getLayoutParams().height = (int) (20 * scale + 0.5f);
            }

            viewHolder.txtName.setText(dataModel.getName());
            viewHolder.txtPriceHigh.setText(String.valueOf(dataModel.getPrice_high()));
            viewHolder.txtPriceLow.setText(String.valueOf(dataModel.getPrice_low()));
        }

        return convertView;
    }
}