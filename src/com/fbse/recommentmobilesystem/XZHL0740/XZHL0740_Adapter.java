package com.fbse.recommentmobilesystem.XZHL0740;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;


public class XZHL0740_Adapter extends BaseAdapter{
LayoutInflater inflater;
List<XZHL0740_Bean> list;
	public XZHL0740_Adapter(Context context,List<XZHL0740_Bean> list,ListView listView){
		this.inflater=LayoutInflater.from(context);
		this.list=list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public XZHL0740_Bean getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=inflater.inflate(R.layout.xzhl0740_listitem,null);
			holder.tv1=(TextView)convertView.findViewById(R.id.xiaoshouId);
			holder.tv2=(TextView)convertView.findViewById(R.id.shangpinName);
			holder.tv3=(TextView)convertView.findViewById(R.id.shangpinId);
			holder.tv4=(TextView)convertView.findViewById(R.id.guige);
			holder.tv5=(TextView)convertView.findViewById(R.id.danjia);
			holder.tv6=(TextView)convertView.findViewById(R.id.zongjine);
			holder.tv7=(TextView)convertView.findViewById(R.id.yifujine);
			holder.tv8=(TextView)convertView.findViewById(R.id.jiesuanfangshi);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.tv1.setText(getItem(position).getXiaoshouId());
		holder.tv2.setText(getItem(position).getShangpinName());
		holder.tv3.setText(getItem(position).getShangpinId());
		holder.tv4.setText(getItem(position).getGuige()+" -");
		holder.tv5.setText("单价:"+getItem(position).getDanjia()+" * "+"数量:"+getItem(position).getShuliang()+" =");
		holder.tv6.setText("总金额:"+getItem(position).getZongjine());
		holder.tv7.setText("已付金额:"+getItem(position).getYifujine());
		holder.tv8.setText("结算方式:"+getItem(position).getJiesuanfangshi());
		return convertView;
	}
	class ViewHolder{
		TextView tv1;
		TextView tv2;
		TextView tv3;
		TextView tv4;
		TextView tv5;
		TextView tv6;
		TextView tv7;
		TextView tv8;
		
	}
}
