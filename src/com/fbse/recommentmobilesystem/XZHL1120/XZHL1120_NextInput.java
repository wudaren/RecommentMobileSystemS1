package com.fbse.recommentmobilesystem.XZHL1120;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.fbse.recommentmobilesystem.R;

public class XZHL1120_NextInput extends Activity implements OnClickListener {
	private ImageView back, finished;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl1120_nextinput);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		finished = (ImageView) findViewById(R.id.finished);
		finished.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
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
