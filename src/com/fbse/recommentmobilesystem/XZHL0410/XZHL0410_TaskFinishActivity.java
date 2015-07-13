package com.fbse.recommentmobilesystem.XZHL0410;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0003.XZHL0003_MyGridView;
import com.fbse.recommentmobilesystem.XZHL0120.XZHL0120_MyListVIew;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

@SuppressLint("SimpleDateFormat")
public class XZHL0410_TaskFinishActivity extends Activity implements OnClickListener {
    // 自定义的GridView
    private XZHL0003_MyGridView GridView_top;
    private XZHL0120_MyListVIew GridView_tong;
    private List<Map<String, Object>> toplist, tonglist;
    private XZHL0410_TopGridAdapter adapter11;
    private XZHL0410_TongGridAdapter adapter12;
    private double totalSum;
    private SharedPreferences shared;

    private TextView shopid, month_begin, month_end, total, chaju, nodata_task;
    private ImageView back, search;
    private LinearLayout dataset;
    private LinearLayout mypro;
    // 获取一个日历对象
    Calendar date = Calendar.getInstance(Locale.CHINA);
    Calendar date2 = Calendar.getInstance(Locale.CHINA);

    // 保存起始日期的数据
    private long beginMonth;
    private long endMonth;
    // 保存上一次的点击时间
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = this.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0410_taskfinish);
        // 控件的初始化
        GridView_top = (XZHL0003_MyGridView) findViewById(R.id.GridView_top);
        GridView_tong = (XZHL0120_MyListVIew) findViewById(R.id.GridView_tong);
        shopid = (TextView) findViewById(R.id.shopid);
        shopid.setText(shared.getString("SHOPNAME", ""));
        month_begin = (TextView) findViewById(R.id.month_begin);
        month_begin.setOnClickListener(this);
        month_end = (TextView) findViewById(R.id.month_end);
        month_end.setOnClickListener(this);
        total = (TextView) findViewById(R.id.total);
        chaju = (TextView) findViewById(R.id.chaju);
        nodata_task = (TextView) findViewById(R.id.nodata_task);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        search = (ImageView) findViewById(R.id.search);
        search.setOnClickListener(this);
        dataset = (LinearLayout) findViewById(R.id.dateset);
        dataset.setOnClickListener(this);
        // 进度条
        mypro = (LinearLayout) findViewById(R.id.mypro);
        if (!Commonutil.isNetworkAvailable(this)) {
        } else {
            mypro.setVisibility(View.VISIBLE);
            // 异步访问网络
            PostNewloginNameAsyncTask dd = new PostNewloginNameAsyncTask();
            dd.execute();
        }
        date.set(Calendar.YEAR, date.get(Calendar.YEAR));
        date.set(Calendar.MONTH, date.get(Calendar.MONTH));
        date.set(Calendar.DAY_OF_MONTH, 1);
        updateMonthBegin();

        int day2 = date2.getActualMaximum(Calendar.DATE);
        date2.set(Calendar.YEAR, date2.get(Calendar.YEAR));
        date2.set(Calendar.MONTH, date2.get(Calendar.MONTH));
        date2.set(Calendar.DAY_OF_MONTH, day2);
        updateMonthEnd();
    }

    // 当点击DatePickerDialog控件的设置按钮时设置开始日期，调用该方法
    DatePickerDialog.OnDateSetListener beginData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            // 修改日历控件的年，月，日
            // 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            // 将页面TextView的显示更新为最新时间
            updateMonthBegin();
        }
    };
    // 设置开始时间后自动弹出改日期选择框，调用该方法
    DatePickerDialog.OnDateSetListener endData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {
            // 修改日历控件的年，月，日
            date2.set(Calendar.YEAR, year);
            date2.set(Calendar.MONTH, monthOfYear);
            date2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            // 将页面TextView的显示更新为最新时间
            updateMonthEnd();
        }
    };

    // 更新开始日期的TextView
    private void updateMonthBegin() {
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
        String str = temp.format(date.getTime());
        month_begin.setText(str);
        SimpleDateFormat intibegin = new SimpleDateFormat("yyyyMMdd");
        try {
            // 毫秒
            beginMonth = intibegin.parse(intibegin.format(date.getTime()))
                    .getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // 更新结束日期的TextView
    private void updateMonthEnd() {
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd");
        String str = temp.format(date2.getTime());
        month_end.setText(str);
        SimpleDateFormat intiend = new SimpleDateFormat("yyyyMMdd");
        try {
            // 毫秒
            endMonth = intiend.parse(intiend.format(date2.getTime())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // 开启线程
    class PostNewloginNameAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... arg0) {
//            WebService ws = new WebService();
//            String methodName = "task";
//            // 提供开始、结束日期的拼接信息
//            StringBuilder sBd = new StringBuilder();
//            sBd.append("{");
//            sBd.append("start_timestamp");
//            sBd.append(":");
//            sBd.append(month_begin.getText().toString().trim());
//            sBd.append(",");
//            sBd.append("end_timestamp");
//            sBd.append(":");
//            sBd.append(month_end.getText().toString().trim());
//            sBd.append("}");
//            List<String> property = new ArrayList<String>();
//            property.add(sBd.toString());
            
            WebServiceOfHttps woh = new WebServiceOfHttps();
            String[] key = {"begin_time","end_time"};
            String[] value = {"1386281600","1498873600"};
            String json = woh.WSservers(XZHL0410_TaskFinishActivity.this, "task/total",
                    JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),shared.getString("ID", ""), JsonUtil.getSign("a001",  JsonUtil.DataToJson(key, value),shared.getString("ID", ""))));
            return json;                                                                                      
        }

        @Override
        protected void onPostExecute(String result) {
            jiexiInit(result);
            super.onPostExecute(result);
        }
    }

    // 解析jason数据
    private void jiexiInit(String jsonData) {
//        // 获取的json,解析json
        XZHL0410_TaskBean tb = JsonUtil.JsonToTask(jsonData);
        System.out.println("==="+tb.toString());
        // 上方、下方ListView集合
        toplist = new ArrayList<Map<String, Object>>();
        tonglist = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
        Map<String, Object> m2 = null;
        mypro.setVisibility(View.VISIBLE);

        // 判断是否网络异常
        if (jsonData.equals("error")) {
            Toast.makeText(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(),"E0002"), Toast.LENGTH_SHORT)
                    .show();
            nodata_task.setVisibility(View.VISIBLE);
            m2 = new HashMap<String, Object>();
            m2.put("tong_item1",
                    getResources().getString(R.string.zongrenwuzhibiao));
            m2.put("tong_item2",
                    getResources().getString(R.string.yuedurenwuzhibiao));
            m2.put("tong_item3",
                    getResources().getString(R.string.yiwanchengrenwu));
            m2.put("tong_item4", getResources()
                    .getString(R.string.shengyurenwu));
            tonglist.add(m2);
            mypro.setVisibility(View.GONE);
            search.setEnabled(true);
        }
        // 上方grid
        for (int i = 0; i < 8; i++) {
            m = new HashMap<String, Object>();
            DecimalFormat df = new DecimalFormat("0.00");
            switch (i) {
            // 总任务指标
            case 0:
                m.put("top_item",
                        getResources().getString(R.string.zongrenwuzhibiao));
                break;
            // 总任务指标的值
            case 1:
                if (tb.getShop_point() == null) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else if (tb.getShop_point().equals("")) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else {
                    m.put("top_item",
                            df.format(Double.parseDouble(tb.getShop_point()))
                                    + getResources().getString(R.string.jifen));
                }
                break;
            // 月度任务指标
            case 2:
                m.put("top_item",
                        getResources().getString(R.string.yuedurenwuzhibiao));
                break;
            // 月度任务指标的值
            case 3:
                if (tb.getMonth_point() == null) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else if (tb.getMonth_point().equals("")) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else {
                    m.put("top_item",
                            df.format(Double.parseDouble(tb.getMonth_point()))
                                    + getResources().getString(R.string.jifen));
                }
                break;
            // 已完成任务
            case 4:
                m.put("top_item",
                        getResources().getString(R.string.yiwanchengrenwu));
                break;
            // 已完成任务的值
            case 5:
                if (tb.getComplete_point() == null) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else if (tb.getComplete_point().equals("")) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else {
                    m.put("top_item",
                            df.format(Double.parseDouble(tb.getComplete_point()))
                                    + getResources().getString(R.string.jifen));
                }
                break;
            // 剩余任务
            case 6:
                m.put("top_item",
                        getResources().getString(R.string.shengyurenwu));
                break;
            // 剩余任务的值
            case 7:
                if (tb.getMonth_complete_point() == null) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else if (tb.getMonth_complete_point().equals("")) {
                    m.put("top_item", MessageUtil.getMessage(getApplicationContext(),"NOTASKDATA"));
                } else {
                    m.put("top_item",
                            df.format(Double.parseDouble(tb
                                    .getMonth_complete_point()))
                                    + getResources().getString(R.string.jifen));
                }
                break;
            default:
                break;
            }
            toplist.add(m);
        }
        if (toplist != null || tonglist != null) {
            mypro.setVisibility(View.GONE);
            search.setEnabled(true);
        }
        // 下方grid
        // 如果数据为空，只显示标题内容
        if (tb.getTask() == null) {
        } else {
            for (int i = -1; i < tb.getTask().size(); i++) {
                m2 = new HashMap<String, Object>();
                if (i == -1) {
                    m2.put("tong_item1",
                            getResources().getString(R.string.yewuneirong));
                    m2.put("tong_item2",
                            getResources().getString(R.string.huansuanlv));
                    m2.put("tong_item3",
                            getResources().getString(R.string.xiaoshoue));
                    m2.put("tong_item4",
                            getResources().getString(R.string.shixiandejifen));
                } else {
                    XZHL0410_TaskArray b = tb.getTask().get(i);
                    m2.put("tong_item1", b.getName());
                    m2.put("tong_item2", b.getRate());
                    m2.put("tong_item3", b.getSales());
                    m2.put("tong_item4", b.getPoint());
                    totalSum += Double.parseDouble(b.getPoint());
                }
                tonglist.add(m2);
            }
        }
        // 计算本月已完成合计
        total.setText(totalSum + getResources().getString(R.string.jifen));
        // 本月任务差距
        if (tb.getMonth_point() == null) {
            chaju.setText(totalSum + getResources().getString(R.string.jifen));
        } else {
            chaju.setText((Double.parseDouble(tb.getMonth_point()) - totalSum)
                    + getResources().getString(R.string.jifen));
            if((Double.parseDouble(tb.getMonth_point()) - totalSum)<0){
                chaju.setText(getResources().getString(R.string.finished));
            }
        }
        adapter11 = new XZHL0410_TopGridAdapter(toplist, this);
        adapter12 = new XZHL0410_TongGridAdapter(tonglist, this);
        GridView_top.setAdapter(adapter11);
        GridView_tong.setAdapter(adapter12);
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        // 返回按钮的事件
        case R.id.back:
            finish();
            break;
        // 更新数据
        case R.id.search:
            nodata_task.setVisibility(View.GONE);
            if (beginMonth >= endMonth) {
                //防止重复点击
                long time = System.currentTimeMillis();
                long timeD = time - lastClickTime;
                if (0 < timeD && timeD < 2800) {
                } else {
                    Toast.makeText(getApplicationContext(),
                            MessageUtil.getMessage(getApplicationContext(),"BEGINEND"),
                            Toast.LENGTH_SHORT).show();
                }
                lastClickTime = time;
            } else {
                if (!Commonutil.isNetworkAvailable(this)) {
                    nodata_task.setVisibility(View.VISIBLE);
                } else {
                    //防止重复点击
                    long time = System.currentTimeMillis();
                    long timeD = time - lastClickTime;
                    if (0 < timeD && timeD < 2800) {
                    } else {
                        total.setText("");
                        chaju.setText("");
                        totalSum = 0;
                        if (toplist != null) {
                            toplist.clear();
                        }
                        if (tonglist != null) {
                            tonglist.clear();
                        }
                        mypro.setVisibility(View.VISIBLE);
                        PostNewloginNameAsyncTask dd = new PostNewloginNameAsyncTask();
                        dd.execute();
                    }
                    lastClickTime = time;
                }
            }
            break;
        // 弹出日期选择控件--开始日期
        case R.id.month_begin:
            new DatePickerDialog(this, beginData, date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH), 1).show();
            break;
        // 弹出日期选择控件--结束日期
        case R.id.month_end:
            // 获取当前月份的天数
            int day = date2.getActualMaximum(Calendar.DATE);
            new DatePickerDialog(this, endData, date2.get(Calendar.YEAR),
                    date2.get(Calendar.MONTH), day).show();
            break;
        default:
            break;
        }

    }

}
