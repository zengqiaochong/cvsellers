package com.caomei.cvseller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caomei.cvseller.bean.StagesData;

import java.util.ArrayList;

/**
 * Created by Wang Xiaojian on 2016/7/9.
 */
public class StagesAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<StagesData> dataSet;
    private LayoutInflater mInflater;

    public StagesAdapter(Context mContext, ArrayList<StagesData> dataSet){
        this.mContext=mContext;
        this.dataSet=dataSet;
        mInflater=LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_stage, null);
            holder.tvName=(TextView)convertView.findViewById(R.id.tv_stage_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(dataSet.get(position).getUserName());
        return convertView;
    }
    class ViewHolder{
        public TextView tvName;
    }
}