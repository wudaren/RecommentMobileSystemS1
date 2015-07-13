/*  XZHL1230_ClerkModify.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL1230                                                                                               */
/*  画面名 ：店员信息修改                                                                                             */
/*  实现功能 ：对店员信息进行修改并保存。                                                                             */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/15   V01L01      FBSE)尹晓超      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1230;

import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_LoginBean;
import com.fbse.recommentmobilesystem.XZHL0130.XZHL0130_VipInfoActivity;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.Encrypt_Util;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * 店员情报修改画面后台类
 * 完成店员情报修改
 */
public class XZHL1230_ClerkModify extends Activity implements OnClickListener {

    // 成功标签
    private static final int EXITSUCCESS = 1;

    // 失败标签
    private static final int EXITFAIL = 0;

    // 店长权限标签
    private static final int ROLENUM = 2;

    // back：退出按钮
    private Button btnBack;

    // modify：修改按钮
    private Button btnModify;

    // 文本输入框：姓名
    private EditText etName;

    // 文本输入框：电话
    private EditText etTel;

    // 文本输入框：登录名
    private EditText etLoginName;

    // 文本输入框：登陆密码
    private EditText etLoginPass;

    // 文本输入框：确认密码
    private EditText etOkPass;

    // 配置文件
    private Properties properties = null;

    // 下拉列表
    private Spinner quanXian;

    // 适配器
    private ArrayAdapter<String> saItem;

    // 接口对象
    private WebServiceOfHttps webservice = new WebServiceOfHttps();

    // 接口返回值
    private XZHL0001_LoginBean resultValue;

    // 登陆者id
    private String id;

    // 登陆者role
    private String role;

    // 用户id
    private String userId;

    // 姓名输入框
    private String name;

    // 电话输入框
    private String tel;

    // 登录名输入框
    private String loginName;

    // 登陆密码输入框
    private String loginPass;

    // 确认密码输入框
    private String okPass;

    // 权限输入框
    protected String quanXianValue;

    // 角色输入
    protected String roleId;

    // 用户原密码
    protected String startPass;

    // 用户原权限
    protected String satrtRoleId;

    // 用户token
    protected String token;

    // 用户权限数组
    private String[] items = null;

    // 画面间参数，用戶信息集合
    private String[] user = new String[5];

    /**
     * 创建activity
     * @param savedInstanceState 视图
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Log开始
        LogUtil.logStart();

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl1230_clerkmodify);

        // 加载配置文件
        loadProperties();

        // 初始化界面
        initView();

        // 输入长度监听
        sizeChangeListener();

        // 添加后退按钮点击事件
        btnBack.setOnClickListener(this);

        // 添加确认按钮点击事件
        btnModify.setOnClickListener(this);

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 对输入字符长度进行监听
     */
    private void sizeChangeListener() {

        // Log开始
        LogUtil.logStart();

        // 姓名输入验证
        etName.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Log开始
                LogUtil.logStart();
                int selectionStart = etName.getSelectionStart();
                int selectionEnd = etName.getSelectionEnd();
                if (temp.length() >= CommonConst.NUMROLE) {
                    MessageUtil.commonToast(XZHL1230_ClerkModify.this,
                        properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etName.setText(s);
                    etName.setSelection(tempSelection-1);
                }

                // Log结束
                LogUtil.logEnd();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        // 电话输入验证
        etTel.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Log开始
                LogUtil.logStart();
                int selectionStart = etTel.getSelectionStart();
                int selectionEnd = etTel.getSelectionEnd();
                if (temp.length() >= CommonConst.NUMROLE) {
                    MessageUtil.commonToast(XZHL1230_ClerkModify.this,
                        properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etTel.setText(s);
                    etTel.setSelection(tempSelection-1);
                }

                // Log结束
                LogUtil.logEnd();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        // 登录名输入验证
        etLoginName.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Log开始
                LogUtil.logStart();
                int selectionStart = etLoginName.getSelectionStart();
                int selectionEnd = etLoginName.getSelectionEnd();
                if (temp.length() >= CommonConst.NUMROLE) {
                    MessageUtil.commonToast(XZHL1230_ClerkModify.this,
                        properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etLoginName.setText(s);
                    etLoginName.setSelection(tempSelection-1);
                }

                // Log结束
                LogUtil.logEnd();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        // 登陆密码输入验证
        etLoginPass.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {

                // Log开始
                LogUtil.logStart();
                int selectionStart = etLoginPass.getSelectionStart();
                int selectionEnd = etLoginPass.getSelectionEnd();
                if (temp.length() >= CommonConst.NUMROLE) {
                    MessageUtil.commonToast(XZHL1230_ClerkModify.this,
                        properties.getProperty(Msg.E0026, CommonConst.SPACE), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etLoginPass.setText(s);
                    etLoginPass.setSelection(tempSelection-1);
                }

                // Log结束
                LogUtil.logEnd();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 初始化配置文件
     */
    private void loadProperties() {

        // Log开始
        LogUtil.logStart();

        properties = Commonutil.loadProperties(XZHL1230_ClerkModify.this);

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 取得输入框的值
     */
    private void getValue() {

        // Log开始
        LogUtil.logStart();

        name = etName.getText().toString().trim();
        tel = etTel.getText().toString().trim();
        loginName = etLoginName.getText().toString().trim();
        loginPass = etLoginPass.getText().toString().trim();
        okPass = etOkPass.getText().toString().trim();

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 界面初始化
     */
    @SuppressLint("WorldReadableFiles")
    private void initView() {

        // Log开始
        LogUtil.logStart();

        // 取得画面id
        btnBack = (Button) findViewById(R.id.btn_gotomain_1230);
        btnModify = (Button) findViewById(R.id.btn_clerkmodify_1230);
        etName = (EditText) findViewById(R.id.et_name_1230);
        etTel = (EditText) findViewById(R.id.et_tel_1230);
        etLoginName = (EditText) findViewById(R.id.et_loginname_1230);
        etLoginPass = (EditText) findViewById(R.id.et_loginpass_1230);
        etOkPass = (EditText) findViewById(R.id.et_okpass_1230);
        quanXian = (Spinner) findViewById(R.id.sp_quanxian_1230);

        // 接收前台页面传值
        SharedPreferences sp = getSharedPreferences(CommonConst.DATA, Context.MODE_WORLD_READABLE);
        id = sp.getString(CommonConst.ID, CommonConst.SPACE);
        role = sp.getString("QUANXIAN", CommonConst.SPACE);
        user = getIntent().getStringArrayExtra("data");
        etName.clearFocus();
        etName.setText(user[1]);
        etName.requestFocus();
        etTel.setText(user[4]);
        etLoginName.setText(user[2]);
        userId = user[0];

        // 登陆者为商家
        if (CommonConst.SUCCESSONE.equals(role)) {
            items = getResources().getStringArray(R.array.role);
        }

        // 登陆者为店长
        else if (CommonConst.ROLETWO.equals(role)) {
            items = getResources().getStringArray(R.array.dyrole);
            quanXian.setEnabled(false);
        }
        saItem = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        // 设置下拉列表
        saItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quanXian.setAdapter(saItem);

        // 权限是商家
        if (CommonConst.SUCCESSONE.equals(role)) {

            // 用户权限商家
            if (CommonConst.SELLGUANLI.equals(user[3])) {
                quanXian.setSelection(EXITFAIL);
            }

            // 用户权限店长
            if (CommonConst.SELLZHANG.equals(user[3])) {
                quanXian.setSelection(EXITSUCCESS);
            }

            // 用户权限是店员
            if (CommonConst.SELLYUAN.equals(user[3])) {
                quanXian.setSelection(ROLENUM);
            }

            // 店长登陆
        }
        if (CommonConst.ROLETWO.equals(role)) {

            // 设置下拉权限不可用
            if (CommonConst.SELLZHANG.equals(user[3])) {
                quanXian.setSelection(EXITFAIL);
            }
            if (CommonConst.SELLYUAN.equals(user[3])) {
                quanXian.setSelection(EXITSUCCESS);
            }
        }

        // 监听权限下拉列表
        quanXian.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                // Log开始
                LogUtil.logStart();

                // 改变后的权限内容
                quanXianValue = items[arg2];
                if (CommonConst.SELLGUANLI.equals(quanXianValue)) {
                    satrtRoleId = CommonConst.SUCCESSONE;
                }
                if (CommonConst.SELLYUAN.equals(quanXianValue)) {
                    satrtRoleId = CommonConst.ROLETHREE;
                }
                if (CommonConst.SELLZHANG.equals(quanXianValue)) {
                    satrtRoleId = CommonConst.ROLETWO;
                }

                // Log结束
                LogUtil.logEnd();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

                // Log开始
                LogUtil.logStart();
                quanXian.setSelection(ROLENUM, true);

                // Log结束
                LogUtil.logEnd();
            }
        });

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 添加点击事件
     * @param v 视图
     */
    @Override
    public void onClick(View v) {

        // Log开始
        LogUtil.logStart();

        switch (v.getId()) {

        // 返回按钮事件
        case R.id.btn_gotomain_1230:
            if (isChanged()) {
                showDialg();
            } else {
                XZHL1230_ClerkModify.this.finish();
            }
            break;

        // 确认按钮事件
        case R.id.btn_clerkmodify_1230:
            btnModify.setEnabled(false);
            getValue();
            judgment();
            break;

        default:
            break;
        }

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 判断画面上的值是否变化
     * @return 画面上的值是否变化 true:画面上的有变化 false:画面上的值没有变化
     */
    private boolean isChanged() {

        // Log开始
        LogUtil.logStart();

        // 画面上的值与从前画面取得的值是否一致
        if (user[1].equals(etName.getText().toString()) && user[2].equals(etLoginName.getText().toString())
            && user[3].equals(quanXianValue) && user[4].equals(etTel.getText().toString())
            && (etLoginPass.getText().toString().equals(CommonConst.SPACE))) {

            // 画面上的值与从前画面取得的值一致
            return false;
        }

        // Log结束
        LogUtil.logEnd();

        // 画面上的值与从前画面取得的值不一致

        return true;
    }

    /**
     * 显示构造对话框
     */
    private void showDialg() {

        // Log开始
        LogUtil.logStart();

        AlertDialog.Builder builder = new AlertDialog.Builder(XZHL1230_ClerkModify.this);
        builder.setTitle(properties.getProperty(Msg.I004, CommonConst.SPACE)).setMessage(
            properties.getProperty(Msg.Q013, CommonConst.SPACE));

        // 确定按钮点击
        builder.setPositiveButton(properties.getProperty(Msg.I012, CommonConst.SPACE),
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Log开始
                    LogUtil.logStart();
                    XZHL1230_ClerkModify.this.finish();

                    // Log结束
                    LogUtil.logEnd();
                }

                // 取消按钮点击
            }).setNegativeButton(properties.getProperty(Msg.I005, CommonConst.SPACE),
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Log开始
                    LogUtil.logStart();
                    dialog.dismiss();

                    // Log结束
                    LogUtil.logEnd();
                }
            });

        // 显示对话框
        builder.create().show();

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 提交信息前的输入验证
     */
    private void judgment() {

        // Log开始
        LogUtil.logStart();

        // 判断姓名输入框是否为空
        if (name == null || name.length() == EXITFAIL) {
            MessageUtil.commonToast(getApplicationContext(),
                MessageUtil.getMessage(this, Msg.E0016, new String[] { CommonConst.NAME }), Toast.LENGTH_SHORT);
            etName.requestFocus();
            btnModify.setEnabled(true);
            return;
        }

        // 判断电话输入框是否为空
        if (tel == null || tel.length() == EXITFAIL) {
            MessageUtil.commonToast(getApplicationContext(),
                MessageUtil.getMessage(this, Msg.E0016, new String[] { CommonConst.TEL }), Toast.LENGTH_SHORT);
            etTel.requestFocus();
            btnModify.setEnabled(true);
            return;
        }

        // 判断登录名输入框是否为空
        if (loginName == null || loginName.length() == EXITFAIL) {
            MessageUtil
                .commonToast(getApplicationContext(),
                    MessageUtil.getMessage(this, Msg.E0016, new String[] { CommonConst.LOGINNAMENULL }),
                    Toast.LENGTH_SHORT);
            etLoginName.requestFocus();
            btnModify.setEnabled(true);
            return;
        }

        // 判断密码输入框是否为空
        if (loginPass == null || CommonConst.SPACE.equals(loginPass)) {
            loginPass = CommonConst.SPACE;
        }

        // 判断密码和确认密码是否一致
        if (!((loginPass == null || CommonConst.SPACE.equals(loginPass)) && (okPass == null || CommonConst.SPACE
            .equals(okPass)))) {
            if (!loginPass.equals(okPass)) {
                MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0013, CommonConst.SPACE),
                    Toast.LENGTH_SHORT);
                etOkPass.requestFocus();
                btnModify.setEnabled(true);
                return;
            } else {
                loginPass = Encrypt_Util.md5(loginPass);
            }
        }

        // 判断姓名输入是否符合规则
        if (!XZHL1230_util.checkUserName(name)) {
            MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0011, CommonConst.SPACE),
                Toast.LENGTH_SHORT);
            etName.requestFocus();
            btnModify.setEnabled(true);
            return;
        }

        // 判断密码入是否符合规则
        if (!XZHL1230_util.checkPassword(loginPass)) {
            if (!CommonConst.SPACE.equals(loginPass)) {
                MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0015, CommonConst.SPACE),
                    Toast.LENGTH_SHORT);
                etLoginPass.requestFocus();
                btnModify.setEnabled(true);
                return;
            }
        }

        // 构造提交前的对话框
        showDig();

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 构造提交前的对话框
     */
    private void showDig() {

        // Log开始
        LogUtil.logStart();

        btnModify.setEnabled(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(XZHL1230_ClerkModify.this);
        builder.setTitle(properties.getProperty(Msg.I004, CommonConst.SPACE)).setMessage(
            MessageUtil.getMessage(getApplicationContext(), Msg.Q011, new String[] { CommonConst.XINXI }));

        // 确定按钮点击
        builder.setPositiveButton(properties.getProperty(Msg.I012, CommonConst.SPACE),
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Log开始
                    LogUtil.logStart();
                    Modigy modigy = new Modigy();
                    modigy.execute();

                    // Log结束
                    LogUtil.logEnd();
                }

                // 取消按钮点击
            }).setNegativeButton(properties.getProperty(Msg.I005, CommonConst.SPACE),
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Log开始
                    LogUtil.logStart();
                    dialog.dismiss();

                    // Log结束
                    LogUtil.logEnd();
                }
            });

        // 显示对话框
        builder.create().show();

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 店员情报修改画面后台类
     * 异步完成提交修改信息
     */
    private class Modigy extends AsyncTask<String, Integer, Integer> {

        /**
         * 异步提交参数，完成信息修改
         * @param params 自动生成的动态参数，可以不传值
         * @return 状态
         */
        @Override
        protected Integer doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();

            // 初期值设置
            int flag = 0;

            try {
                // 接口请求参数
                String[] key = { "id", "username", "password", "realname", "tel", "role" };
                String[] value = { userId, loginName, loginPass, name, tel, satrtRoleId };
                String json = webservice.WSservers(
                    XZHL1230_ClerkModify.this,
                    "user/update",
                    JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                        JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));

                // 接口返回参数的结果集
                resultValue = JsonUtil.JsonToLogin(json);

                // 接口返回参数success
                String success = resultValue.getSuccess();

                // 修改失败
                if ((CommonConst.FAILZERO).equals(success)) {
                    flag = EXITFAIL;
                }

                // 修改成功
                else if ((CommonConst.SUCCESSONE).equals(success)) {
                    flag = EXITSUCCESS;
                }

            } catch (Exception e) {
                // 异常log输出
                LogUtil.logException(e);
            }

            // Log结束
            LogUtil.logEnd();

            // 返回值
            return flag;
        }

        /**
         * 开启异步前主线程操作
         */
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        /**
         * 主线程完成信息提示
         * @param result 结果
         */
        @Override
        protected void onPostExecute(Integer result) {

            // Log开始
            LogUtil.logStart();

            switch (result) {
            // 修改失败
            case EXITFAIL:
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.E0024, new String[] { CommonConst.XINXI }),
                    Toast.LENGTH_SHORT);

                btnModify.setEnabled(true);
                break;

            // 修改成功
            case EXITSUCCESS:
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.I020, new String[] { CommonConst.XINXI }),
                    Toast.LENGTH_SHORT);
                XZHL1230_ClerkModify.this.finish();
                btnModify.setEnabled(true);
                break;

            // 其他情况
            default:
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.E0024, new String[] { CommonConst.XINXI }),
                    Toast.LENGTH_SHORT);
                btnModify.setEnabled(true);
                break;
            }

            // Log结束
            LogUtil.logEnd();

        }

        @Override
        protected void onCancelled() {

            super.onCancelled();
        }
    }

    /**
     * 重写的键盘按下监听
     * @param keyCode 键盘码
     * @param event 事件
     * @return 成功或失败
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Log开始
        LogUtil.logStart();

        boolean flag = true;

        // 返回按键按下
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isChanged()) {
                showDialg();
            } else {
                XZHL1230_ClerkModify.this.finish();
            }
        }

        // 不是返回按键按下
        else {
            flag = super.onKeyDown(keyCode, event);
        }

        // Log结束
        LogUtil.logEnd();

        return flag;
    }
}
