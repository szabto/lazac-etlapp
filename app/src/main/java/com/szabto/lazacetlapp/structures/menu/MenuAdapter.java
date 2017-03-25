package com.szabto.lazacetlapp.structures.menu;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.szabto.lazacetlapp.R;

import java.util.ArrayList;

/**
 * Created by root on 3/24/17.
 */

public class MenuAdapter extends ArrayAdapter<MenuDataModel> implements View.OnClickListener{

    private ArrayList<MenuDataModel> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtValid;
        TextView txtPosted;
        TextView txtItemCount;
        RelativeLayout wrapper;
        TextView txtWeekNumber;
        RelativeLayout background;
    }

    public MenuAdapter(ArrayList<MenuDataModel> data, Context context) {
        super(context, R.layout.menu_row, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        MenuDataModel dataModel=(MenuDataModel)object;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuDataModel dataModel = getItem(position);
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.menu_row, parent, false);
            viewHolder.txtItemCount = (TextView) convertView.findViewById(R.id.item_count);
            viewHolder.txtPosted = (TextView) convertView.findViewById(R.id.posted_at);
            viewHolder.txtValid = (TextView) convertView.findViewById(R.id.validity);
            viewHolder.background = (RelativeLayout) convertView.findViewById(R.id.layout_bg);

            viewHolder.txtWeekNumber = (TextView)convertView.findViewById(R.id.week_number);
            viewHolder.wrapper = (RelativeLayout)convertView.findViewById(R.id.data_layout);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;

        if( dataModel.isHeader() ) {
            viewHolder.wrapper.setVisibility(View.GONE);
            viewHolder.txtWeekNumber.setVisibility(View.VISIBLE);

            viewHolder.txtWeekNumber.setText(dataModel.getValid() + " " + String.valueOf(dataModel.getId()) + ". hét");
        }
        else {
            viewHolder.wrapper.setVisibility(View.VISIBLE);
            viewHolder.txtWeekNumber.setVisibility(View.GONE);
            if( dataModel.getNew() ) {
                viewHolder.txtPosted.setTypeface(null, Typeface.BOLD);
                viewHolder.txtValid.setTypeface(null, Typeface.BOLD);
                viewHolder.background.setBackgroundColor( convertView.getResources().getColor(R.color.unseenMenu) );
            }
            else {
                viewHolder.txtPosted.setTypeface(null, Typeface.NORMAL);
                viewHolder.txtValid.setTypeface(null, Typeface.NORMAL);
                viewHolder.background.setBackgroundColor( 0 );
            }

            viewHolder.txtPosted.setText(dataModel.getPosted());
            viewHolder.txtValid.setText(dataModel.getValid());
            viewHolder.txtItemCount.setText(String.valueOf(dataModel.getItemCount()));
        }

        return convertView;
    }
}