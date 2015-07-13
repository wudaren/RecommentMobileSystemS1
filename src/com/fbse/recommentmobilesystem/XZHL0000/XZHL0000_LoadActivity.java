
package com.fbse.recommentmobilesystem.XZHL0000;

import java.io.IOException;
import java.util.Properties;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_Constats;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_Login;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_LoginActivity;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_UpdateManage;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.CrashHandler;

public class XZHL0000_LoadActivity extends Activity {

    private SharedPreferences shared;

    public static Properties properties;

    private XZHL0001_UpdateManage updateManage = new XZHL0001_UpdateManage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initProperties();

        shared = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0000_load);
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init();
        // 配置CommonConst的SERVER
        Properties pro = Commonutil.loadIPProperties(this);
        String server = "http://" + pro.getProperty("xmppHost") + ":" + pro.getProperty("tomcatPort") + "/"
                + pro.getProperty("workspacename");
        CommonConst.SERVER = server;
        // 开启线程
        PostNewloginNameAsyncTask asyncTask = new PostNewloginNameAsyncTask();
        asyncTask.execute();
    }

    private void initProperties() {

        // TODO Auto-generated method stub
        Properties p1 = XZHL0001_Constats.loadIPProperties(this, "androidpn");
        XZHL0001_Constats.initData(p1);
        Properties p2 = XZHL0001_Constats.loadIPProperties(this, "message");
        XZHL0001_Constats.initStrings(p2);
    }

    class PostNewloginNameAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... arg0) {

            try {
                Thread.sleep(3000);
                boolean auto = shared.getBoolean("AUTO_ISCHECK", false);
                if (auto) {
                    try {
                        boolean cc = updateManage.isUpdate();
                        if (cc) {
                            return "true";
                        } else {
                            return "false";
                        }
                    } catch (XmlPullParserException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(getApplicationContext(), XZHL0001_LoginActivity.class);
                    startActivity(intent);
                    XZHL0000_LoadActivity.this.finish();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "false";
        }

        @Override
        protected void onPostExecute(String result) {

            // TODO Auto-generated method stub
            if (shared.getBoolean("AUTO_ISCHECK", false)) {
                if (result.equals("true")) {
                    Intent intent = new Intent(getApplicationContext(), XZHL0001_LoginActivity.class);
                    startActivity(intent);
                    XZHL0000_LoadActivity.this.finish();
                }
                if (result.equals("false")) {
                    String log = shared.getString("USER_NAME", "");
                    String pwd = shared.getString("PASSWORD", "");
                    XZHL0001_Login.Login login = new XZHL0001_Login.Login(log, pwd, XZHL0000_LoadActivity.this);
                    login.execute();
                }
            }
        }
    }
}
