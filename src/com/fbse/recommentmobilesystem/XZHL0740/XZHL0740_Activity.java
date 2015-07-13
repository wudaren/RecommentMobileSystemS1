package com.fbse.recommentmobilesystem.XZHL0740;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0740_Activity extends Activity {
ListView listView;
private List<XZHL0740_Bean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl0740_main);
		listView=(ListView)findViewById(R.id.caigouListView);
		 list=initData();
	     XZHL0740_Adapter adapter=new XZHL0740_Adapter(this, list, listView);
	     listView.setAdapter(adapter);
	}
	public List<XZHL0740_Bean> initData(){
		List<XZHL0740_Bean> list=new ArrayList<XZHL0740_Bean>();
		for(int i=0;i<10;i++){
			XZHL0740_Bean bean=new XZHL0740_Bean();
			bean.setXiaoshouId("20140422PM"+i);
			bean.setShangpinName("淘宝女装"+(i+1));
			bean.setShangpinId("EOF025FOE"+i);
			bean.setGuige("统一规格");
			bean.setDanjia("100"+i*5);
			bean.setShuliang((i+1)+"");
			bean.setZongjine((100+i*5)*(i+1)+"");
			bean.setYifujine("1000");
			bean.setJiesuanfangshi("现金");
			list.add(bean);
		}
		
		return list;
		
	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.xzhl0740_, menu);
//		return true;
//	}

}
