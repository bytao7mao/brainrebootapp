package com.taozen.quithabit.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taozen.quithabit.R;
import com.taozen.quithabit.cardActivities.AchievmentsActivity;
import com.taozen.quithabit.cardActivities.AchievmentsActivity2;

public class CustomAdapterListView extends BaseAdapter {
    private String[] itemValues;
    private LayoutInflater inflater;
    public ViewHolder holder=null;
    public CustomAdapterListView(Context context, String[] values) {
        // this.tcontext=context;
        this.itemValues=values;
        this.inflater=LayoutInflater.from(context);
    }
    public int getCount() {
        return itemValues.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView=inflater.inflate(R.layout.list_item,null);
            holder=new ViewHolder();
            holder.tickMark=(ImageView) convertView.findViewById(R.id.listview_image);
            holder.itemDataView=(TextView)convertView.findViewById(R.id.listview_item_title);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.itemDataView.setText(itemValues[position]);

        if(AchievmentsActivity2.tickMarkVisibileListPosition[position]==Boolean.TRUE) {
            holder.tickMark.setVisibility(View.VISIBLE);
        }
        else {
            holder.tickMark.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    static class ViewHolder {
        TextView itemDataView;
        ImageView tickMark;
    }
}
