package com.fbse.recommentmobilesystem.XZHL0120;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0120_InfoAdapter extends BaseAdapter {

    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private Context context;

    public XZHL0120_InfoAdapter(List<Map<String, Object>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.xzhl0110_history_item,
                null);
        if (convertView != null) {
            TextView view_menu = (TextView) convertView
                    .findViewById(R.id.view_menu);
            view_menu.setText(list.get(position).get("view_menu") + "");
        }
        return convertView;
    }

}
