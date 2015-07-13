/*  XZHL0810_MsgDialog.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL0810                                                                                           */
/*  画面名     ：商品信息查询                                                                                         */
/*  实现功能 ：商品列表对话框类                                                                                       */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/22   V01L01      FBSE)高振川      新規作成                                                        */
/*                                                                                                                    */
package com.fbse.recommentmobilesystem.XZHL0810;

import java.util.Properties;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0840.XZHL0840_ShopModifyActivity;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 *  对话框显示类
 *
 *  完成商品一览对话框
 */
@SuppressLint("HandlerLeak")
public class XZHL0810_MsgDialog extends Dialog{

    // 文本框: 姓名
    private TextView tvName;

    // 文本框: 修改操作
    private TextView tvXiugai;

    // 文本框: 删除操作
    private TextView tvShanchu;

    // 传递给下一个activity的数据
    private String[] content;

    // Activity上下文
    private Context context;

    // 初始化配置文件
    private Properties properties;

    // 单一商品信息
    private XZHL0810_GoodsItemBean bean;

    private XZHL0810_RefreshInterface refresh;

    private String msg;

    /**
     * 自定义构造器初始化参数
     * @param context Activity的上下文
     * @param theme 自定义dialog的样式
     * @param position 删除标志位
     * @param content 传递给下一个activity的数据
     * @return XZHL0810_MsgDialog 返回dialog
     */
    public XZHL0810_MsgDialog(Context context, int theme, int position, String[] content,
            XZHL0810_GoodsItemBean bean, XZHL0810_RefreshInterface refresh) {
        super(context, theme);
        this.content=content;
        this.context = context;
        this.bean = bean;
        this.refresh = refresh;
    }

    /**
     * 重写的加载自定义dialog视图的方法
     * @param savedInstanceState 界面参数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logStart();
        this.setContentView(R.layout.xzhl0810_dialogview);

        // 初始化控件
        setupDialogView();

        // 给dialog里的控件加监听器
        addDialogListener();

        // 初始化页面文件
        initData();
        LogUtil.logEnd();
    }

    /**
     * 初始化配置文件
     */
    private void initData(){
        LogUtil.logStart();
        properties = Commonutil.loadProperties(context);
        LogUtil.logEnd();
    }

    /**
     * 给dialog里的控件加监听器
     */
    private void addDialogListener() {

        LogUtil.logStart();
        tvXiugai.setOnClickListener(new View.OnClickListener(){

        // 修改文本框被点击时出发的事件
            @Override
            public void onClick(View v) {
                LogUtil.logStart();
                Intent intent = new Intent(context, XZHL0840_ShopModifyActivity.class);
                intent.putExtra("goodsinfo", bean);
                XZHL0810_MsgDialog.this.dismiss();
                context.startActivity(intent);
                LogUtil.logEnd();
            }

        });

        tvShanchu.setOnClickListener(new View.OnClickListener(){

            // 删除文本框被点击时触发的事件
            @Override
            public void onClick(View v) {

                LogUtil.logStart();
                // 构建是否确定删除商品的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(
                        MessageUtil.getMessage(context, Msg.Q015, new String[]{CommonConst.GOODS})).setCancelable(
                                true).setPositiveButton(properties.getProperty(Msg.I012,
                            CommonConst.SPACE), new DialogInterface.OnClickListener() {

                                    // 点击确认按钮触发的事件
                                    public void onClick(DialogInterface dialog, int id) {
                                        LogUtil.logStart();
                                        // 删除数据
                                        new PostGoodsInfo().start();
                                        refresh.refresh();
                                        LogUtil.logEnd();
                                    }
                                }).setNegativeButton(properties.getProperty(Msg.I005, CommonConst.SPACE),
                                  new DialogInterface.OnClickListener() {
                                    // 点击取消按钮触发的事件
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                XZHL0810_MsgDialog.this.dismiss();
                builder.create().show();
                LogUtil.logEnd();
            }
        });
    }

    /**
     * 初始化对话框样式
     */
    private void setupDialogView() {
        LogUtil.logStart();
        tvName=(TextView) findViewById(R.id.tv_name_0810);
        tvName.setText(content[1]);
        tvXiugai=(TextView) findViewById(R.id.tv_caozuoxiugai_0810);
        tvShanchu=(TextView) findViewById(R.id.tv_caozuoshanchu_0810);
        LogUtil.logEnd();
    }

    /**
     * 提交新增店员请求
     * @param null
     * @return String result 返回服务器返回的JSON
     */
    private String postGoodsDelete(){
        LogUtil.logStart();
        //http://192.168.1.230:8081/FBSEMobileSystemS1/goods/list?wsdl
        String result = null;
        WebServiceOfHttps woh = new WebServiceOfHttps();
        String[] key = {"id"};
        String[] value = {content[0]};
        String json = woh.WSservers(context, "goods/delete",
                JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),
                        "a0065207-fefe-4166-ac00-a6a09ce5e2a1", JsonUtil.getSign("a001",
                                JsonUtil.DataToJson(key, value), "a0065207-fefe-4166-ac00-a6a09ce5e2a1")));
        // 本地服务器异常
        if("{\"states\":\"99\"}".equals(json)){
            result = properties.getProperty(Msg.E0027, CommonConst.SPACE);
        }else{
            // 请求成功
            if(JsonUtil.errorJson(json)==null){
                result = MessageUtil.getMessage(context, Msg.I021, new String[]{content[1]});
            // 远程服务器异常
            }else if(CommonConst.TALENTERRORSTATES.equals(JsonUtil.errorJson(json).getSuccess())){
                result = properties.getProperty(Msg.E0028, CommonConst.SPACE);
            }else{
                result = JsonUtil.errorJson(json).getErr_msg();
            }
        }
        LogUtil.logEnd();
        return result;
    }

    /**
     * 请求删除结果处理
     */
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            LogUtil.logStart();
            super.handleMessage(msg);
            MessageUtil.commonToast(context, msg.obj.toString(), Toast.LENGTH_SHORT);
            LogUtil.logEnd();
        }
    };

    /**
     * 删除商品后台类
     *
     * 异步提交删除商品请求
     */
    class PostGoodsInfo extends Thread{

        @Override
        public void run() {
            LogUtil.logStart();
            super.run();
            msg = postGoodsDelete();
            Message message = new Message();
            message.obj = msg;
            handler.sendMessage(message);
            LogUtil.logEnd();
        }
    }

}
