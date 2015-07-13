package com.fbse.recommentmobilesystem.XZHL0110;

import java.util.List;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.common_entity.ShowInfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XZHL0110_SingleUserDialog extends Dialog {
	// 定义回调事件，用于dialog的点击事件
	public interface OnCustomDialogListener {
		public void back(Bitmap bitmap, ShowInfo user);
	}

	@SuppressWarnings("unused")
    private Context context;
	private OnCustomDialogListener customDialogListener;
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	private TextView textView6;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private LinearLayout layout1, layout2, layout3;
	private List<ShowInfo> userlist;
	private List<Bitmap> imageList;

	public XZHL0110_SingleUserDialog(Context context, List<ShowInfo> userlist,
			List<Bitmap> imageList, OnCustomDialogListener customDialogListener) {
		super(context);
		this.context = context;
		this.userlist = userlist;
		this.imageList = imageList;
		this.customDialogListener = customDialogListener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		if (userlist.size() == 3) {

			setContentView(R.layout.xzhl0110_dialogitem2);
			// 设置dialog点击外部自动清除
			setCanceledOnTouchOutside(true);

			// 获得控件
			imageView1 = (ImageView) findViewById(R.id.dialogimage21);
			imageView2 = (ImageView) findViewById(R.id.dialogimage22);
			textView1 = (TextView) findViewById(R.id.dialogtextView21);
			textView2 = (TextView) findViewById(R.id.dialogtextView22);
			textView3 = (TextView) findViewById(R.id.dialogtextView23);
			textView4 = (TextView) findViewById(R.id.dialogtextView24);
			layout1 = (LinearLayout) findViewById(R.id.linear21);
			layout2 = (LinearLayout) findViewById(R.id.linear22);
			// 初始化控件
			imageView1.setImageBitmap(imageList.get(0));
			imageView2.setImageBitmap(imageList.get(1));
			textView1.setText(userlist.get(1).getVipName());
			textView2.setText(userlist.get(1).getMobile());
			textView3.setText(userlist.get(2).getVipName());
			textView4.setText(userlist.get(2).getMobile());

			layout1.setOnClickListener(clickListener);
			layout2.setOnClickListener(clickListener);
		} else {

			setContentView(R.layout.xzhl0110_dialogitem);
			// 设置dialog点击外部自动清除

			setCanceledOnTouchOutside(true);

			// 获得控件
			imageView1 = (ImageView) findViewById(R.id.dialogimage1);
			imageView2 = (ImageView) findViewById(R.id.dialogimage2);
			imageView3 = (ImageView) findViewById(R.id.dialogimage3);
			textView1 = (TextView) findViewById(R.id.dialogtextView1);
			textView2 = (TextView) findViewById(R.id.dialogtextView2);
			textView3 = (TextView) findViewById(R.id.dialogtextView3);
			textView4 = (TextView) findViewById(R.id.dialogtextView4);
			textView5 = (TextView) findViewById(R.id.dialogtextView5);
			textView6 = (TextView) findViewById(R.id.dialogtextView6);
			layout1 = (LinearLayout) findViewById(R.id.linear1);
			layout2 = (LinearLayout) findViewById(R.id.linear2);
			layout3 = (LinearLayout) findViewById(R.id.linear3);
			// 初始化控件
			imageView1.setImageBitmap(imageList.get(0));
			imageView2.setImageBitmap(imageList.get(1));
			imageView3.setImageBitmap(imageList.get(2));
			textView1.setText(userlist.get(1).getVipName());
			textView2.setText(userlist.get(1).getMobile());
			textView3.setText(userlist.get(2).getVipName());
			textView4.setText(userlist.get(2).getMobile());
			textView5.setText(userlist.get(3).getVipName());
			textView6.setText(userlist.get(3).getMobile());

			layout1.setOnClickListener(clickListener);
			layout2.setOnClickListener(clickListener);
			layout3.setOnClickListener(clickListener);
			// clickBtn.setOnClickListener(clickListener);
		}

	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// 点击事件处理
			switch (v.getId()) {
			case R.id.linear1:
				customDialogListener.back(imageList.get(0), userlist.get(1));
				XZHL0110_SingleUserDialog.this.dismiss();
				break;
			case R.id.linear2:
				customDialogListener.back(imageList.get(1), userlist.get(2));
				XZHL0110_SingleUserDialog.this.dismiss();
				break;
			case R.id.linear3:
				customDialogListener.back(imageList.get(2), userlist.get(3));
				XZHL0110_SingleUserDialog.this.dismiss();
				break;
			case R.id.linear21:
				customDialogListener.back(imageList.get(0), userlist.get(1));
				XZHL0110_SingleUserDialog.this.dismiss();
				break;
			case R.id.linear22:
				customDialogListener.back(imageList.get(1), userlist.get(2));
				XZHL0110_SingleUserDialog.this.dismiss();
				break;
			default:
				break;
			}

        }
    };

}
