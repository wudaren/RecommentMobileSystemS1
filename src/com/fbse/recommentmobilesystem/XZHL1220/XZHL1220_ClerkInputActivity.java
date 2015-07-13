/*  XZHL1220_ClerkInputActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                   */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1220                                                                                           */
/*  画面名     ：店员情报录入                                                                                         */
/*  实现功能 ：显示店员情报录入                                                                                       */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)高振川      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1220;

import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0310.XZHL0310_JsonUtil;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.Encrypt_Util;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 *  店员情报录入显示类
 *
 *  完成店员情报录入
 */
@SuppressLint({ "HandlerLeak", "WorldReadableFiles" })
public class XZHL1220_ClerkInputActivity extends Activity implements OnClickListener {

    // 文本录入的最大值
    private static final int MAX = 50;

    // 返回参数标识
    private static final int TAG = 2;

    // 商家权限标识
    private static final int SJ = 1;

    // 店长权限标识
    private static final int DZ = 2;

    // 店员权限标识
    private static final int DY = 3;

    // 线性布局变量
    private LinearLayout llBgLinearLayout;

    // 返回 按钮
    private ImageView ivBackMenu;

    // 提交按钮
    private ImageView ivSubmit;

    // 权限下拉选框
    private Spinner spRole;

    // 文本输入框：确认密码
    private EditText etSurePassword;

    // 文本输入框：登录密码
    private EditText etLoginPassword;

    // 文本输入框：登录名
    private EditText etLoginName;

    // 文本输入框：电话变量
    private EditText etMobile;

    // 文本输入框：姓名
    private EditText etUsername;

    // 获取登录者信息变量
    private SharedPreferences sharedPreferences;

    // 获取用户信息
    private String token;

    // 获取用户信息 id
    private String id;

    // 获取用户信息 角色
    private String role;

    // 适配器
    private ArrayAdapter<String> adapter;

    // 权限选项
    private String[] item;

    // role
    private int roleid = 3;

    // 配置文件信息
    private Properties properties = null;

    //最后一次点击时间
    private long lastClickTime;

    /**
     * Activity 界面初始化
     * @param savedInstanceState 继承参数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.logStart();
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl1220_clerkinput);

        // 获取当前用户信息
        getUserInfo();

        // 初始化界面组件
        initView();

        // 初始化Message配置文件
        initProperties();

        // 初始化权限适配器
        initAdapter();

        // 文本框焦点事件
        focusEvent();
        LogUtil.logEnd();
    }

    /**
     * 初始化配置文件
     */
    private void initProperties(){
        LogUtil.logStart();
        properties = Commonutil
                .loadProperties(XZHL1220_ClerkInputActivity.this);
        LogUtil.logEnd();
    }

    /**
     * 初始化界面组件
     */
    private void initView(){
        LogUtil.logStart();
        llBgLinearLayout = (LinearLayout) findViewById(R.id.ll_addassistantinfo_1220);
        ivBackMenu = (ImageView) findViewById(R.id.iv_gobackmenu_1220);
        ivSubmit = (ImageView) findViewById(R.id.iv_submitassistantinfo_1220);
        etSurePassword = (EditText) findViewById(R.id.et_assistantsurepassword_1220);
        etLoginPassword = (EditText) findViewById(R.id.et_assistantloginpassword_1220);
        etLoginName = (EditText) findViewById(R.id.et_assistantloginname_1220);
        etMobile = (EditText) findViewById(R.id.et_assistantmobile_1220);
        etUsername = (EditText) findViewById(R.id.et_assistantusername_1220);
        spRole = (Spinner) findViewById(R.id.sp_assistantrolespinner_1220);
        ivBackMenu.setOnClickListener(this);
        ivSubmit.setOnClickListener(this);
        llBgLinearLayout.setOnClickListener(this);
        LogUtil.logEnd();
    }

    /**
     * 权限选框适配器
     */
    private void initAdapter(){

        LogUtil.logStart();
        // 商家权限
        if(Integer.parseInt(role) == SJ){
            item = getResources().getStringArray(R.array.role);
        }

        // 店长权限
        else if(Integer.parseInt(role) == DZ){
            item = getResources().getStringArray(R.array.dzrole);

        }

        // 店员权限
        else{
            item = getResources().getStringArray(R.array.dzrole);
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, item);
        // 设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 将typetypeSpinner 添加到typeSpinner�?
        spRole.setAdapter(adapter);

        // 添加事件typeSpinner事件监听
        spRole.setOnItemSelectedListener(new SpinnerSelectedListener());

        // 设置默认
        spRole.setVisibility(View.VISIBLE);

        if(Integer.parseInt(role) == DZ){
            spRole.setEnabled(true);
        }

        // 初始不让EditText得焦点
        etUsername.setFocusable(true);
        etUsername.setFocusableInTouchMode(true);
        etUsername.requestFocus();
        LogUtil.logEnd();
    }

    /**
     * 界面响应事件
     *
     * @param v 用户界面
     */
    @Override
    public void onClick(View v) {

        LogUtil.logStart();
        // 界面的点击触发事件
        switch (v.getId()) {
            // 单击返回按钮的触发事件
        case R.id.iv_gobackmenu_1220:
            if(isHaveValue()){
                showReturnDialog();
            }else{
                finish();
            }

            break;
        // 单击提交按钮的触发事件
        case R.id.iv_submitassistantinfo_1220:
            long time = System.currentTimeMillis();
            long temp = time-lastClickTime;
            if(temp<2800){
                MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                        properties.getProperty(Msg.I025, CommonConst.SPACE),
                        Toast.LENGTH_SHORT);
            }else{
                // 非空校验
                if(isNotNullInput()){
                    if(checkLoginUsername()&&checkLoginPassword()&&similarPassword()){
                     // 是否确认添加店员
                        showSubmitDialog();
                    }

                }
            }
            break;
        // 单击页面背景的触发事件
        case R.id.ll_addassistantinfo_1220:
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            break;
        default:
            break;
        }
        LogUtil.logEnd();
    }

    /**
     * 必须输入校验
     * @return result 返回用户是否输入信息，如果录入返回true，如果没有录入返回false
     */
    private boolean isHaveValue(){

        LogUtil.logStart();
        boolean result = false;
        // 判断页面文本框中是否录入值
        if(!CommonConst.SPACE.equals(etSurePassword.getText().toString())
                || !CommonConst.SPACE.equals(etLoginPassword.getText().toString())
                || !CommonConst.SPACE.equals(etLoginName.getText().toString())
                || !CommonConst.SPACE.equals(etMobile.getText().toString())
                || !CommonConst.SPACE.equals(etUsername.getText().toString())){
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
        if(!CommonConst.SPACE.equals(etSurePassword.getText().toString())
                && !CommonConst.SPACE.equals(etLoginPassword.getText().toString())
                && !CommonConst.SPACE.equals(etLoginName.getText().toString())
                && !CommonConst.SPACE.equals(etMobile.getText().toString())
                && !CommonConst.SPACE.equals(etUsername.getText().toString())){
            result = true;
        }else{
            // 确认密码为空的情况
            if(CommonConst.SPACE.equals(etSurePassword.getText().toString())){
                MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                        MessageUtil.getMessage(this, Msg.E0016,
                                new String[]{CommonConst.SURPASSWORD}), Toast.LENGTH_SHORT);
            }

            // 登录密码为空的情况
            if(CommonConst.SPACE.equals(etLoginPassword.getText().toString())){
                MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                        MessageUtil.getMessage(this, Msg.E0016,
                                new String[]{CommonConst.LOGINPASSWORD}), Toast.LENGTH_SHORT);
            }

            // 登录名为空的情况
            if(CommonConst.SPACE.equals(etLoginName.getText().toString().trim())){
                MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                        MessageUtil.getMessage(this, Msg.E0016,
                            new String[]{CommonConst.LOGINNAMENULL}), Toast.LENGTH_SHORT);
            }

            // 电话为空的情况
            if(CommonConst.SPACE.equals(etMobile.getText().toString())){
                MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                    MessageUtil.getMessage(this, Msg.E0016,
                                new String[]{CommonConst.MOBILE}), Toast.LENGTH_SHORT);
            }

            // 用户名为空的情况
            if(CommonConst.SPACE.equals(etUsername.getText().toString())){
                MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                    MessageUtil.getMessage(this, Msg.E0016,
                                new String[]{CommonConst.NAME}), Toast.LENGTH_SHORT);
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 是否确认确定要返回到前画面
     */
    private void showReturnDialog(){

        LogUtil.logStart();
        AlertDialog.Builder builder = new Builder(XZHL1220_ClerkInputActivity.this);
        builder.setMessage(properties.getProperty(Msg.Q013,  CommonConst.SPACE));
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
            new android.content.DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which){
                    LogUtil.logStart();
                    dialog.dismiss();
                    LogUtil.logEnd();

                }
            });
        builder.create().show();
        LogUtil.logEnd();
    }

    /**
     * 是否确认添加店员对话框
     */
    private void showSubmitDialog(){

        LogUtil.logStart();
        AlertDialog.Builder builder = new Builder(XZHL1220_ClerkInputActivity.this);
        builder.setMessage(MessageUtil.getMessage(XZHL1220_ClerkInputActivity.this, Msg.Q010,
                new String[]{CommonConst.ASSISTANTINFO}));
        // 用户选择"是"的情况
        builder.setPositiveButton(properties.getProperty(Msg.I012, CommonConst.SPACE),
            new android.content.DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    LogUtil.logStart();
                    dialog.dismiss();
                    PostNewAssistantInfo info = new PostNewAssistantInfo();
                    info.start();
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
     * 提交新增店员请求
     * @param null
     * @return String result 返回服务器返回的JSON
     */
    private String postNewAssistantInfo(){

        LogUtil.logStart();
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"username", "password", "realname", "tel", "role"};
        String[] value = {etLoginName.getText().toString().trim(),
                Encrypt_Util.md5(etSurePassword.getText().toString().trim()),
                   etUsername.getText().toString().trim(),
                      etMobile.getText().toString().trim(), String.valueOf(roleid)};
        String json = woh.WSservers(this, "user/add", JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),
            id, JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), token)));
        Log.v("json", json);
        // 本地服务器连接异常的情况
        if(CommonConst.TALENTERRORSTATES.equals(json)){
            result = properties.getProperty(Msg.E0002, CommonConst.SPACE);
        }else{
            if(JsonUtil.errorJson(json)==null){
             // 远程服务器连接异常的情况
                result = null;
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
     * 获取用户信息
     */
    private void getUserInfo(){

        LogUtil.logStart();
        sharedPreferences = this.getSharedPreferences("data", Context.MODE_WORLD_READABLE);
        id = sharedPreferences.getString("ID", CommonConst.SPACE);
        token = sharedPreferences.getString("ID", CommonConst.SPACE);
        role = sharedPreferences.getString("QUANXIAN", CommonConst.SPACE);
        LogUtil.logEnd();
    }

    /**
     * 使用数组形式操作
     */
    class SpinnerSelectedListener implements OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            LogUtil.logStart();
            // 商家权限选择权限的情况
            if(Integer.parseInt(role) == SJ){
                if(CommonConst.SELLZHANG.equals(item[arg2])){
                    spRole.setSelection(arg2, true);
                    roleid = arg2+1;
                }
                if(CommonConst.SELLYUAN.equals(item[arg2])){
                    spRole.setSelection(arg2, true);
                    roleid = arg2+1;
                }
            // 店长权限选择权限的情况
            }else if(Integer.parseInt(role) == DZ){
                if(CommonConst.SELLYUAN.equals(item[arg2])){
                    spRole.setSelection(arg2, true);
                    roleid = arg2+2;
                }
            // 店员权限选择权限的情况
            }else if(Integer.parseInt(role) == DY){
                if(CommonConst.SELLYUAN.equals(item[arg2])){
                    spRole.setSelection(arg2, true);
                    roleid = arg2+2;
                }
            }
            LogUtil.logEnd();

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            LogUtil.logStart();
            spRole.setSelection(0, true);
            LogUtil.logEnd();
        }
    }

    /**
     * 设置编辑框的监听事件
     */
    private void focusEvent(){

        LogUtil.logStart();
        // 登录密码校验
        etLoginPassword.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etLoginPassword.getSelectionStart();
                selectionEnd = etLoginPassword.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE),
                                Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etLoginPassword.setText(s);
                    etLoginPassword.setSelection(tempSelection);

                }
                LogUtil.logEnd();
            }
        });

        // 电话校验
        etMobile.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etMobile.getSelectionStart();
                selectionEnd = etMobile.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                         properties.getProperty(Msg.E0026, CommonConst.SPACE),
                             Toast.LENGTH_SHORT);

                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etMobile.setText(s);
                    etMobile.setSelection(tempSelection);

                }
                LogUtil.logEnd();
            }
        });

        // 登录密码校验
        etLoginPassword.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtil.logStart();
                if (etLoginPassword.hasFocus() == false) {

                    // 失去焦点，判断输入框是否为空
                    if(!CommonConst.SPACE.equals(etLoginPassword.getText().toString())){
                        // 判断密码是否符合规则
                        if(!XZHL0310_JsonUtil.checkPassword(etLoginPassword.getText().toString().trim())){
                            etLoginPassword.setFocusable(true);
                            etLoginPassword.setFocusableInTouchMode(true);
                            etLoginPassword.clearFocus();
                            MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                                    properties.getProperty(Msg.E0015, CommonConst.SPACE), Toast.LENGTH_SHORT);
                        }
                    }else{
                        MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                                MessageUtil.getMessage(XZHL1220_ClerkInputActivity.this,
                                        Msg.E0016, new String[]{CommonConst.LOGINPASSWORD}), Toast.LENGTH_SHORT);
                    }

                }
                LogUtil.logEnd();
            }
        });

        // 确认密码校验
        etSurePassword.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etSurePassword.getSelectionStart();
                selectionEnd = etSurePassword.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE),
                                Toast.LENGTH_SHORT);

                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etSurePassword.setText(s);
                    etSurePassword.setSelection(tempSelection);

                }
                LogUtil.logEnd();
            }
        });

        // 确认密码失去焦点校验
        etSurePassword.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtil.logStart();
                if (etSurePassword.hasFocus() == false){
                    // 失去焦点，判断输入框是否为空
                    if(!CommonConst.SPACE.equals(etLoginPassword.getText().toString())){
                        if(!XZHL0310_JsonUtil.checkPassword(etSurePassword.getText().toString().trim())){
                            etSurePassword.setFocusable(true);
                            etSurePassword.setFocusableInTouchMode(true);
                            etSurePassword.clearFocus();
                            MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                                    properties.getProperty(Msg.E0015, CommonConst.SPACE), Toast.LENGTH_SHORT);
                        }

                        // 判断登录密码和确认密码是否相同
                        if(!similarPassword()){
                            etSurePassword.setFocusable(true);
                            etSurePassword.setFocusableInTouchMode(true);
                            etSurePassword.clearFocus();
                            MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                                    properties.getProperty(Msg.E0013, CommonConst.SPACE), Toast.LENGTH_SHORT);
                        }
                    }else{
                        MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                                MessageUtil.getMessage(XZHL1220_ClerkInputActivity.this,
                                Msg.E0016, new String[]{CommonConst.SURPASSWORD}), Toast.LENGTH_SHORT);
                    }
                }
                LogUtil.logEnd();
            }
        });
        addEvent();
        LogUtil.logEnd();
    }

    /**
     * 添加文本框画面监听事件
     */
    private void addEvent(){

        LogUtil.logStart();
        // 姓名校验
        etUsername.addTextChangedListener(new TextWatcher() {
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
                selectionStart = etUsername.getSelectionStart();
                selectionEnd = etUsername.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                            properties.getProperty(Msg.E0026, CommonConst.SPACE),
                                Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etUsername.setText(s);
                    etUsername.setSelection(tempSelection);
                }
                LogUtil.logEnd();
            }
        });

        // 登录名校验
        etLoginName.addTextChangedListener(new TextWatcher() {

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
                selectionStart = etLoginName.getSelectionStart();
                selectionEnd = etLoginName.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                        properties.getProperty(Msg.E0026, CommonConst.SPACE),
                            Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etLoginName.setText(s);
                    etLoginName.setSelection(tempSelection);
                }
                LogUtil.logEnd();
            }

        });
        LogUtil.logEnd();
    }

    /**
     * 校验密码是否一致
     * @return 密码是否相同的布尔值   密码相同返回true，密码不同返回false
     */
    private boolean similarPassword(){

        LogUtil.logStart();
        boolean result = false;
        if(etSurePassword.getText().toString().trim()
                .equals(etLoginPassword.getText().toString().trim())){
            result = true;
        }else{
            MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                    properties.getProperty(Msg.E0013, CommonConst.SPACE), Toast.LENGTH_SHORT);
            result = false;
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 校验用户名是否符合规范
     * @return 校验用户名是否符合规范的布尔值
     */
    private boolean checkLoginUsername(){

        LogUtil.logStart();
        boolean result = false;
        // 判断确认和登录密码是否一致
        if(XZHL0310_JsonUtil.checkUserName(etLoginName.getText().toString().trim())){
            result = true;
        }else{
            MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                    properties.getProperty(Msg.E0011, CommonConst.SPACE), Toast.LENGTH_SHORT);
            result = false;
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 校验密码是否符合规范
     * @return 校验密码是否符合规范的布尔值
     */
    private boolean checkLoginPassword(){

        LogUtil.logStart();
        boolean result = false;
        if(XZHL0310_JsonUtil.checkPassword(etLoginPassword.getText().toString().trim())){
            result = true;
        }else{
            MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                    properties.getProperty(Msg.E0015, CommonConst.SPACE), Toast.LENGTH_SHORT);
            result = false;
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     *  店员情报录入画面后台类
     *
     *  异步完成提交录入信息
     */
    class PostNewAssistantInfo extends Thread{

        @Override
        public void run() {
            LogUtil.logStart();
            Message msg = new Message();
            msg.what = TAG;
            msg.obj = postNewAssistantInfo();
            handler.sendMessage(msg);
            LogUtil.logEnd();

        }
    }

    /**
     * 接收异步注册店员的返回结果
     */
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.logStart();
            if(msg.what == TAG){
                if (msg.obj == null) {
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                        MessageUtil.getMessage(XZHL1220_ClerkInputActivity.this, Msg.I019,
                            new String[]{CommonConst.ASSISTANTINFO}), Toast.LENGTH_SHORT);
                    XZHL1220_ClerkInputActivity.this.finish();
                }else{
                    MessageUtil.commonToast(XZHL1220_ClerkInputActivity.this,
                            msg.obj.toString(), Toast.LENGTH_SHORT);
                }
            }
            LogUtil.logEnd();

        }
    };

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
