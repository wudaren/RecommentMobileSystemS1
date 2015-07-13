package com.fbse.recommentmobilesystem.XZHL0001;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0003.XZHL0003_FunctionListActivity;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.Encrypt_Util;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.WebService;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

public class XZHL0001_LoginActivity extends Activity {
	/** Called when the activity is first created. */

	Button loginbtn;

	EditText username;
	EditText password;
	private String usernameString;
	private String passwordString;
	public static Properties properties;
	private CheckBox rem_pw, auto_login;
	private SharedPreferences sp;
	public static String Path;
	private XZHL0001_UpdateManage bb = new XZHL0001_UpdateManage(this);
	WebServiceOfHttps webservice = new WebServiceOfHttps();
	XZHL0001_LoginBean login;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl0001_login);
		// getperties();
		findViewById();
		setlistener();
		initProperties();
		PostNewloginNameAsyncTask postNewloginNameAsyncTask = new PostNewloginNameAsyncTask();
		postNewloginNameAsyncTask.execute(0);

	}

	// 读取配置文件的信息方法

	private void initProperties() {
		// TODO Auto-generated method stub
		Properties p1 = XZHL0001_Constats.loadIPProperties(this, "androidpn");
		XZHL0001_Constats.initData(p1);
		Properties p2 = XZHL0001_Constats.loadIPProperties(this, "message");
		XZHL0001_Constats.initStrings(p2);
	}

	// 异步访问服务端判断是否更新版本
	class PostNewloginNameAsyncTask extends AsyncTask<Integer, Integer, String> {
		public PostNewloginNameAsyncTask() {

		}

		@Override
		protected String doInBackground(Integer... arg0) {
			// TODO Auto-generated method stub
			try {
				boolean cc = bb.isUpdate();

				if (cc) {
					return "true";

				} else {
					return "false";
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "false";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (result.equals("true")) {
				bb.checkUpdate();
			}

			super.onPostExecute(result);
		}
	}

	// 实例化对象
	private void findViewById() {

		sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString("USER_NAME", "");
		editor.putString("PASSWORD", "");
		editor.putBoolean("ISCHECK", false);
		editor.putBoolean("AUTO_ISCHECK", false);
		loginbtn = (Button) findViewById(R.id.signin_button);
		username = (EditText) findViewById(R.id.username_edit);
		password = (EditText) findViewById(R.id.password_edit);
		rem_pw = (CheckBox) findViewById(R.id.rememberPassword);
		auto_login = (CheckBox) findViewById(R.id.autoLogin);
		if (sp.getBoolean("ISCHECK", false)) {
			// 设置默认是记录密码状态
			rem_pw.setChecked(true);
			username.clearFocus();
			username.setText(sp.getString("USER_NAME", ""));
			password.setText(sp.getString("PASSWORD", ""));
			username.requestFocus();
		}
		// 监听记住密码多选框按钮事件
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

			}
		});

		// 监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (auto_login.isChecked()) {

					rem_pw.setChecked(true);
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

				} else {

					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});

	}

	// 判断是否有网络访问
	private boolean isNetworkAvailable() {
		Context context = getApplicationContext();
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// 监听事件
	private void setlistener() {
		username.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				if (s.length() > 19) {
					Toast.makeText(XZHL0001_LoginActivity.this,
							XZHL0001_Constats.ZIFUXIANZHI, Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

		});
		password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

				if (s.length() > 19) {
					Toast.makeText(XZHL0001_LoginActivity.this,
							XZHL0001_Constats.ZIFUXIANZHI, Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}
		});
		// 按钮监听
		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean net = isNetworkAvailable();

				loginbtn.setEnabled(false);
				if (net) {
					usernameString = username.getText().toString();
					passwordString = password.getText().toString();
					if ((usernameString == null || usernameString.length() == 0)
							&& (passwordString == null || passwordString
									.length() == 0)) {
						Toast.makeText(XZHL0001_LoginActivity.this,
								XZHL0001_Constats.SHURUMZ, Toast.LENGTH_SHORT)
								.show();
						loginbtn.setEnabled(true);
						// return;
					} else if (usernameString == null
							|| usernameString.length() == 0) {
						Toast.makeText(XZHL0001_LoginActivity.this,
								XZHL0001_Constats.SHURUZH, Toast.LENGTH_SHORT)
								.show();
						loginbtn.setEnabled(true);

					}

					else if (passwordString == null
							|| passwordString.length() == 0) {
						Toast.makeText(XZHL0001_LoginActivity.this,
								XZHL0001_Constats.SHURUMM, Toast.LENGTH_SHORT)
								.show();
						loginbtn.setEnabled(true);
						// return;
					}

					else {
						Login login = new Login();
						login.execute();
					}

				} else {
					Toast.makeText(XZHL0001_LoginActivity.this,
							XZHL0001_Constats.NETERROR, Toast.LENGTH_SHORT)
							.show();
					loginbtn.setEnabled(true);
				}

			}
		});

	}

	// 异步判断登陆
	private class Login extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			int flag = 0;
			try {
				List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				parameters.add(new BasicNameValuePair("loginName",
						usernameString));
				parameters.add(new BasicNameValuePair("password",
						passwordString));
				properties = Commonutil
						.loadIPProperties(XZHL0001_LoginActivity.this);

				// String result1 = XZHL0001_JsonUtil1_HttpSubmitto.ListTest2(
				// path, parameters);
				String[] key = { "username", "password" };
				String[] value = { usernameString,
						Encrypt_Util.md5(passwordString) };
				// uap.setUser(usernameString);
				// uap.setPassword(passwordString);
				String json = webservice.WSservers(
						XZHL0001_LoginActivity.this,
						"user/login",
						JsonUtil.DataToJson(
								"a001",
								JsonUtil.DataToJson(key, value),
								"",
								JsonUtil.getLogSign("a001",
										JsonUtil.DataToJson(key, value))));
				// String result2 = XZHL0001_JsonUtil1.jiexiJSON(result1);

				login = JsonUtil.JsonToLogin(json);

				// 0失败1成功
				String result2 = login.getSuccess();
				Log.i("2222", result2);
				// 功能临时屏蔽
				String yanzheng = yanzheng(XZHL0001_LoginInfo.SHOPID);
				Log.i("yanzheng", yanzheng);
				// String yanzheng = "success";
				if (result2.equals("0")) {
					flag = 2;

				} else if (result2.equals("1") && "success".equals(yanzheng)) {
					flag = 1;
				} else if (result2.equals("1") && "false".equals(yanzheng)) {
					flag = 4;
				} else if (result2.equals("1") && "error".equals(yanzheng)) {
					flag = 3;
				} else {
					flag = 3;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return flag;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {

			case 1:
				String shopnameString = login.getShops() + login.getStore();
				if (rem_pw.isChecked()) {

					// 记住用户名、密码、
					Editor editor = sp.edit();
					editor.putString("USER_NAME", usernameString);
					editor.putString("PASSWORD", passwordString);
					editor.putString("QUANXIAN", login.getRole());
					editor.putString("SHOPNAME", shopnameString);
					editor.commit();
				}
				SharedPreferences.Editor sharedata = getSharedPreferences(
						"data", 0).edit();
				sharedata.putString("NAME", usernameString);
				sharedata.putString("PASS", passwordString);
				sharedata.putString("QUANXIAN", login.getRole());
				sharedata.putString("ID", login.getId());
				sharedata.putString("SHOPNAME", shopnameString);
				sharedata.putString("REALNAME", login.getRealname());
				sharedata.putString("TEL", login.getTel());
				sharedata.putString("TOKEN", login.getToken());
				sharedata.putString("shopId", XZHL0001_LoginInfo.SHOPID);
				sharedata.commit();
				Log.i("11111", shopnameString);

				if (rem_pw.isChecked()) {

					sp.edit().putBoolean("ISCHECK", true).commit();

				} else {

					sp.edit().putBoolean("ISCHECK", false).commit();

				}
				if (auto_login.isChecked()) {

					rem_pw.setChecked(true);
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

				} else {

					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}

				Intent intent = new Intent();

				intent.setClass(XZHL0001_LoginActivity.this,
						XZHL0003_FunctionListActivity.class);
				//bb.loging();
				startActivity(intent);
				XZHL0001_LoginActivity.this.finish();
				break;
			case 2:

				Toast.makeText(XZHL0001_LoginActivity.this,
						XZHL0001_Constats.LIEBIAOSHIBAI, Toast.LENGTH_SHORT)
						.show();
				loginbtn.setEnabled(true);
				break;
			case 3:
				Toast.makeText(XZHL0001_LoginActivity.this,
						XZHL0001_Constats.SERVRCEERROR, Toast.LENGTH_SHORT)
						.show();
				loginbtn.setEnabled(true);
				break;
			case 4:
				Toast.makeText(XZHL0001_LoginActivity.this,
						XZHL0001_Constats.FEIFALOGIN, Toast.LENGTH_SHORT)
						.show();
				loginbtn.setEnabled(true);
				break;
			default:
				Toast.makeText(XZHL0001_LoginActivity.this,
						XZHL0001_Constats.SERVRCEERROR, Toast.LENGTH_SHORT)
						.show();
				loginbtn.setEnabled(true);
				break;
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}
	}

	protected String yanzheng(String shopid) {
		Log.i("shopid", Encrypt_Util.encrypt(shopid));
		if (!"".equals(shopid)) {
			WebService webService = new WebService();
			ArrayList<String> arrayList = new ArrayList<String>();

			arrayList.add("{\"shopId\":\"" + Encrypt_Util.encrypt(shopid)
					+ "\"}");
			String web = webService.WSserversoflocal(
					XZHL0001_LoginActivity.this, "checkShopId", "CheckPort",
					arrayList);
			Log.i("shopid", Encrypt_Util.encrypt(shopid));
			Log.i("web", Encrypt_Util.encrypt(shopid));
			String yanzheng = XZHL0001_JsonUtil1.json2Miwen(web);
			Log.i("false---------------------------", yanzheng);
			if ("{JSONERROR}".equals(yanzheng)) {
				return "error";
			} else if ("success".equals(yanzheng)) {
				return "success";
			} else if ("false".equals(yanzheng)) {
				return "false";
			} else {
				return "error";
			}
		} else {
			return "none";
		}
	}
}