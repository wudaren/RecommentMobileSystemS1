package com.fbse.recommentmobilesystem.XZHL0650;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0620.XZHL0620_MailInfoDisplayActivity;

public class XZHL0650_MailSummary extends Activity implements OnClickListener {
	private ImageView back;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl0650_mailsummary);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.ll:
			Intent intent2 = new Intent(getApplicationContext(),
					XZHL0620_MailInfoDisplayActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}

	}
}
