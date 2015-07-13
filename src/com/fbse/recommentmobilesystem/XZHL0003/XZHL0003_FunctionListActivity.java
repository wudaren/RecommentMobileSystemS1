/*
 * 文件名 XZHL0003_FunctionListActivity
 * 包含类名列表 
 * 版本信息，版本号 V1.0
 * 创建日期 （2014-05-13）
 */

package com.fbse.recommentmobilesystem.XZHL0003;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0001.XZHL0001_LoginActivity;
import com.fbse.recommentmobilesystem.XZHL0110.XZHL0110_CustomerListActivity;
import com.fbse.recommentmobilesystem.XZHL0150.XZHL0150_MemberQuery;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_ShowQuestioniareActivity;
import com.fbse.recommentmobilesystem.XZHL0310.XZHL0310_InfoChangeActivity;
import com.fbse.recommentmobilesystem.XZHL0410.XZHL0410_TaskFinishActivity;
import com.fbse.recommentmobilesystem.XZHL0510.XZHL0510_EquitmentListActivity;
import com.fbse.recommentmobilesystem.XZHL0650.XZHL0650_MailSummary;
import com.fbse.recommentmobilesystem.XZHL0810.XZHL0810_ShopDisplayActivity;
import com.fbse.recommentmobilesystem.XZHL1010.XZHL1010_Advertisement;
import com.fbse.recommentmobilesystem.XZHL1050.XZHL1050_Advertisement;
import com.fbse.recommentmobilesystem.XZHL1190.XZHL1190_EveryShopTotal;
import com.fbse.recommentmobilesystem.XZHL1240.XZHL1240_StoreCountsActivity;
import com.fbse.recommentmobilesystem.XZHL1310.XZHL1310_MsgActivity;
import com.fbse.recommentmobilesystem.XZHL1450.XZHL1450_ShopList;
import com.fbse.recommentmobilesystem.XZHL1550.XZHL1550_ShopList;
import com.fbse.recommentmobilesystem.XZHL1610.XZHL1610_Camera;
import com.fbse.recommentmobilesystem.XZHL1710.XZHL1710_PassengerFlowActivity;
import com.fbse.recommentmobilesystem.XZHL1810.XZHL1810_HotShowActivity;
import com.fbse.recommentmobilesystem.common.DoubleClickReturn;
import com.fbse.recommentmobilesystem.common.MessageUtil;

/**
 * XZHL0003_FunctionListActivity.java
 * 
 * @author 张海静 功能 功能表数据的获取与显示 创建日期 (2014-05-13)
 * 
 */
public class XZHL0003_FunctionListActivity extends Activity implements
        OnClickListener {
    // 自定义的GridView
    private XZHL0003_MyGridView infoGridView, managementGridView,
            marketingGridVIew, equipentGridView, otherGridView;
    // 显示的标题
    private TextView infoText, managementText, marketingText, equipentText,
            otherText;
    // 注销
    private ImageView cancle;
    private SharedPreferences shared;
    private static final String QUANXIAN = "1";

    /**
     * 初始化数据，如控件的初始化
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0003_functionlist);
        // 控件的初始化引用方法
        initOverall();
        // 保持屏幕常亮服务
        setKeepScreen();
        // 登录
        setFunctionListData();
    }

    private void initOverall() {
        // 控件的初始化
        infoGridView = (XZHL0003_MyGridView) findViewById(R.id.infogridview);
        infoGridView.setOnItemClickListener(new FunctionInfoListener());
        managementGridView = (XZHL0003_MyGridView) findViewById(R.id.managementgridview);
        managementGridView
                .setOnItemClickListener(new FunctionManagementListener());
        marketingGridVIew = (XZHL0003_MyGridView) findViewById(R.id.marketinggridview);
        marketingGridVIew
                .setOnItemClickListener(new FunctionMarketingListener());
        equipentGridView = (XZHL0003_MyGridView) findViewById(R.id.equipentgridview);
        equipentGridView
                .setOnItemClickListener(new FunctionEquipmentListener());
        otherGridView = (XZHL0003_MyGridView) findViewById(R.id.othergridview);
        otherGridView.setOnItemClickListener(new FunctionOtherListener());
        infoText = (TextView) findViewById(R.id.infotext_0003);
        managementText = (TextView) findViewById(R.id.managementtext_0003);
        marketingText = (TextView) findViewById(R.id.marketingtext_0003);
        equipentText = (TextView) findViewById(R.id.equipenttext_0003);
        otherText = (TextView) findViewById(R.id.othertext_0003);
        cancle = (ImageView) findViewById(R.id.cancle);
        cancle.setOnClickListener(this);
    }

    /**
     * 解析本地数据,分配页面显示数据
     */
    private void setFunctionListData() {
        // 功能表数据
        String[] funcName = getResources().getStringArray(
                R.array.function_list_text_host);
        // 菜单数据
        String[] caiName = getResources().getStringArray(
                R.array.function_list_cai);
        List<XZHL0003_Function> functionList = new ArrayList<XZHL0003_Function>();
        XZHL0003_Function func = null;
        // 往XZHL0003_Function中赋值
        int funLin = funcName.length;
        for (int i = 0; i < funLin; i++) {
            func = new XZHL0003_Function();
            func.setId(i);
            func.setName(funcName[i]);
            // 通过每个菜单拥有的子列表的数目不同，从而分配菜单的所属子列表
            // 设置菜单1 --消息与通知
            if (i == 0) {
                func.setFid(0);
                func.setFname(caiName[0]);
            }
            // 设置菜单2 --经营管理
            if (i >= 1 && i < 10) {
                func.setFid(1);
                func.setFname(caiName[1]);
            }
            // 设置菜单3 --营销
            if (i >= 10 && i < 14) {
                func.setFid(2);
                func.setFname(caiName[2]);
            }
            // 设置菜单4 --设备管理
            if (i >= 14 && i < 16) {
                func.setFid(3);
                func.setFname(caiName[3]);
            }
            // 设置菜单5 --其他
            if (i >= 16) {
                func.setFid(4);
                func.setFname(caiName[4]);
            }
            functionList.add(func);
        }
        // 取数据，并用List<Map<String, Object>>集合封装
        List<Map<String, Object>> functionInfoList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> functionManagementList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> functionMarketingList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> functionEquipentList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> functionOtherList = new ArrayList<Map<String, Object>>();
        Map<String, Object> functionInfo = null;
        Map<String, Object> functionManagement = null;
        Map<String, Object> functionMarketing = null;
        Map<String, Object> functionEquipent = null;
        Map<String, Object> functionOther = null;
        // 功能表图标资源获取
        int[] functionListImg = { R.drawable.xiaoxiyutongzhi,
                R.drawable.shopguide, R.drawable.questionnaire,
                R.drawable.renwuwancheng, R.drawable.jinrongshuju,
                R.drawable.keliutongji, R.drawable.jiankong,
                R.drawable.yewushujuchaxun, R.drawable.renliudong,
                R.drawable.youji, R.drawable.huiyuanzhuce,
                R.drawable.shangpinzhanshi, R.drawable.shangchengtuijian,
                R.drawable.guanggaoshezhi, R.drawable.yingxiao,
                R.drawable.shebeiguanli, R.drawable.fuwupingjia,
                R.drawable.userinfo, R.drawable.userinfo, R.drawable.userinfo  };
        // 设置通知栏的显示
        for (int i = 0; i < functionList.size(); i++) {
            XZHL0003_Function function = functionList.get(i);
            if (i == 0) {
                functionInfo = new HashMap<String, Object>();
                functionInfo.put("function_img", functionListImg[i]);
                functionInfo.put("function_text", function.getName());
                functionInfoList.add(functionInfo);
            }
            // 设置菜单显示的名称
            switch (i) {
            case 0:
                // 消息与通知
                infoText.setText(function.getFname());
                break;
            case 1:
                // 经营管理
                managementText.setText(function.getFname());
                break;
            case 11:
                // 营销
                marketingText.setText(function.getFname());
                break;
            case 15:
                // 设备管理
                equipentText.setText(function.getFname());
                break;
            case 17:
                // 其他
                otherText.setText(function.getFname());
                break;
            }
        }
        // 设置经营管理的显示
        for (int i = 1; i < 11; i++) {
            XZHL0003_Function function = functionList.get(i);
            functionManagement = new HashMap<String, Object>();
            functionManagement.put("function_img", functionListImg[i]);
            functionManagement.put("function_text", function.getName());
            functionManagementList.add(functionManagement);
        }
        // 设置营销的显示
        for (int i = 11; i < 12; i++) {
            XZHL0003_Function function = functionList.get(i);
            functionMarketing = new HashMap<String, Object>();
            functionMarketing.put("function_img", functionListImg[i]);
            functionMarketing.put("function_text", function.getName());
            functionMarketingList.add(functionMarketing);
        }
        // 设置营销的显示2
        for (int i = 13; i < 15; i++) {
            XZHL0003_Function function = functionList.get(i);
            functionMarketing = new HashMap<String, Object>();
            functionMarketing.put("function_img", functionListImg[i]);
            functionMarketing.put("function_text", function.getName());
            functionMarketingList.add(functionMarketing);
        }
        // 设置设备管理的显示
        for (int i = 15; i < 16; i++) {
            XZHL0003_Function function = functionList.get(i);
            functionEquipent = new HashMap<String, Object>();
            functionEquipent.put("function_img", functionListImg[i]);
            functionEquipent.put("function_text", function.getName());
            functionEquipentList.add(functionEquipent);
        }
        // 设置其他的显示
        for (int i = 18; i < 20; i++) {
            XZHL0003_Function function = functionList.get(i);
            functionOther = new HashMap<String, Object>();
            functionOther.put("function_img", functionListImg[i]);
            functionOther.put("function_text", function.getName());
            functionOtherList.add(functionOther);
        }
        // 定义适配器，分配数据
        XZHL0003_FunctionListAdapter adapter11 = new XZHL0003_FunctionListAdapter(
                functionInfoList, this);
        XZHL0003_FunctionListAdapter adapter12 = new XZHL0003_FunctionListAdapter(
                functionManagementList, this);
        XZHL0003_FunctionListAdapter adapter13 = new XZHL0003_FunctionListAdapter(
                functionMarketingList, this);
        XZHL0003_FunctionListAdapter adapter14 = new XZHL0003_FunctionListAdapter(
                functionEquipentList, this);
        XZHL0003_FunctionListAdapter adapter15 = new XZHL0003_FunctionListAdapter(
                functionOtherList, this);
        // 获取适配器的值分配给GridView显示
        infoGridView.setAdapter(adapter11);
        managementGridView.setAdapter(adapter12);
        marketingGridVIew.setAdapter(adapter13);
        equipentGridView.setAdapter(adapter14);
        otherGridView.setAdapter(adapter15);
    }

    // 通知栏点击事件
    private class FunctionInfoListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Intent intent = null;
            switch (arg2) {
            case 0:
                // 消息与通知
                intent = new Intent(getApplicationContext(),
                        XZHL1310_MsgActivity.class);
                break;
            default:
                break;
            }
            startActivity(intent);
        }
    }

    // 经营管理点击事件
    private class FunctionManagementListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Intent intent = null;
            switch (arg2) {
            case 0:
                // 导航
                intent = new Intent(getApplicationContext(),
                        XZHL0110_CustomerListActivity.class);
                break;
            case 1:
                // 问卷调查
                intent = new Intent(getApplicationContext(),
                        XZHL0210_ShowQuestioniareActivity.class);
                break;
            case 2:
                // 任务完成状况
                intent = new Intent(getApplicationContext(),
                        XZHL0410_TaskFinishActivity.class);
                break;
            case 3:
                // 金融数据查询
                intent = new Intent(getApplicationContext(),
                        XZHL1450_ShopList.class);
                break;
            case 4:
                // 客流分析
                intent = new Intent(getApplicationContext(),
                        XZHL1710_PassengerFlowActivity.class);
                break;
            case 5:
                // 监控
                intent = new Intent(getApplicationContext(),
                        XZHL1610_Camera.class);
                break;
            case 6:
                // 业务数据查询
                intent = new Intent(getApplicationContext(),
                        XZHL1550_ShopList.class);
                break;
            case 7:
                // 人流动线分析
                intent = new Intent(getApplicationContext(),
                        XZHL1810_HotShowActivity.class);
                break;
            case 8:
                // 代邮
                intent = new Intent(getApplicationContext(),
                        XZHL0650_MailSummary.class);
                break;
            case 9:
                // 会员一览
                intent = new Intent(getApplicationContext(),
                        XZHL0150_MemberQuery.class);
                break;
            default:
                break;
            }
            startActivity(intent);
        }
    }

    // 营销点击事件
    private class FunctionMarketingListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Intent intent = null;
            switch (arg2) {
            case 0:
                // 商品展示
                intent = new Intent(getApplicationContext(),
                        XZHL0810_ShopDisplayActivity.class);
                break;
            // case 1:
            // // 商城推荐
            // intent = new Intent(getApplicationContext(),
            // XZHL0910_ShopSuggest.class);
            // break;
            case 1:
                // 广告设置
             // 从登录页面获取权限值
                String lim = shared.getString("QUANXIAN", QUANXIAN);
                // String类型转换成int类型
                int li = Integer.parseInt(lim);
                switch (li) {
                case 1:
                    // 商家登录
                    intent = new Intent(getApplicationContext(),
                            XZHL1010_Advertisement.class);
                    break;
                case 2:
                    // 店长登录
                    intent = new Intent(getApplicationContext(),
                            XZHL1010_Advertisement.class);
                    break;
                case 3:
                    // 店员登录
                    intent = new Intent(getApplicationContext(),
                            XZHL1010_Advertisement.class);
                    break;
                default:
                    break;
                }
                break;
            case 2:
                // 下一站营销设置
                intent = new Intent(getApplicationContext(),
                        XZHL1190_EveryShopTotal.class);
                break;
            default:
                break;
            }
            startActivity(intent);
        }
    }

    // 设备管理点击事件
    private class FunctionEquipmentListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Intent intent = null;
            switch (arg2) {
            case 0:
                // 设备管理
                intent = new Intent(getApplicationContext(),
                        XZHL0510_EquitmentListActivity.class);
                break;
            case 1:
                // 服务评价
                intent = new Intent(getApplicationContext(),
                        XZHL0510_EquitmentListActivity.class);
                break;
            default:
                break;
            }
            startActivity(intent);
        }
    }

    // 其他点击事件
    private class FunctionOtherListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            Intent intent = null;
            switch (arg2) {
            // case 0:
            // // 店员管理
            // intent = new Intent(getApplicationContext(),
            // XZHL1240_ClerkCountsTotal.class);
            // break;
            case 0:
                // 账户设置
                intent = new Intent(getApplicationContext(),
                        XZHL0310_InfoChangeActivity.class);
                break;
            case 1:
                // 店员管理
                intent = new Intent(getApplicationContext(),
                        XZHL1240_StoreCountsActivity.class);
                break;
            default:
                break;
            }
            startActivity(intent);
        }
    }

    // 注销前的DIalog
    private void exitSure() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setIcon(null);
        build.setTitle(MessageUtil.getMessage(getApplicationContext(),
                "EXITSURE"));
        // 确定点击事件
        build.setPositiveButton(
                MessageUtil.getMessage(getApplicationContext(), "I012"),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 移除 记住密码、自动登录信息
                        Editor pwd = shared.edit();
                        pwd.remove("PASSWORD");
                        pwd.remove("ISCHECK");
                        pwd.remove("AUTO_ISCHECK");
                        pwd.commit();
                        // 跳转到登录页面
                        Intent login = new Intent(getApplicationContext(),
                                XZHL0001_LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                });
        // 取消点击事件
        build.setNegativeButton(
                MessageUtil.getMessage(getApplicationContext(), "I005"),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        build.show();
    }

    /**
     * 控件的点击事件
     */
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        case R.id.cancle:
            // 引用退出方法
            exitSure();
            break;
        default:
            break;
        }
    }

    // 保持屏幕常亮的Service
    private void setKeepScreen() {
        Intent power = new Intent(getApplicationContext(),
                XZHL0003_PowerService.class);
        startService(power);
    }

    // 返回时屏幕常亮
    @Override
    protected void onResume() {
        setKeepScreen();
        setFunctionListData();
        super.onResume();
    }

    /**
     * 返回按钮的监听
     * 
     * @param keyCode
     *            按键的值--这里是返回按钮
     * @param event
     *            按钮事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 双击效果的显示
            DoubleClickReturn.doubleClickReturnEvent(this);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
