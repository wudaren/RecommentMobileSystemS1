/* XZHL1240_ClerkModify.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/* 项目名称 ：信智互联                                                                                                 */
/* 画面ＩＤ ：XZHL1240                                                                                                 */
/* 画面名 ：各门店店员数汇总                                                                                            */
/* 实现功能 ：显示各个门店店员数一览表                                                                                   */
/*                                                                                                                    */
/* 变更历史                                                                                                            */
/* NO 日付 Ver 更新者 内容                                                                                             */
/* 1 2014/05/23 V01L01 FBSE）张宁 新規作成                                                                             */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/

package com.fbse.recommentmobilesystem.XZHL1240;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL1210.XZHL1210_ClerkDisplayActivity;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebService;

/**
 * 画面显示类
 * 显示店铺店员数
 */
public class XZHL1240_StoreCountsActivity extends Activity {

    // 返回按钮定义
    private Button btnGoBack;

    // 店员数列表
    private ListView lvYuanShu;

    // 店员列表的适配器
    private XZHL1240_Adapter adapter;

    // 存储店员信息的list
    private List<Map<String, String>> list;

    // 加载中的进度条
    private ProgressBar pbLoading;

    // message资源文件
    private Properties properties = null;

    // 线程返回值OK
    private static final Integer OK = 1;

    // 本地异常
    private static final Integer LOCALERROR = 2;

    // 远程异常
    private static final Integer NETERROR = 3;

    /**
     * 启动activity时调用初始化方法
     * @param savedInstanceState 默认参数,画面重绘存值
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        LogUtil.logStart();
        super.onCreate(savedInstanceState);
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 定义布局
        setContentView(R.layout.xzhl1240_clerkcountstotal);
        // 控件与布局绑定方法
        findViewById();
        // 获得properties文件
        getProperties();
        // 设置监听事件
        setListener();
        LogUtil.logEnd();

    }

    /**
     * 页面加载时执行
     */
    @Override
    protected void onResume() {

        LogUtil.logStart();
        // 初始化数据
        doTsak();
        super.onResume();
        LogUtil.logEnd();
    }

    /**
     * 获得properties文件
     */
    public void getProperties() {

        LogUtil.logStart();
        properties = Commonutil.loadProperties(XZHL1240_StoreCountsActivity.this);
        LogUtil.logEnd();
    }

    /**
     * 网络状况判断，执行异步线程
     */
    private void doTsak() {

        LogUtil.logStart();
        if (Commonutil.isNetworkAvailable(XZHL1240_StoreCountsActivity.this)) {
            GetDianyuan getDianyuan = new GetDianyuan();
            getDianyuan.execute();
        }
        LogUtil.logEnd();
    }

    /**
     * 将layout当中的控件与activity当中的控件绑定
     */
    private void findViewById() {

        LogUtil.logStart();
        lvYuanShu = (ListView) findViewById(R.id.lv_dianyuanshu_1240);
        btnGoBack = (Button) findViewById(R.id.btn_goback_1240);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading_1240);
        LogUtil.logEnd();
    }

    /**
     * 设置监听事件
     */
    private void setListener() {

        LogUtil.logStart();
        // 设置返回按钮的监听事件
        btnGoBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                LogUtil.logStart();
                // 操作点击返回按钮，关闭本页面
                XZHL1240_StoreCountsActivity.this.finish();
                LogUtil.logEnd();
            }
        });
        // 设置点击listview的事件
        lvYuanShu.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                LogUtil.logStart();
                // 新建intent
                Intent intent = new Intent();
                // 设置跳转到XZHL1210_ClerkDisplay页面
                intent.setClass(XZHL1240_StoreCountsActivity.this, XZHL1210_ClerkDisplayActivity.class);
                // 增加传值 以store作为key
                intent.putExtra(CommonConst.STORE, list.get(arg2).get(CommonConst.MENDIAN));
                // 启动新页面
                startActivity(intent);
                LogUtil.logEnd();
            }
        });
        LogUtil.logEnd();
    }

    /**
     * GetDianyuan
     * 开启异步线程，获得店员数列表
     */
    class GetDianyuan extends AsyncTask<String, Integer, Integer> {

        /**
         * 开启异步线程，执行网络获取方法
         * @param params 传值参数
         * @return 线程标记
         */
        @Override
        protected Integer doInBackground(String... params) {

            LogUtil.logStart();
            // 调用webservuce访问网络
            WebService webService = new WebService();
            // 取得返回值
            String result = webService.WSserversofdaili(XZHL1240_StoreCountsActivity.this, CommonConst.LISTSTORE,
                    CommonConst.USERPORT, null);
            // 获得解析值 并赋值给全局list
            list = jiexi(result);
            LogUtil.logEnd();
            // 判断list的状态 给主线程返回值
            if (list != null) {
                if (list.size() == 0) {
                    return LOCALERROR;
                }
                return OK;
            }
            return NETERROR;
        }

        /**
         * 根据返回值进行现实操作
         * @param result
         *            结果值
         */
        @Override
        protected void onPostExecute(Integer result) {

            LogUtil.logStart();
            // 获得返回值后将进度条取消
            pbLoading.setVisibility(View.GONE);
            super.onPostExecute(result);
            // 显示获得数据
            if (result == OK) {
                adapter = new XZHL1240_Adapter(XZHL1240_StoreCountsActivity.this, list);
                lvYuanShu.setAdapter(adapter);
            } else if (result == NETERROR) {
                // 提示远程服务器异常
                MessageUtil.commonToast(XZHL1240_StoreCountsActivity.this,
                        properties.getProperty(Msg.E0027, CommonConst.SPACE), Toast.LENGTH_SHORT);
            } else if (result == LOCALERROR) {
                // 提示本地服务器异常
                MessageUtil.commonToast(XZHL1240_StoreCountsActivity.this,
                        properties.getProperty(Msg.E0028, CommonConst.SPACE), Toast.LENGTH_SHORT);
            }
            LogUtil.logEnd();
        }

    }

    /**
     * 解析从网络段获得的json
     * @param json
     *            网络获得的JSON
     * @return 返回 Json解析出的list
     */
    private List<Map<String, String>> jiexi(String json) {

        LogUtil.logStart();
        try {
            List<Map<String, String>> list = null;
            JSONObject jsonObject = new JSONObject(json);
            list = new ArrayList<Map<String, String>>();
            if (CommonConst.CHENGGONG.equals(jsonObject.getString(CommonConst.SUCCESS))) {
                JSONArray jsonArray = jsonObject.getJSONArray(CommonConst.DATA);
                Map<String, String> map = null;

                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        map = new HashMap<String, String>();

                        map.put(CommonConst.MENDIAN, jsonArray.getJSONObject(i).getString(CommonConst.STORE));
                        map.put(CommonConst.SHULIANG, jsonArray.getJSONObject(i).getString(CommonConst.COUNT));
                        list.add(map);
                    }
                }
                LogUtil.logEnd();
                return list;
            } else if (CommonConst.FALSE.equals(jsonObject.getString(CommonConst.SUCCESS))) {
                LogUtil.logEnd();
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.logException(e);
            return list;
        }
        LogUtil.logEnd();
        return list;

    }
}
