package com.fbse.recommentmobilesystem.XZHL0320;

import java.io.IOException;

import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0310.XZHL0310_JsonUtil;
import com.fbse.recommentmobilesystem.XZHL0310.XZHL0310_InfoChangeActivity;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.Encrypt_Util;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

@SuppressLint({ "HandlerLeak", "WorldReadableFiles" })
public class XZHL0320_PwdChangeActivity extends Activity implements
        OnClickListener {
        
    private static int MAX = 50;
    private static int FILTER = 40;
    private EditText edt_oldPwd, edt_newPwd, edt_surePwd;
    private Button rtn, subtn;
    private String  oldPwd,tel, role,realName, loginName, Id;//,shopId, loginName, realName, token,  shopName;
    private String flag = "";
    private LinearLayout linearLayoutpwd;
    private SharedPreferences sharedPreferences;
    // 配置文件信息
    Properties properties = null;
    Properties ipProperties = null;
    
    /**
     * 页面初始化方法
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0320_pwd);
        initView();
        receiveData();
        
        }
    
    /**
     * 初始化頁面控件
     */
    private void initView(){
        rtn = (Button) findViewById(R.id.gobackBtn);
        subtn = (Button) findViewById(R.id.gosubmit);
        edt_oldPwd = (EditText) findViewById(R.id.oldPwd);
        edt_newPwd = (EditText) findViewById(R.id.newPwd);
        edt_surePwd = (EditText) findViewById(R.id.surePwd);
        linearLayoutpwd = (LinearLayout) findViewById(R.id.linearLayoutpwd);
        rtn.setOnClickListener(this);
        subtn.setOnClickListener(this);
        edt_oldPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(FILTER)});
        edt_newPwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(FILTER)});
        edt_surePwd.setFilters(new InputFilter[]{new InputFilter.LengthFilter(FILTER)});
        linearLayoutpwd.setOnClickListener(this);
    }
    
    /**
     * 接收页面传入数据
     */
    private void receiveData(){
        
        //接收店铺的ID和需要修改的用户名
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Id = bundle.getString("id");
        //shopId = bundle.getString("shopId");
        oldPwd = bundle.getString("password");
        loginName = bundle.getString("loginName");
        //shopName = bundle.getString("shopName");
        tel = bundle.getString("tel");
        //token = bundle.getString("token");
        realName = bundle.getString("realName");
        role = bundle.getString("role").toString();
        properties = Commonutil
                .loadProperties(XZHL0320_PwdChangeActivity.this);
        ipProperties = Commonutil
                .loadIPProperties(XZHL0320_PwdChangeActivity.this);
        initCheckCharNumber();
        sharedPreferences = this.getSharedPreferences("data", Context.MODE_WORLD_READABLE);
        
    }
    
    /**
     * 判断按钮点击事件
     * @return flag
     */
    @Override
    public void onClick(View arg0) {
        
        if (arg0.getId() == R.id.gobackBtn) {
            Intent intent=new Intent(this,XZHL0310_InfoChangeActivity.class);
            startActivity(intent);
            finish();
        } else if (arg0.getId() == R.id.gosubmit) {
            judgeSubmit();
        }
        
        if(arg0.getId() == R.id.linearLayoutpwd) {
            hideKey();
        } 
    }
    
    /**
     * 点击提交事件逻辑处理
     */
    private void judgeSubmit(){
        
      //检测网络是否连接
      if(XZHL0310_JsonUtil.isNetworkAvailable(this) ) {
          //判断密码是否为空
          if(!isNotNullPassword(edt_newPwd.getText().toString())&&!isNotNullPassword(edt_surePwd.getText().toString())&&!isNotNullPassword(edt_oldPwd.getText().toString())){
               //判断密码是否是英文、数字、下划线
               if(XZHL0310_JsonUtil.checkPassword(edt_newPwd.getText().toString())&&XZHL0310_JsonUtil.checkPassword(edt_surePwd.getText().toString())){
                   //判断原密码是否输入正确
                   if(checkPwd()) {
                        //判断修改的密码是否一致
                        if (checkNewPwd()) {
                            TreadPassword treadPassword=new TreadPassword();
                            treadPassword.start();
                        } else {
                             MessageUtil.commonToast(getApplicationContext()
                                     ,  properties.getProperty(Msg.E0013, ""), Toast.LENGTH_SHORT);
                        }
                      
                   } else {
                        MessageUtil.commonToast(getApplicationContext()
                                     ,  properties.getProperty(Msg.E0014, ""), Toast.LENGTH_SHORT);

                                edt_oldPwd.setText("");
                                edt_newPwd.setText("");
                                edt_surePwd.setText("");

                      }           
                  } else {
                         MessageUtil.commonToast(getApplicationContext()
                                     ,  properties.getProperty(Msg.E0015, ""), Toast.LENGTH_SHORT);
                         //ILLEGALPASSWORD=密码设置为英文字符大小写、数字和下划线，请勿输入特殊字符
                  }
                  
              } else {
                  //判断输入框为空的情况
                  if(isNotNullPassword(edt_oldPwd.getText().toString())){
                      //原密码不能为空
                         MessageUtil.commonToast(getApplicationContext()
                           ,MessageUtil.getMessage(this, Msg.E0016, new String[]{CommonConst.OLDPASSWORD})
                              , Toast.LENGTH_SHORT);
                  }else if(isNotNullPassword(edt_newPwd.getText().toString())){
                      //新密码不能为空
                         MessageUtil.commonToast(getApplicationContext()
                           ,MessageUtil.getMessage(this, Msg.E0016, new String[]{CommonConst.NEWPASSWORD}), Toast.LENGTH_SHORT);
                  }else if(isNotNullPassword(edt_surePwd.getText().toString())){
                      //确认密码不能为空
                         MessageUtil.commonToast(getApplicationContext()
                           ,MessageUtil.getMessage(this, Msg.E0016, new String[]{CommonConst.SURPASSWORD}), Toast.LENGTH_SHORT);
                  }else{
                      
                  }
                     //PASSWORDISNULL=密码设置不允许为空
              }
        }else{
            MessageUtil.commonToast(this, properties.getProperty(Msg.E0008, ""), Toast.LENGTH_SHORT);
        }
    }
    
    /**
     * 点击界面布局块事件处理
     * @return
     */
    private void hideKey(){
      //------阻止EditText得到焦点，以防输入法弄丑画面
        edt_oldPwd.setFocusable(true);
        edt_oldPwd.setFocusableInTouchMode(true);
        edt_oldPwd.requestFocus();  // 初始不让EditText得焦点
        edt_oldPwd.requestFocusFromTouch();
        edt_oldPwd.clearFocus();
        edt_newPwd.setFocusable(true);
        edt_newPwd.setFocusableInTouchMode(true);
        edt_newPwd.requestFocus();  // 初始不让EditText得焦点
        edt_newPwd.requestFocusFromTouch();
        edt_newPwd.clearFocus();
        edt_surePwd.setFocusable(true);
        edt_surePwd.setFocusableInTouchMode(true);
        edt_surePwd.requestFocus();  // 初始不让EditText得焦点
        edt_surePwd.requestFocusFromTouch();
        edt_surePwd.clearFocus();
        // 关闭界面软键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    
    /**
     * 判断原密码是与原密码一致
     * @return flag
     */
    private boolean checkPwd() {
        
        boolean flag = false;
        String oldpass = edt_oldPwd.getText().toString() == null ? "" : edt_oldPwd.getText().toString();
        if (oldpass.equals(oldPwd)) {
            flag = true;
        }
        return flag;
    }
    
    /**
     * 判断新密码是与确认密码一致
     * @return flag
     */
    private boolean checkNewPwd() {
        
        boolean flag = false;
        String newPass = edt_newPwd.getText().toString() == null ? "" : edt_newPwd.getText().toString();
        String surePass = edt_surePwd.getText().toString() == null ? "" : edt_surePwd.getText().toString();
        if (newPass.equals(surePass)) {
            flag = true;
        }
        return flag;
    }
    
    /**
     * 弹出对话框处理
     */
    class DialogButtonClick implements
                android.content.DialogInterface.OnClickListener {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    dialog.dismiss();
                } else if (which == DialogInterface.BUTTON_NEUTRAL) {
    
                } else if (which == DialogInterface.BUTTON_POSITIVE) {
                    edt_oldPwd.setFocusableInTouchMode(true);
                } else {
                    // 其他情况不作处理
                }
            }
        }
    
    /**
     *  post提交方式,提交修改密码请求
     */
    public  String postNewPassword() throws ClientProtocolException, IOException, JSONException{ 
        
        // 封装请求的参数集合
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"id","realname","password","tel","role","username"};
        String[] value = {Id,realName,Encrypt_Util.md5(edt_surePwd.getText().toString()),tel,role,loginName};
        String json = JsonUtil.DataToJson(key, value);//"bc80c21a-3d90-4100-a8ba-056e41eb933c"
        String result = woh.WSservers(this,"user/update", JsonUtil.DataToJson("a001", json,Id , JsonUtil.getHaveToken("a001", json,Id)));
        JSONObject jb = new JSONObject(result);
        return jb.getString("success").toString();
    }

    /**
     *  异步接收异步访问网络请求的结果
     */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==2){
                if (flag.equals("1")) {
                    Intent intent=new Intent(XZHL0320_PwdChangeActivity.this,XZHL0310_InfoChangeActivity.class);
                    startActivity(intent);
                    MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.I013, ""),
                            Toast.LENGTH_SHORT);
                    oldPwd=edt_surePwd.getText().toString();
                    Editor edt =sharedPreferences.edit();
                    edt.putString("PASS", edt_surePwd.getText().toString().trim());
                    edt.commit();
                    finish();
                } else if (flag.equals("0")) {
                    MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0009, ""),
                            Toast.LENGTH_SHORT);
                }else{
                    MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0002, ""),
                            Toast.LENGTH_SHORT);
                }
            }else if(msg.what==3){
                MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0008, ""),
                        Toast.LENGTH_SHORT);
            }else{
                MessageUtil.commonToast(getApplicationContext(), properties.getProperty(Msg.E0008, ""),
                        Toast.LENGTH_SHORT);
            }
        }
    };
        
    /**
     *点击返回按钮响应事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(this,XZHL0310_InfoChangeActivity.class);
            startActivity(intent);
            finish();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    } 

    /**
     * 判断密码是否为空
     * @param password
     */
    public boolean isNotNullPassword(String password){

        boolean result=false;
        if("".equals(password)){
            result=true;
        }
            return result;
    }

    /**
     * 异步线程提交密码修改请求
     */
    class TreadPassword extends Thread{

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
                    msg.what=5;
                }
                handler.sendMessage(msg);
        }
    }

    /**
     * 输入框文字输入最大值校验提示
     */
    private void initCheckCharNumber(){
        edt_oldPwd.addTextChangedListener(new TextWatcher() {
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
                selectionStart = edt_oldPwd.getSelectionStart();
                selectionEnd = edt_oldPwd.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0320_PwdChangeActivity.this, properties.getProperty(Msg.E0026, ""),
                            Toast.LENGTH_SHORT);

                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    edt_oldPwd.setText(s);
                    edt_oldPwd.setSelection(tempSelection);
                }
            }
        });
            
        edt_newPwd.addTextChangedListener(new TextWatcher() {
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
                selectionStart = edt_newPwd.getSelectionStart();
                selectionEnd = edt_newPwd.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0320_PwdChangeActivity.this, properties.getProperty(Msg.E0026, ""),
                            Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    edt_newPwd.setText(s);
                    edt_newPwd.setSelection(tempSelection);
                }
            }
        });
        edt_surePwd.addTextChangedListener(new TextWatcher() {
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
                selectionStart = edt_surePwd.getSelectionStart();
                selectionEnd = edt_surePwd.getSelectionEnd();
                if (temp.length() > MAX) {
                    MessageUtil.commonToast(XZHL0320_PwdChangeActivity.this, properties.getProperty(Msg.E0026, ""),
                            Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    edt_surePwd.setText(s);
                    edt_surePwd.setSelection(tempSelection);
                }
            }
        });
    }
}
