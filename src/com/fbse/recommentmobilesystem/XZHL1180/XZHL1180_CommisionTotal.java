package com.fbse.recommentmobilesystem.XZHL1180;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.fbse.recommentmobilesystem.R;

public class XZHL1180_CommisionTotal extends Activity implements
		OnClickListener {
	private ImageView back, add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl1180_commisiontotal);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		add = (ImageView) findViewById(R.id.add);
		add.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add:

			break;
		default:
			break;
		}

	}
}
