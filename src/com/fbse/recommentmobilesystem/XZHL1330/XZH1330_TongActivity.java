package com.fbse.recommentmobilesystem.XZHL1330;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL1340.XZHL1340_TongdetailActivity;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class XZH1330_TongActivity extends Activity {

	 private ListView listView;
	    private Button button1;
	    private Button button2;
	    private ProgressBar pb;
	    SimpleAdapter saItem;
	   public   ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	   
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.xzhl1330_tong);
			listView=(ListView)findViewById(R.id.listSurvey);
			button1=(Button)findViewById(R.id.goToMain);
			button2=(Button)findViewById(R.id.shuxinList);
			pb=(ProgressBar)findViewById(R.id.pblist);
			pb.setVisibility(View.VISIBLE);
			         Map<String, Object> moreAttentionMap = new HashMap<String,Object>(); 
			         moreAttentionMap.put("msg", "派送中！您有一批货正在发送");
			         moreAttentionMap.put("msgdetail", "您购买的女装正在发送中，编号是快递员是某某某");
			       
			         moreAttentionMap.put("content","尊敬的用户您好" );
			         list.add(moreAttentionMap);
			         Map<String, Object> moreAttentionMapa = new HashMap<String,Object>(); 
			         moreAttentionMapa.put("msg", "库存预警");
			         moreAttentionMapa.put("msgdetail", "您购买的女装正在发送中，编号是快递员是某某某");
			       
			         moreAttentionMapa.put("content","库存低于警戒" );
			         list.add(moreAttentionMapa);
			     
			init();
			addListener();
		}
		private void addListener() {
			button1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						XZH1330_TongActivity.this.finish();
					}
				});
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					ListView lView=(ListView)arg0;
					@SuppressWarnings("unchecked")
					Map<String, Object> map=(Map<String, Object>)lView.getItemAtPosition(arg2);
					String contentString=map.get("content").toString();
					String msString=map.get("msg").toString();
					ArrayList<String> mlist=new ArrayList<String>();
					mlist.add(contentString);
					mlist.add(msString);
					Intent intent=new Intent();
					intent.putStringArrayListExtra("list", mlist);
					intent.setClass(XZH1330_TongActivity.this,XZHL1340_TongdetailActivity.class);
					XZH1330_TongActivity.this.startActivity(intent);
					
				
				}
			});	
			button2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					list.clear();
					   Map<String, Object> moreAttentionMapb = new HashMap<String,Object>(); 
				         moreAttentionMapb.put("msg", "派xxxxx");
				         moreAttentionMapb.put("msgdetail", "您购买的");
				       
				         moreAttentionMapb.put("content","尊敬的" );
				         list.add(moreAttentionMapb);
				         pb.setVisibility(View.VISIBLE);
				         init();
				       
				      
				}
			});
		}
	

		private void init() {
			if (list!=null) {
				Toast.makeText(getApplicationContext(), "4444", Toast.LENGTH_SHORT).show();
				 pb.setVisibility(View.GONE);
			}
				 saItem = new SimpleAdapter(this,  

						  list, //数据源  

		                  R.layout.xzhl1330_tongitem, //xml实现  

		                  new String[]{"msg","msgdetail"}, //对应map的Key  

		                  new int[]{R.id.textView1,R.id.textView2});  //对应R的Id  

	              //添加Item到网格中  
			 
			  listView.setAdapter(saItem);
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.general_survey, menu);
			return true;
		}

	}
