package com.fbse.recommentmobilesystem.XZHL1340;

import java.util.ArrayList;

import com.fbse.recommentmobilesystem.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class XZHL1340_TongdetailActivity extends Activity {
   private Button bt1;
   private TextView tx1;
   private TextView tx2;
   ArrayList<String> intents;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xzhl1340_tongdetail);
		bt1=(Button)this.findViewById(R.id.goToMain);
		tx1=(TextView)this.findViewById(R.id.textView1);
		tx2=(TextView)this.findViewById(R.id.textView2);
		Intent intent=getIntent();
		intents=intent.getStringArrayListExtra("list");
	   tx1.setText(intents.get(0));
	 tx2.setText(intents.get(1));
	 addListener();
	}
	private void addListener() {
		bt1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					XZHL1340_TongdetailActivity.this.finish();
				}
			});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.general_survey, menu);
		return true;
	}

}
