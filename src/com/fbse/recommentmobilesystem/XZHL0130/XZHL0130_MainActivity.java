package com.fbse.recommentmobilesystem.XZHL0130;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.gorillatechnolody.fdrcontrol.FDRControl;
import com.gorillatechnolody.fdrcontrol.FDRServiceClient;

public class XZHL0130_MainActivity extends Activity {

	private static final String TAG = "FDRControlSample";
	
	private FDRControl fdrCtrl = null;
	private TextView nameView = null;
	
	private static int cameraId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl0310_activity_main);
		
		// add FDR-Control
		fdrCtrl = new FDRControl(this, cameraId);
        FrameLayout fdrFrame = (FrameLayout)findViewById(R.id.fdr_frame);
        fdrFrame.addView(fdrCtrl);
        
        // init FDR-Control
        fdrCtrl.setFDRServiceResponseListener(onFdrResponse);
        
        // get name TextView
        nameView = (TextView)findViewById(R.id.name_view);
        
        // add enroll action
        Button enrollBtn = (Button)findViewById(R.id.enroll_btn);
        enrollBtn.setOnClickListener(onEnrollClick);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		// get settings
		XZHL0130_AppPreferences prefs = new XZHL0130_AppPreferences(this);
		String host = prefs.getHost();
		int port = prefs.getPort();
		String username = prefs.getUsername();
		String password = prefs.getPassword();
		
		// login FDR-Control
		//fdrCtrl.loginFDRService("192.168.4.97", 8041, "FDR_HTTP_CLIENT", "GorillaAndroidAppYNB");
        fdrCtrl.loginFDRService(host, port, username, password);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.action_switch_camera:
			fdrCtrl.switchCamera((++cameraId) % 2);
			break;
			
		case R.id.action_settings:
			Intent intent = new Intent();
			intent.setClass(this, XZHL0130_SettingsActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		
		fdrCtrl.logoutFDRService();
		fdrCtrl.release();

		super.onDestroy();
	}
	
	private FDRServiceClient.ResponseListener onFdrResponse = new FDRServiceClient.ResponseListener() {

		@Override
		public void onLoginResponse(int respCode) {

			Log.d(TAG, String.format("onLoginResponse - code:%s", respCode));
			
			if (respCode == 0) {
				
				Toast.makeText(XZHL0130_MainActivity.this, "Login succeeded.", Toast.LENGTH_SHORT)
					.show();
			}
			else {
				Toast.makeText(XZHL0130_MainActivity.this, String.format("Login reponse error %d.", respCode), Toast.LENGTH_SHORT)
				.show();
			}
		}

		@Override
		public void onLogoutResponse(int respCode) {

			Log.d(TAG, String.format("onLogoutResponse - code:%s", respCode));
		}

		@Override
		public void onSetTargetInfoResponse(int respCode, String targetId) {

			Log.d(TAG, String.format("onSetTargetInfoResponse - code:%s, id:%s", respCode, targetId));
			
			if (respCode == 0) {

				boolean ret = fdrCtrl.enrollTarget(targetId);
				if(!ret) {
					Toast.makeText(XZHL0130_MainActivity.this, "Enroll target failed.", Toast.LENGTH_SHORT)
						.show();
				}
			}
			else {
				Toast.makeText(XZHL0130_MainActivity.this, String.format("Set target reponse error %d.", respCode), Toast.LENGTH_SHORT)
					.show();
			}
		}

		@Override
		public void onGetTargetInfoResponse(int respCode, String targetInfo) {

			Log.d(TAG, String.format("onGetTargetInfoResponse - code:%s, info:", respCode, targetInfo));
		}

		@Override
		public void onDeleteTargetResponse(int respCode) {

			Log.d(TAG, String.format("onDeleteTargetResponse - code:%s", respCode));
		}

		@Override
		public void onEnrollTargetResponse(int respCode) {

			Log.d(TAG, String.format("onEnrollTargetResponse - code:%s", respCode));
			
			if (respCode == 0) {

				Toast.makeText(XZHL0130_MainActivity.this, "Enroll target succeeded.", Toast.LENGTH_SHORT)
					.show();
		}
			else {
				Toast.makeText(XZHL0130_MainActivity.this, String.format("Enroll target reponse error %d.", respCode), Toast.LENGTH_SHORT)
					.show();
			}
		}

		@Override
		public void onFinalizeTemplatesResponse(int respCode) {

			Log.d(TAG, String.format("onFinalizeTemplatesResponse - code:%s", respCode));
		}
		
	};
	
	private OnClickListener onEnrollClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
					 
			String name = nameView.getText().toString();
			if (name.isEmpty()) {
				
				Toast.makeText(XZHL0130_MainActivity.this, "ID/Name is empty.", Toast.LENGTH_SHORT)
					.show();
			}
			else if (!fdrCtrl.isTargetSelected()) {
				
				Toast.makeText(XZHL0130_MainActivity.this, "No target selected.", Toast.LENGTH_SHORT)
					.show();
			}
			else {
				
				String targetInfo = String.format("[Target_Info]\r\nID=%s\r\nName=%s\r\nBirth=1990-01-01\r\n",
						name, name);
				
				// need call enrollTarget after setTargetInfo response to complete enroll
				fdrCtrl.setTargetInfo("0", targetInfo);
				Toast.makeText(XZHL0130_MainActivity.this, "回传相片", Toast.LENGTH_SHORT).show();
                    //Bitmap bm=BitmapFactory.decodeStream(XZHL0130_MainActivity.this.getAssets().open("nvzhang1.png"));
                    //XZHL0130_Bitmap bitmapclass=new XZHL0130_Bitmap();
                   // bitmapclass.setBitmap(bm);
                    Intent intent=new Intent();
                   // Bundle bundle=new Bundle();
                   // bundle.putSerializable("data", bitmapclass);
                   // intent.putExtras(bundle);
                    XZHL0130_MainActivity.this.setResult(RESULT_OK, intent);
                    XZHL0130_MainActivity.this.finish();
                
			}
		}
		
	};
}
