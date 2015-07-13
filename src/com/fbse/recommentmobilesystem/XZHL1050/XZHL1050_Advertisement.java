/*  XZHL1050_Advertisement.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                        */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL1050                                                                                               */
/*  画面名 ：商城产品广告一览                                                                                         */
/*  实现功能 ：商城产品广告数据一览。                                                                                 */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/21   V01L01      FBSE)尹小超      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1050;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL1020.XZHL1020_AdvertisementInput;
import com.fbse.recommentmobilesystem.XZHL1030.XZHL1030_AdvertisementModify;
import com.fbse.recommentmobilesystem.XZHL1040.XZHL1040_AdverAllInfo;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * 商城产品广告一览画面后台类
 * 显示商城产品广告一览数据
 */
public class XZHL1050_Advertisement extends Activity {

    // 标题栏回退图标
    private ImageView ivBack;

    // 标题栏新增图标
    private ImageView ivAdd;

    // 页面上的查询图标
    private ImageView ivSearch;

    // 广告条目
    private ListView listView;

    // 广告信息列表
    private List<XZHL1050_Bean> list;

    // 上下文
    private static XZHL1050_Advertisement context;

    // 自定义对话框
    private XZHL1050_MsgDialog dialog;

    // 适配器
    private XZHL1050_ItemsAdapter itemAdapter;

    // 数据加载进度条
    private ProgressBar pbBar;

    // shopID
    private String shopId;

    // token
    private String token;

    // serial
    private String serial;

    /**
     * 创建activity
     * @param savedInstanceState 绑定的实例
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Log开始
        LogUtil.logStart();

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载页面布局文件
        setContentView(R.layout.xzhl1050_advertisement);

        // 初始化数据
        initData();

        // 初始化视图
        initView();

        // 增加监听器
        addListener();

        // 绘制界面
        setUpView();

        // 初始化页面时请求数据,异步获得服务器数据
        List<String> params = new ArrayList<String>();
        params.add(shopId);
        params.add(CommonConst.SPACE);
        new AsyGetDataTask(this).execute(params);

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 绘制界面
     */
    private void setUpView() {

        // Log开始
        LogUtil.logStart();
        pbBar.setVisibility(View.VISIBLE);

        // 适配器
        itemAdapter = new XZHL1050_ItemsAdapter(this, list);
        listView.setAdapter(itemAdapter);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 事件监听
     */
    private void addListener() {

        // Log开始
        LogUtil.logStart();

        // 后退按钮设置监听事件
        ivBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();
                XZHL1050_Advertisement.this.finish();

                // Log结束
                LogUtil.logEnd();
            }
        });

        // 点击搜索图标监听事件
        ivSearch.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();

                // 禁止查询
                ivSearch.setClickable(false);
                EditText etSearch = (EditText) findViewById(R.id.et_search_1050);
                String condition = etSearch.getText().toString();

                // 请求条件检索数据,异步获得服务器数据
                List<String> params = new ArrayList<String>();
                params.add(shopId);
                params.add(condition);
                new AsyGetDataTask(XZHL1050_Advertisement.context).execute(params);

                // Log结束
                LogUtil.logEnd();
            }
        });

        // 新增按钮设置监听事件
        ivAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();

                // 跳转到新增画面
                Intent intent = new Intent();
                intent.setClass(XZHL1050_Advertisement.this, XZHL1020_AdvertisementInput.class);
                startActivity(intent);

                // Log结束
                LogUtil.logEnd();
            }
        });

        // 点击广告条目进入详细设置监听事件
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Log开始
                LogUtil.logStart();

                String[] str = dataList(view);

                // 将数据传递给详细页面
                Intent intent = new Intent();
                intent.putExtra(CommonConst.DATA, str);
                intent.setClass(XZHL1050_Advertisement.context, XZHL1040_AdverAllInfo.class);
                startActivity(intent);

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

                String[] str = dataList(view);

                // 显示自定义的对话框
                dialog = new XZHL1050_MsgDialog(XZHL1050_Advertisement.context, R.style.mydialog, position, str);
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

    /**
     * 初始化视图
     */
    private void initView() {

        // Log开始
        LogUtil.logStart();

        // 标题栏后退图标
        ivBack = (ImageView) findViewById(R.id.iv_back_1050);

        // 标题栏新增图标
        ivAdd = (ImageView) findViewById(R.id.iv_add_1050);

        // 搜索图标
        ivSearch = (ImageView) findViewById(R.id.iv_search_1050);

        // 禁止查询
        ivSearch.setClickable(false);

        // 数据列表
        listView = (ListView) findViewById(R.id.tv_adv_1050);

        // 数据加载进度
        pbBar = (ProgressBar) findViewById(R.id.pb_pblist_1050);

        // Log结束
        LogUtil.logEnd();

    }

    /**
     * 绑定数据
     * @param view 视图
     * @return 数据
     */
    private String[] dataList(View view) {

        // Log开始
        LogUtil.logStart();

        // 获得页面的view
        String[] str = new String[9];
        TextView tvId = (TextView) view.findViewById(R.id.tv_id_1050);
        TextView tvName = (TextView) view.findViewById(R.id.tv_hdmc_1050);
        TextView tvBeginDate = (TextView) view.findViewById(R.id.tv_kssj_1050);
        TextView tvEndDate = (TextView) view.findViewById(R.id.tv_jssj_1050);
        TextView tvTarget = (TextView) view.findViewById(R.id.tv_hddx_1050);
        TextView tvProduct = (TextView) view.findViewById(R.id.tv_hdsp_1050);
        TextView tvMethod = (TextView) view.findViewById(R.id.tv_yhfs_1050);
        TextView tvSetting = (TextView) view.findViewById(R.id.tv_yhsz_1050);
        TextView tvAdv = (TextView) view.findViewById(R.id.tv_hdxy_1050);

        // 绑定条目的数据
        str[0] = tvId.getText() + CommonConst.SPACE;
        str[1] = tvName.getText() + CommonConst.SPACE;
        str[2] = tvBeginDate.getText() + CommonConst.SPACE;
        str[3] = tvEndDate.getText() + CommonConst.SPACE;
        str[4] = tvTarget.getText() + CommonConst.SPACE;
        str[5] = tvProduct.getText() + CommonConst.SPACE;
        str[6] = tvMethod.getText() + CommonConst.SPACE;
        str[7] = tvSetting.getText() + CommonConst.SPACE;
        str[8] = tvAdv.getText() + CommonConst.SPACE;

        // Log结束
        LogUtil.logEnd();
        return str;
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // Log开始
        LogUtil.logStart();

        XZHL1050_Advertisement.context = this;
        list = new ArrayList<XZHL1050_Bean>();

        // 获得登录传递的数据
        SharedPreferences sp = getSharedPreferences(CommonConst.DATA, 0);

        // 获得shopid
        shopId = sp.getString(CommonConst.SHOPID, CommonConst.SPACE);

        // 获得token
        token = sp.getString(CommonConst.TOKEN, CommonConst.SPACE);

        // 获得serial
        serial = sp.getString(CommonConst.SERIAL, CommonConst.SPACE);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 异步请求获得数据库数据
     * 发送异步请求,获得数据库数据
     */
    class AsyGetDataTask extends AsyncTask<List<String>, String, List<XZHL1050_Bean>> {

        XZHL1050_Advertisement context;

        /**
         * 构造器
         */
        public AsyGetDataTask(XZHL1050_Advertisement context) {

            // Log开始
            LogUtil.logStart();

            this.context = context;

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 后台请求数据
         * @param params 传入参数
         * @return 返回参数
         */
        @Override
        protected List<XZHL1050_Bean> doInBackground(List<String>... params) {

            // Log开始
            LogUtil.logStart();
            String result = null;

            // 调用接口请求数据
            String portName = CommonConst.PORTNAME1050;
            String data = JsonUtil.DataToJson(new String[] { CommonConst.EXERCISENAME },
                new String[] { params[0].get(1) });

            String json = JsonUtil.DataToJson(serial, data, token, JsonUtil.getSign(serial, data, token));
            WebServiceOfHttps ws = new WebServiceOfHttps();
            result = ws.WSservers(context, portName, json);
            try {
                return XZHL1050_Util.jsonToList(result);
            } catch (Exception e) {

                // 异常log输出
                LogUtil.logException(e);
                return new ArrayList<XZHL1050_Bean>();
            } finally {

                // Log结束
                LogUtil.logEnd();
            }
        }

        /**
         * 钩子方法传递数据
         * @param result 传入参数
         */
        @Override
        protected void onPostExecute(List<XZHL1050_Bean> result) {

            super.onPostExecute(result);

            // Log开始
            LogUtil.logStart();
            this.context.updateListView(result);

            // Log结束
            LogUtil.logEnd();
        }
    }

    /**
     * 异步请求删除数据库数据
     * 发送异步请求,进行删除操作
     */
    class AsyDeleteTask extends AsyncTask<String, String, String> {

        XZHL1050_Advertisement context;

        int position;

        /**
         * 构造器初始化数据
         */
        public AsyDeleteTask(XZHL1050_Advertisement context, int position) {

            // Log开始
            LogUtil.logStart();
            this.context = context;
            this.position = position;

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 重写的后台方法
         * @param params 传入参数
         * @return 返回参数
         */
        @Override
        protected String doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();
            String result = null;

            // 调用接口请求删除
            String data = JsonUtil.DataToJson(new String[] { CommonConst.ADVID }, new String[] { params[0] });
            String json = JsonUtil.DataToJson(serial, data, token, JsonUtil.getSign(serial, data, token));
            String portName = CommonConst.PORTDELETE;
            WebServiceOfHttps ws = new WebServiceOfHttps();
            result = ws.WSservers(context, portName, json);

            // Log结束
            LogUtil.logEnd();
            return result;
        }

        /**
         * 回调方法
         * @param result 传入参数
         */
        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            // Log开始
            LogUtil.logStart();
            try {

                // 解析result,为空时异常
                if (result == null) {

                    // 异常弹出提示信息
                    MessageUtil.commonToast(XZHL1050_Advertisement.context, MessageUtil.getMessage(context, Msg.E0002),
                        Toast.LENGTH_SHORT);
                    return;
                } else {
                    JSONObject jb = new JSONObject(result);
                    String success = jb.getString(CommonConst.SUCCESS);
                    // success删除成功为1,失败为0
                    if (CommonConst.ONE.equals(success)) {

                        // 成功时弹出提示信息并更新条目更新条目
                        MessageUtil.commonToast(XZHL1050_Advertisement.context,
                            MessageUtil.getMessage(context, Msg.I021, new String[] { list.get(position).getName() }),
                            Toast.LENGTH_SHORT);
                        list.remove(position);
                        updateListView(list);
                    } else {

                        // 失败弹出提示并返回
                        MessageUtil.commonToast(XZHL1050_Advertisement.context,
                            MessageUtil.getMessage(context, Msg.E0025, new String[] { list.get(position).getName() }),
                            Toast.LENGTH_SHORT);
                        return;
                    }
                }
            } catch (JSONException e) {

                // 异常log输出
                LogUtil.logException(e);
                // 解析数据异常弹出提示信息
                MessageUtil.commonToast(XZHL1050_Advertisement.context, MessageUtil.getMessage(context, Msg.E0002),
                    Toast.LENGTH_SHORT);
            }

            // Log结束
            LogUtil.logEnd();
        }
    }

    /**
     * 更新页面
     * @param result 待更新的数据源
     */
    public void updateListView(List<XZHL1050_Bean> result) {

        // Log开始
        LogUtil.logStart();
        pbBar.setVisibility(View.GONE);

        // 更新页面数据,result为空时为异常,result无条目时无数据库数据
        if (result == null) {
            Toast.makeText(this, MessageUtil.getMessage(context, Msg.E0002), Toast.LENGTH_SHORT).show();
        } else if (result.size() == 0) {
            Toast.makeText(this,
                MessageUtil.getMessage(getApplicationContext(), Msg.I022, new String[] { CommonConst.ADV }),
                Toast.LENGTH_SHORT).show();
            this.list = result;
            this.itemAdapter.changeData(list);
        } else {
            this.list = result;
            this.itemAdapter.changeData(list);
        }
        ivSearch.setClickable(true);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 商城产品广告一览对话框提示类
     * 修改和删除显示对话框
     */
    class XZHL1050_MsgDialog extends Dialog {

        // 显示信息标志位
        private int position;

        // 文本框: 信息
        private TextView tvMsg;

        // 文本框: 修改操作
        private TextView tvChange;

        // 文本框: 删除操作
        private TextView tvDelete;

        // 传递给下一个activity的数据
        private String[] content;

        // 上下文
        private XZHL1050_Advertisement context;

        /**
         * 自定义构造器初始化参数
         */
        public XZHL1050_MsgDialog(XZHL1050_Advertisement context, int theme, int position, String[] content) {

            super(context, theme);

            // Log开始
            LogUtil.logStart();
            this.position = position;
            this.content = content;
            this.context = context;

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 重写的加载自定义dialog视图的方法
         * @param savedInstanceState 绑定的对象
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            // Log开始
            LogUtil.logStart();
            this.setContentView(R.layout.xzhl1050_dialogview);

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
            // 修改按钮被点击
            tvChange.setOnClickListener(new View.OnClickListener() {

                // 修改被点击时出发的事件
                @Override
                public void onClick(View v) {

                    // Log开始
                    LogUtil.logStart();
                    dialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra(CommonConst.DATA, content);

                    // 跳转界面
                    intent.setClass(context, XZHL1030_AdvertisementModify.class);
                    context.startActivity(intent);

                    // Log结束
                    LogUtil.logEnd();
                }
            });

            // 删除按钮被点击
            tvDelete.setOnClickListener(new View.OnClickListener() {

                // 删除文本框被点击时触发的事件
                @Override
                public void onClick(View v) {

                    // Log开始
                    LogUtil.logStart();
                    // 构建是否确定删除店员的对话框
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder
                        .setTitle(tvMsg.getText())
                        .setMessage(MessageUtil.getMessage(context, Msg.Q015, new String[] { content[1] }))
                        .setCancelable(true)
                        .setPositiveButton(MessageUtil.getMessage(context, Msg.I005),
                            new DialogInterface.OnClickListener() {

                                // 点击取消按钮触发的事件
                                public void onClick(DialogInterface dialogIn, int id) {

                                    dialog.dismiss();
                                }
                            })
                        .setNegativeButton(MessageUtil.getMessage(context, Msg.I012),
                            new DialogInterface.OnClickListener() {

                                // 点击确认按钮触发的事件
                                public void onClick(DialogInterface dialogIn, int id) {

                                    dialog.dismiss();
                                    new AsyDeleteTask(context, position).execute(content[0]);
                                }
                            });
                    builder.create().show();

                    // Log结束
                    LogUtil.logEnd();
                }
            });

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 设置dialog
         */
        private void setupDialogView() {

            // Log开始
            LogUtil.logStart();
            tvMsg = (TextView) findViewById(R.id.tv_name_1050);
            tvMsg.setText(content[1]);
            tvChange = (TextView) findViewById(R.id.tv_change_1050);
            tvDelete = (TextView) findViewById(R.id.tv_delete_1050);

            // Log结束
            LogUtil.logEnd();
        }
    }
}
