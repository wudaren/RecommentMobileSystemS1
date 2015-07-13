package com.fbse.recommentmobilesystem.XZHL0410;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0410_TopGridAdapter extends BaseAdapter {

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Context context;

	public XZHL0410_TopGridAdapter(List<Map<String, Object>> list,
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
		convertView = View.inflate(context, R.layout.xzhl0410_topitem, null);
		if (convertView != null) {
			LinearLayout setbg = (LinearLayout) convertView
					.findViewById(R.id.setbg);
			if (position > 3) {
				setbg.setBackgroundResource(R.drawable.top_item_background_shape_white);
			} else {
				setbg.setBackgroundResource(R.drawable.top_item_background_shape);
			}
			TextView functionlist_text = (TextView) convertView
					.findViewById(R.id.top_item);
			functionlist_text.setText(list.get(position).get("top_item") + "");
			if (position % 2 == 1) {
				functionlist_text.setTextColor(android.graphics.Color.RED);
			}
		}
		return convertView;
	}

}
