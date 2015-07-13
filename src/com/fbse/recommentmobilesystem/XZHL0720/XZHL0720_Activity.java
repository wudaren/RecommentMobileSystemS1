package com.fbse.recommentmobilesystem.XZHL0720;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0710.XZHL0710_Bean;


public class XZHL0720_Activity extends Activity {
Button btn1;
Button btn2;
ListView listView;
Calendar calendar;
private String start_timestamp;// 开始时间
private String end_timestamp;// 结束时间
private List<XZHL0710_Bean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl0720_main);
		btn1=(Button)findViewById(R.id.datestart);
		btn2=(Button)findViewById(R.id.dateend);
		listView=(ListView)findViewById(R.id.listTotal);
		initDate();
		DatePicker dp=new DatePicker();
		btn1.setOnClickListener(dp);
		btn2.setOnClickListener(dp);
		 list=initData();
	     XZHL0720_Adapter adapter=new XZHL0720_Adapter(this, list, listView);
	     listView.setAdapter(adapter);
	}
private void initDate() {
		// TODO Auto-generated method stub
	calendar = Calendar.getInstance();
	Integer year = calendar.get(Calendar.YEAR);
	Integer month = calendar.get(Calendar.MONTH) + 1;
	calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
	// 设置Calendar日期为下一个月一号
	calendar.set(Calendar.DATE, 1);
	// 设置Calendar日期减一,为本月末
	calendar.add(Calendar.DATE, -1);
	Integer lstdat = calendar.get(Calendar.DAY_OF_MONTH);
	btn1.setText(year + "/" + month + "/1");
	btn2.setText(year + "/" + month + "/" + lstdat);
	start_timestamp = year + "/" + month + "/1";
	end_timestamp = year + "/" + month + "/" + lstdat;
	}
public List<XZHL0710_Bean> initData(){
	List<XZHL0710_Bean> list=new ArrayList<XZHL0710_Bean>();
	for(int i=0;i<10;i++){
		XZHL0710_Bean bean=new XZHL0710_Bean();
		bean.setXiaoshouId("20140422PM"+i);
		bean.setShangpinName("淘宝女装"+(i+1));
		bean.setShangpinId("EOF025FOE"+i);
		bean.setGuige("统一规格");
		bean.setDanjia("100"+i*5);
		bean.setShuliang((i+1)+"");
		bean.setZongjine((100+i*5)*(i+1)+"");
		list.add(bean);
	}
	
	return list;
	
}
class DatePicker implements OnClickListener{

	@Override
	public void onClick(final View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		switch (id) {
		case R.id.datestart:
			new DatePickerDialog(
					XZHL0720_Activity.this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(android.widget.DatePicker view,
								int year, int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							btn1.setText(year + "/"
									+ (monthOfYear + 1) + "/" + dayOfMonth);
							start_timestamp = year + "/"
									+ (monthOfYear + 1) + "/" + dayOfMonth;

						}
					}, Integer.parseInt(start_timestamp.split("/")[0]),
					Integer.parseInt(start_timestamp.split("/")[1]) - 1,
					Integer.parseInt(start_timestamp.split("/")[2])).show();
			break;
		case R.id.dateend:
			new DatePickerDialog(
					XZHL0720_Activity.this,
					new DatePickerDialog.OnDateSetListener() {
						@Override
						public void onDateSet(android.widget.DatePicker view,
								int year, int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub
							btn2.setText(year + "/"
									+ (monthOfYear + 1) + "/" + dayOfMonth);
							end_timestamp = year + "/" + (monthOfYear + 1)
									+ "/" + dayOfMonth;
						}
					}, Integer.parseInt(end_timestamp.split("/")[0]),
					Integer.parseInt(end_timestamp.split("/")[1]) - 1,
					Integer.parseInt(end_timestamp.split("/")[2])).show();
			break;
		default:
			break;
		}
	}
}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.xzhl0720_, menu);
//		return true;
//	}

}
