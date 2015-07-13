/*  XZHL0620_MailInfoDisplayActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012               */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ ：XZHL0620                                                                                                    */
/*  画面名     ：邮寄信息一览                                                                                                   */
/*  实现功能 ：显示所有的邮寄信息。                                                                                                  */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/20   V01L01      FBSE)张海静      新規作成                                                               */
/*                                                                                                                    */
/*----------------------------------------------------------------------------------------------------------------- --*/
package com.fbse.recommentmobilesystem.XZHL0620;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0120.XZHL0120_MyListVIew;
import com.fbse.recommentmobilesystem.XZHL0610.XZHL0610_MailInfoInput;
import com.fbse.recommentmobilesystem.XZHL0630.XZHL0630_MailDetailsShow;
import com.fbse.recommentmobilesystem.XZHL0640.XZHL0640_MailInfoModify;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * 邮寄一览类
 *
 * 完成所有邮寄信息
 */
public class XZHL0620_MailInfoDisplayActivity extends Activity implements
        OnClickListener {

    // TAG标签
    private static final String TAG="XZHL0620_MailInfoDisplayActivity";

    // 返回按钮
    private ImageView ivBack;

    // 添加按钮
    private ImageView ivAdd;

    // 搜索框中的EditText
    private EditText etMailSearch;

    // 进度条
    private LinearLayout mailPro;

    // 自定义的ListView控件
    private XZHL0120_MyListVIew mListView;

    // 保存显示数据
    private XZHL0620_MailInfoArray mailInfoArray;

    // 解析数据的bean
    private XZHL0620_MailInfoBean mailInfoBean;

    // List数组
    private List<XZHL0620_MailInfoArray> mailBean;

    // 共享信息
    private SharedPreferences sharedPerf;

    // 共享Name
    private static final String DATA = "data";

    // 共享Name-保存检索数据
    private static final String SEARCH = "search";

    // 共享键值
    private static final String KEY = "ID";

    // 邮寄详细信息显示KEY
    private static final String KEYMAIL = "MAIL";

    // 空的常量
    private static final String KONG = "";

    // 0常量
    private static final String ZERO="0";

    // 已发货状态
    private static final int ONE = 1;

    // 发货状态中
    private static final int TWO = 2;

    // 待发货状态
    private static final int THREE = 3;

    // 已收货状态
    private static final int FOUR = 4;

    // 搜索框的长度
    private static final int ORDERLENGTH = 50;

    /**
     * 完成界面初始化
     * @param savedInstanceState 状态保存
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // log打印开始
        LogUtil.logStart();

        // 共享信息
        sharedPerf = this.getSharedPreferences(DATA, Context.MODE_PRIVATE);

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0620_mailinfodisplay);

        // 引用初始化界面组件方法
        initView();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {

        // log打印开始
        LogUtil.logStart();

        // 返回控件
        ivBack = (ImageView) findViewById(R.id.iv_gotoback_xzhl0620);

        // 返回控件的点击
        ivBack.setOnClickListener(this);

        // 添加控件的点击
        ivAdd = (ImageView) findViewById(R.id.iv_addmailinfo_xzhl0620);

        // 返回控件的点击
        ivAdd.setOnClickListener(this);

        // 数据ListView显示控件
        mListView = (XZHL0120_MyListVIew) findViewById(R.id.lv_mailinfo_xzhl0620);

        // 数据点击事件
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> aView, View view, int position, long arg) {

                // log打印开始
                LogUtil.logStart();

                // 得到点击的所有信息
                mailInfoArray = mailBean.get(position);

                // 跳转到【邮寄详细信息显示】画面
                Intent sendInfo = new Intent(getApplicationContext(),
                        XZHL0630_MailDetailsShow.class);

                // 附加Bean
                sendInfo.putExtra(KEYMAIL, mailInfoArray);
                startActivity(sendInfo);

                // log打印结束
                LogUtil.logEnd();
            }
        });

        // 数据长按事件
        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> aView, View view,
                    int position, long arg) {

                // log打印开始
                LogUtil.logStart();

                // 得到点击的订单号
                String mailOrder=mailBean.get(position).getNumber();

                // 为id赋值
                int id=mailBean.get(position).getId();

                // 得到点击的订单号的状态
                int mailStates=mailBean.get(position).getState();

                // 长按对话框显示
                selectItem(mailOrder, mailStates, id);

                // log打印结束
                LogUtil.logEnd();
                return false;
            }
        });

        // 搜索EditText
        etMailSearch = (EditText) findViewById(R.id.et_mailsearch_xzhl0620);

        // 输入框点击
        etMailSearch.setOnClickListener(this);
        // 搜索点击
        etMailSearch.addTextChangedListener(new TextWatcher() {

            // 文字改变中的方法
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // log打印开始
                LogUtil.logStart();

                // 搜索框中内容超出长度
                if(etMailSearch.getText().toString().trim().length()>ORDERLENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(), Msg.E0026), Toast.LENGTH_SHORT);
                }

                Editor text=sharedPerf.edit();
                text.putString(SEARCH, etMailSearch.getText().toString().trim());
                text.commit();

                // 搜索
                getData(sharedPerf.getString(SEARCH, KONG));

                // log打印结束
                LogUtil.logEnd();
            }

            // 文字改变之前的方法
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            // 文字改变之后的方法
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 进度条
        mailPro=(LinearLayout) findViewById(R.id.ll_mailpro_xzhl0620);

        // 查询数据方法引用
        netJudge();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 查询数据
     */
    private void netJudge() {

        // log打印开始
        LogUtil.logStart();

        // 判断网络是否异常,没有异常
        if (Commonutil.isNetworkAvailable(getApplicationContext())) {

            // ListView隐藏
            mListView.setVisibility(View.GONE);

            // 进度条显示
            mailPro.setVisibility(View.VISIBLE);

            // 向服务发送消息接受数据
            MailAsyncTask mailAsyncTask = new MailAsyncTask();
            mailAsyncTask.execute();
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 邮寄信息显示的异步类
     *
     * 向服务器接受数据，完成邮寄信息的显示
     */
    private class MailAsyncTask extends AsyncTask<String, Integer, String> {

        /**
         * 后台取数据
         * @param params 参数
         * @return info
         */
        @Override
        protected String doInBackground(String... params) {

            // log打印开始
            LogUtil.logStart();

            // 提交数据,封装数据
            String portName = "post/list";
            String serial = "a001";
            String[] key = { "timestamp" };
            String[] value = { "0" };
            String id = sharedPerf.getString(KEY, KONG);

            // 调用网络方法
            WebServiceOfHttps webSO = new WebServiceOfHttps();

            // 请求数据
            String jSon = JsonUtil.DataToJson(serial, JsonUtil.DataToJson(key, value), id, JsonUtil.getSign(serial,
                JsonUtil.DataToJson(key, value), id));
            String info = webSO.WSservers(XZHL0620_MailInfoDisplayActivity.this, portName, jSon);

            // log打印结束
            LogUtil.logEnd();
            return info;
        }

        /**
         * 从doInBackground方法中得到值作相应判断
         * @param result 返回值
         */
        @Override
        protected void onPostExecute(String result) {

            // log打印开始
            LogUtil.logStart();

            // 打印log
            Log.v(TAG, result);

             // 解析Json中success的值
            String resultInfo=JsonUtil.successJSON(result);

            // 1为成功
            if(String.valueOf(ONE).equals(resultInfo)){

                // 解析数据
                mailInfoBean = JsonUtil.JsonToMail(result);

                // 判断数据是否为空
                if (mailInfoBean.getMail() == null) {
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(), Msg.I022, new String[] { CommonConst.MAIL }),
                        Toast.LENGTH_SHORT);

                    // 进度条隐藏
                    mailPro.setVisibility(View.GONE);
                    mListView.setVisibility(View.GONE);
                } else {

                    // 封装数据并显示
                    getData(sharedPerf.getString(SEARCH, KONG));

                    // 进度条隐藏
                    mailPro.setVisibility(View.GONE);
                    mListView.setVisibility(View.VISIBLE);
                }
            }

            // 0为失败
            else if (ZERO.equals(resultInfo)) {
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.E0002), Toast.LENGTH_SHORT);

                // 进度条隐藏
                mailPro.setVisibility(View.GONE);
            }

            // 判断服务器是否异常
            if (CommonConst.TALENTERRORSTATES.equals(result)||CommonConst.HOSTERRORSTATES.equals(result)) {
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                    .getMessage(getApplicationContext(), Msg.E0002), Toast.LENGTH_SHORT);

                // 进度条隐藏
                mailPro.setVisibility(View.GONE);
            }
            super.onPostExecute(result);

            // log打印结束
            LogUtil.logEnd();
        }
    }

    /**
     * 封装数据方法
     * @param data 数据
     */
    private void getData(String data) {

        // log打印开始
        LogUtil.logStart();
        mailBean = new ArrayList<XZHL0620_MailInfoArray>();
        for (int i = 0; i < mailInfoBean.getMail().size(); i++) {

            // 从数据中得数值
            mailInfoArray = mailInfoBean.getMail().get(i);
            if (mailInfoArray.getNumber().contains(data)) {
                mailBean.add(mailInfoArray);
            }
        }

        // 查询为空
        if(mailBean.isEmpty()){
            MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.I027),
                    Toast.LENGTH_SHORT);
        }

        // 数据适配器
        XZHL0620_MailInfoAdapter mailInfoAdapter = new XZHL0620_MailInfoAdapter(
            mailBean, XZHL0620_MailInfoDisplayActivity.this);
        mListView.setAdapter(mailInfoAdapter);

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 自定义一个弹出框
     * @param mailTitle 主题
     * @param mailStates 状态
     * @param id id
     */
    private void selectItem(final String mailTitle, final int mailStates, final int id) {

        // log打印开始
        LogUtil.logStart();

        // 弹出对话框选择
        AlertDialog.Builder selfBuild = new AlertDialog.Builder(this);

        // 设置标题
        selfBuild.setTitle(mailTitle);

        // item中要显示的文字
        CharSequence[] items = null;

        // 资源文件
        String[] n = getResources().getStringArray(R.array.mailinfo);

        // 判断发货状态
        switch (mailStates) {

        // 已发货
        case ONE:

            // 已发货状态不可弹出对话框
            items = new CharSequence[1];
            items[0] = n[1];
            break;

        // 发货中
        case TWO:

            items = new CharSequence[2];
            for (int i = 0; i < n.length; i++) {
                items[i] = n[i];
            }
            break;

        // 待发货
        case THREE:

            items = new CharSequence[2];
            for (int i = 0; i < n.length; i++) {
                items[i] = n[i];
            }
            break;

        // 已收货
        case FOUR:

            // 删除这一条邮寄的对话框
            deleteSure(String.valueOf(id));
            break;
        default:
            break;
        }

        //设置item点击
        selfBuild.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // log打印开始
                LogUtil.logStart();
                switch (which) {
                case 0:

                    // 跳转到邮寄信息修改
                    Intent intent = new Intent(getApplicationContext(), XZHL0640_MailInfoModify.class);
                    startActivity(intent);
                    break;
                case 1:

                    // 删除这一条邮寄的对话框
                    deleteSure(String.valueOf(id));
                    break;
                default:
                    break;
                }

                // log打印结束
                LogUtil.logEnd();
            }
        });

        // 如果状态不是已发货状态，则显示对话框
        if (mailStates == TWO || mailStates == THREE) {
            selfBuild.show();
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 删除确认对话框
     * @param mailId  标题
     */
    private void deleteSure(final String mailId){

        // log打印开始
        LogUtil.logStart();

        // 定义对话框
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setIcon(null);
        build.setMessage(MessageUtil.getMessage(getApplicationContext(),
            Msg.Q015, new String[] { CommonConst.BENMAIL }));

        // 确定按钮
        build.setPositiveButton(MessageUtil.getMessage(getApplicationContext(), Msg.I012),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // log打印开始
                        LogUtil.logStart();

                        // 关闭对话框
                        dialog.dismiss();

                        // 判断网络是否异常,无异常
                        if (Commonutil.isNetworkAvailable(getApplicationContext())) {

                            //删除邮寄信息
                            DeleteMailAsny deleteMailAsny=new DeleteMailAsny();
                            deleteMailAsny.execute(mailId);
                        }

                        // log打印结束
                        LogUtil.logEnd();
                    }
                });

        // 取消按钮
        build.setNegativeButton(
                MessageUtil.getMessage(getApplicationContext(), Msg.I005),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // log打印开始
                        LogUtil.logStart();

                        // 关闭对话框
                        dialog.dismiss();

                        // log打印结束
                        LogUtil.logEnd();
                    }
                });

        // 显示对话框
        build.show();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 邮寄信息删除的异步类
     *
     * 向服务器接受数据，完成邮寄信息的删除
     */
    private class DeleteMailAsny extends AsyncTask<String, Integer, String>{

        /**
         * 后台取数据
         * @param params 参数
         * @return info
         */
        @Override
        protected String doInBackground(String... params) {

            // log打印开始
            LogUtil.logStart();

            // 获取要删除的订单号
            String number=params[0];

            // 提交数据,封装数据
            String portName = "post/delete";
            String serial = "a001";
            String[] key = { "id" };
            String[] value = { number };
            String id = sharedPerf.getString(KEY, KONG);

            // 调用网络方法
            WebServiceOfHttps webSO = new WebServiceOfHttps();

            // 请求数据
            String jSon = JsonUtil.DataToJson(serial, JsonUtil.DataToJson(key, value), id, JsonUtil.getSign(serial,
                JsonUtil.DataToJson(key, value), id));
            String info = webSO.WSservers(XZHL0620_MailInfoDisplayActivity.this, portName, jSon);

            // log打印结束
            LogUtil.logEnd();
            return info;
        }

        /**
         * 从doInBackground方法中得到值作相应判断
         * @param result 返回值
         */
        @Override
        protected void onPostExecute(String result) {

            // log打印开始
            LogUtil.logStart();

            // 打印log
            Log.v(TAG, result);

            // 判断服务器是否异常
            if (CommonConst.TALENTERRORSTATES.equals(result)||CommonConst.HOSTERRORSTATES.equals(result)) {
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                    .getMessage(getApplicationContext(), Msg.E0002), Toast.LENGTH_SHORT);

                // 进度条隐藏
                mailPro.setVisibility(View.GONE);
            } else{

                // 查询数据方法引用
                netJudge();

                // 删除该条邮寄信息
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.I021, new String[] { CommonConst.BENMAIL }),
                    Toast.LENGTH_SHORT);
            }
            super.onPostExecute(result);

            // log打印结束
            LogUtil.logEnd();
        }
    }

    /**
     * 点击方法实现
     * @param v 点击的控件
     */
    @Override
    public void onClick(View v) {

        // log打印开始
        LogUtil.logStart();
        switch (v.getId()) {

        // 返回按钮
        case R.id.iv_gotoback_xzhl0620:

            // 返回清除
            save();
            break;

        // 添加按钮
        case R.id.iv_addmailinfo_xzhl0620:

            // 跳转到【邮寄信息录入】画面
            Intent intent = new Intent(getApplicationContext(), XZHL0610_MailInfoInput.class);
            startActivity(intent);
            break;

        // 输入框
        case R.id.et_mailsearch_xzhl0620:

            // 输入框得到焦点
            etMailSearch.setFocusable(true);
            etMailSearch.setFocusableInTouchMode(true);
            break;
        default:
            break;
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 返回清除
     */
    private void save() {

        // 保存数据
        Editor text=sharedPerf.edit();
        text.remove(SEARCH);
        text.commit();

        // 关闭本画面
        finish();

    }
    /**
     * 返回按钮的判断
     * @param keyCode 键盘值
     * @param event 事件
     * @return 键盘事件
     */
    @Override
    public boolean onKeyDown(int keyCode,  KeyEvent event) {

        // log打印开始
        LogUtil.logStart();
        if(keyCode==KeyEvent.KEYCODE_BACK){

            // 返回清除
            save();
        }

        // log打印结束
        LogUtil.logEnd();
        return super.onKeyDown(keyCode,  event);
    }

    /**
     * 重新返回到本画面，刷新数据
     */
    @Override
    protected void onRestart() {

        // log打印开始
        LogUtil.logStart();

        // 输入框失去焦点
        etMailSearch.setFocusable(false);

        // 查询数据方法引用
        netJudge();
        super.onRestart();

        // log打印结束
        LogUtil.logEnd();
    }

}
