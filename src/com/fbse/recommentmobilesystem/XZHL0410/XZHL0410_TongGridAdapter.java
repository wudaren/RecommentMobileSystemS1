package com.fbse.recommentmobilesystem.XZHL0410;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0410_TongGridAdapter extends BaseAdapter {

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Context context;

	public XZHL0410_TongGridAdapter(List<Map<String, Object>> list,
			Context context) {
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
		convertView = View.inflate(context, R.layout.xzhl0410_tongitem, null);
		if (convertView != null) {
			TextView view1 = (TextView) convertView.findViewById(R.id.view1);
			view1.setText(list.get(position).get("tong_item1") + "");
			TextView view2 = (TextView) convertView.findViewById(R.id.view2);
			view2.setText(list.get(position).get("tong_item2") + "");
			TextView view3 = (TextView) convertView.findViewById(R.id.view3);
			view3.setText(list.get(position).get("tong_item3") + "");
			TextView view4 = (TextView) convertView.findViewById(R.id.view4);
			view4.setText(list.get(position).get("tong_item4") + "");
			if (position % 2 == 1) {
				view1.setBackgroundResource(R.drawable.top_item_background_shape_white);
				view2.setBackgroundResource(R.drawable.top_item_background_shape_white);
				view3.setBackgroundResource(R.drawable.top_item_background_shape_white);
				view4.setBackgroundResource(R.drawable.top_item_background_shape_white);
			} else {
				view1.setBackgroundResource(R.drawable.top_item_background_shape);
				view2.setBackgroundResource(R.drawable.top_item_background_shape);
				view3.setBackgroundResource(R.drawable.top_item_background_shape);
				view4.setBackgroundResource(R.drawable.top_item_background_shape);
			}
		}
		return convertView;
	}
}
