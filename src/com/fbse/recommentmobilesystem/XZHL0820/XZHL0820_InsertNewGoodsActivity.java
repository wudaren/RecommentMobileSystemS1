/*  XZHL0820_InsertNewGoodsActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ     ：XZHL0820                                                                                                */
/*  画面名     ：商品录入                                                                                                     */
/*  实现功能 ：显示商品录入                                                                                                    */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/22   V01L01      FBSE)高振川      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0820;

import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.fbse.recommentmobilesystem.common.common_entity.Category;

/**
 *  商品录入显示类
 *
 *  完成商品录入
 */
@SuppressLint("WorldReadableFiles")
public class XZHL0820_InsertNewGoodsActivity extends Activity implements OnClickListener {

    // 文本录入最大值限制
    private static final int MAX = 50;

    // 返回 按钮
    private ImageView ivBackMenu;

    // 提交按钮
    private ImageView ivSubmit;

    // 商品品类下拉选框
    private Spinner spGoodsRole;

    // 供应商下拉选框
    private Spinner spSupporterRole;

    // 文本输入框：商品名称
    private EditText etGoodsName;

    // 文本输入框：商品货号
    private EditText etGoodsNumber;

    // 文本输入框：库存
    private EditText etStacks;

    // 文本输入框：市场价格
    private EditText etPrice;

    // 文本输入框：实际销售价格
    private EditText etRealPrice;

 // 配置文件信息
    private Properties properties = null;

    // 获取登录者信息变量
    private SharedPreferences sharedPreferences;

    // 初始化用户 id
    private String id;

    // 初始化商品类别
    private String[] goodsType;

    // 初始化商品供应商
    private String[] goodsSuppooter;

    // 初始化界面适配器
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logStart();
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 初始化页面布局
        setContentView(R.layout.xzhl0820_shopload);

        // 初始化界面
        initView();

        // 初始化用户界面数据
        initData();

        // 初始化下拉列表
        initSelect();

        // 文本框监听事件
        textChangeEvent();
        LogUtil.logEnd();
    }

    /**
     * 初始化商品录入界面
     */
    private void initView(){

        LogUtil.logStart();
        ivBackMenu = (ImageView) findViewById(R.id.iv_gobackmenu_0820);
        spGoodsRole = (Spinner) findViewById(R.id.sp_agoodstype_0820);
        spSupporterRole = (Spinner) findViewById(R.id.sp_supporter_0820);
        etGoodsName = (EditText) findViewById(R.id.et_goodsname_0820);
        etGoodsNumber = (EditText) findViewById(R.id.et_goodsnumber_0820);
        etStacks = (EditText) findViewById(R.id.et_stacks_0820);
        etPrice = (EditText) findViewById(R.id.et_saleprice_0820);
        etRealPrice = (EditText) findViewById(R.id.et_suresale_0820);
        ivBackMenu.setOnClickListener(this);
        ivSubmit = (ImageView) findViewById(R.id.iv_submitgoods_0820);
        ivSubmit.setOnClickListener(this);
        etGoodsName.setFocusable(true);
        etGoodsName.setFocusableInTouchMode(true);
        etGoodsName.requestFocus();
        LogUtil.logEnd();
    }

    /**
     * 初始化用户界面数据
     */
    private void initData(){
        LogUtil.logStart();
        properties = Commonutil.loadProperties(this);
        sharedPreferences = this.getSharedPreferences("data", Context.MODE_WORLD_READABLE);
        id = sharedPreferences.getString("ID", CommonConst.SPACE);
        LogUtil.logEnd();
    }

    /**
     * 画面按钮点击响应事件
     * @param arg0 按钮的id
     */
    @Override
    public void onClick(View arg0) {
        LogUtil.logStart();
        switch (arg0.getId()) {
        // 点击返回按钮响应事件
        case R.id.iv_gobackmenu_0820:
            if(isHaveValue()){
                showReturnDialog();
            }else{
                finish();
            }
            break;
        // 点击提交响应事件
        case R.id.iv_submitgoods_0820:
            if(isNotNullInput()){
                showSubmitDialog();
            }
            break;
        default:
            break;
        }
        LogUtil.logEnd();
    }

    /**
     * 是否确认添加商品对话框
     */
    private void showSubmitDialog() {

        LogUtil.logStart();
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage(MessageUtil.getMessage(this, Msg.Q010,
            new String[]{CommonConst.GOODSINFO}));
        // 用户选择"是"的情况
        builder.setPositiveButton(properties.getProperty(Msg.I012, CommonConst.SPACE),
            new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LogUtil.logStart();
                    dialog.dismiss();
                    SubmitGoodsInfo info = new SubmitGoodsInfo();
                    info.execute();
                    LogUtil.logEnd();
                }
            });

        // 用户选择"否"的情况
        builder.setNegativeButton(properties.getProperty(Msg.I005, CommonConst.SPACE),
            new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LogUtil.logStart();
                    dialog.dismiss();
                    LogUtil.logEnd();
                }
            });
        builder.create().show();
        LogUtil.logEnd();
    }

    /**
     * 是否确认确定要返回到前画面
     */
    private void showReturnDialog() {

        LogUtil.logStart();
        AlertDialog.Builder builder = new Builder(this);
        builder.setMessage(properties.getProperty(Msg.Q013, CommonConst.SPACE));
        // 用户选择"是"的情况
        builder.setPositiveButton(properties.getProperty(Msg.I012, CommonConst.SPACE),
            new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtil.logStart();
                        dialog.dismiss();
                        finish();
                        LogUtil.logEnd();
                    }
                });

        // 用户选择"否"的情况
        builder.setNegativeButton(properties.getProperty(Msg.I005, CommonConst.SPACE),
            new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LogUtil.logStart();
                        dialog.dismiss();
                        LogUtil.logEnd();
                    }
                });
        builder.create().show();
        LogUtil.logEnd();
    }

    /**
     * 是否输入校验
     * @return result 返回用户是否输入信息，如果录入返回true，如果没有录入返回false
     */
    private boolean isHaveValue(){

        LogUtil.logStart();
        boolean result = false;
        // 判断页面文本框中是否录入值
        if(!CommonConst.SPACE.equals(etGoodsName.getText().toString().trim())
                || !CommonConst.SPACE.equals(etGoodsNumber.getText().toString().trim())
                || !CommonConst.SPACE.equals(etStacks.getText().toString().trim())
                || !CommonConst.SPACE.equals(etPrice.getText().toString().trim())
                || !CommonConst.SPACE.equals(etRealPrice.getText().toString().trim())){
            result = true;
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 非空校验
     * @return result 校验是否必填录入，如果必填录入返回true，如果必填没有录入返回false
     */
    private boolean isNotNullInput(){

        LogUtil.logStart();
        boolean result = false;
        // 判断页面文本框中是否录入值
        if(!CommonConst.SPACE.equals(etGoodsName.getText().toString().trim())
                && !CommonConst.SPACE.equals(etGoodsNumber.getText().toString().trim())
                && !CommonConst.SPACE.equals(etStacks.getText().toString().trim())
                && !CommonConst.SPACE.equals(etPrice.getText().toString().trim())
                && !CommonConst.SPACE.equals(etRealPrice.getText().toString().trim())){

            // 校验库存
            if(!XZHL0820_JsonUtil.checkStacks(etStacks.getText().toString().trim())){
                MessageUtil.commonToast(this,
                        MessageUtil.getMessage(this, Msg.E0029,
                           new String[]{CommonConst.GOODSSTACKS}), Toast.LENGTH_SHORT);
                etStacks.requestFocus();
            }
            // 校验市场价格
            else if(!XZHL0820_JsonUtil.checkMoney(etPrice.getText().toString().trim())){
                MessageUtil.commonToast(this,
                        MessageUtil.getMessage(this, Msg.E0029,
                           new String[]{CommonConst.GOODSPRICE}), Toast.LENGTH_SHORT);
                etPrice.requestFocus();
            }
            // 校验真实价格
            else if(!XZHL0820_JsonUtil.checkMoney(etRealPrice.getText().toString().trim())){
                MessageUtil.commonToast(this,
                        MessageUtil.getMessage(this, Msg.E0029,
                           new String[]{CommonConst.GOODSREALPRICE}), Toast.LENGTH_SHORT);
                etRealPrice.requestFocus();
            }else{
                result = true;
            }
        }else{
            // 商品名称为空的情况
            if(CommonConst.SPACE.equals(etGoodsName.getText().toString().trim())){
                MessageUtil.commonToast(this,
                    MessageUtil.getMessage(this, Msg.E0016,
                        new String[]{CommonConst.GOODSNAME}), Toast.LENGTH_SHORT);
                etGoodsName.requestFocus();
            }

            // 商品货号为空的情况
            else if(CommonConst.SPACE.equals(etGoodsNumber.getText().toString().trim())){
                MessageUtil.commonToast(this,
                     MessageUtil.getMessage(this, Msg.E0016,
                        new String[]{CommonConst.GOODSNUMBER}), Toast.LENGTH_SHORT);
                etGoodsNumber.requestFocus();
            }

            // 库存为空的情况
            else if(CommonConst.SPACE.equals(etStacks.getText().toString().trim())){
                MessageUtil.commonToast(this,
                     MessageUtil.getMessage(this, Msg.E0016,
                        new String[]{CommonConst.GOODSSTACKS}), Toast.LENGTH_SHORT);
                etStacks.requestFocus();
            }

            // 市场价格为空的情况
            else if(CommonConst.SPACE.equals(etPrice.getText().toString().trim())){
                MessageUtil.commonToast(this,
                     MessageUtil.getMessage(this, Msg.E0016,
                        new String[]{CommonConst.GOODSPRICE}), Toast.LENGTH_SHORT);
                etPrice.requestFocus();
            }

            // 真实价格为空的情况
            else if(CommonConst.SPACE.equals(etRealPrice.getText().toString().trim())){
                MessageUtil.commonToast(this,
                     MessageUtil.getMessage(this, Msg.E0016,
                        new String[]{CommonConst.GOODSREALPRICE}), Toast.LENGTH_SHORT);
                etRealPrice.requestFocus();
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 提交新增商品请求
     * @param null
     * @return String result 返回服务器返回的JSON
     */
    private String postNewGoods(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"goodsName", "goodsType", "goodsNumber", "stocks", "supplier", "price", "realityPrice"};
        String[] value = {etGoodsName.getText().toString().trim() , spGoodsRole.getSelectedItem().toString(),
                etGoodsNumber.getText().toString().trim(), etStacks.getText().toString().trim(),
                spSupporterRole.getSelectedItem().toString().trim(), etPrice.getText().toString().trim(),
                etRealPrice.getText().toString().trim()};
        Log.v("json1---------->>>", JsonUtil.DataToJson("a001",
                JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        String json = woh.WSservers(this, "goods/add",
            JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        Log.v("json2---------->>>", json);
        // 本地服务器连接异常的情况
        if(CommonConst.TALENTERRORSTATES.equals(json)){
            result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
        }else{
            // 正常获得请求数据
            if(JsonUtil.errorJson(json)==null){
                result = null;

            // 远程服务器连接异常的情况
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
            }else{
                result = JsonUtil.errorJson(json).getErr_msg();
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 获取商品列表下拉列表
     * @param null
     * @return String result 返回服务器返回的JSON
     */
    private String getGoodsCategory(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"timestamp"};
        String[] value = {"0"};
        Log.v("json1getGoodsCategory---------->>>", JsonUtil.DataToJson("a001",
                JsonUtil.DataToJson(key, value), id,
                JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        String json = woh.WSservers(this, "category",
                JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                    JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        // 本地服务器连接异常的情况
        if(CommonConst.TALENTERRORSTATES.equals(json)){
            result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
        }else{
            // 正常获得请求数据
            if(JsonUtil.errorJson(json)==null){
            // 远程服务器连接异常的情况
                Category category = XZHL0820_JsonUtil.jsonToCategory(json);
                goodsType = new String[category.getObj().size()];
                if(category.getObj()!=null){
                    for(int i = 0; i< category.getObj().size(); i++){
                        Log.v("json2getGoodsCategory-----SSSSSSSSSS----->>>", category.getObj().get(i).getName());
                        goodsType[i] = category.getObj().get(i).getName();
                    }
                }
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
            }else{
                result = JsonUtil.errorJson(json).getErr_msg();
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 获取供应商下拉列表
     * @param null
     * @return String result 返回服务器返回的JSON
     */
    private String getSupportCategory(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"timestamp"};
        String[] value = {"0"};
        Log.v("json1getSupportCategory---------->>>", JsonUtil.DataToJson("a001",
                JsonUtil.DataToJson(key, value), id,
                    JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        String json = woh.WSservers(this, "supplier",
                JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                    JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));
        // 本地服务器连接异常的情况
        if(CommonConst.TALENTERRORSTATES.equals(json)){
            result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
        }else{
            // 正常获得请求数据
            if(JsonUtil.errorJson(json)==null){
            // 远程服务器连接异常的情况
                Category category = XZHL0820_JsonUtil.jsonToCategoryList(json);
                goodsSuppooter = new String[category.getObj().size()];
                if(category.getObj()!=null){
                    for (int i = 0; i < category.getObj().size(); i++) {
                        goodsSuppooter[i] = category.getObj().get(i).getName();
                        Log.v("goodsSuppooter<<<<<<<<<<<<<<<<---------->>>", category.getObj().get(i).getName());
                    }
                }
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
            }else{
                result = JsonUtil.errorJson(json).getErr_msg();
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     *  提交商品录入类
     *
     *  异步提交商品录入信息
     */
    class SubmitGoodsInfo extends AsyncTask<Object, Object, String>{

        @Override
        protected String doInBackground(Object... arg0) {
            LogUtil.logStart();
            LogUtil.logEnd();
            return postNewGoods();
        }

        @Override
        protected void onPostExecute(String result) {
            LogUtil.logStart();
            if(result == null){
                MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this,
                    MessageUtil.getMessage(XZHL0820_InsertNewGoodsActivity.this, Msg.I019,
                        new String[]{CommonConst.INFO}), Toast.LENGTH_SHORT);
                finish();
            }else{
                MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this, result, Toast.LENGTH_SHORT);
            }
            super.onPostExecute(result);
            LogUtil.logEnd();
        }
    }


    /**
     * 查询商品列表信息
     */
    private void initSelect(){
        LogUtil.logStart();
        GetSelectData select = new GetSelectData();
        select.execute();
        LogUtil.logEnd();
    }

    /**
     *  提交商品录入类
     *
     *  异步提交商品录入信息
     */
    class GetSelectData extends AsyncTask<Object, Object, String>{

        @Override
        protected String doInBackground(Object... arg0) {
            LogUtil.logStart();
            getGoodsCategory();
            getSupportCategory();
            LogUtil.logEnd();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            LogUtil.logStart();
            adapter = new ArrayAdapter<String>(XZHL0820_InsertNewGoodsActivity.this,
                    android.R.layout.simple_spinner_item, goodsType);
            // 设置下拉列表
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // 将typetypeSpinner 添加到typeSpinner�?
            spGoodsRole.setAdapter(adapter);

            // 添加事件typeSpinner事件监听
            spGoodsRole.setOnItemSelectedListener(null);

            // 设置默认
            spGoodsRole.setVisibility(View.VISIBLE);

            adapter = new ArrayAdapter<String>(
                XZHL0820_InsertNewGoodsActivity.this,
                    android.R.layout.simple_spinner_item, goodsSuppooter);
            // 设置下拉列表
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // 将typetypeSpinner 添加到typeSpinner�?
            spSupporterRole.setAdapter(adapter);

            // 添加事件typeSpinner事件监听
            spSupporterRole.setOnItemSelectedListener(null);

            // 设置默认
            spSupporterRole.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
            LogUtil.logEnd();

        }
    }

    /**
     *  文本框输入值限制
     */
    private void textChangeEvent(){

        LogUtil.logStart();
        // 商品名称校验
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
                    MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this,
                        properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etGoodsName.setText(s);
                    etGoodsName.setSelection(tempSelection);
                }
                LogUtil.logEnd();
            }
        });

        // 商品编号校验
        etGoodsNumber.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etGoodsNumber.getSelectionStart();
                selectionEnd = etGoodsNumber.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);

                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etGoodsNumber.setText(s);
                    etGoodsNumber.setSelection(tempSelection);
                }
                LogUtil.logEnd();
            }
        });

        // 商品库存校验
        etStacks.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etStacks.getSelectionStart();
                selectionEnd = etStacks.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etStacks.setText(s);
                    etStacks.setSelection(tempSelection);
                }
                LogUtil.logEnd();
            }
        });

        // 商品市场价格校验
        etPrice.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etPrice.getSelectionStart();
                selectionEnd = etPrice.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etPrice.setText(s);
                    etPrice.setSelection(tempSelection);

                }
                LogUtil.logEnd();
            }
        });

        // 商品真实价格       校验
        etRealPrice.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etRealPrice.getSelectionStart();
                selectionEnd = etRealPrice.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0820_InsertNewGoodsActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etRealPrice.setText(s);
                    etRealPrice.setSelection(tempSelection);
                }
                LogUtil.logEnd();
            }
        });
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
            // 页面中是否输入值
            if(isHaveValue()){
                // 如果输入，显示确认对话框
                showReturnDialog();
            }else{
                // 否则关闭页面
                finish();
            }
            LogUtil.logEnd();
            return true;
        }else{
            LogUtil.logEnd();
            return super.onKeyDown(keyCode, event);
        }

    }

}
