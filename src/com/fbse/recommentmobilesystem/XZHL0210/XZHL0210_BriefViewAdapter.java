package com.fbse.recommentmobilesystem.XZHL0210;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0210_BriefViewAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<XZHL0210_Questioniare> list;
    ListView listView;

    public XZHL0210_BriefViewAdapter(Context context,
            List<XZHL0210_Questioniare> list, ListView listView) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.listView = listView;
    }

    public void changeData(ArrayList<XZHL0210_Questioniare> list) {
        this.setList(list);
        this.notifyDataSetChanged();
    }

    public void setList(List<XZHL0210_Questioniare> list) {
        if (list != null)
            this.list = list;
        else
            this.list = new ArrayList<XZHL0210_Questioniare>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public XZHL0210_Questioniare getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 轮询给Item条目附上数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater
                    .inflate(R.layout.xzhl0210_listviewitem, null);
            holder.tv1 = (TextView) convertView
                    .findViewById(R.id.tv_wenjuanId_0210);
            holder.tv2 = (TextView) convertView
                    .findViewById(R.id.tv_qnumber_0210);
            holder.tv3 = (TextView) convertView
                    .findViewById(R.id.tv_company_0210);
            holder.tv4 = (TextView) convertView.findViewById(R.id.tv_mark_0210);
            holder.tv5 = (TextView) convertView
                    .findViewById(R.id.tv_totalTask_0210);
            holder.tv6 = (TextView) convertView
                    .findViewById(R.id.tv_alreadyTask_0210);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(getItem(position).getWenjuanId());
        holder.tv2.setText((position + 1) + "");
        holder.tv3.setText(getItem(position).getWenjuanName());
        holder.tv4.setText(getItem(position).getWenjuanPoint());
        holder.tv5.setText(getItem(position).getRenwuCount());
        holder.tv6.setText(getItem(position).getWanchengCount());
        return convertView;
    }

    class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
    }
}
