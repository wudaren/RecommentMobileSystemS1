package com.fbse.recommentmobilesystem.XZHL0210;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0220.XZHL0220_QuestioniareDetailActivity;

public class XZHL0210_ShowQuestioniareActivity extends Activity {
    public static final String TAG = "XZHL0210_ShowQuestioniareActivity";
    ListView listSurvey;
    TextView company;
    TextView wenjuanId;
    TextView mark;
    TextView total;
    TextView hadDone;
    List<XZHL0210_Questioniare> list;
    String resultInfo;
    Button goToMain;
    Button refresh;
    Handler mHandler;
    ProgressBar pb;
    XZHL0210_BriefViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0210_main);
        // 初始化界面
        setupView();
        // 加监听器
        addListener();
        // 初始化常量数据
        initProperties();
        // 开启异步任务读取问卷列表
        Log.i("0416", XZHL0210_Constants.LISTACTION + XZHL0210_Constants.SHOPID);
        if (XZHL0210_Utils.isNetworkAvailable(XZHL0210_ShowQuestioniareActivity.this)) {
            new XZHL0210_BriefListParserTask(this).execute(XZHL0210_Constants.LISTACTION);
        } else {
            XZHL0210_Utils.showToast(XZHL0210_ShowQuestioniareActivity.this, XZHL0210_Constants.NETERROR,
                    Toast.LENGTH_SHORT);
        }

    }

    private void initProperties() {
        Properties p1 = XZHL0210_Constants.loadIPProperties(this, "androidpn");
        XZHL0210_Constants.initData(p1);
        XZHL0210_Constants.initStrings(XZHL0210_ShowQuestioniareActivity.this);
        SharedPreferences sp = getSharedPreferences("data", 0);
        XZHL0210_Constants.SHOPID = sp.getString("shopId", "");
        XZHL0210_Constants.USERNAME = sp.getString("NAME", "");
        XZHL0210_Constants.TOKEN = sp.getString("ID", "");
    }

    private void setupView() {
        listSurvey = (ListView) findViewById(R.id.lv_listSurvey_0210);
        goToMain = (Button) findViewById(R.id.btn_goToMain_0210);
        refresh = (Button) findViewById(R.id.btn_shuxinList_0210);
        pb = (ProgressBar) findViewById(R.id.pb_pblist_0210);
        pb.setVisibility(View.VISIBLE);
        list = new ArrayList<XZHL0210_Questioniare>();
        adapter = new XZHL0210_BriefViewAdapter(this, list, listSurvey);
        listSurvey.setAdapter(adapter);
    }

    // 根据返回的数据更新ListView界面
    public void updateListView(ArrayList<XZHL0210_Questioniare> list) {
        pb.setVisibility(View.GONE);
        if (list == null) {
            XZHL0210_Utils.showToast(XZHL0210_ShowQuestioniareActivity.this, XZHL0210_Constants.LIEBIAOSHIBAI,
                    Toast.LENGTH_SHORT);
        } else if (list.size() == 0) {
            XZHL0210_Utils.showToast(XZHL0210_ShowQuestioniareActivity.this, XZHL0210_Constants.ZANWU,
                    Toast.LENGTH_SHORT);
        } else {
            adapter.changeData(list);
        }
    }

    public void addListener() {
        goToMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                XZHL0210_ShowQuestioniareActivity.this.finish();
            }
        });
        refresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (XZHL0210_Utils.isNetworkAvailable(XZHL0210_ShowQuestioniareActivity.this)) {
                    pb.setVisibility(View.VISIBLE);
                    new XZHL0210_BriefListParserTask(XZHL0210_ShowQuestioniareActivity.this)
                            .execute(XZHL0210_Constants.LISTACTION);
                } else {
                    XZHL0210_Utils.showToast(XZHL0210_ShowQuestioniareActivity.this, XZHL0210_Constants.NETERROR,
                            Toast.LENGTH_SHORT);
                }
            }
        });
        // 给ListView设置Item点击事件跳转到问卷详细界面
        listSurvey.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                company = (TextView) arg1.findViewById(R.id.tv_company_0210);
                wenjuanId = (TextView) arg1.findViewById(R.id.tv_wenjuanId_0210);
                mark = (TextView) arg1.findViewById(R.id.tv_mark_0210);
                total = (TextView) arg1.findViewById(R.id.tv_totalTask_0210);
                hadDone = (TextView) arg1.findViewById(R.id.tv_alreadyTask_0210);
                if (Integer.parseInt(hadDone.getText().toString()) >= Integer.parseInt(total.getText().toString())) {
                    XZHL0210_Utils.showToast(XZHL0210_ShowQuestioniareActivity.this, XZHL0210_Constants.YIDAWAN,
                            Toast.LENGTH_SHORT);
                    return;
                }
                ArrayList<String> list = new ArrayList<String>();
                list.add(wenjuanId.getText().toString());
                list.add(company.getText().toString());
                list.add(mark.getText().toString());
                Intent intent = new Intent();
                intent.putStringArrayListExtra("list", list);
                intent.setClass(XZHL0210_ShowQuestioniareActivity.this, XZHL0220_QuestioniareDetailActivity.class);
                XZHL0210_ShowQuestioniareActivity.this.startActivity(intent);
                XZHL0210_ShowQuestioniareActivity.this.finish();
            }
        });
    }

    class XZHL0210_BriefListParserTask extends AsyncTask<String, String, ArrayList<XZHL0210_Questioniare>> {
        private XZHL0210_ShowQuestioniareActivity context;

        public XZHL0210_BriefListParserTask(XZHL0210_ShowQuestioniareActivity context) {
            this.context = context;
        }

        // 异步任务执行的核心方法
        @Override
        protected ArrayList<XZHL0210_Questioniare> doInBackground(String... params) {
            ArrayList<XZHL0210_Questioniare> list = new ArrayList<XZHL0210_Questioniare>();
            try {
                // 调用返回实体类的集合
                list = XZHL0210_Utils.parserBrief(params[0]);
                if (list == null) {
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return list;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<XZHL0210_Questioniare> result) {
            // 更新界面
            context.updateListView(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }
    }
}