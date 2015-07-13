package com.fbse.recommentmobilesystem.XZHL0510;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0510_EquitmentMoneyPosAdapter extends BaseAdapter {

	private List<XZHL0510_DeviceArray> list;
	private Context context;
	private ViewHolder viewHolder;
	private XZHL0510_ListViewPosClick callback;

	public XZHL0510_EquitmentMoneyPosAdapter(List<XZHL0510_DeviceArray> list,
			Context context, XZHL0510_ListViewPosClick callback) {
		this.list = list;
		this.context = context;
		this.callback = callback;
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
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, final ViewGroup arg2) {
		if (arg1 == null) {
			arg1 = View.inflate(context,
					R.layout.xzhl0510_equipmentlist_moneypos_item, null);
		}
		viewHolder = new ViewHolder();
		viewHolder.sequence = (TextView) arg1.findViewById(R.id.sequence4);
		viewHolder.sequence.setText(new DecimalFormat("000").format(arg0+1)+"");
		viewHolder.pepairstreet = (TextView) arg1.findViewById(R.id.pepairstreet4);
		viewHolder.pepairstreet.setText(list.get(arg0).getName());
		viewHolder.pepairnum = (TextView) arg1.findViewById(R.id.pepairnum4);
		viewHolder.pepairnum.setText(list.get(arg0).getOrgin_serial());
		viewHolder.pepairDate = (TextView) arg1.findViewById(R.id.pepairDate4);
		String ss=list.get(arg0).getDate().substring(0,list.get(arg0).getDate().indexOf(" "));
		viewHolder.pepairDate.setText(ss+context.getResources().getString(R.string.saled));
		viewHolder.stateRepair = (TextView) arg1.findViewById(R.id.stateRepair4);
		if(list.get(arg0).getStatus().equals("1")){
			viewHolder.stateRepair.setText(Html.fromHtml("<u>"+context.getResources().getString(R.string.fixed)+"</u>"));
		}else if(list.get(arg0).getStatus().equals("3")){
			viewHolder.stateRepair.setText(context.getResources().getString(R.string.stopuse));
		}else {
			viewHolder.stateRepair.setText(context.getResources().getString(R.string.weixiuzhong));
		}
		viewHolder.historyquery = (TextView) arg1.findViewById(R.id.historyquery4);
		viewHolder.historyquery.setText(Html.fromHtml("<u>"+context.getResources().getString(R.string.fixedhis)+"</u>"));
		arg1.setTag(viewHolder);
		final View view = arg1;
		final int p = arg0;
		final int one = viewHolder.stateRepair.getId();
		final int his = viewHolder.historyquery.getId();
		viewHolder.stateRepair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.onClick(view, arg2, p, one);
			}
		});
		viewHolder.historyquery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.onClick(view, arg2, p, his);
			}
		});
		return arg1;
	}

	public static class ViewHolder {
		TextView sequence, pepairstreet, pepairnum, pepairDate, stateRepair,
				historyquery;
	}
}
