package com.fbse.recommentmobilesystem.XZHL0710;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fbse.recommentmobilesystem.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class XZHL0710_Activity extends Activity {
Spinner spinner;
ListView listView;
List<XZHL0710_Bean> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl0710_main);
		spinner=(Spinner)findViewById(R.id.spinnerAdapter);
		listView=(ListView)findViewById(R.id.fenleiListView);
		//声明一个SimpleAdapter独享，设置数据与对应关系
	       SimpleAdapter simpleAdapter = new SimpleAdapter(
		                XZHL0710_Activity.this, getData(), R.layout.xzhl0710_spinner,
		               new String[] { "ivLogo", "fenleiName" }, new int[] {
	                        R.id.imageview, R.id.leibie });
		       //绑定Adapter到Spinner中
		        spinner.setAdapter(simpleAdapter);
		         //Spinner被选中事件绑定。
		        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		            @SuppressWarnings("unchecked")
                    @Override
		            public void onItemSelected(AdapterView<?> parent, View view,
		                     int position, long id) {
		                //parent为一个Map结构的和数据
		                Map<String, Object> map = (Map<String, Object>) parent
		                        .getItemAtPosition(position);
		                Toast.makeText(XZHL0710_Activity.this,
	                        map.get("fenleiName").toString(),
		                        Toast.LENGTH_SHORT).show();
	             }
		
		            @Override
		            public void onNothingSelected(AdapterView<?> arg0) {
		                
		            }
		        });
		        list=initData();
		        XZHL0710_Adapter adapter=new XZHL0710_Adapter(this, list, listView);
		        listView.setAdapter(adapter);
		        
		        
		    }

		    public List<Map<String, Object>> getData() {
		        //生成数据源
		        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		         //每个Map结构为一条数据，key与Adapter中定义的String数组中定义的一一对应。
		         Map<String, Object> map = new HashMap<String, Object>();
		         map.put("ivLogo", R.drawable.nvzhang1);
		        map.put("fenleiName", "女装");
		        list.add(map);
	         Map<String, Object> map2 = new HashMap<String, Object>();
		         map2.put("ivLogo", R.drawable.nanzhuang);
		         map2.put("fenleiName", "男装");
		         list.add(map2);
		         Map<String, Object> map3 = new HashMap<String, Object>();
		        map3.put("ivLogo", R.drawable.tongzhuang);
		        map3.put("fenleiName", "童装");
		        list.add(map3);
		        return list;
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

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.xzhl0710_, menu);
//		return true;
//	}

}
