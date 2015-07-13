/*  XZHL1710_PassengerFlowActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                */
/*  画面ＩＤ ：XZHL1710                                                                                                */
/*  画面名   ：客流统计                                                                                                     */
/*  实现功能 ：客流统计界面切换监听                                                                                                 */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/06/03   V01L01      FBSE)高振川      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1710;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * 客流统计显示类
 *
 * 实现客流统计的显示和业务处理
 */
@SuppressLint({ "WorldReadableFiles", "SimpleDateFormat" })
public class XZHL1710_PassengerFlowActivity extends Activity implements OnClickListener {

    // 按钮：返回
    private ImageView ivBack;

    // 按钮：搜索
    private Button btnSearch;

    // 界面切换按钮：历史客流
    private Button btnHistory;

    // 界面切换按钮：即时客流
    private Button btnOntime;

    // 历史客流界面：报表图片
    private ImageView ivChart;

    // 即时客流界面：报表图片
    private ImageView ivOnTimeChart;

    //#######################################################
    // 接口中未追加的参数
    // 单选按钮组
    private RadioGroup rgShowType;

    // 单选按钮：合并显示
    private RadioButton rbLink;

    // 单选按钮：个别显示
    private RadioButton rbSingle;

    // 用户所在的shopId
    private String shopId;
    //############################################################

    // 下拉框：显示类型
    private Spinner spShowType;

    // 下拉框：图表类型
    private Spinner spChartType;

    // 日期选择器：开始时间
    private EditText edtStartTime;

    // 日期选择器：结束时间
    private EditText edtEndTime;

    // 登录用户信息
    private SharedPreferences sharedPreferences;

    // 用户id
    private String id;

    // 请求客流报表
    private Bitmap bmHistotyBit;

    // 请求客流报表
    private Bitmap bmOnTimeBit;

    // 界面保存
    private List<View> viewList;

    // 界面切换
    private ViewPager viewPager;

    // 日期类型
    private String[] datetype;

    // 图表类型
    private String[] charttype;

    // 即时客流画面
    private View onTimeView;

    // 历史客流画面
    private View historyView;

    // 用户选择画面标志 1历史客流 0即时客流
    private int step = 1;

    // 日期选择监听器
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    // 日期状态
    private int timeFlag;

    // 日期：年
    private int year;

    // 日期：月
    private int month;

    // 日期：日
    private int day;

    // 配置文件信息
    private Properties properties;

    // 监听器
    private ArrayAdapter<String> adapter;

    /**
     * Acitvity画面初始化
     * @param savedInstanceState 界面初始化参数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logStart();

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 初始化布局文件
        setContentView(R.layout.xzhl1710_passengerflow);

        // 初始化ViewPager
        initViewPager();

        // 初始化页面显示空间
        initView();

        // 初始化页面显示数据
        initData();

        // 初始化日期监听控件
        dateClickListener();

        // 初始化页面控件的监听器
        initListener();

        // 初始化页面查询
        searchListener();
        LogUtil.logEnd();

    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager(){

        LogUtil.logStart();
        viewPager = (ViewPager) findViewById(R.id.vp_viewpager_1710);
        this.getLayoutInflater();
        LayoutInflater lf = LayoutInflater.from(this);
        onTimeView = lf.inflate(R.layout.xzhl1710_passengerflow_ontime, null);
        historyView = lf.inflate(R.layout.xzhl1710_passengerflow_history, null);
        // 将要分页显示的View装入数组中
        viewList = new ArrayList<View>();
        viewList.add(onTimeView);
        viewList.add(historyView);
        LogUtil.logEnd();

    }

    /**
     * 初始话界面显示组件
     */
    private void initView(){

        LogUtil.logStart();
        ivBack = (ImageView) findViewById(R.id.iv_returnmenu_1710);
        btnSearch = (Button) historyView.findViewById(R.id.btn_search_1710);
        edtStartTime = (EditText) historyView.findViewById(R.id.et_starttime_1710);
        edtEndTime = (EditText) historyView.findViewById(R.id.et_endtime_1710);
        btnHistory = (Button) findViewById(R.id.btn_history_1710);
        btnOntime = (Button) findViewById(R.id.btn_ontime_1710);
        rbSingle = (RadioButton) historyView.findViewById(R.id.rb_single_1710);
        rbLink = (RadioButton) historyView.findViewById(R.id.rb_link_1710);
        ivChart = (ImageView) historyView.findViewById(R.id.iv_chart_1710);
        ivOnTimeChart = (ImageView) onTimeView.findViewById(R.id.iv_ontimechart_1710);

        spShowType = (Spinner) historyView.findViewById(R.id.sp_daytype_1710);
        spChartType = (Spinner) historyView.findViewById(R.id.sp_charttype_1710);
        ivBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnHistory.setOnClickListener(this);
        btnOntime.setOnClickListener(this);
        ivChart.setImageResource(R.drawable.normal);
        XZHL1710_ViewPagerAdapter adpter = new XZHL1710_ViewPagerAdapter(viewList);
        viewPager.setAdapter(adpter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new XZHL1710_PassengerPageChangeListener());
        LogUtil.logEnd();
    }

    /**
     * 校验日期的大小
     * @return result 如果前面日期小于后面日期返回true
     */
    private boolean checkDate(){

        LogUtil.logStart();
        boolean result = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(df.parse(edtStartTime.getText().toString()).before(df.parse(edtEndTime.getText().toString()))){
                result = true;
            }else{
                MessageUtil.commonToast(this,
                        MessageUtil.getMessage(this, Msg.E0030,
                           CommonConst.STARTTIME), Toast.LENGTH_SHORT);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 点击查询按钮事件
     */
    private void searchListener(){
        LogUtil.logStart();
        ReceiveChartTask task = new ReceiveChartTask();
        task.execute();
        LogUtil.logEnd();
    }

   /**
    * 初始化页面数据
    */
    @SuppressWarnings("deprecation")
    private void initData(){

        LogUtil.logStart();
        properties = Commonutil.loadProperties(this);
        sharedPreferences = this.getSharedPreferences("data", Context.MODE_WORLD_READABLE);
        id = sharedPreferences.getString("ID", CommonConst.SPACE);
        shopId = sharedPreferences.getString("ID", CommonConst.SPACE);
        datetype = getResources().getStringArray(R.array.datetype);
        charttype = getResources().getStringArray(R.array.charttype);

        adapter = new ArrayAdapter<String>(XZHL1710_PassengerFlowActivity.this,
                android.R.layout.simple_spinner_item, datetype);
        // 设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 将typetypeSpinner 添加到typeSpinner�?
        spShowType.setAdapter(adapter);

        // 添加事件typeSpinner事件监听
        spShowType.setOnItemSelectedListener(null);

        // 设置默认
        spShowType.setVisibility(View.VISIBLE);

        adapter = new ArrayAdapter<String>(
                XZHL1710_PassengerFlowActivity.this,
                android.R.layout.simple_spinner_item, charttype);
        // 设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 将typetypeSpinner 添加到typeSpinner�?
        spChartType.setAdapter(adapter);

        // 添加事件typeSpinner事件监听
        spChartType.setOnItemSelectedListener(null);
        btnOntime.setBackgroundResource(R.drawable.xzhl1710_bg_ontimebutton_down);
        btnOntime.setTextColor(Color.WHITE);
        btnHistory.setBackgroundResource(R.drawable.xzhl1710_bg_historybutton_up);

        btnHistory.setTextColor(Color.BLACK);

        // 当月的第一天
        edtStartTime.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+
                (Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+"01");

        // 当月的最后一天
        edtEndTime.setText(Calendar.getInstance().get(Calendar.YEAR)+"-"+
                (Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+
                    Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH));
        LogUtil.logEnd();
    }

    /**
     * 提交获取即时客流图表数据请求
     * @return String result 返回服务器返回的url
     */
    private String getChartData(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"shopId", "beginDate", "endDate"};
        String[] value = {"123", "", "1999-00-00", "1999-00-00"};
        Log.v("json1---------->>>", JsonUtil.DataToJson("a001",
                JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        String json = woh.WSservers(this, "statistics/showTime",
            JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        Log.v("json2---------->>>", json);

        // 本地服务器连接异常的情况
        if(CommonConst.TALENTERRORSTATES.equals(json)){
            MessageUtil.commonToast(XZHL1710_PassengerFlowActivity.this,
                    properties.getProperty(Msg.E0002, CommonConst.SPACE), Toast.LENGTH_SHORT);
        }else{
            // 正常获得请求数据
            if(JsonUtil.errorJson(json)==null){
                result = XZHL1710_JsonUtil.jsonToCategory(json);

            // 远程服务器连接异常的情况
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                MessageUtil.commonToast(XZHL1710_PassengerFlowActivity.this,
                        properties.getProperty(Msg.E0002, CommonConst.SPACE), Toast.LENGTH_SHORT);

            }else if("0".equals(JsonUtil.errorJson(json).getSuccess())){
                MessageUtil.commonToast(XZHL1710_PassengerFlowActivity.this,
                        properties.getProperty(Msg.I027, CommonConst.SPACE), Toast.LENGTH_SHORT);
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 提交获取历史客流图表数据请求
     * @return String result 返回服务器返回的url
     */
    private String getHistoryChartData(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"shopId"};
        String[] value = {"123"};
        Log.v("json1---------->>>", JsonUtil.DataToJson("a001",
                JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        String json = woh.WSservers(this, "statistics/showHistory",
            JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        Log.v("json2---------->>>", json);

        // 本地服务器连接异常的情况
        if(CommonConst.TALENTERRORSTATES.equals(json)){
            MessageUtil.commonToast(XZHL1710_PassengerFlowActivity.this,
                    properties.getProperty(Msg.E0002, CommonConst.SPACE), Toast.LENGTH_SHORT);
        }else{
            // 正常获得请求数据
            if(JsonUtil.errorJson(json)==null){
                result = XZHL1710_JsonUtil.jsonToCategory(json);

            // 远程服务器连接异常的情况
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                MessageUtil.commonToast(XZHL1710_PassengerFlowActivity.this,
                        properties.getProperty(Msg.E0002, CommonConst.SPACE), Toast.LENGTH_SHORT);

            }else if("0".equals(JsonUtil.errorJson(json).getSuccess())){
                MessageUtil.commonToast(XZHL1710_PassengerFlowActivity.this,
                        properties.getProperty(Msg.I027, CommonConst.SPACE), Toast.LENGTH_SHORT);
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     *  页面点击事件
     *  @param  v 当前画面
     */
    @Override
    public void onClick(View v) {

        LogUtil.logStart();
        switch (v.getId()) {

        // 单击返回按钮事件
        case R.id.iv_returnmenu_1710:
            finish();
            break;

        // 单击查询按钮事件
        case R.id.btn_search_1710:
            if(checkDate()){
                searchListener();
            }
            break;

        // 单击历史客流事件
        case R.id.btn_history_1710:
            btnHistory.setBackgroundResource(R.drawable.xzhl1710_bg_historybutton_down);
            btnHistory.setTextColor(Color.WHITE);
            btnOntime.setBackgroundResource(R.drawable.xzhl1710_bg_ontimebutton_up);
            btnOntime.setTextColor(Color.BLACK);
            viewPager.setCurrentItem(1);
            step = 0;
            break;

        // 单击即时客流事件
        case R.id.btn_ontime_1710:
            btnOntime.setBackgroundResource(R.drawable.xzhl1710_bg_ontimebutton_down);
            btnOntime.setTextColor(Color.WHITE);
            btnHistory.setBackgroundResource(R.drawable.xzhl1710_bg_historybutton_up);
            btnHistory.setTextColor(Color.BLACK);
            viewPager.setCurrentItem(0);
            step = 1;
            break;

        default:
            break;
        }
        LogUtil.logEnd();
    }


    /**
     *  日期选择控件
     */
    private void dateClickListener(){

        LogUtil.logStart();
        mDateSetListener =
           new DatePickerDialog.OnDateSetListener() {
                int mMonth;
                int mYear;
                int mDay;

                /**
                 * 日期设置
                 * @param view 日期选择器
                 * @param year 初始化：年
                 * @param monthOfYear 初始化：月
                 * @param dayOfMonth 初始化：日
                 */
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    String mm;
                    String dd;
                    if(monthOfYear<=9){
                        mMonth = monthOfYear+1;
                        mm="0"+mMonth;
                    }
                    else{
                        mMonth = monthOfYear+1;
                        mm=String.valueOf(mMonth);
                    }

                    if(dayOfMonth<=9){
                        mDay = dayOfMonth;
                        dd="0"+mDay;
                    }
                    else{
                        mDay = dayOfMonth;
                        dd=String.valueOf(mDay);
                    }

                    mDay = dayOfMonth;
                    if(timeFlag == 0) {
                        edtStartTime.setText(String.valueOf(mYear)+"-"+mm+"-"+dd);
                        edtStartTime.clearFocus();
                    }else {
                        edtEndTime.setText(String.valueOf(mYear)+"-"+mm+"-"+dd);
                        edtEndTime.clearFocus();
                    }
                }
            };
        LogUtil.logEnd();
    }


    /**
     *  返回日期选择对话框
     *  @param id 活动的id
     *  @return Dialog 返回对话框
     */
    protected Dialog onCreateDialog(int id) {

        LogUtil.logStart();
        switch (id) {

        case 0:
            return new DatePickerDialog(this, mDateSetListener, year, month, day);

        case 1:
            return new DatePickerDialog(this, mDateSetListener, year, month, day);
        default:
            break;
        }
        LogUtil.logEnd();
        return null;
    }


    /**
     *  隐藏手机键盘
     *  @param edt 活动的View
     */
    private void hideIM(View edt){
        LogUtil.logStart();
        try {
            InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            IBinder  windowToken = edt.getWindowToken();
            if(windowToken != null) {
                im.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
    }


    /**
     *  初始化界面组件监听事件
     */
    private void initListener(){

        LogUtil.logStart();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // 开始日期的点击事件
        edtStartTime.setOnClickListener(new OnClickListener(){
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                LogUtil.logStart();
                showDialog(0, null);
                LogUtil.logEnd();
            }
        });

        // 结束日期的点击事件
        edtEndTime.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("deprecation")
            public void onClick(View v) {
                LogUtil.logStart();
                timeFlag = 1;
                showDialog(1);
                LogUtil.logEnd();
            }
        });

        // 开始日期的焦点改变事件
        edtStartTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @SuppressWarnings("deprecation")
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtil.logStart();
                if (hasFocus == true) {
                    timeFlag = 0;
                    hideIM(v);
                    showDialog(0);
                }
                LogUtil.logEnd();
            }
        });

        // 结束日期的焦点改变事件
        edtEndTime.setOnFocusChangeListener(new OnFocusChangeListener() {
            @SuppressWarnings("deprecation")
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtil.logStart();
                if (hasFocus == true) {
                    timeFlag = 1;
                    hideIM(v);
                    showDialog(1);
                }
                LogUtil.logEnd();
            }
        });
        LogUtil.logEnd();
    }


    /**
     *  获取客流信息类
     *
     *  异步获取客流图表
     */
    class ReceiveChartTask extends AsyncTask<Object, Object, String>{

        /**
         * 后台执行访问网络请求
         * @param arg0 网络参数
         * @return String 返回访问网络处理结果
         */
        @Override
        protected String doInBackground(Object... arg0) {
            LogUtil.logStart();
            String result = null;

            // 异步加载图片
            if(getChartData() != null){
                bmHistotyBit = Commonutil.loadBitmapWithOutCash(getHistoryChartData());
                bmOnTimeBit = Commonutil.loadBitmapWithOutCash(getChartData());
            }else{
                result = CommonConst.SPACE;
            }
            LogUtil.logEnd();
            return result;
        }

        /**
         * 更新UI的处理线程
         * @param result 访问网络处理结果
         */
        @Override
        protected void onPostExecute(String result) {

            LogUtil.logStart();
            if(result == null){

                // 当选择的是即时客流的情况
                if(step == 1){
                    ivOnTimeChart.setImageBitmap(bmOnTimeBit);

                // 当选择的是历史客流的情况
                }else{
                    ivChart.setImageBitmap(bmHistotyBit);
                }
            // 异步请求没有获得数据的时候
            }else{

                // 即时客流的显示情况
                if(step == 1){
                    ivOnTimeChart.setImageResource(R.drawable.error);

                // 历史客流的显示情况
                }else{
                    ivChart.setImageResource(R.drawable.error);
                }
            }
            super.onPostExecute(result);
            LogUtil.logEnd();
        }
    }


    /**
     * 点击返回按钮响应事件
     *
     * @param keyCode 键盘码
     *  @param event 监听事件
     *  @return true 是否正常返回
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        LogUtil.logEnd();

        // 单击返回键
        if(keyCode==KeyEvent.KEYCODE_BACK){

            // 否则关闭页面
            finish();
            LogUtil.logEnd();
            return true;
        }else{
            LogUtil.logEnd();
            return super.onKeyDown(keyCode, event);
        }

    }
}
