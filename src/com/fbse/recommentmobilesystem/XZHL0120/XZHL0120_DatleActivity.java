package com.fbse.recommentmobilesystem.XZHL0120;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0110.XZHL0110_Utils;
import com.fbse.recommentmobilesystem.XZHL0130.XZHL0130_VipInfoActivity;
import com.fbse.recommentmobilesystem.common.common_entity.ShowInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XZHL0120_DatleActivity extends Activity {
    ImageView photoView;
    Bitmap bi;
    Button retbtn;
    Button delbtn;
    TextView nametv;
    TextView phonetv;
    TextView cardtype;
    XZHL0120_MyListVIew history;
    XZHL0120_MyListVIew display;
    Bitmap bitmap;
    ShowInfo user;
    Button zhucebtn;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
        setContentView(R.layout.xzhl0120_user_detail);
        findviewbyid();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getSerializableExtra("EntityOfImage") != null) {
                user = (ShowInfo) intent.getSerializableExtra("EntityOfImage");
                zhucebtn.setVisibility(View.VISIBLE);
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.GONE);

            } else {
                user = (ShowInfo) intent.getSerializableExtra("Entity");
            }

            MyTask1 myTask1 = new MyTask1();
            myTask1.execute();
        }
        setButtonOnclicklistener();
        setlist();
    }

    private void findviewbyid() {
        retbtn = (Button) findViewById(R.id.gobackBtn);
        photoView = (ImageView) findViewById(R.id.dish_picture);
        nametv = (TextView) findViewById(R.id.date_name);
        history = (XZHL0120_MyListVIew) findViewById(R.id.history);
        cardtype = (TextView) findViewById(R.id.view_phone);
        display = (XZHL0120_MyListVIew) findViewById(R.id.display);
        phonetv = (TextView) findViewById(R.id.dish_cooker);
        delbtn = (Button) findViewById(R.id.delbtn);
        zhucebtn = (Button) findViewById(R.id.zhucebtn);
        linearLayout1 = (LinearLayout) findViewById(R.id.linear0120_1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linear0120_2);

    }

    private void setButtonOnclicklistener() {
        retbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                XZHL0120_DatleActivity.this.finish();
            }
        });
        delbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(
                        XZHL0120_DatleActivity.this);
                builder.setTitle("确定要删除该顾客吗？");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                Intent intent = new Intent();
                                intent.putExtra("delflg", true);
                                setResult(1, intent);
                                finish();
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                    int which) {

                            }
                        });
                builder.show();

            }
        });
        zhucebtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(XZHL0120_DatleActivity.this,
                        XZHL0130_VipInfoActivity.class);
                intent.putExtra("imageurl", user.getImageUrl());
                startActivity(intent);

            }
        });
    }

    private void setlist() {

        nametv.setText(user.getVipName());
        phonetv.setText(user.getMobile());
        cardtype.setText(user.getCardTypeName());
        // 消费习惯
        List<Map<String, Object>> hisList = new ArrayList<Map<String, Object>>();
        Map<String, Object> m = null;
        for (int i = 0; i < user.getConTrendInfor().length; i++) {
            m = new HashMap<String, Object>();
            m.put("view_menu", user.getConTrendInfor()[i]);
            hisList.add(m);
        }
        XZHL0120_InfoAdapter xzhl0120_InfoAdapter = new XZHL0120_InfoAdapter(
                hisList, this);
        history.setAdapter(xzhl0120_InfoAdapter);
        // 推荐信息
        List<Map<String, Object>> disList = new ArrayList<Map<String, Object>>();
        Map<String, Object> m2 = null;
        for (int i = 0; i < user.getPromoInfor().length; i++) {
            m2 = new HashMap<String, Object>();
            m2.put("view_menu", user.getPromoInfor()[i]);
            disList.add(m2);
        }
        XZHL0120_InfoAdapter xzhl0120_InfoAdapter2 = new XZHL0120_InfoAdapter(
                disList, this);
        display.setAdapter(xzhl0120_InfoAdapter2);
    }

    private class MyTask1 extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            // photoView.setImageBitmap(bitmap);
            bi = XZHL0110_Utils.loadBitmap(user.getImageUrl());
            return null;

        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // showDilog();
        }

        @Override
        protected void onPostExecute(Integer result) {
            // sendDatadialog.dismiss();
            photoView.setImageBitmap(bi);

        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }
    }
}
