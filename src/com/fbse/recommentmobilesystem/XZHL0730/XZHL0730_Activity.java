package com.fbse.recommentmobilesystem.XZHL0730;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0730_Activity extends Activity {
ListView listView;
private List<XZHL0730_Bean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl0730_main);
		listView=(ListView)findViewById(R.id.shishiListView);
		list=initData();
        XZHL0730_Adapter adapter=new XZHL0730_Adapter(this, list, listView);
        listView.setAdapter(adapter);
	}
	public List<XZHL0730_Bean> initData(){
		List<XZHL0730_Bean> list=new ArrayList<XZHL0730_Bean>();
		for(int i=0;i<10;i++){
			XZHL0730_Bean bean=new XZHL0730_Bean();
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
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.xzhl0730_, menu);
//		return true;
//	}

}
