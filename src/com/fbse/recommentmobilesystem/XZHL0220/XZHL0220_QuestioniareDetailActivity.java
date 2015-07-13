package com.fbse.recommentmobilesystem.XZHL0220;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_Constants;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_ShowQuestioniareActivity;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_Utils;
import com.fbse.recommentmobilesystem.common.HttpSubmit;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

public class XZHL0220_QuestioniareDetailActivity extends Activity {
    ListView listView;
    RadioGroup rg;
    LinearLayout linearCheckBox;
    LinearLayout linearRadio;
    LinearLayout linearbs;
    LayoutInflater inflater;
    Button back;
    Button go;
    Button goToList;
    Button submit;
    View.OnClickListener cl;
    XZHL0220_QuestionAdapter qa;
    TextView progressTv;
    int index = 0;
    List<XZHL0220_Choice> questions;
    int pages;
    XZHL0220_MapData datas;
    XZHL0220_MapCheckBox mcb;
    ProgressBar pro;
    ProgressBar pbDetail;
    String wenjuan;
    String wenjuanId;
    String sorce;
    ArrayList<String> intents;
    Map<Integer, String> answerMap;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0220_main);
        // 取出问卷一览界面传过来的参数
        Intent intent = getIntent();
        intents = intent.getStringArrayListExtra("list");
        wenjuanId = intents.get(0);
        wenjuan = intents.get(1);
        sorce = intents.get(2);
        // 初始化界面
        setupView();
        // 给控件加监听器
        addListener();
        // 判断传过来的问卷名称是否为空
        if (wenjuan == null || wenjuan.length() == 0) {
            pbDetail.setVisibility(View.GONE);
            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.LIEBIAOSHIBAI,
                    Toast.LENGTH_SHORT);
        } else {
            // 判断网络是否连接正常
            if (XZHL0210_Utils.isNetworkAvailable(XZHL0220_QuestioniareDetailActivity.this)) {
                // 开启异步任务读取对应问卷的xml文件
                new XZHL0220_AsyncGetData(this).execute(XZHL0210_Constants.DETAILACTION + wenjuan + ".xml");
            } else {
                XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.NETERROR,
                        Toast.LENGTH_SHORT);
            }

        }
    }

    // 给手机返回键注册监听器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDlog();
        }
        return super.onKeyDown(keyCode, event);
    }

    // 界面初始化
    private void setupView() {
        listView = (ListView) findViewById(R.id.lv_detail_0220);
        back = (Button) findViewById(R.id.btn_back_0220);
        progressTv = (TextView) findViewById(R.id.tv_progressTv_0220);
        go = (Button) findViewById(R.id.btn_go_0220);
        goToList = (Button) findViewById(R.id.btn_goToList_0220);
        submit = (Button) findViewById(R.id.btn_submitanswer_0220);
        pbDetail = (ProgressBar) findViewById(R.id.pb_pbdetail_0220);
        pbDetail.setVisibility(View.VISIBLE);
        questions = new ArrayList<XZHL0220_Choice>();
        qa = new XZHL0220_QuestionAdapter(this, questions, listView, 0);
        listView.setAdapter(qa);
    }

    // 当ListView数据变化时更新界面的控制器方法
    private void changeData(List<XZHL0220_Choice> list, int index) {
        // TODO Auto-generated method stub
        qa = new XZHL0220_QuestionAdapter(this, list, listView, index);
        listView.setAdapter(qa);
        // 重新计算ListView显示所有内容需要的高度
        setListViewHeight(listView);
        progressTv.setText((index + 1) + "/" + pages);
    }

    // 根据翻页操作动态操作需要显示的题目数据并更新ListView
    private void initView(List<XZHL0220_Choice> list) {
        progressTv.setText((index + 1) + "/" + pages);
        if (list.size() > 5) {
            qa = new XZHL0220_QuestionAdapter(this, list.subList(0, 5), listView, 0);
            listView.setAdapter(qa);
            setListViewHeight(listView);
        } else {
            qa = new XZHL0220_QuestionAdapter(this, list.subList(0, list.size()), listView, 0);
            listView.setAdapter(qa);
            setListViewHeight(listView);
        }
    }

    // 初始化存储操作逻辑数据的类，保存用户输入的信息
    private void initData() {
        int page = questions.size() % 5;
        if (page == 0) {
            pages = questions.size() / 5;
        } else {
            pages = questions.size() / 5 + 1;
        }
        datas = new XZHL0220_MapData(pages);
        mcb = new XZHL0220_MapCheckBox(pages);
    }

    // 注册监听器
    private void addListener() {
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                EditText et = (EditText) listView.findViewById(R.id.et_edittext_0220);
                if (et != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                }
                return false;
            }
        });
        cl = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                // 点击上一页的事件
                case R.id.btn_back_0220:
                    index -= 1;
                    if (index < 0) {
                        index = 0;
                        // 提示已是第一页的信息
                        XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.GINFO,
                                Toast.LENGTH_SHORT);
                        return;
                    }
                    if (index * 5 < questions.size()) {
                        if ((index + 1) * 5 < questions.size()) {
                            changeData(questions.subList(index * 5, (index + 1) * 5), index);
                        } else {
                            changeData(questions.subList(index * 5, questions.size()), index);
                        }
                    }
                    break;
                // 点击下一页的事件
                case R.id.btn_go_0220:
                    index += 1;
                    if (index >= pages) {
                        index = pages - 1;
                        // 提示已是最后一页的信息
                        XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.TINFO,
                                Toast.LENGTH_SHORT);
                        return;
                    }
                    if (index * 5 < questions.size()) {
                        if ((index + 1) * 5 < questions.size()) {
                            changeData(questions.subList(index * 5, (index + 1) * 5), index);
                        } else {
                            changeData(questions.subList(index * 5, questions.size()), index);
                        }
                    }

                    break;
                }
            }
        };
        back.setOnClickListener(cl);
        go.setOnClickListener(cl);
        // 返回问卷一览界面的按钮注册监听器
        goToList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (XZHL0220_MapData.answers.size() == 0) {
                    // 用户一题都没答
                    clearData();
                } else {
                    showDlog();
                }
            }
        });
        // 给提交答案按钮注册监听器
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断网络连接是否正常
                if (XZHL0210_Utils.isNetworkAvailable(XZHL0220_QuestioniareDetailActivity.this)) {
                    submit.setClickable(false);
                    answerMap = XZHL0220_MapData.answers;
                    if (answerMap == null || answerMap.size() == 0) {
                        XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this,
                                XZHL0210_Constants.HAIMEIDA, Toast.LENGTH_SHORT);
                        submit.setClickable(true);
                        return;
                    } else {
                        if (answerMap.size() != questions.size()) {
                            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this,
                                    XZHL0210_Constants.QINGDAWAN, Toast.LENGTH_SHORT);
                            submit.setClickable(true);
                            return;
                        } else {
                            // 检索用户提交的答案的HashMap中有没有空值
                            Collection<String> c = answerMap.values();
                            Iterator<String> it = c.iterator();
                            while (it.hasNext()) {
                                String str = it.next().trim();
                                if (str.equals("")) {
                                    XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this,
                                            XZHL0210_Constants.QINGDAWAN, Toast.LENGTH_SHORT);
                                    submit.setClickable(true);
                                    return;
                                }
                            }
                        }
                        showSureDlog();
                    }
                } else {
                    XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.NETERROR,
                            Toast.LENGTH_SHORT);
                }

            }
        });
    }

    // 确认放弃回答问卷按钮的对话框
    private void showDlog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(XZHL0210_Constants.FANGQI).setCancelable(true)
                .setPositiveButton(XZHL0210_Constants.QUEDING, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 点击确定的事件
                        clearData();
                    }
                }).setNegativeButton(XZHL0210_Constants.QUXIAO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 点击取消的事件
                        dialog.cancel();
                    }
                });
        builder.create().show();
    };

    // 确认提交问卷的对话框
    private void showSureDlog() {
        submit.setClickable(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(XZHL0210_Constants.TIJIAOMA).setCancelable(true)
                .setPositiveButton(XZHL0210_Constants.QUEDING, new DialogInterface.OnClickListener() {
                    @SuppressWarnings("unchecked")
                    public void onClick(DialogInterface dialog, int id) {
                        // 点击确定的事件
                        List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
                        parameters.add(new BasicNameValuePair("wenjuanId", wenjuanId));
                        parameters.add(new BasicNameValuePair("qname", wenjuan));
                        parameters.add(new BasicNameValuePair("qpoint", sorce));
                        parameters.add(new BasicNameValuePair("userName", XZHL0210_Constants.USERNAME));
                        Date date = new Date();
                        // SimpleDateFormat sdf =
                        // new SimpleDateFormat("yyyy-mm-dd HH:mm:ss E");
                        // String str = sdf.format(date);
                        String str = date.getTime() / 1000 + "";
                        // String str = new
                        // java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new
                        // java.util.Date(UnixTimestamp * 1000));
                        parameters.add(new BasicNameValuePair("time", str));

                        parameters.add(new BasicNameValuePair("serial", XZHL0210_Constants.SERIAL));
                        parameters.add(new BasicNameValuePair("token", XZHL0210_Constants.TOKEN));

                        // parameters.add(new BasicNameValuePair("shopId",
                        // XZHL0210_Constants.SHOPID));
                        // parameters.add(new BasicNameValuePair("answer",
                        // answerMap.toString()));
                        List<BasicNameValuePair> parameters2 = new ArrayList<BasicNameValuePair>();
                        parameters2.add(new BasicNameValuePair("answer", "这是上海数据" + answerMap.toString()));
                        parameters2.add(new BasicNameValuePair("qname", wenjuan));
                        parameters2.add(new BasicNameValuePair("shopId", XZHL0210_Constants.SHOPID));
                        parameters2.add(new BasicNameValuePair("wenjuanId", wenjuanId));
                        parameters2.add(new BasicNameValuePair("qpoint", sorce));
                        parameters2.add(new BasicNameValuePair("userName", XZHL0210_Constants.USERNAME));

                        // 开启异步任务提交答案，检测网络连接是否正常
                        if (XZHL0210_Utils.isNetworkAvailable(XZHL0220_QuestioniareDetailActivity.this)) {
                            new XZHL0220_AsyncSendAnswerTask(XZHL0220_QuestioniareDetailActivity.this).execute(
                                    parameters, parameters2);
                            showProgeressBar();
                        } else {
                            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this,
                                    XZHL0210_Constants.NETERROR, Toast.LENGTH_SHORT);
                        }
                        // 将提交按钮重置为可点击状态
                        submit.setClickable(true);
                    }
                }).setNegativeButton(XZHL0210_Constants.QUXIAO, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 点击取消按钮的事件
                        dialog.cancel();
                        submit.setClickable(true);
                    }
                });
        builder.create().show();
    }

    // 提交答案过程中的进度框
    protected void showProgeressBar() {
        AlertDialog.Builder builder = new Builder(XZHL0220_QuestioniareDetailActivity.this);
        // 给dialog加上视图
        builder.setTitle(XZHL0210_Constants.DAANING);
        inflater = LayoutInflater.from(XZHL0220_QuestioniareDetailActivity.this);
        View v = inflater.inflate(R.layout.xzhl0220_progressbar, null);
        pro = (ProgressBar) v.findViewById(R.id.pb_pro_0220);
        pro.setVisibility(View.VISIBLE);
        builder.setView(v);
        mDialog = builder.create();
        mDialog.show();
    }

    // 清除保存数据的结合,并关闭activity
    private void clearData() {
        if (XZHL0220_MapData.datas != null) {
            XZHL0220_MapData.datas.clear();
        }
        if (XZHL0220_MapData.answers != null) {
            XZHL0220_MapData.answers.clear();
        }
        if (XZHL0220_MapCheckBox.multipleData != null) {
            XZHL0220_MapCheckBox.multipleData.clear();
        }
        Intent intent = new Intent();
        intent.setClass(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_ShowQuestioniareActivity.class);
        startActivity(intent);
        XZHL0220_QuestioniareDetailActivity.this.finish();
    }

    // 重新计算ListView的高度
    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    // 根据后台任务返回的结果UI线程来决定怎样更新界面
    public void updateResultView(String result) {
        mDialog.dismiss();
        if (result == null || result.length() == 0) {
            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.ERRORSENDANSWER,
                    Toast.LENGTH_SHORT);
            submit.setClickable(true);
            return;
        }

        // result.split(regularExpression)
        if (result.equals("1")) {
            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.SENDINFO,
                    Toast.LENGTH_SHORT);
            submit.setClickable(true);
            clearData();
        } else if (result.equals("0")) {
            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.ERRORSENDANSWER,
                    Toast.LENGTH_SHORT);
            submit.setClickable(true);
        } else {
            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.ERRORSENDANSWER,
                    Toast.LENGTH_SHORT);
            submit.setClickable(true);
        }
        // try {
        // if(JsonParser.jiexiResult(result)){
        // XZHL0210_MessageUtil.showToast2(XZHL0220_DetailActivity.this,XZHL0210_Constants.SENDINFO,
        // Toast.LENGTH_SHORT);
        // submit.setClickable(true);
        // clearData();
        // }else{
        // XZHL0210_MessageUtil.showToast2(XZHL0220_DetailActivity.this,XZHL0210_Constants.ERRORSENDANSWER,
        // Toast.LENGTH_SHORT);
        // submit.setClickable(true);
        // }
        // } catch (JSONException e) {
        // XZHL0210_MessageUtil.showToast2(XZHL0220_DetailActivity.this,
        // XZHL0210_Constants.ERRORSENDANSWER, Toast.LENGTH_SHORT);
        // submit.setClickable(true);
        // e.printStackTrace();
        // }
    }

    // 根据解析的xml文件得到的结果让UI线程来决定怎样更新界面
    public void updateView(ArrayList<XZHL0220_Choice> choices) {
        pbDetail.setVisibility(View.GONE);
        if (choices != null) {
            questions = choices;
            initData();
            initView(questions);
        } else {
            XZHL0210_Utils.showToast2(XZHL0220_QuestioniareDetailActivity.this, XZHL0210_Constants.LIEBIAOSHIBAI,
                    Toast.LENGTH_SHORT);
        }
    }

    class XZHL0220_AsyncGetData extends AsyncTask<String, String, ArrayList<XZHL0220_Choice>> {
        XZHL0220_QuestioniareDetailActivity context;

        public XZHL0220_AsyncGetData(XZHL0220_QuestioniareDetailActivity context) {
            this.context = context;
        }

        // 后台任务核心方法
        @Override
        protected ArrayList<XZHL0220_Choice> doInBackground(String... params) {
            ArrayList<XZHL0220_Choice> choices = null;
            try {
                // 调用工具类解析数据并返回实体类的集合
                choices = XZHL0220_Utils.getChoices(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return null;
            }
            return choices;
        }

        // 在后台方法执行完毕调用该方法传入执行后的结果当作参数
        @Override
        protected void onPostExecute(ArrayList<XZHL0220_Choice> choices) {
            // 调用主界面的UI线程更新界面
            context.updateView(choices);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }

    }

    class XZHL0220_AsyncSendAnswerTask extends AsyncTask<List<BasicNameValuePair>, String, String> {
        XZHL0220_QuestioniareDetailActivity context;

        /**
         * 异步任务发送答案到服务器并解析服务器返回的响应结果传递到UI界面
         * 
         * @param context
         */
        public XZHL0220_AsyncSendAnswerTask(XZHL0220_QuestioniareDetailActivity context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(List<BasicNameValuePair>... params) {
            String result = null;
            String back = null;
            Log.i("0429", "&&&&&&&&" + params[0].toString());
            // 调用工具类发送问卷答案，解析服务器返回的相应信息
            // result=HttpSubmit.ListTest2(XZHL0210_Constants.SENDANSWER,params[0]);
            String[] keys = { "q_id", "q_name", "q_point", "target_name", "q_time" };
            String[] values = new String[params[0].size() - 2];
            for (int i = 0; i < values.length; i++) {
                values[i] = params[0].get(i).getValue();
            }
            String serial = params[0].get(params[0].size() - 2).getValue();
            String token = params[0].get(params[0].size() - 1).getValue();
            String data = JsonUtil.DataToJson(keys, values);
            String json = JsonUtil.DataToJson(serial, data, token, JsonUtil.getSign(serial, data, token));
            String portName = "quest/add";
            WebServiceOfHttps ws = new WebServiceOfHttps();
            back = ws.WSservers(context, portName, json);
            Log.i("0429", "###########" + back);
            XZHL0220_Wenjuan wenjian = JsonUtil.jsonToWenjuan(back);
            Log.i("0429", json);
            result = wenjian.getSuccess();
            // String result2=null;
            try {
                // 调用工具类发送问卷答案，解析服务器返回的相应信息
                // String str=params[1].get(0).getValue()+"上海数据"+json;
                // result2=
                HttpSubmit.ListTest2(XZHL0210_Constants.SENDANSWER, params[1]);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                // result2=null;
            } catch (IOException e) {
                e.printStackTrace();
                // result2=null;
            }
            return result;
        }

        // 根据后台任务执行完毕的解析结果传递到UI线程，通知其更新界面
        @Override
        protected void onPostExecute(String result) {
            // 更新界面
            context.updateResultView(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(context, values[0], Toast.LENGTH_SHORT).show();
        }

    }
}
