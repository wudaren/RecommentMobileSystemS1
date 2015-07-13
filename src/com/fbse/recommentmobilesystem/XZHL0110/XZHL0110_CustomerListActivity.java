package com.fbse.recommentmobilesystem.XZHL0110;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;
import org.json.JSONException;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0110.XZHL0110_UserAdapter.ViewHolder;
import com.fbse.recommentmobilesystem.XZHL0110.XZHL0110_Utils;
import com.fbse.recommentmobilesystem.XZHL0120.XZHL0120_DatleActivity;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.common_entity.ShowInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class XZHL0110_CustomerListActivity extends Activity {
    // 页面注销按钮
    private Button relogbtn;
    // 删除按钮
    private Button refushbtn;
    // 删除全部按钮
    private Button delallbtn;
    // 服务管理器
    private ServiceManager serviceManager;
    // 推出状态保存
    private boolean isExit = false;
    // 图片加载线程
    private GetSingleUserImage myTask2;
    // 提示框
    private ProgressDialog sendDatadialog = null;
    private ProgressDialog sendDatadialog1 = null;
    // 整体显示gridview
    private GridView gridView;
    // 页面删除状态标记
    private Boolean delflg = false;
    // 点击位置存储
    private Integer delInteger;
    // 删除多选框状态存储
    private List<Boolean> delList = new ArrayList<Boolean>();
    // 用户信息缓存
    private ArrayList<ShowInfo> usertemp = new ArrayList<ShowInfo>();
    // 用户图片缓存
    private ArrayList<Bitmap> imagetemp = new ArrayList<Bitmap>();
    // 图片存储
    private List<Bitmap> imagelist = new ArrayList<Bitmap>();
    // 页面显示的用户信息存储
    private List<ShowInfo> userlist = new ArrayList<ShowInfo>();
    // 单用信息
    private ArrayList<ShowInfo> singleuserlist = new ArrayList<ShowInfo>();
    // 全局用户信息存储
    private ArrayList<ArrayList<ShowInfo>> viewentitylist = new ArrayList<ArrayList<ShowInfo>>();
    // 向页面传输的信息类
    private MsgReceiver msgReceiver;
    // 配置文件信息
    Properties properties = null;
    // 图片工厂
    BitmapFactory bitmapFactory = null;
    Bitmap jiazaishibai = null;
    // 适配器
    XZHL0110_UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getperties(); // 获得配置文件数据

        setContentView(R.layout.xzhl0110_custumer_list);// 定义布局文件
        findviewbyid(); // 绑定控件
        setlistener(); // 绑定按钮监听事件
        setadapter();
        setviewlistener(); // 设置listview的监听事件
        startserce(); // 启动androidPn服务

    }

    /**
     * 获得配置文件
     */

    @SuppressWarnings("static-access")
    public void getperties() {
        properties = Commonutil
                .loadProperties(XZHL0110_CustomerListActivity.this);

        bitmapFactory = new BitmapFactory();
        jiazaishibai = bitmapFactory.decodeResource(
                XZHL0110_CustomerListActivity.this.getResources(),
                R.drawable.jiazaishibai);
    }

    public void setadapter() {
        userAdapter = new XZHL0110_UserAdapter(
                XZHL0110_CustomerListActivity.this, viewentitylist,
                imagelist.size(), imagelist, false, delList);
        gridView.setAdapter(userAdapter);
    }

    /**
     * 调用onConfigurationChanged方法 防止转屏时重绘画面
     * 
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        } else {
        }

    }

    /**
     * 设置全局按钮点击监控 监控 返回键
     * 
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 如果没有处于删除状态 执行退出方法
            if (!delflg) {
                exit();
            } else {
                delflg = false;
                // 如果 屏幕处于删除状态 则退出删除状态 重绘页面
                userAdapter.refresh(XZHL0110_CustomerListActivity.this,
                        viewentitylist, imagelist.size(), imagelist, delflg,
                        delList);
                refushbtn.setVisibility(View.GONE);
                for (int i = 0; i < delList.size(); i++) {
                    delList.set(i, false);
                }
                // 删除状态还原

            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    // 双击退出时的 handler监听
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    // 双击退出事件
    public void exit() {
        if (!isExit) {
            isExit = true;
            MessageUtil.commonToast(getApplicationContext(),
                    properties.getProperty(Msg.I003, ""),
                    Toast.LENGTH_SHORT);
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            // 停止androidpn框架相关服务
            serviceManager.stopService();
            // 缓存清空方法
            XZHL0110_Utils.tempClean();
            this.finish();
        }
    }

    // 绑定按钮与控件
    private void findviewbyid() {
        relogbtn = (Button) findViewById(R.id.gobackBtn);
        refushbtn = (Button) findViewById(R.id.shuaxin);
        gridView = (GridView) findViewById(R.id.gridview);
        delallbtn = (Button) findViewById(R.id.qingchu);
    }

    // 设置绑定按钮事件
    private void setlistener() {
        // 返回按钮
        relogbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!delflg) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            XZHL0110_CustomerListActivity.this);
                    builder.setTitle(MessageUtil.getMessage(
                            XZHL0110_CustomerListActivity.this, Msg.Q007));
                    builder.setPositiveButton(MessageUtil.getMessage(
                            XZHL0110_CustomerListActivity.this, Msg.I012),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {
                                    XZHL0110_CustomerListActivity.this.finish();
                                    serviceManager.stopService();
                                }
                            });
                    builder.setNegativeButton(MessageUtil.getMessage(
                            XZHL0110_CustomerListActivity.this, Msg.I005),
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                        int which) {

                                }
                            });
                    builder.show();
                } else {
                    // 如果 屏幕处于删除状态 则退出删除状态 重绘页面
                    delflg = false;
                    userAdapter.refresh(XZHL0110_CustomerListActivity.this,
                            viewentitylist, imagelist.size(), imagelist,
                            delflg, delList);
                    // 删除状态还原
                    refushbtn.setVisibility(View.GONE);
                }

            }
        });
        // 清空按钮
        delallbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                clear();
            }
        });

    }

    /*
     * 刷新方法
     */
    private synchronized void shuaxin(ArrayList<ShowInfo> list) {
        // 页面显示用户列表增加
        userlist.add(list.get(0));
        // 总用户信息存储
        viewentitylist.add(list);
        // 删除状态列表添加
        delList.add(false);
        // 调用获得图片异步线程
        GetUserListimage mTask1 = new GetUserListimage();
        mTask1.execute();
    }

    // 初始化人员功能预留 线程

    /**
     * 
     * 从用户信息页面返回操作
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 删除操作返回
        if (1 == requestCode) {
            if (data != null) {
                // 获得删除标记
                if (data.getBooleanExtra("delflg", false)) {
                    if (delInteger != -1) {
                        // 删除条目确定
                        final int in = delInteger;
                        Log.i("int", "" + in);
                        // 分别设置清空 用户列表 图片列表 用户显示列表相应信息

                        viewentitylist.remove(in);
                        imagelist.remove(in);
                        userlist.remove(in);

                        int i = 0;
                        for (; i < delList.size(); i++) {
                            delList.set(i, false);
                        }
                        delList.remove(delList.size() - 1);

                        // 刷新控件
                        userAdapter.refresh(XZHL0110_CustomerListActivity.this,
                                viewentitylist, imagelist.size(), imagelist,
                                delflg, delList);

                        delInteger = -1;

                    }
                }
            }

        }

    }

    /**
     * 异步获得页面图片类
     * 
     * @author F1322
     * 
     */
    private class GetUserListimage extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            try {
                // 显示图片列表添加
                Log.i("image", userlist.get(userlist.size() - 1).getImageUrl());
                final Bitmap bitmap = XZHL0110_Utils.loadBitmap(userlist.get(
                        userlist.size() - 1).getImageUrl());
                if (bitmap != null) {
                    imagelist.add(bitmap);
                } else {
                    imagelist.add(jiazaishibai);
                }

            } catch (Exception e) {

                e.printStackTrace();
                return 0;
            }
            if (imagelist.size() != 0) {

                return 1;
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            // 显示图片加载中 提示框
            showDilog();
        }

        @Override
        protected void onPostExecute(Integer result) {
            // 如果已有本提示框 防止 重复 先删除原提示框
            sendDatadialog.dismiss();
            if (result == 1) {
                // 判断删除状态
                if (!delflg) {

                    // XZHL0110_UserAdapter userAdapter = new
                    // XZHL0110_UserAdapter(
                    // XZHL0110_CustomerListActivity.this, viewentitylist,
                    // imagelist.size(), imagelist, delflg, delList);
                    //
                    // gridView.setAdapter(userAdapter);
                    //
                    // // XZHL0110_UserAdapter userAdapter = new
                    // // XZHL0110_UserAdapter(
                    // // XZHL0110_CustomerListActivity.this, viewentitylist,
                    // // imagelist.size(), imagelist);
                    // // gridView.setAdapter(userAdapter);
                    // // // 刷新控件
                    // ((BaseAdapter) gridView.getAdapter())
                    // .notifyDataSetChanged();

                    userAdapter.refresh(XZHL0110_CustomerListActivity.this,
                            viewentitylist, imagelist.size(), imagelist,
                            delflg, delList);

                }
                // 响铃操作 调用系统通知铃声
                XZHL0110_Utils.PlaySound(XZHL0110_CustomerListActivity.this);
            }
        }

    }

    /**
     * 异步处理获得弹出框
     * 
     * @author F1322
     * 
     */
    private class GetSingleUserImage extends
            AsyncTask<Integer, Integer, Integer> {
        // 后台方法
        @Override
        protected Integer doInBackground(Integer... params) {
            Integer t = 0;
            try {

                for (Integer integer : params) {
                    t = integer;
                }
                Bitmap bitmap = null;
                imagetemp.clear();

                for (int i = 1; i < usertemp.size(); i++) {

                    // imagetemp.add(XZHL0110_Utils.loadBitmap(usertemp.get(i)
                    // .getImageUrl()));

                    bitmap = XZHL0110_Utils.loadBitmap(usertemp.get(i)
                            .getImageUrl());
                    if (bitmap != null) {
                        imagetemp.add(bitmap);
                    } else {
                        imagetemp.add(jiazaishibai);
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (imagelist.size() != 0) {

                return t;
            }
            return 0;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            showDilog1();
        }

        @Override
        protected void onPostExecute(Integer result) {
            // 得到响应后的后台处理
            sendDatadialog1.dismiss();
            // 获得返回值
            delInteger = result;
            XZHL0110_SingleUserDialog mDialog = new XZHL0110_SingleUserDialog(
                    XZHL0110_CustomerListActivity.this,
                    viewentitylist.get(result), imagetemp,
                    new XZHL0110_SingleUserDialog.OnCustomDialogListener() {
                        // 调用接口
                        @Override
                        public void back(Bitmap bitmap, ShowInfo user) {

                            Intent intent = new Intent();
                            intent.setClass(XZHL0110_CustomerListActivity.this,
                                    XZHL0120_DatleActivity.class);

                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable("Entity", user);
                            intent.putExtras(mBundle);
                            startActivityForResult(intent, 1);
                        }
                    });
            mDialog.show();

        }

    }

    // 启动链接androidPn框架服务
    public void startserce() {
        serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();

        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        // 截获androidpn发送的推送广播
        intentFilter.addAction("org.androidpn.client.SHOW_NOTIFICATION");
        registerReceiver(msgReceiver, intentFilter);

    }

    // 定义广播接收类 接收推送信息
    public class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 拿到进度，更新UI

            String notificationMessage = intent
                    .getStringExtra(Constants.NOTIFICATION_MESSAGE);

            try {
                // 解析JSON
                singleuserlist = (ArrayList<ShowInfo>) XZHL0110_Utils
                        .jiexiJSON(notificationMessage);
                System.out.println(singleuserlist);
            } catch (JSONException e) {
                // Json解析错误 提示网络异常
                MessageUtil.commonToast(XZHL0110_CustomerListActivity.this,
                        properties.getProperty(Msg.E0002, ""),
                        Toast.LENGTH_SHORT);

            }
            shuaxin(singleuserlist);
        }

    }

    // 提示图片正在加载图片
    private void showDilog() {
        if (sendDatadialog != null) {
            sendDatadialog.dismiss();
        }
        sendDatadialog = new ProgressDialog(this);
        sendDatadialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sendDatadialog.setMessage(properties.getProperty(Msg.I006, ""));
        // sendDatadialog.setButton(properties.getProperty(Msg.I005, ""),
        // sendDataDialogListener);
        sendDatadialog.setCancelable(false);
        sendDatadialog.setCanceledOnTouchOutside(false);
        sendDatadialog.show();
    }

    @SuppressWarnings("deprecation")
    private void showDilog1() {
        if (sendDatadialog1 != null) {
            sendDatadialog1.dismiss();
        }
        sendDatadialog1 = new ProgressDialog(this);
        sendDatadialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sendDatadialog1.setMessage(properties.getProperty(Msg.I006, ""));
        sendDatadialog1.setButton(properties.getProperty(Msg.I005, ""),
                sendDataDialogListener);

        sendDatadialog1.setCancelable(false);
        sendDatadialog1.setCanceledOnTouchOutside(false);
        sendDatadialog1.show();
    }

    // 点击取消 停止加载图片
    private DialogInterface.OnClickListener sendDataDialogListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // 取消上传进程
            try {
                myTask2.cancel(true);
                sendDatadialog.dismiss();
            } catch (Exception e) {

            }
        }
    };

    // 清空顾客列表操作
    private void clear() {
        if (viewentitylist != null && viewentitylist.size() != 0) {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    XZHL0110_CustomerListActivity.this);
            builder.setTitle(properties.getProperty(Msg.Q001, ""));
            builder.setPositiveButton(properties.getProperty(Msg.I012, ""),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewentitylist = new ArrayList<ArrayList<ShowInfo>>();
                            userlist = new ArrayList<ShowInfo>();

                            delList = new ArrayList<Boolean>();
                            imagelist = new ArrayList<Bitmap>();
                            userAdapter.refresh(
                                    XZHL0110_CustomerListActivity.this,
                                    viewentitylist, imagelist.size(),
                                    imagelist, false, delList);
                            refushbtn.setVisibility(View.GONE);

                            delflg = false;
                        }
                    });

            builder.setNegativeButton(properties.getProperty(Msg.I005, ""),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.show();
        } else {
            MessageUtil.commonToast(XZHL0110_CustomerListActivity.this,
                    properties.getProperty(Msg.E0001, ""),
                    Toast.LENGTH_SHORT);
        }
        // 一键删除

    }

    // gridview 点击事件处理
    private void setviewlistener() {
        // 短按事件
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    final int position, long id) {
                // 判断是否处在删除状态
                if (delflg) {
                    Log.i("delList.size()", delList.size() + "");
                    delList.set(position, !delList.get(position));
                    Log.i("log", delList.toString());
                    ViewHolder viewHolder = (ViewHolder) view.getTag();
                    viewHolder.delCheckBox.toggle();

                } else {
                    // 非删除状态 单人对应模式
                    if (viewentitylist
                            .get(viewentitylist.size() - position - 1).size() == 1) {

                        delInteger = position;
                        Intent intent = new Intent();
                        intent.setClass(XZHL0110_CustomerListActivity.this,
                                XZHL0120_DatleActivity.class);
                        Bundle mBundle = new Bundle();

                        mBundle.putSerializable(
                                "EntityOfImage",
                                viewentitylist.get(
                                        viewentitylist.size() - position - 1)
                                        .get(0));
                        intent.putExtras(mBundle);
                        startActivityForResult(intent, 1);

                    }

                    else if (viewentitylist.get(
                            viewentitylist.size() - position - 1).size() == 2) {
                        Intent intent = new Intent();
                        intent.setClass(XZHL0110_CustomerListActivity.this,
                                XZHL0120_DatleActivity.class);
                        delInteger = position;
                        // 设置传到后页面的参数
                        Bundle mBundle = new Bundle();

                        mBundle.putSerializable(
                                "Entity",
                                viewentitylist.get(
                                        viewentitylist.size() - position - 1)
                                        .get(1));
                        intent.putExtras(mBundle);
                        // 开启带返回值的
                        startActivityForResult(intent, 1);

                    } else {

                        // 如果有多人适配， 开始图片列表加载线程
                        myTask2 = new GetSingleUserImage();
                        usertemp = viewentitylist.get(viewentitylist.size()
                                - position - 1);
                        myTask2.execute(viewentitylist.size() - position - 1);
                    }

                }
            }
        });

        // 菜单长按事件设置
        gridView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                    final int arg2, long arg3) {

                /*
                 * 长按批量删除
                 */

                // 删除状态 ----开启删除状态
                delflg = true;
                // 刷新控件
                userAdapter.refresh(XZHL0110_CustomerListActivity.this,
                        viewentitylist, imagelist.size(), imagelist, delflg,
                        delList);
                // 删除事件显示
                refushbtn.setVisibility(View.VISIBLE);
                refushbtn.setBackgroundResource(R.drawable.wrong);
                // 删除绑定
                refushbtn.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(
                                XZHL0110_CustomerListActivity.this);
                        builder.setTitle(MessageUtil.getMessage(
                                XZHL0110_CustomerListActivity.this,
                                Msg.Q009));
                        builder.setPositiveButton(MessageUtil.getMessage(
                                XZHL0110_CustomerListActivity.this, Msg.I012),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which) {
                                        // 删除选中的客户
                                        for (int i = 0; i < delList.size(); i++) {
                                            if (delList.get(i)) {
                                                viewentitylist.remove(delList
                                                        .size() - i - 1);
                                                imagelist.remove(delList.size()
                                                        - i - 1);
                                                userlist.remove(delList.size()
                                                        - i - 1);
                                            }
                                        }
                                        List<Boolean> temp = new ArrayList<Boolean>();
                                        for (int i = 0; i < delList.size(); i++) {
                                            if (!delList.get(i)) {
                                                temp.add(false);
                                            }
                                        }
                                        delList = temp;
                                        // 刷新控件
                                        userAdapter
                                                .refresh(
                                                        XZHL0110_CustomerListActivity.this,
                                                        viewentitylist,
                                                        imagelist.size(),
                                                        imagelist, false,
                                                        delList);
                                        delflg = false;
                                        // 删除按钮绑定
                                        refushbtn.setVisibility(View.GONE);
                                    }
                                });
                        builder.setNegativeButton(
                                MessageUtil.getMessage(
                                        XZHL0110_CustomerListActivity.this,
                                        Msg.I005),
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                            int which) {

                                    }
                                });
                        builder.show();

                    }
                });

                return false;
            }
        });
    }

}
