package com.fbse.recommentmobilesystem.XZHL1110;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL1120.XZHL1120_NextInput;
import com.fbse.recommentmobilesystem.XZHL1140.XZHL1140_NextInfo;

public class XZHL1110_NextDisplay extends Activity implements OnClickListener {
	private ImageView back, add;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl1110_nextdisplay);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		add = (ImageView) findViewById(R.id.add);
		add.setOnClickListener(this);
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add:
			Intent intent = new Intent(getApplicationContext(),
					XZHL1120_NextInput.class);
			startActivity(intent);
			break;
		case R.id.ll:
			Intent intent2 = new Intent(getApplicationContext(),
					XZHL1140_NextInfo.class);
			startActivity(intent2);
			break;
		default:
			break;
		}

	}
}
