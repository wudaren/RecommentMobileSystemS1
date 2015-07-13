/*  XZHL1810_HotShowActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO., LTD 2012                      */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ ：XZHL1810                                                                                                    */
/*  画面名   ：热区分布显示                                                                                                     */
/*  实现功能 ：进行热区显示。                                                                                                     */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/30   V01L01      FBSE)张海静      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1810;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
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
 * 热区分布显示类
 *
 * 完成热区分布显示
 */
public class XZHL1810_HotShowActivity extends Activity implements OnClickListener {

    // TAG
    private static final String TAG = "XZHL1810_HotShowActivity";

    // 返回
    private ImageView ivBack;

    // 查询
    private ImageView ivSearch;

    // 图片
    private ImageView ivHotDisplay;

    // 下拉框
    private Spinner spEquip;

    // 开始日期
    private TextView tvBegin;

    // 结束日期
    private TextView tvEnd;

    // 存储图片
    private Bitmap btPicture;

    // 时间设置
    private Button timeSet;

    // 下拉框值
    private String[] equipType;

    // 设备值
    private String equipText;

    // 屏幕宽度
    private int windowWidth;

    // 日期对话框
    private AlertDialog adDate;

    // 开始时间的毫秒数
    private long beginMonth;

    // 结束时间的毫秒数
    private long endMonth;

    // 空的常量
    private static final String KONG = "";

    // 共享信息
    private SharedPreferences sharedPerf;

    // 共享Name
    private static final String DATA = "data";

    // 共享键值
    private static final String KEY = "ID";

    // 0常量
    private static final String ZERO = "0";

    // 1常量
    private static final String ONE = "1";

    // 获取一个日历对象-开始日期时间
    private Calendar beginDate = Calendar.getInstance(Locale.CHINA);

    // 获取一个日历对象-结束日期时间
    private Calendar endDate = Calendar.getInstance(Locale.CHINA);

    /**
     * 完成界面初始化
     * @param savedInstanceState 状态保存
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // log打印开始
        LogUtil.logStart();

        // 共享信息
        sharedPerf = this.getSharedPreferences(DATA, Context.MODE_PRIVATE);

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl1810_hotshow);

        // 控件初始化方法引用
        initView();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 控件初始化
     */
    private void initView() {

        // log打印开始
        LogUtil.logStart();

        // 返回控件
        ivBack = (ImageView) findViewById(R.id.iv_gotoback_xzhl1810);
        ivBack.setOnClickListener(this);

        // 查询控件
        ivSearch = (ImageView) findViewById(R.id.iv_hotrefresh_xzhl1810);
        ivSearch.setOnClickListener(this);

        // 图片显示控件
        ivHotDisplay=(ImageView) findViewById(R.id.iv_hotendimage_xzhl1810);

        // 开始日期显示
        tvBegin=(TextView) findViewById(R.id.tv_hotbegintime_xzhl1810);
        tvBegin.setOnClickListener(this);

        // 结束日期显示
        tvEnd=(TextView) findViewById(R.id.tv_hotendtime_xzhl1810);
        tvEnd.setOnClickListener(this);

        // 下拉框
        spEquip=(Spinner) findViewById(R.id.sp_hotequipment_xzhl1810);

        // 设置图片显示大小
        Point outSize=new Point();
        WindowManager windowManager=(WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getSize(outSize);
        windowWidth=outSize.x;

        // 初始化显示日期-开始时间
        beginDate.set(Calendar.YEAR, beginDate.get(Calendar.YEAR));
        beginDate.set(Calendar.MONTH, beginDate.get(Calendar.MONTH));
        beginDate.set(Calendar.DAY_OF_MONTH, beginDate.get(Calendar.DAY_OF_MONTH)-Integer.parseInt(ONE));
        beginDate.set(Calendar.HOUR_OF_DAY, beginDate.get(Calendar.HOUR_OF_DAY));
        beginDate.set(Calendar.MINUTE, beginDate.get(Calendar.MINUTE));

        // 结束时间
        endDate.set(Calendar.YEAR, endDate.get(Calendar.YEAR));
        endDate.set(Calendar.MONTH, endDate.get(Calendar.MONTH));
        endDate.set(Calendar.DAY_OF_MONTH, endDate.get(Calendar.DAY_OF_MONTH));
        endDate.set(Calendar.HOUR_OF_DAY, endDate.get(Calendar.HOUR_OF_DAY));
        endDate.set(Calendar.MINUTE, endDate.get(Calendar.MINUTE));

        // 文本框显示日期
        updateMonthBegin();
        updateMonthEnd();

        // 判断网络是否异常,没有异常
        if (Commonutil.isNetworkAvailable(getApplicationContext())) {

            // 向服务器接受列表
            HotListAsyncTask hotListAsyncTask=new HotListAsyncTask();
            hotListAsyncTask.execute();
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 服务器接收设备列表的异步类
     *
     * 向服务器接受数据，完成设备列表信息的显示
     */
    private class HotListAsyncTask extends AsyncTask<String, Integer, String>{

        /**
         * 后台取数据
         * @param params 参数
         * @return resultInfo
         */
        @Override
        protected String doInBackground(String... params) {

            // log打印开始
            LogUtil.logStart();

            // 提交数据,封装数据
            String portName = "camera/list";
            String serial = "a001";
            String[] key = { "timestamp" };
            String[] value = { "0" };
            String id = sharedPerf.getString(KEY, KONG);

            // 调用网络方法
            WebServiceOfHttps webSO = new WebServiceOfHttps();

            // 请求数据
            String jSon = JsonUtil.DataToJson(serial, JsonUtil.DataToJson(key, value), id, JsonUtil.getSign(serial,
                JsonUtil.DataToJson(key, value), id));
            String info = webSO.WSservers(XZHL1810_HotShowActivity.this, portName, jSon);

            // log打印结束
            LogUtil.logEnd();
            return info;
        }

        /**
         * 从doInBackground方法中得到值作相应判断
         * @param result 返回值
         */
        @Override
        protected void onPostExecute(String result) {

            // log打印开始
            LogUtil.logStart();

            // 解析Json中success的值
            String resultInfo=JsonUtil.successJSON(result);

            // 1为成功
            if(ONE.equals(resultInfo)){
                XZHL1810_HotShowBean hotShowBean=JsonUtil.JsonToHot(result);
                String[] temp=new String[hotShowBean.getHot().size()];
                for (int i = 0; i < hotShowBean.getHot().size(); i++) {
                    temp[i]=hotShowBean.getHot().get(i).getName();
                }
                equipType=temp;

                // Spinner适配器
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(XZHL1810_HotShowActivity.this,
                    android.R.layout.simple_spinner_item,  equipType);

                // 设置下拉列表
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // 将typetypeSpinner 添加到typeSpinner
                spEquip.setAdapter(adapter);
            }

            // 0为失败
            if (ZERO.equals(result)) {
                MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(),  Msg.I027), Toast.LENGTH_SHORT);

            }

            // 服务器异常
            else if(CommonConst.TALENTERRORSTATES.equals(result)||CommonConst.HOSTERRORSTATES.equals(result)){
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                        .getMessage(getApplicationContext(), Msg.E0002), Toast.LENGTH_SHORT);
            }

            // 打印log
            Log.v(TAG, result);

            super.onPostExecute(result);

            // log打印结束
            LogUtil.logEnd();
        }
    }

    /**
     * 点击方法实现
     * @param v 点击的控件
     */
    @Override
    public void onClick(View v) {

        // log打印开始
        LogUtil.logStart();
        switch (v.getId()) {

        // 返回按钮
        case R.id.iv_gotoback_xzhl1810:
            finish();
            break;

        // 查询按钮
        case R.id.iv_hotrefresh_xzhl1810:

            // 开始时间与结束时间的比较
            if(beginMonth>endMonth){
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.E0030,
                        CommonConst.HOTTIME), Toast.LENGTH_SHORT);
            }else{

                // 数据显示
                getData();
            }
            break;

        // 开始日期
        case R.id.tv_hotbegintime_xzhl1810:

            // 日期时间选择对话框显示
            setSearchDate(ONE, ONE);
            break;

        // 结束日期
        case R.id.tv_hotendtime_xzhl1810:

            // 日期时间选择对话框显示
            setSearchDate(ZERO, ZERO);
            break;

        // 设置按钮
        case R.id.btn_hottimeensure_xzhl1810:

            // 对画框消失
            adDate.dismiss();

            // 设置文本框显示时间
            updateMonthBegin();
            updateMonthEnd();
            break;
        default:
            break;
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 更新开始日期的TextView
     */
    private void updateMonthBegin() {

        // log打印开始
        LogUtil.logStart();
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String str = temp.format(beginDate.getTime());
        tvBegin.setText(str);

        // 毫秒
        beginMonth = beginDate.getTimeInMillis();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 更新结束日期的TextView
     */
    private void updateMonthEnd() {

        // log打印开始
        LogUtil.logStart();
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String str = temp.format(endDate.getTime());
        tvEnd.setText(str);

        // 毫秒
        endMonth = endDate.getTimeInMillis();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 设置日期、时间选择对话框
     * @param day 两个文本框
     * @param number 判断弹出的是哪个日期
     */
    private void setSearchDate(final String day, String number){

        // log打印开始
        LogUtil.logStart();
        adDate = new AlertDialog.Builder(this).create();
        adDate.show();
        Window window = adDate.getWindow();
        window.setContentView(R.layout.xzhl1810_hottime);
        DatePicker datePicker = (DatePicker) window.findViewById(R.id.dp_date_xzhl1810);
        TimePicker timePicker = (TimePicker) window.findViewById(R.id.tp_time_xzhl1810);
        datePicker.setCalendarViewShown(false);

        // 时间24小时
        timePicker.setIs24HourView(true);
        if(ONE.equals(number)){

            // 时间默认显示
            timePicker.setCurrentHour(beginDate.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(beginDate.get(Calendar.MINUTE));

            // 开始日期
            datePicker.init(beginDate.get(Calendar.YEAR), beginDate.get(Calendar.MONTH),
                    beginDate.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

                        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
                            beginDate.set(Calendar.YEAR,  year);
                            beginDate.set(Calendar.MONTH,  monthOfYear);
                            beginDate.set(Calendar.DAY_OF_MONTH,  dayOfMonth);
                        }
                    });

            // 开始时间
            timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    beginDate.set(Calendar.HOUR_OF_DAY,  hourOfDay);
                    beginDate.set(Calendar.MINUTE,  minute);
                }
            });
        }else{

            // 时间默认显示
            timePicker.setCurrentHour(endDate.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(endDate.get(Calendar.MINUTE));

            // 结束日期
            datePicker.init(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH),
                    endDate.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {

                        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                            int dayOfMonth) {
                            endDate.set(Calendar.YEAR,  year);
                            endDate.set(Calendar.MONTH,  monthOfYear);
                            endDate.set(Calendar.DAY_OF_MONTH,  dayOfMonth);
                        }
                    });

            // 结束时间
            timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {

                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    endDate.set(Calendar.HOUR_OF_DAY,  hourOfDay);
                    endDate.set(Calendar.MINUTE,  minute);
                }
            });
        }
        timeSet=(Button) window.findViewById(R.id.btn_hottimeensure_xzhl1810);
        timeSet.setText(MessageUtil.getMessage(getApplicationContext(),  Msg.I012));
        timeSet.setOnClickListener(this);

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 数据显示
     */
    private void getData(){

        // log打印开始
        LogUtil.logStart();

        // 判断网络是否异常,没有异常
        if (Commonutil.isNetworkAvailable(getApplicationContext())) {
            if (equipType != null) {

                // 得到选中的设备值
                for (int i = 0; i < equipType.length; i++) {
                    if (equipType[i].equals(spEquip.getSelectedItem()
                            .toString())) {
                        equipText = i + KONG;
                    }
                }

                // 调用异步方法
                HotAsyncTask hotAsyncTask = new HotAsyncTask();
                hotAsyncTask.execute();
            }else{

                // 向服务器接受列表
                HotListAsyncTask hotListAsyncTask=new HotListAsyncTask();
                hotListAsyncTask.execute();
            }
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 服务器接收数据显示的异步类
     *
     * 向服务器接受数据，完成信息的显示
     */
    private class HotAsyncTask extends AsyncTask<String, Integer, String>{

        /**
         * 后台取数据
         * @param params 参数
         * @return resultInfo
         */
        @Override
        protected String doInBackground(String... params) {

            // log打印开始
            LogUtil.logStart();

            // 提交数据,封装数据
            String portName = "camera/show";
            String serial = "a001";
            String[] key = { "beginDate", "endDate", "deviceId"};
            String[] value = { String.valueOf(beginMonth), String.valueOf(endMonth), equipText };
            String id = sharedPerf.getString(KEY, KONG);

            // 调用网络方法
            WebServiceOfHttps webSO = new WebServiceOfHttps();

            // 请求数据
            String jSon = JsonUtil.DataToJson(serial, JsonUtil.DataToJson(key, value), id, JsonUtil.getSign(serial,
                JsonUtil.DataToJson(key, value), id));
            String info = webSO.WSservers(XZHL1810_HotShowActivity.this, portName, jSon);

            // 解析Jason中success的值
            String resultInfo=JsonUtil.successJSON(info);

            // 1为成功
            if(ONE.equals(resultInfo)){

                try {
                    String path = new JSONObject(info).getString(DATA);
                    if (path != null) {
                        btPicture = Commonutil.loadBitmapWithOutCash(path);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtil.logException(e);
                }
            }

            // log打印结束
            LogUtil.logEnd();
            return info;
        }

        /**
         * 从doInBackground方法中得到值作相应判断
         * @param result 返回值
         */
        @Override
        protected void onPostExecute(String result) {

            // log打印开始
            LogUtil.logStart();

            // 打印log
            Log.v(TAG, result);

            // 解析Json中success的值
            String resultInfo=JsonUtil.successJSON(result);

            // 0为失败
            if (ZERO.equals(resultInfo)) {
                MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(),  Msg.I027), Toast.LENGTH_SHORT);

                // 设置图片显示
                ivHotDisplay.setImageResource(R.drawable.error);
            }

            // 1为成功
            else if(ONE.equals(resultInfo)){
                Bitmap b=Bitmap.createScaledBitmap(btPicture, windowWidth, windowWidth, false);
                ivHotDisplay.setImageBitmap(b);
            }

            // 服务器异常
            else{
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                        .getMessage(getApplicationContext(), Msg.E0002), Toast.LENGTH_SHORT);

                // 设置图片显示
                ivHotDisplay.setImageResource(R.drawable.error);
            }

            super.onPostExecute(result);

            // log打印结束
            LogUtil.logEnd();
        }
    }
}
