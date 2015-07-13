/*  XZHL0810_ShopDisplayActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                  */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL0810                                                                                           */
/*  画面名     ：商品信息查询                                                                                       */
/*  实现功能 ：显示商品列表和查询商品列表                                                                            */
/*                                                                                                                    */
/*  变更历史                                                                                                         */
/*      NO  日付                       Ver         更新者                 内容                                      */
/*      1   2014/05/19   V01L01      FBSE)高振川      新規作成                                                      */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0810;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0820.XZHL0820_InsertNewGoodsActivity;
import com.fbse.recommentmobilesystem.XZHL0830.XZHL0830_ShopAllDisplay;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 *  商品列表显示画面
 *
 *  完成对商品列表的显示
 */
public class XZHL0810_ShopDisplayActivity extends Activity implements OnClickListener, XZHL0810_RefreshInterface {

    // 文本录入最大值限制
    private static final int MAX = 50;

    // 界面返回按钮
    private ImageView ivGoBack;

    // 界面添加按钮
    private ImageView ivGoAdd;

    // 商品一览列表
    private ListView lvGoodsList;

    // 页面布局Layout
    private LinearLayout llLinearLayout;

    // 页面布局进度条
    private ProgressBar pgProgressBar;

    // 页面布局进度条下标显示
    private TextView tvData;

    // 页面布局适配器
    private XZHL0810_GoodsItemsAdapter itemAdapter;

    // 商品数组
    private List<XZHL0810_GoodsItemBean> list = new ArrayList<XZHL0810_GoodsItemBean>();

    // 加载配置文件
    private Properties properties;

    // 页面布局检索框
    private EditText etGoodsName;

    // 服务器返回信息
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logStart();
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl0810_shopdisplay);

        // 初始化页面组件
        initView();

        // 页面布局检索框事件
        textChangeEvent();
        LogUtil.logEnd();
    }

    /**
     * 初始化用户列表的界面
     */
    private void initView(){
        LogUtil.logStart();
        tvData = (TextView) findViewById(R.id.tv_null_0810);
        pgProgressBar = (ProgressBar) findViewById(R.id.pb_progressbar_0810);
        etGoodsName = (EditText) findViewById(R.id.et_goodsname_0810);
        ivGoBack = (ImageView) findViewById(R.id.iv_returnmenu_0810);
        ivGoAdd = (ImageView) findViewById(R.id.iv_insertshoplist_0810);
        llLinearLayout = (LinearLayout) findViewById(R.id.ll_linearlayout_0810);
        lvGoodsList = (ListView) findViewById(R.id.lv_shoplist_0810);
        ivGoAdd.setOnClickListener(this);
        ivGoBack.setOnClickListener(this);
        llLinearLayout.setOnClickListener(this);
        pgProgressBar.setVisibility(View.VISIBLE);
        tvData.setVisibility(View.VISIBLE);
        properties = Commonutil.loadProperties(this);
        LogUtil.logEnd();
    }

    /**
     * 初始化用户列表的数据
     */
    private void initData(){
        LogUtil.logStart();
        if(list.size()==0){
            MessageUtil.commonToast(XZHL0810_ShopDisplayActivity.this,
                    properties.getProperty(Msg.I022, CommonConst.SPACE), Toast.LENGTH_SHORT);
        }
        // 异步请求商品列
        itemAdapter = new XZHL0810_GoodsItemsAdapter(this, list);
        lvGoodsList.setAdapter(itemAdapter);
        listener();
        LogUtil.logEnd();
    }

    /**
     * 页面查询方法
     */
    private void selectSearch(){
        LogUtil.logStart();
        XZHL0810_GoodsManager manager = new XZHL0810_GoodsManager(this);
        list.clear();
        list = manager.selectGoodsList(etGoodsName.getText().toString().trim());
        if(list.size()==0){
            MessageUtil.commonToast(XZHL0810_ShopDisplayActivity.this,
                    properties.getProperty(Msg.I027, CommonConst.SPACE), Toast.LENGTH_SHORT);
        }
        itemAdapter = new XZHL0810_GoodsItemsAdapter(this, list);
        lvGoodsList.setAdapter(itemAdapter);
        listener();
        LogUtil.logEnd();
    }

    /**
     * 页面点击事件
     * @param v 画面
     */
    @Override
    public void onClick(View v) {

        LogUtil.logStart();
        switch (v.getId()) {
        // 单击返回按钮
        case R.id.iv_returnmenu_0810:
            finish();
            break;
        // 单击添加按钮
        case R.id.iv_insertshoplist_0810:
            Intent intent = new Intent(getApplicationContext(),
                    XZHL0820_InsertNewGoodsActivity.class);
            startActivity(intent);
            break;
        // 单击页面主题
        case R.id.ll_linearlayout_0810:
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            break;
        default:
            break;
        }
        LogUtil.logEnd();
    }

    /**
     * 查询商品一览请求
     * @param null
     * @return String result 返回服务器返回的JSON
     */
    private String postGoodsList(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"timestamp"};
        String[] value = {"0"};
        String json = woh.WSservers(this, "goods/list",
                JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),
                        "a0065207-fefe-4166-ac00-a6a09ce5e2a1", JsonUtil.getSign("a001",
                                JsonUtil.DataToJson(key, value), "a0065207-fefe-4166-ac00-a6a09ce5e2a1")));
        //##############################
        Log.v(">>>>>>>>>>>>><<<<<<<<<>>>>>>>>", ""+JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),
                "a0065207-fefe-4166-ac00-a6a09ce5e2a1", JsonUtil.getSign("a001",
                        JsonUtil.DataToJson(key, value), "a0065207-fefe-4166-ac00-a6a09ce5e2a1")));
        Log.v(">>>>>>>>>>>>>>>>>>>>>", ""+json);
        //##############################
        // 本地服务器异常
        if("{\"states\":\"99\"}".equals(json)){
            result = properties.getProperty(Msg.E0027, CommonConst.SPACE);
        }else{
            // 查询成功
            if(JsonUtil.errorJson(json)==null){
                list = JsonUtil.JsonToGoodsList(json);
                Log.v(">>>>>>>>>>>>>>>>>>>>>", ""+list.size());
            // 远程服务器异常
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                result = properties.getProperty(Msg.E0028, CommonConst.SPACE);
            }else{
                result = JsonUtil.errorJson(json).getErr_msg();
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 异步请求商品列表
     */
    public void postServer(){
        LogUtil.logStart();
        clearDatabase();
        GetDataAsyncTask task = new GetDataAsyncTask();
        task.execute();
        LogUtil.logEnd();
    }

    /**
     * 保存商品列表
     */
    private void save(){

        LogUtil.logStart();
        XZHL0810_GoodsManager manager = new XZHL0810_GoodsManager(this);
        manager.save(list);
        LogUtil.logEnd();
    }

    /**
     * 清除商品数据
     */
    private void clearDatabase(){

        LogUtil.logStart();
        XZHL0810_GoodsManager manager = new XZHL0810_GoodsManager(this);
        manager.delete();
        LogUtil.logEnd();
    }

    /**
     * 商品列表重新激活
     */
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.logStart();
        clearDatabase();
        LogUtil.logEnd();
    }

    /**
     *  商品列表获取数据
     *
     *  完成对商品列表数据请求
     */
    class GetDataAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            LogUtil.logStart();
            msg = postGoodsList();
            LogUtil.logEnd();
            return msg;

        }

        @Override
        protected void onPostExecute(String result) {

            LogUtil.logStart();
            // 暂无数据
            if(result == null){
                pgProgressBar.setVisibility(View.GONE);
                tvData.setVisibility(View.GONE);
                initData();
                save();
                selectSearch();
            }

            // 本地服务器异常
            else if((properties.getProperty(Msg.E0027, CommonConst.SPACE)).equals(result)){
                MessageUtil.commonToast(XZHL0810_ShopDisplayActivity.this,
                        properties.getProperty(Msg.E0002, CommonConst.SPACE), Toast.LENGTH_SHORT);
            }

            // 远程服务器异常
            else if((properties.getProperty(Msg.E0028, CommonConst.SPACE)).equals(result)){
                MessageUtil.commonToast(XZHL0810_ShopDisplayActivity.this,
                        properties.getProperty(Msg.E0002, CommonConst.SPACE), Toast.LENGTH_SHORT);
            }

            // 正常显示数据
            else{
                pgProgressBar.setVisibility(View.GONE);
                tvData.setVisibility(View.GONE);
                initData();
                save();
                selectSearch();
            }
            LogUtil.logEnd();
        }
    }

    /**
     * 重新跳转到商品信息查询
     */
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.logStart();
        postServer();
        LogUtil.logEnd();
    }

    /**
     * 商品列表监听器
     */
    private void listener(){
        LogUtil.logStart();
        lvGoodsList.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LogUtil.logStart();
                Intent intent = new Intent(XZHL0810_ShopDisplayActivity.this, XZHL0830_ShopAllDisplay.class);
                XZHL0810_GoodsItemBean goods = new XZHL0810_GoodsItemBean();
                goods = list.get(arg2);
                intent.putExtra("goodsArray", goods);
                Log.i("msg", goods.toString());
                XZHL0810_ShopDisplayActivity.this.startActivity(intent);
                LogUtil.logEnd();
            }
        });
        lvGoodsList.setOnItemLongClickListener(new OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                LogUtil.logStart();
                XZHL0810_MsgDialog dialog = new XZHL0810_MsgDialog(XZHL0810_ShopDisplayActivity.this,
                        R.style.mydialog, arg2, new String[]{ list.get(arg2).getId(),
                            list.get(arg2).getGoodsName()}, list.get(arg2), XZHL0810_ShopDisplayActivity.this);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                LogUtil.logEnd();
                return true;
            }
        });
        LogUtil.logEnd();
    }

    /**
     * 搜索文本框输入改变事件
     */
    private void textChangeEvent(){
        LogUtil.logStart();
        etGoodsName.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.logStart();
                selectSearch();
                LogUtil.logEnd();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        // 查询编辑框字数限制事件
        etGoodsName.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                LogUtil.logStart();
                temp = s;
                LogUtil.logEnd();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.logStart();
                selectionStart = etGoodsName.getSelectionStart();
                selectionEnd = etGoodsName.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0810_ShopDisplayActivity.this,
                            MessageUtil.getMessage(XZHL0810_ShopDisplayActivity.this, Msg.I022,
                               new String[]{CommonConst.INFO}), Toast.LENGTH_SHORT);

                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etGoodsName.setText(s);
                    etGoodsName.setSelection(tempSelection);
                    LogUtil.logEnd();

                }
            }
        });
    }


    /**
     * 点击返回按钮响应事件
     *
     * @param keyCode 键盘码
     * @param event 事件
     * @return true 返回事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        LogUtil.logStart();
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

    /**
     * 对话框刷新Activity数据
     */
    @Override
    public void refresh() {

        LogUtil.logStart();
        clearDatabase();
        postServer();
        selectSearch();
        LogUtil.logEnd();

    }

    /**
     * 页面重新加载的刷新逻辑
     */
    public void returnRefresh() {

        LogUtil.logStart();
        if(CommonConst.SPACE.equals(etGoodsName.getText().toString().trim())){
           // postServer();
        }else{
            selectSearch();
        }
        LogUtil.logEnd();

    }
}
