/**
 * XZHL0520_RecordListActivity.java 
 * XZHL0520_RecordListActivity
 * 版本信息 V1.0
 * 创建日期（2014-05-14）
 */
package com.fbse.recommentmobilesystem.XZHL0520;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0310.XZHL0310_JsonUtil;
import com.fbse.recommentmobilesystem.XZHL0510.XZHL0510_DeviceArray;
import com.fbse.recommentmobilesystem.XZHL0530.XZHL0530_Activity;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * XZHL0520_RecordListActivity
 * @author gaozhenchuan 
 * 报修记录页面显示
 * 创建日期（2014-05-14）
 * 修改者，修改日期（YYYY-MM-DD），修改内容
 */
@SuppressLint("WorldReadableFiles")
public class XZHL0520_RecordListActivity extends Activity implements
        OnClickListener, XZHL0520_ListItemClickHelp{

    // 提交 返回按钮
    private Button cancle_btn, update_btn;

    // 检索开始时间
    private Button starttime;

    // 检索结束时间
    private Button endtime;

    // 界面布局
    private TextView numbertw, posName_tw,nulldatatw;

    private ListView listView;

    // 设备列表传入值
    private XZHL0510_DeviceArray deviceStrBean;

    // 设备记录适配器
    private XZHL0520_ListViewAdapter listViewAdapter;

    private LinearLayout linearLayout;
    
    // 配置文件信息
    Properties properties = null;

    // 申请报修记录列表
    public List<XZHL0520_RepairArray> list = null;

    // 历史报修记录列表
    public List<XZHL0520_RepairArray> list2 = new ArrayList<XZHL0520_RepairArray>();

    private XZHL0520_RepairBean repairLogBean2 = new XZHL0520_RepairBean();

    private Calendar calendar = null;

    // 用户信息
    private SharedPreferences shared;

    /**
     * 初始化Activity界面
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0520_record);
        initView();
        initData();
        receiveParentData();
    }

    /**
     * 初始化界面组件
     */
    private void initView(){
        cancle_btn = (Button) findViewById(R.id.btn_goToList_xzhl0520);
        update_btn = (Button) findViewById(R.id.btn_updateList_xzhl0520);
        listView = (ListView) findViewById(R.id.lv_listRecord_xzhl0520);
        starttime = (Button) findViewById(R.id.btn_commondateed1_xzhl0520);
        endtime = (Button) findViewById(R.id.btn_commondateed2_xzhl0520);
        numbertw = (TextView) findViewById(R.id.tv_repairNo_xzhl0520);
        posName_tw = (TextView) findViewById(R.id.tv_repairName_xzhl0520);
        nulldatatw = (TextView) findViewById(R.id.tv_nulldata_xzhl0520);
        linearLayout = (LinearLayout) findViewById(R.id.ll_linearLayout_xzhl0520);
    }

    /**
     * 初始化配置文件
     */
    private void initData(){
        shared = this.getSharedPreferences("data",
                Context.MODE_WORLD_READABLE);
        properties = Commonutil
                .loadProperties(this);
    }

    /**
     * 接收设备列表界面传递的值
     */
    private void receiveParentData(){
        Intent intent = getIntent();
        deviceStrBean = (XZHL0510_DeviceArray) intent
                .getSerializableExtra("equiplist");
        numbertw.setText(intent.getCharSequenceExtra("hisNum").toString());
        posName_tw.setText(deviceStrBean.getName());
        linearLayout.setVisibility(View.VISIBLE);
        nulldatatw.setVisibility(View.GONE);
        initdate();
        listViewAdapter = new XZHL0520_ListViewAdapter(this, list, this);
        listView.setAdapter(listViewAdapter);
        endtime.setFocusableInTouchMode(false);
        starttime.setFocusableInTouchMode(false);
        update_btn.setOnClickListener(this);
        cancle_btn.setOnClickListener(this);

    }
    
    /**
     * 初始化日期控件
     */
    private void initdate() {
        calendar = Calendar.getInstance();
        Integer year = calendar.get(Calendar.YEAR);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
        // 设置Calendar日期为下一个月一号
        calendar.set(Calendar.DATE, 1);
        // 设置Calendar日期减一,为本月末
        calendar.add(Calendar.DATE, -1);
        Integer lstdat = calendar.get(Calendar.DAY_OF_MONTH);
        starttime.setText(year + "/" + month + "/1");
        endtime.setText(year + "/" + month + "/" + lstdat);

    }

    /**
     * (点击返回按钮响应事件)
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 按钮点击响应事件
     */
    @Override
    public void onClick(View v) {
        // 点击按钮操作
        if (v.getId() == R.id.btn_goToList_xzhl0520) {
            finish();
        } else if (v.getId() == R.id.btn_updateList_xzhl0520) {
            if (XZHL0310_JsonUtil.isNetworkAvailable(this)) {
                    GetDataAsyncTask getDataAsyncTask = new GetDataAsyncTask();
                    getDataAsyncTask.execute();

            }else{
                MessageUtil.commonToast(this, properties.getProperty(Msg.E0008, ""), Toast.LENGTH_SHORT);
            }

        }else{
            //功能补充
        }

    }

    /**
     * GetDataAsyncTask
     * @author gaozhenchuan 
     *异步网络请求，申请设备记录信息，并且对于请求数据做逻辑判断
     * 创建日期（2014-05-14）
     * 修改者，修改日期（YYYY-MM-DD），修改内容
     */
    class GetDataAsyncTask extends AsyncTask<Integer, Integer, String> {

        public GetDataAsyncTask() {
        }

        @Override
        protected String doInBackground(Integer... params) {
            WebServiceOfHttps woh = new WebServiceOfHttps();
            String[] key = {"id"};
            String[] value = {"a0065207-fefe-4166-ac00-a6a09ce5e2a1"};
            String  json = JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),shared.getString("ID", ""), JsonUtil.getSign("a001", JsonUtil.DataToJson(key, value),shared.getString("ID", "")));
            String json2 = woh.WSservers(XZHL0520_RecordListActivity.this,"repair/list", json);
            return json2;
        }

        @Override
        protected void onPostExecute(String result) {
            XZHL0520_RepairBean repairLogBean = new XZHL0520_RepairBean();
            linearLayout = (LinearLayout) findViewById(R.id.ll_linearLayout_xzhl0520);
            linearLayout.setVisibility(View.VISIBLE);
            // 判断服务器是否正常响应
            if (!result.equals("error")) {
                Log.v("jsonResult", result);
               repairLogBean = JsonUtil.JsonToRepair(result);
                // 判断服务器响应是否有数据
                if (repairLogBean != null) {
                    if (repairLogBean.getSuccess().equals("1")) {
                        Log.v("repairLogBean.....)", repairLogBean.getSuccess());
                        repairLogBean2.setSuccess(repairLogBean.getSuccess());
                        list2 = new ArrayList<XZHL0520_RepairArray>();
                        if(list !=null){
                            for (int i = 0; i < list.size(); i++) {
                                list2.add(list.get(i));
                            }
                        }

                        list = new ArrayList<XZHL0520_RepairArray>();
                        for (int i = 0;i<repairLogBean.getRepair().size();i++){
                            list.add(repairLogBean.getRepair().get(i));
                            System.out.println(repairLogBean.getRepair().get(i).getDate());
                        }
                        
                        linearLayout.setVisibility(View.GONE);
                        listViewAdapter = new XZHL0520_ListViewAdapter(XZHL0520_RecordListActivity.this, list, XZHL0520_RecordListActivity.this);
                        listView.setAdapter(listViewAdapter);
                    } else {
                        repairLogBean2.setSuccess(repairLogBean.getSuccess());
                        this.cancel(true);
                    }
                }else{
                    list = null;
                    this.cancel(true);
                }
            } else {
                update_btn.setEnabled(true);
                listView.setVisibility(View.GONE);
                nulldatatw.setVisibility(View.VISIBLE);
                MessageUtil.commonToast(XZHL0520_RecordListActivity.this, properties.getProperty(Msg.E0006, ""),
                        Toast.LENGTH_SHORT);    
                this.cancel(true);

            }
            //
            if (list != null) {
                if(repairLogBean2.getSuccess().equals("1")){

                    if(!similarList(list,list2)){
                        listView.setVisibility(View.VISIBLE);
                        listViewAdapter.notifyDataSetChanged();
                        listView.setAdapter(listViewAdapter);
                        nulldatatw.setVisibility(View.GONE);
                    }else{
                        nulldatatw.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        listViewAdapter.notifyDataSetChanged();
                        listView.setAdapter(listViewAdapter);
                        MessageUtil.commonToast(XZHL0520_RecordListActivity.this,
                                properties.getProperty(Msg.I017, ""), Toast.LENGTH_SHORT);
                    }

                }else{

                    MessageUtil.commonToast(XZHL0520_RecordListActivity.this,
                            properties.getProperty(Msg.E0020, ""), Toast.LENGTH_SHORT);
                }
            } else {
                listView.setVisibility(View.GONE);
                nulldatatw.setVisibility(View.VISIBLE);
                nulldatatw.setText(properties.getProperty(Msg.I018));
                linearLayout.setVisibility(View.GONE);
                MessageUtil.commonToast(XZHL0520_RecordListActivity.this, properties.getProperty(Msg.I018, ""), Toast.LENGTH_SHORT);
            }
            super.onPostExecute(result);
            
        }
    }

    /**
     * 适配器内容点击触发事件
     * @param View item 适配器选项
     * @param View widget Activity界面
     * @param int position 适配器选项下标
     * @param int which 适配器选项下标
     */
    @Override
    public void onClick(View item, View widget, int position, int which) {

        // 判断点击按钮
        if (which == R.id.btn_operate_xzhl0520) {
            // 跳转到 报修明细页面
            Intent intent = new Intent(this, XZHL0530_Activity.class);
            XZHL0520_RepairArray posRecordResult = new XZHL0520_RepairArray();
            posRecordResult = list.get(position);
            intent.putExtra("equipmentLog1", deviceStrBean);
            intent.putExtra("equipmentLog", posRecordResult);
            Log.i("msg", deviceStrBean.toString());
            intent.setFlags(1);
            list2.clear();
            this.startActivity(intent);
        }
    }
    
    /**
     * 判断向服务其请求的前后两次的数据是否一致
     * @param List<XZHL0520_RepairArray> list 最新请求的数据
     * @param List<XZHL0520_RepairArray> list2 历史请求的数据
     * @return boolean 两次数据是否一致的布尔值
     */
    private boolean similarList(List<XZHL0520_RepairArray> list,List<XZHL0520_RepairArray> list2){
        
        boolean result = false;
        int temp = 0;
        if(list.size()==list2.size()){
            for(int i = 0;i<list.size();i++){
                for(int j = 0;j<list2.size();j++){
                    if(list.get(i).getDate().equals(list2.get(j).getDate())){
                        temp++;
                    }
                }
            }
            if(list.size()==temp){
                result = true;
            }
        }else{
            result = false;
        }
        return result;
    }

}
