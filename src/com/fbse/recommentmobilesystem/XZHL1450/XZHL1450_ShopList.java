package com.fbse.recommentmobilesystem.XZHL1450;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL1410.XZHL1410_MoneyShow;

public class XZHL1450_ShopList extends Activity implements OnClickListener {
	private ImageView back;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题显示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.xzhl1450_shoplist);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(this);
		ll = (LinearLayout) findViewById(R.id.ll);
		ll.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.ll:
			Intent intent2 = new Intent(getApplicationContext(),
					XZHL1410_MoneyShow.class);
			startActivity(intent2);
			break;
		default:
			break;
		}

	}
}
