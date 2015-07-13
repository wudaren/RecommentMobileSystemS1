package com.fbse.recommentmobilesystem.XZHL0003;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0003_FunctionListAdapter extends BaseAdapter {

	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private Context context;

	public XZHL0003_FunctionListAdapter(List<Map<String, Object>> list,
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
		convertView = View.inflate(context, R.layout.xzhl0003_functionlistitem,
				null);
		if (convertView != null) {
			ImageView functionlist_img = (ImageView) convertView
					.findViewById(R.id.functionlist_img);
			functionlist_img.setBackgroundResource((Integer) list.get(position)
					.get("function_img"));
			TextView functionlist_text = (TextView) convertView
					.findViewById(R.id.functionlist_text);
			functionlist_text.setText(list.get(position).get("function_text")
					+ "");
		}
		return convertView;
	}

}
