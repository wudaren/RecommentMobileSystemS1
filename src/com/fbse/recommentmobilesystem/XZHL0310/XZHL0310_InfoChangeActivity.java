package com.fbse.recommentmobilesystem.XZHL0310;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0320.XZHL0320_PwdChangeActivity;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.Encrypt_Util;
import com.fbse.recommentmobilesystem.common.HttpSubmit;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

@SuppressLint({ "HandlerLeak", "WorldReadableFiles" })
public class XZHL0310_InfoChangeActivity extends Activity implements OnClickListener{

    // 类标识
    private static final String TAG = "XZHL0310_InfoChangeActivity";
    private static int MAX = 20;
    private EditText edt_loginName;
    private TextView edt_shopid;
    private Button rtn,subtn,pwdset;
    private String shopId,oldpwd,loginName,Id,shopName,tel,token,realName,role;
    private static String flag="";
    // 界面布局组件
    private LinearLayout linearLayoutuser;
    // 用户登录信息
    private SharedPreferences sharedPreferences;
    // 配置文件信息
    Properties properties = null;
    
    /**
     * 页面初始化方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0310_userinfo);
        initView();
        Log.v(TAG, TAG);
    }

    /**
     * 初始化界面控件
     */
    private void initView(){

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        sharedPreferences = this.getSharedPreferences("data", Context.MODE_WORLD_READABLE);
        rtn = (Button) findViewById(R.id.gobackBtn);
        subtn = (Button) findViewById(R.id.gobackBtn2);
        pwdset = (Button) findViewById(R.id.pwdedt);
        edt_shopid = (TextView) findViewById(R.id.storeid);
        edt_loginName = (EditText) findViewById(R.id.loginname);
        linearLayoutuser = (LinearLayout) findViewById(R.id.linearLayoutuser);
        linearLayoutuser.setOnClickListener(this);
        rtn.setOnClickListener(this);
        subtn.setOnClickListener(this);
        pwdset.setOnClickListener(this);
        /*
         * 接收店铺的ID和需要修改的用户名
         */
        Id = sharedPreferences.getString("ID", "");
        shopId = sharedPreferences.getString("ID", "");
        oldpwd = sharedPreferences.getString("PASS", "");
        loginName = sharedPreferences.getString("NAME", "");
        shopName = sharedPreferences.getString("SHOPNAME", "");
        tel = sharedPreferences.getString("TEL", "");
        token = sharedPreferences.getString("TOKEN", "");
        realName = sharedPreferences.getString("REALNAME", "");
        role = sharedPreferences.getString("QUANXIAN", "");
        edt_loginName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(40)});
        properties = Commonutil
            .loadProperties(XZHL0310_InfoChangeActivity.this);
        edt_shopid.setText(shopName);
        edt_loginName.clearFocus();
        edt_loginName.setText(realName);
        edt_loginName.requestFocus();
        pwdset.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        initCheckCharNumber();
        
    }

    /**
     * 按钮点击响应事件
     */
    @Override
    public void onClick(View arg0) {
        // 返回，退回到主功能菜单
        if(arg0.getId()==R.id.gobackBtn){
        finish();
        // 提交，提交到服务器
        }else if(arg0.getId()==R.id.gobackBtn2){
            judgeSubmit();
        // 点击密码设置，进入密码修改页面
        }else if(arg0.getId()==R.id.pwdedt){
            Intent intent=new Intent(this,XZHL0320_PwdChangeActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("id",Id);            
            bundle.putString("shopId", shopId);
            bundle.putString("loginName", loginName);
            bundle.putString("password", oldpwd);
            bundle.putString("tel",tel);            
            bundle.putString("token", token);
            bundle.putString("realName", realName);
            bundle.putString("shopName", shopName);
            bundle.putString("role", role);
            intent.putExtras(bundle);  
            startActivity(intent);
            finish();
        }else if(arg0.getId()==R.id.linearLayoutuser){
        edt_loginName.setFocusable(true);
        edt_loginName.setFocusableInTouchMode(true);
        edt_loginName.requestFocus();  // 初始不让EditText得焦点
        edt_loginName.requestFocusFromTouch();
        edt_loginName.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        
    }
    
    /**
     * 页面提交的判断逻辑
     */
    private void judgeSubmit(){
     // 检测是否开启网络连接
        if(XZHL0310_JsonUtil.isNetworkAvailable(this)){
            // 判断用户名是否为空
            if(!checkUserNameNull(edt_loginName.getText().toString())){
            // 检验用户名是否符合规范
            if(XZHL0310_JsonUtil.checkUserName(edt_loginName.getText().toString())){
//                if(edt_loginName.getText().toString().trim().equals(loginName)){
                // 启动线程请求修改用户名
                 TreadLoginName treadLoginName=new TreadLoginName();
                 treadLoginName.start();
//                }else{
                // SIMILARUSERNAME=当前登录名与修改修改用户名一致
//                MessageUtil.commonToast(getApplicationContext()
//                         ,  properties.getProperty(Msg.SIMILARUSERNAME, ""), Toast.LENGTH_SHORT);
//                }

            }else{
                MessageUtil.commonToast(getApplicationContext()
                     ,  properties.getProperty(Msg.E0011, ""), Toast.LENGTH_SHORT);
                 //ILLEGALLOGINNAME=登录名为汉字、英文字符、数字和下划线，请勿输入特殊字符
            }
            
            }else{
            // 登录名
            MessageUtil.commonToast(getApplicationContext()
                     ,MessageUtil.getMessage(this, Msg.E0016, new String[]{CommonConst.LOGINNAMENULL}), Toast.LENGTH_SHORT);
            }
        }else{
            MessageUtil.commonToast(this, properties.getProperty(Msg.E0008, ""), Toast.LENGTH_SHORT);
        }
    }
    /**
     * post提交方式,提交修改登录名请求
     * @return result
     */
    public  String postNewUsername()
        throws ClientProtocolException, IOException { 
              // 封装请求的参数集合   
              List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();  
              parameters.add(new BasicNameValuePair("id", Id)); 
              parameters.add(new BasicNameValuePair("loginName", edt_loginName.getText().toString().trim()));  
              // 返回参数 
              StringBuilder result = new StringBuilder();
              String path=CommonConst.SERVER.trim()+CommonConst.POSTNEWLOGINNAME;
              String backInfo=HttpSubmit.ListTest2(path, parameters);
              new XZHL0310_JsonUtil();
              result.append(XZHL0310_JsonUtil.JSON2Result(backInfo)) ;
         return result.toString();
    }
    
    /**
     *  post提交方式,提交修改密码请求
     */
    public  String postNewPassword() throws ClientProtocolException, IOException, JSONException{ 
        // 封装请求的参数集合
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"id","realname","password","tel","role","username"};
        String[] value = {Id,realName.toString(),Encrypt_Util.md5(oldpwd),tel,role,edt_loginName.getText().toString()};
        String json = JsonUtil.DataToJson(key, value);
        String result = woh.WSservers(this,"user/update", JsonUtil.DataToJson("a001", json, Id, JsonUtil.getHaveToken("a001", json,Id)));
        JSONObject jb = new JSONObject(result);
        return jb.getString("success").toString();
    }
    
    /**
     *点击返回按钮事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
        finish();
        return true;
        }else{
        return super.onKeyDown(keyCode, event);
        }
    } 
    
    /**
     * 判断用户名是否为空
     * @param userName
     */
    public boolean checkUserNameNull(String userName) { 
        boolean result = false; 
        if(userName.equals("")){
        result = true;
        }
        return result;
    }
    
    /**
     *  异步接收异步访问网络请求的结果
     */
    Handler handler=new Handler(){
    
    /**
     *  异步接收异步访问网络请求的结果
    */
        @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what==2){
            if (flag.equals("1")){
             MessageUtil.commonToast(getApplicationContext()
                 ,  properties.getProperty(Msg.I009, ""), Toast.LENGTH_SHORT);
         
            Editor edt =sharedPreferences.edit();
            edt.putString("REALNAME", edt_loginName.getText().toString().trim());
            edt.commit();
            } else if (flag.equals("error")){
            MessageUtil.commonToast(getApplicationContext()
                    , properties.getProperty(Msg.E0022, ""), Toast.LENGTH_SHORT);
               } else {
              MessageUtil.commonToast(getApplicationContext()
                , properties.getProperty(Msg.E0007, ""), Toast.LENGTH_SHORT);
               }
        } else if (msg.what==3){
            MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0008, ""),
                Toast.LENGTH_SHORT);
        } else {
            MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0008, ""),
                Toast.LENGTH_SHORT);
        }
        }
        
    };

    /**
     *  异步线程提交用户名修改请求
     */ 
    class TreadLoginName extends Thread{

        @Override
        public void run() {
            super.run();
                Message msg=new Message();
                try {
                    flag=postNewPassword();
                    msg.what=2;
                } catch (ClientProtocolException e) {
                    msg.what=3;
                } catch (IOException e) {
                    msg.what=4;
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally{
                    handler.sendMessage(msg);
                }
        }
    }

    /**
     * 输入框文字输入最大值校验提示
     */
    private void initCheckCharNumber(){
        edt_loginName.addTextChangedListener(new TextWatcher() {
        private CharSequence temp;
        private int selectionStart;
        private int selectionEnd;

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
            int count) {
            temp = s;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
            int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            selectionStart = edt_loginName.getSelectionStart();
            selectionEnd = edt_loginName.getSelectionEnd();
            if (temp.length() > MAX) {
            MessageUtil.commonToast(XZHL0310_InfoChangeActivity.this, "超出最大输入限制",
                Toast.LENGTH_SHORT);
            s.delete(selectionStart - 1, selectionEnd);
            int tempSelection = selectionStart;
            edt_loginName.setText(s);
            edt_loginName.setSelection(tempSelection);
            }
        }
        });
    }
    
}
