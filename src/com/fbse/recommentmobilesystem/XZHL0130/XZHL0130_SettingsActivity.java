package com.fbse.recommentmobilesystem.XZHL0130;

import com.fbse.recommentmobilesystem.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class XZHL0130_SettingsActivity extends Activity {
	
	private TextView hostView = null;
	private TextView portView = null;
	private TextView usernameView = null;
	private TextView passwordView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl0130_activity_settings);
		
		// get text view
		hostView = (TextView) findViewById(R.id.host_view);
		portView = (TextView) findViewById(R.id.port_view);
		usernameView = (TextView) findViewById(R.id.username_view);
		passwordView = (TextView) findViewById(R.id.password_view);
		
		// set text view text
		XZHL0130_AppPreferences prefs = new XZHL0130_AppPreferences(this);
		hostView.setText(prefs.getHost());
		portView.setText(String.valueOf(prefs.getPort()));
		usernameView.setText(prefs.getUsername());
		passwordView.setText(prefs.getPassword());
		
        // add OK action
        Button enrollBtn = (Button)findViewById(R.id.ok_btn);
        enrollBtn.setOnClickListener(onOKClick);
	}
	
	private OnClickListener onOKClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			// save settings
			XZHL0130_AppPreferences prefs = new XZHL0130_AppPreferences(XZHL0130_SettingsActivity.this);
			prefs.setHost(hostView.getText().toString());
			prefs.setPort(Integer.parseInt(portView.getText().toString()));
			prefs.setUsername(usernameView.getText().toString());
			prefs.setPassword(passwordView.getText().toString());
			
			finish();
		}
	};
}
