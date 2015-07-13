/*  XZHL1210_ClerkDisplayActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1210                                                                                           */
/*  画面名     ：店员一览                                                                                             */
/*  实现功能 ：对店员信息进行一览显示。                                                                               */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1210;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_Constants;
import com.fbse.recommentmobilesystem.XZHL1220.XZHL1220_ClerkInputActivity;
import com.fbse.recommentmobilesystem.XZHL1230.XZHL1230_ClerkModify;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebService;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * 店员信息一览后台类
 *
 * 完成店员信息一览显示
 */
public class XZHL1210_ClerkDisplayActivity extends Activity {

    // 删除店员成功的标志位
    private static final String SUCCESS="1";

    // 店员信息列表
    private List<XZHL1210_DianYuan> listDianyuan;

    // 店员信息列表数据适配器
    private XZHL1210_ListViewAdapter adapter;

    // back：退出按钮
    private Button btnBack;

    // new：新增按钮
    private Button btnNew;

    // 数据加载进度条
    private ProgressBar pbBar;

    // 店员信息一览列表视图
    private ListView listView;

    // 该类的静态上下文
    static XZHL1210_ClerkDisplayActivity context;

    // 自定义对话框
    private MyDialog dialog;

    /**
     * 创建activity
     * @param savedInstanceState 保存的activity的实例化状态
     */
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log开始
        LogUtil.logStart();

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl1210_dianyuanyilan_simple);

        // 取出从上一个画面传过来的数据
        getPreActivityData();

        // 初始化数据
        initlistDianyuan();

        // 初始化视图
        setupView();

        // 给控件加监听器
        addListener();

        // 开启异步任务访问网络获取数据
        new XZHL1210_GetlistDianyuanAsnyTask(this).execute();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 从Itent中取出上一个activity传过来的数据
     */
    private void getPreActivityData() {

        // Log开始
        LogUtil.logStart();

        Intent intent = getIntent();
        XZHL1210_Constants.store = intent.getStringExtra(XZHL1210_Constants.store_tag);
        SharedPreferences sp = getSharedPreferences("data", 0);
        XZHL1210_Constants.denglurole=sp.getString("QUANXIAN", "");
        XZHL1210_Constants.dengluname = sp.getString("NAME", "");

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 弹出提示信息的工具方法
     * @param str 提示的消息
     */
    protected void showToast(String str) {

        // Log开始
        LogUtil.logStart();
        MessageUtil.commonToast(this, str, Toast.LENGTH_SHORT);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 画面初期化数据初始化
     */
    private void initlistDianyuan() {

        // Log开始
        LogUtil.logStart();
        listDianyuan = new ArrayList<XZHL1210_DianYuan>();
        XZHL1210_ClerkDisplayActivity.context = this;
        XZHL1210_Constants.initStrings(XZHL1210_ClerkDisplayActivity.context);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 初始化视图,初始化布局控件
     */
    private void setupView() {

        // Log开始
        LogUtil.logStart();
        listView = (ListView) findViewById(R.id.lv_listdianyuan_1210);
        btnBack = (Button) findViewById(R.id.btn_gotomain_1210);
        btnNew = (Button) findViewById(R.id.btn_tianjia_1210);
        pbBar = (ProgressBar) findViewById(R.id.pb_pblist_1210);
        pbBar.setVisibility(View.VISIBLE);
        adapter = new XZHL1210_ListViewAdapter(this, listDianyuan);
        listView.setAdapter(adapter);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 给各个控件加监听器
     */
    private void addListener() {

        // Log开始
        LogUtil.logStart();

        // 后退按钮设置监听事件
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();
                XZHL1210_ClerkDisplayActivity.this.finish();

                // Log结束
                LogUtil.logEnd();
            }
        });

        // 新增按钮设置监听事件
        btnNew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();

                // 跳转到店员信息录入画面
                Intent intent = new Intent();
                intent.setClass(XZHL1210_ClerkDisplayActivity.this, XZHL1220_ClerkInputActivity.class);
                startActivity(intent);

                // Log结束
                LogUtil.logEnd();
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adapter.changeData(listDianyuan);

                // Log开始
                LogUtil.logStart();
                String[] str = new String[5];
                TextView tvId = (TextView) view.findViewById(R.id.tv_dianyuanyilanid_1210);
                TextView tvName = (TextView) view.findViewById(R.id.tv_xingming_1210);
                TextView tvLoginName = (TextView) view.findViewById(R.id.tv_dengluming_1210);
                TextView tvRole = (TextView) view.findViewById(R.id.tv_quanxian_1210);
                TextView tvTel = (TextView) view.findViewById(R.id.tv_dianhua_1210);
                str[0] = tvId.getText() + XZHL1210_Constants.DEFAULT;
                str[1] = tvName.getText() + XZHL1210_Constants.DEFAULT;
                str[2] = tvLoginName.getText() + XZHL1210_Constants.DEFAULT;
                str[3] = tvRole.getText() + XZHL1210_Constants.DEFAULT;
                str[4] = tvTel.getText() + XZHL1210_Constants.DEFAULT;
                if(Integer.parseInt(XZHL1210_Constants.denglurole)>=3){
                    showToast("权限不够!");
                    return;
                }else if(Integer.parseInt(XZHL1210_Constants.denglurole)==2){
                    if(Integer.parseInt(XZHL1210_Constants.denglurole)>
                        Integer.parseInt(listDianyuan.get(position).getStore())){
                        showToast("权限不够");
                        return;
                    }else{
                        Intent intent = new Intent();
                        intent.putExtra(XZHL1210_Constants.DATA, str);

                        // 跳转界面
                        intent.setClass(XZHL1210_ClerkDisplayActivity.context, XZHL1230_ClerkModify.class);
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent();
                    intent.putExtra(XZHL1210_Constants.DATA, str);

                    // 跳转界面
                    intent.setClass(XZHL1210_ClerkDisplayActivity.context, XZHL1230_ClerkModify.class);
                    startActivity(intent);
                }

                // Log结束
                LogUtil.logEnd();
            }

        });

        // listView设置条目长按事件
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                // Log开始
                LogUtil.logStart();
                String[] str = new String[5];
                TextView tvId = (TextView) view.findViewById(R.id.tv_dianyuanyilanid_1210);
                TextView tvName = (TextView) view.findViewById(R.id.tv_xingming_1210);
                TextView tvLoginName = (TextView) view.findViewById(R.id.tv_dengluming_1210);
                TextView tvRole = (TextView) view.findViewById(R.id.tv_quanxian_1210);
                TextView tvTel = (TextView) view.findViewById(R.id.tv_dianhua_1210);
                str[0] = tvId.getText() + XZHL1210_Constants.DEFAULT;
                str[1] = tvName.getText() + XZHL1210_Constants.DEFAULT;
                str[2] = tvLoginName.getText() + XZHL1210_Constants.DEFAULT;
                str[3] = tvRole.getText() + XZHL1210_Constants.DEFAULT;
                str[4] = tvTel.getText() + XZHL1210_Constants.DEFAULT;

                // 显示自定义的对话框
                dialog=new MyDialog(XZHL1210_ClerkDisplayActivity.context, R.style.mydialog, position, str);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();

                // Log结束
                LogUtil.logEnd();
                return false;
            }
        });

        // Log结束
        LogUtil.logEnd();
    }

    @Override
    protected void onRestart() {

        // Log开始
        LogUtil.logStart();
        super.onRestart();
        new XZHL1210_GetlistDianyuanAsnyTask(this).execute();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 开启异步任务到服务器删除数据
     * @param str 异步删除的地址
     * @param position 删除的标志位
     */
    public void useAsnyc(String str, int position) {

        // Log开始
        LogUtil.logStart();
        dialog.dismiss();
        new AsnyDeleteTask(this, position).execute(str);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 根据异步任务的返回结果更新listView
     * @param result 返回的服务器数据
     */
    public void updateListView(List<XZHL1210_DianYuan> result) {

        // Log开始
        LogUtil.logStart();
        pbBar.setVisibility(View.GONE);

        // 如果删除数据失败会返回null
        if (result == null || result.size() == 0) {
            showToast(XZHL1210_Constants.yichang);
            return;
        }

        // 更新listView数据,重绘
        listDianyuan = result;
        adapter.changeData(listDianyuan);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 异步删除数据的任务后台类
     */
    static class AsnyDeleteTask extends AsyncTask<String, String, String> {

        private XZHL1210_ClerkDisplayActivity context;
        int position;

        /**
         * 构造器初始化数据
         *
         * @param context XZHL1210_ClerkDisplayActivity的上下文
         * @param position listView的条目操作位置
         */
        public AsnyDeleteTask(XZHL1210_ClerkDisplayActivity context, int position) {

            // Log开始
            LogUtil.logStart();
            this.context = context;
            this.position = position;

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 重写的后台方法
         * @param params 传递进来的参数集
         * @return 返回服务器响应的数据
         */
        @Override
        protected String doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();
            String result = null;

            // 调用接口访问数据
            String serial = XZHL0210_Constants.SERIAL;
            String token = XZHL0210_Constants.TOKEN;
            String data = JsonUtil.DataToJson(new String[]{XZHL1210_Constants.ID} , new String[]{params[0]});
            String json = JsonUtil.DataToJson(serial, data, token, JsonUtil.getSign(serial, data, token));
            String portName = XZHL1210_Constants.DELETEADDRESS;
            WebServiceOfHttps ws = new WebServiceOfHttps();
            result = ws.WSservers(context, portName, json);
            // 解析返回的数据
            result = XZHL1210_Utils.jsonParser(result);

            // Log结束
            LogUtil.logEnd();
            return result;
        }

        /**
         * doInBackground方法执行完毕立即执行的钩子方法
         * @param result 服务器返回数据的解析结果
         */
        @Override
        protected void onPostExecute(String result) {

            // Log开始
            LogUtil.logStart();

            // 根据返回的数据更新界面
            context.updateResultView(result, position);

            LogUtil.logEnd();
        }
    }

    /**
     * 异步访问接口获取店员的数据后台类
     */
    static class XZHL1210_GetlistDianyuanAsnyTask extends AsyncTask<String, String, List<XZHL1210_DianYuan>> {
        private XZHL1210_ClerkDisplayActivity context;

        /**
         * 构造器初始化数据
         *
         * @param context XZHL1210_ClerkDisplayActivity的上下文
         */
        public XZHL1210_GetlistDianyuanAsnyTask(XZHL1210_ClerkDisplayActivity context) {
            this.context = context;
        }

        /**
         * 异步任务执行的核心方法
         * @param params 传递进来的参数集
         * @return 根据服务器返回的数据解析出来的数据
         */
        @Override
        protected List<XZHL1210_DianYuan> doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();
            List<String> list = new ArrayList<String>();
            list.add(XZHL1210_Constants.DEFAULT);
            list.add(XZHL1210_Constants.store);

            // 调用接口请求数据
            WebService ws = new WebService();
            String result = ws.WSserversofdaili(this.context,
                    XZHL1210_Constants.GETADDRESS,
                    XZHL1210_Constants.PORTNAME, list);

            // 解析返回的数据
            XZHL1210_UserList userList = XZHL1210_Utils.jsonToUserList(result);
            if(userList==null){

                // Log结束
                LogUtil.logEnd();
                return null;
            }
            List<XZHL1210_DianYuan> ul = userList.getUser();
            if(ul==null){

                // Log结束
                LogUtil.logEnd();
                return null;
            }

            List<XZHL1210_DianYuan> listDianyuan = new ArrayList<XZHL1210_DianYuan>();
            for (int i = 0; i < ul.size(); i++) {
                XZHL1210_DianYuan bean = new XZHL1210_DianYuan();
                bean.setId(ul.get(i).getId());
                bean.setName(ul.get(i).getName());
                bean.setLoginName(ul.get(i).getLoginName());
                bean.setRole(ul.get(i).getRole());
                bean.setTel(ul.get(i).getTel());
                listDianyuan.add(bean);
            }

            // Log结束
            LogUtil.logEnd();
            return listDianyuan;
        }

        /**
         * doInBackground方法执行完毕立即执行的钩子方法 根据解析数据,调用activity的方法更新界面
         * @param result 服务器返回的数据
         */
        @Override
        protected void onPostExecute(List<XZHL1210_DianYuan> result) {

            // Log开始
            LogUtil.logStart();

            // 更新界面
            context.updateListView(result);

            // Log结束
            LogUtil.logEnd();
        }
    }

    /**
     * 根据异步删除任务的返回结果更新界面
     *
     * @param result 异步任务的返回结果解析出来的标志位,1表示删除成功,0表示删除失败
     * @param position 删除哪一条店员信息的位置
     */
    public void updateResultView(String result, int position) {

        // Log开始
        LogUtil.logStart();
        if (result == null) {
            showToast(XZHL1210_Constants.yichang);

            // Log结束
            LogUtil.logEnd();
            return;
        }
        if(XZHL1210_Constants.DEFAULT.equals(result)){
            showToast(XZHL1210_Constants.zanwu);

            // Log结束
            LogUtil.logEnd();
            return;
        }
        if (XZHL1210_ClerkDisplayActivity.SUCCESS.equals(result)) {
            showToast(XZHL1210_Constants.deletesuccess);
            listDianyuan.remove(position);
            updateListView(listDianyuan);
        } else{
            showToast(XZHL1210_Constants.deletefail);
        }

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 自定义的对话框类
     *
     * 实现对话框的自定义样式
     */
    class MyDialog extends Dialog{

        // 显示信息标志位
        private int position;

        // 文本框: 姓名
        private TextView tvName;

        // 文本框: 修改操作
        private TextView tvXiugai;

        // 文本框: 删除操作
        private TextView tvShanchu;

        // 传递给下一个activity的数据
        private String[] content;

        /**
         * 自定义构造器初始化参数
         * @param context XZHL1210_ClerkDisplayActivity的上下文
         * @param theme 自定义dialog的样式
         * @param position 删除标志位
         * @param content 传递给下一个activity的数据
         */
        public MyDialog(XZHL1210_ClerkDisplayActivity context, int theme, int position, String[] content) {
            super(context, theme);
            this.position=position;
            this.content=content;
        }

        /**
         * 重写的加载自定义dialog视图的方法
         * @param savedInstanceState 保存的实例化状态
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            // Log开始
            LogUtil.logStart();
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.xzhl1210_dialogview);

            // 初始化控件
            setupDialogView();

            // 给dialog里的控件加监听器
            addDialogListener();

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 给dialog里的控件加监听器
         */
        private void addDialogListener() {

            // Log开始
            LogUtil.logStart();
            tvXiugai.setOnClickListener(new View.OnClickListener() {

                // 修改文本框被点击时出发的事件
                @Override
                public void onClick(View v) {

                    // Log开始
                    LogUtil.logStart();
                    if (Integer.parseInt(XZHL1210_Constants.denglurole) >= 3) {
                        showToast(MessageUtil.getMessage(getApplicationContext(), Msg.W004));

                        // Log结束
                        LogUtil.logEnd();
                        return;
                    } else if (Integer.parseInt(XZHL1210_Constants.denglurole) == 2) {
                        if (Integer.parseInt(XZHL1210_Constants.denglurole) > Integer.parseInt(listDianyuan.get(
                                position).getStore())) {
                            showToast(MessageUtil.getMessage(getApplicationContext(), Msg.W004));

                            // Log结束
                            LogUtil.logEnd();
                            return;
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra(XZHL1210_Constants.DATA, content);

                            // 跳转界面
                            intent.setClass(XZHL1210_ClerkDisplayActivity.context, XZHL1230_ClerkModify.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra(XZHL1210_Constants.DATA, content);

                        // 跳转界面
                        intent.setClass(XZHL1210_ClerkDisplayActivity.context, XZHL1230_ClerkModify.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }

                    // Log结束
                    LogUtil.logEnd();
                }
            });

            tvShanchu.setOnClickListener(new View.OnClickListener() {

                // 删除文本框被点击时触发的事件
                @Override
                public void onClick(View v) {

                    // Log开始
                    LogUtil.logStart();
                    if (Integer.parseInt(XZHL1210_Constants.denglurole) >= 3) {
                        showToast(MessageUtil.getMessage(getApplicationContext(), Msg.W005));

                        // Log结束
                        LogUtil.logEnd();
                        return;
                    } else if (Integer.parseInt(XZHL1210_Constants.denglurole) == 2) {
                        if (Integer.parseInt(XZHL1210_Constants.denglurole) > Integer.parseInt(listDianyuan.get(
                                position).getStore())) {
                            showToast(MessageUtil.getMessage(getApplicationContext(), Msg.W005));

                            // Log结束
                            LogUtil.logEnd();
                            return;
                        } else if (XZHL1210_Constants.dengluname.equals(listDianyuan.get(position).getLoginName())) {
                            showToast(MessageUtil.getMessage(getApplicationContext(), Msg.W005));

                            // Log结束
                            LogUtil.logEnd();
                            return;
                        } else {
                            // 构建是否确定删除店员的对话框
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(XZHL1210_ClerkDisplayActivity.context);
                            builder.setTitle(tvName.getText())
                                    .setMessage(XZHL1210_Constants.warninfo)
                                    .setCancelable(true)
                                    .setPositiveButton(XZHL1210_Constants.sure, new DialogInterface.OnClickListener() {

                                        // 点击确认按钮触发的事件
                                        public void onClick(DialogInterface dialog, int id) {

                                            // Log开始
                                            LogUtil.logStart();

                                            // 删除数据
                                            useAsnyc(content[0], position);

                                            // Log结束
                                            LogUtil.logEnd();
                                        }
                                    })
                                    .setNegativeButton(XZHL1210_Constants.cancle,
                                            new DialogInterface.OnClickListener() {

                                                // 点击取消按钮触发的事件
                                                public void onClick(DialogInterface dialogIn, int id) {
                                                    dialog.dismiss();
                                                }
                                            });
                            builder.create().show();
                        }
                    } else {
                        if (XZHL1210_Constants.dengluname.equals(listDianyuan.get(position).getLoginName())) {
                            showToast(MessageUtil.getMessage(getApplicationContext(), Msg.W005));

                            // Log结束
                            LogUtil.logEnd();
                            return;
                        } else {
                            // 构建是否确定删除店员的对话框
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(XZHL1210_ClerkDisplayActivity.context);
                            builder.setTitle(tvName.getText())
                                    .setMessage(XZHL1210_Constants.warninfo)
                                    .setCancelable(true)
                                    .setPositiveButton(XZHL1210_Constants.sure, new DialogInterface.OnClickListener() {

                                        // 点击确认按钮触发的事件
                                        public void onClick(DialogInterface dialog, int id) {

                                            // Log开始
                                            LogUtil.logStart();

                                            // 删除数据
                                            useAsnyc(content[0], position);

                                            // Log结束
                                            LogUtil.logEnd();
                                        }
                                    })
                                    .setNegativeButton(XZHL1210_Constants.cancle,
                                            new DialogInterface.OnClickListener() {

                                                // 点击取消按钮触发的事件
                                                public void onClick(DialogInterface dialogIn, int id) {
                                                    dialog.dismiss();
                                                }
                                            });
                            builder.create().show();
                        }
                    }
                    // Log结束
                    LogUtil.logEnd();
                }
            });

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 初始化控件
         */
        private void setupDialogView() {

            // Log开始
            LogUtil.logStart();
            tvName=(TextView) findViewById(R.id.tv_name_1210);
            tvName.setText(content[1]);
            tvXiugai=(TextView) findViewById(R.id.tv_caozuoxiugai_1210);
            tvShanchu=(TextView) findViewById(R.id.tv_caozuoshanchu_1210);

            // Log结束
            LogUtil.logEnd();
        }
    }

}
