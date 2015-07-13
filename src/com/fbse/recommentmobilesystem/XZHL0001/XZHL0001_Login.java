package com.fbse.recommentmobilesystem.XZHL0001;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.XZHL0003.XZHL0003_FunctionListActivity;
import com.fbse.recommentmobilesystem.common.Encrypt_Util;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;
public class XZHL0001_Login {
	public static class Login extends AsyncTask<String, Integer, Integer> {
		private String log;
		private String pwd;
		private Activity activity;
		public XZHL0001_UpdateManage updateManage;
		 XZHL0001_LoginBean login ;
		WebServiceOfHttps webservice=new WebServiceOfHttps();
		public Login(String log, String pwd, Activity activity) {
			this.log = log;
			this.pwd = pwd;
			this.activity = activity;
			 updateManage=new XZHL0001_UpdateManage(activity);
		}
//异步实现自动登录功能
		@Override
		protected Integer doInBackground(String... params) {
			int flag = 0;
			try {
				
				List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("loginName", log));
				parameters.add(new BasicNameValuePair("password", pwd));
				//String path =XZHL0001_Constats.LOGINACTION;
				String[] key={"username","password"};
				String[] value={log,Encrypt_Util.md5(pwd)};
				String json =webservice.WSservers(activity, "user/login", JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), "",
						JsonUtil.getLogSign("a001", JsonUtil.DataToJson(key, value))));
	
		//		String path ="http://192.168.1.227:8080/FBSEMobileSystemS1/FBSEMobilSystem/login.action";
		//		String result1 = HttpSubmit.ListTest2(path, parameters);

		//		String result2 = XZHL0001_JsonUtil1.jiexiJSON(result1);
				 login = JsonUtil.JsonToLogin(json);
					
					//0失败1成功
					String result2 = login.getSuccess();
			
				if (updateManage.isUpdate()) {
					flag=4;
				}
				
				if (result2.equals("0")) {

					flag = 2;

				} else if (result2.equals("1")) {
					Log.i("conn", XZHL0001_LoginInfo.SHOPNAME);
					flag = 1;
				} else {
					flag = 3;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return flag;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
//由返回结果判断登陆状态
		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {

			case 1:
				Intent intent = new Intent();
				intent.setClass(activity, XZHL0003_FunctionListActivity.class);
				activity.startActivity(intent);
				activity.finish();
				break;
			case 2:
				Toast.makeText(activity,
						XZHL0001_Constats.LIEBIAOSHIBAI, Toast.LENGTH_SHORT).show();
				Intent log = new Intent();
				log.setClass(activity, XZHL0001_LoginActivity.class);
				activity.startActivity(log);
				activity.finish();
				break;
			case 3:
				Toast.makeText(activity,
						XZHL0001_Constats.SERVRCEERROR, Toast.LENGTH_SHORT).show();
				Intent loga = new Intent();
				loga.setClass(activity, XZHL0001_LoginActivity.class);
				activity.startActivity(loga);
				activity.finish();
				break;
			default:
				Toast.makeText(activity,
						XZHL0001_Constats.SERVRCEERROR, Toast.LENGTH_SHORT).show();
				Intent logb = new Intent();
				logb.setClass(activity, XZHL0001_LoginActivity.class);
				activity.startActivity(logb);
				activity.finish();
				break;
			}
			
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}
}
