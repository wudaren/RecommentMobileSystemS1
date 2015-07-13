package com.fbse.recommentmobilesystem.XZHL1310;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.fbse.recommentmobilesystem.XZHL1310.DataService;
import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL1320.XZHL1320_MsgdetailActivity;




import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class XZHL1310_MsgActivity extends Activity {
    private ListView listView;
    private Button button1,button2;
 
    View footer;
    SimpleAdapter adapter;
   public   ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题不显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl1310_msg);
	       footer = getLayoutInflater().inflate(R.layout.xzhl1310_footer, null);

		
	       listView=(ListView)findViewById(R.id.listSurvey);
			button1=(Button)findViewById(R.id.goToMain);
			button2=(Button)findViewById(R.id.shuxinList);
		data.addAll(DataService.getData(0, 5)); 
		 listView.setOnScrollListener(new ScrollListener());

		
		 adapter = new SimpleAdapter(this,  

				   data, //数据源  

                  R.layout.xzhl1310_msgitem, //xml实现  

                  new String[]{"msg","msgdetail","time"}, //对应map的Key  

                  new int[]{R.id.textView1,R.id.textView2,R.id.textView3});  //对应R的Id  

              //添加Item到网格中  
	        listView.addFooterView(footer);//添加页脚(放在ListView最后)。该方法要求在setAdapter之前调用。

		  listView.setAdapter(adapter); 
		  listView.removeFooterView(footer);
		addListener();
	}

	   private int number = 5;//每次获取多少条数据

	   private int maxpage = 10;//总共有多少页

	   private boolean loadfinish = true;

	   private final class ScrollListener implements OnScrollListener{

	        public void onScrollStateChanged(AbsListView view, int scrollState) {

	            Log.i("MainActivity","onScrollStateChanged(scrollState="+ scrollState+ ")"); //如果scrollState=1，用户的手指正在拖动ListView；如果为2，用户已停止拖动ListView，由于惯性原因，还会继续滚动；如果为0，ListView停止滚动，回到空闲的状态

	        }

	        

	        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

	            Log.i("MainActivity","onScroll(firstVisibleItem="+ firstVisibleItem+",visibleItemCount="+

	                    visibleItemCount+",totalItemCount="+ totalItemCount+ ")");

	            

	            final int loadtotal = totalItemCount;

	            int lastItemid = listView.getLastVisiblePosition();//获取当前屏幕最后Item的ID

	           if((lastItemid+1) == totalItemCount){//达到数据的最后一条记录

	                if(totalItemCount> 0){

	                    //当前页

	                    int currentpage = totalItemCount%number == 0 ? totalItemCount/number :totalItemCount/number+1;

	                    int nextpage = currentpage + 1;//下一页

	                    if(nextpage<= maxpage && loadfinish){

	                        loadfinish= false;

	                        listView.addFooterView(footer);

	                        

	                        new Thread(new Runnable() {                     

	                            public void run() {

	                                try{

	                                    Thread.sleep(3000);

	                                }catch (InterruptedException e) {

	                                    e.printStackTrace();

	                                }

	                                List<HashMap<String, Object>> result = DataService.getData(loadtotal, number);

	                                handler.sendMessage(handler.obtainMessage(100,result));

	                            }

	                        }).start();

	                    }       

	                }

	                        

	            }

	        }

	    }

	    

	   Handler handler = new Handler(){

	        @SuppressWarnings("unchecked")
			public void handleMessage(Message msg) {

	            data.addAll((ArrayList<Map<String, Object>>)msg.obj);

	           adapter.notifyDataSetChanged();//告诉ListView数据已经发生改变，要求ListView更新界面显示

	            if(listView.getFooterViewsCount()> 0) listView.removeFooterView(footer);

	            loadfinish= true;

	        }       

	    };

	private void addListener() {
		button1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					XZHL1310_MsgActivity.this.finish();
				}
			});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				ListView lView=(ListView)arg0;
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map=(HashMap<String, Object>)lView.getItemAtPosition(arg2);
				String contentString=map.get("msgdetail").toString();
				String msString=map.get("msg").toString();
				ArrayList<String> mlist=new ArrayList<String>();
				mlist.add(contentString);
				mlist.add(msString);
				Intent intent=new Intent();
				intent.putStringArrayListExtra("list", mlist);
				intent.setClass(XZHL1310_MsgActivity.this,XZHL1320_MsgdetailActivity.class);
				XZHL1310_MsgActivity.this.startActivity(intent);
				
			
			}
		});	
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.general_survey, menu);
		return true;
	}

}
