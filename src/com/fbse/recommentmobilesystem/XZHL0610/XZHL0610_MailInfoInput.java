package com.fbse.recommentmobilesystem.XZHL0610;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.fbse.recommentmobilesystem.R;

public class XZHL0610_MailInfoInput extends Activity implements OnClickListener {
	private ImageView back, finished;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl0610_mailinfoinput);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		finished = (ImageView) findViewById(R.id.finished);
		finished.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.finished:
			finish();
			break;
		default:
			break;
		}

	}
}
