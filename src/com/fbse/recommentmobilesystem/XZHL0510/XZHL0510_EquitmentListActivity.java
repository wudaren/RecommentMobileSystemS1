/*
 * 文件名 XZHL0510_EquitmentList
 * 包含类名列表 
 * 版本信息，版本号 V1.0
 * 创建日期 （2014-05-14）
 */
package com.fbse.recommentmobilesystem.XZHL0510;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0520.XZHL0520_RecordListActivity;
import com.fbse.recommentmobilesystem.XZHL0530.XZHL0530_Activity;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;
/**
 * XZHL0510_EquitmentList.java
 * @author 张海静
 * 从服务器获取所有的设备
 * 创建日期 (2014-05-13)
 */
public class XZHL0510_EquitmentListActivity extends Activity implements
        OnClickListener, XZHL0510_ListViewPosClick {
    //控件的定义、初始化
    private ViewPager viewPagers;
    private List<String> titleList;//viewPagers的标题
    private PagerTabStrip pagerTabStrip;//一个viewPagers的指示器，效果就是一个横的粗的下划线
    private XZHL0510_EquipmentViewPage xzhl0510_EquipmentViewPage;
    //所有界面的集合
    private List<View> equiplist;
    private View viewBusinessPos, viewMmoneypos,viewCamera, viewPad,viewNet, viewTv, viewSoft,viewConveniencePeople, viewElectronicScale;
    private ListView pos_list,moneypos_list, camera_list, pad_list,wangluo_list, tv_list, soft_list, bianminzhongduan_list, dianzicheng_list;
    private Button back, shuxinList;
    private List<XZHL0510_DeviceArray> compareOldList = null;
    private List<XZHL0510_DeviceArray> compareNewList = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> businessPos = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> moneyPos = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> camera = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> pad = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> netEquipment = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> tv = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> dianzicheng = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> soft = new ArrayList<XZHL0510_DeviceArray>();
    private List<XZHL0510_DeviceArray> bianminzhongduan = new ArrayList<XZHL0510_DeviceArray>();
    // 商业pos、摄像头、PAD、网络设备等的适配器
    private XZHL0510_EquitmentPosAdapter xzhl0510_EquitmentPosAdapter;
    private XZHL0510_EquitmentMoneyPosAdapter xzhl0510_EquitmentMoneyPosAdapter;
    private XZHL0510_EquitmentCameraAdapter xzhl0510_EquitmentCameraAdapter;
    private XZHL0510_EquitmentPadAdapter xzhl0510_EquitmentPadAdapter;
    private XZHL0510_EquitmentWangLuoSheBeiAdapter xzhl0510_EquitmentWangLuoSheBeiAdapter;
    private XZHL0510_EquitmentTVAdapter xzhl0510_EquitmentTVAdapter;
    private XZHL0510_EquitmentDianZiChengAdapter xzhl0510_EquitmentDianZiChengAdapter;
    private XZHL0510_EquitmentSoftAdapter xzhl0510_EquitmentSoftAdapter;
    private XZHL0510_EquitmentBianMinZhongDuanAdapter xzhl0510_EquitmentBianMinZhongDuanAdapter;
 // 商业pos、摄像头、PAD、网络设备等无数据的文本显示
    private TextView nodataPos,nodataMoneypos, nodataCamera, nodataPad,
        nodataNetEquipment,nodataTv,nodataSoft,nodataElectronicScale,nodataConveniencePeople;
    //显示的进度条布局
    private LinearLayout mypro;
    //保存永久信息
    private SharedPreferences shared;
    // 保存上一次的点击时间
    private long lastClickTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shared = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0510_equipmentlist);
        // 初始化控件
        Init();
        //判断网络异常,无网络显示 暂无数据
        if (!Commonutil.isNetworkAvailable(this)) {
            //引用无网络显示的方法
            hideData();
        } else {
            PostNewloginNameAsyncTask dd = new PostNewloginNameAsyncTask();
            dd.execute();
            mypro.setVisibility(View.VISIBLE);
            //引用数据判断
            ii();
        }
    }
    //无网络显示 暂无数据
    private void hideData() {
        viewPagers.setVisibility(View.VISIBLE);
        pos_list.setVisibility(View.GONE);
        nodataPos.setVisibility(View.VISIBLE);
        moneypos_list.setVisibility(View.GONE);
        nodataMoneypos.setVisibility(View.VISIBLE);
        camera_list.setVisibility(View.GONE);
        nodataCamera.setVisibility(View.VISIBLE);
        pad_list.setVisibility(View.GONE);
        nodataPad.setVisibility(View.VISIBLE);
        wangluo_list.setVisibility(View.GONE);
        nodataNetEquipment.setVisibility(View.VISIBLE);
        tv_list.setVisibility(View.GONE);
        nodataTv.setVisibility(View.VISIBLE);
        bianminzhongduan_list.setVisibility(View.GONE);
        nodataConveniencePeople.setVisibility(View.VISIBLE);
        dianzicheng_list.setVisibility(View.GONE);
        nodataElectronicScale.setVisibility(View.VISIBLE);
        soft_list.setVisibility(View.GONE);
        nodataSoft.setVisibility(View.VISIBLE);
    }

    // 开启线程
    private class PostNewloginNameAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... arg0) {
            //从web端获取数据
            WebServiceOfHttps webSO = new WebServiceOfHttps();
            String[] key = {"timestamp"};
            String[] value = {"0"};
            return  webSO.WSservers(XZHL0510_EquitmentListActivity.this, "device/list", 
                    JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value),shared.getString("ID", ""), JsonUtil.getSign("a001",  JsonUtil.DataToJson(key, value),shared.getString("ID", ""))));
        }
        @Override
        protected void onPostExecute(String result) {
            XZHL0510_DeviceBean tb = JsonUtil.JsonToDevice(result);
            //如果网络异常 显示没有数据
            if (result.equals("error")) {
                Toast.makeText(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(),"E0002"),
                        Toast.LENGTH_SHORT).show();
                shuxinList.setEnabled(true);
                hideData();
            } else {
                //如果没有数据显示 没有数据
                if (tb.getDevice() == null) {
                    mypro.setVisibility(View.GONE);
                    //引用无网络显示的方法
                    hideData();
                } else {
                    //有数据，解析数据
                    viewPagers.setVisibility(View.VISIBLE);
                    mypro.setVisibility(View.GONE);
                    shuxinList.setEnabled(true);
                    for (int i = 0; i <tb.getDevice().size(); i++) {
                        XZHL0510_DeviceArray da=tb.getDevice().get(i);
                        //比较数据，判断是否新数据和旧数据
                        compareNewList=new ArrayList<XZHL0510_DeviceArray>();
                        if(compareOldList!=null){
                            for (int j = 0; j < compareOldList.size(); j++) {
                                compareNewList.add(compareOldList.get(i));
                            }
                        }
                        compareOldList=new ArrayList<XZHL0510_DeviceArray>();
                        for (int j = 0; j < tb.getDevice().size(); j++) {
                            compareOldList.add(da);
                        }
                        if(da.getName().contains("商业pos")){
                            businessPos.add(da);
                        }
                        if(da.getName().contains("金融pos")){
                            moneyPos.add(da);
                        }
                        if(da.getName().contains("摄像头")){
                            camera.add(da);
                        }
                        if(da.getName().contains("pad")){
                            pad.add(da);
                        }
                        if(da.getName().contains("网络设备")){
                            netEquipment.add(da);
                        }
                        if(da.getName().contains("电视")){
                            tv.add(da);
                        }
                        if(da.getName().contains("电子秤")){
                            dianzicheng.add(da);
                        }
                        if(da.getName().contains("软件")){
                            soft.add(da);
                        }
                        if(da.getName().contains("便民终端")){
                            bianminzhongduan.add(da);
                        }
                    }
                }
                ii();

            }
            super.onPostExecute(result);
        }
    }
    //判断数据的显示，如果没有数据，就显示【没有数据】
    private void ii() {
        //商业pos
        if (mypro.getVisibility() == 0) {
            nodataPos.setVisibility(View.GONE);
        } else if (businessPos.size() == 0) {
            pos_list.setVisibility(View.GONE);
            nodataPos.setVisibility(View.VISIBLE);
        } else {
            pos_list.setVisibility(View.VISIBLE);
            nodataPos.setVisibility(View.GONE);
        }
        //摄像头
        if (mypro.getVisibility() == 0) {
            nodataCamera.setVisibility(View.GONE);
        } else if (camera.size() == 0) {
            camera_list.setVisibility(View.GONE);
            nodataCamera.setVisibility(View.VISIBLE);
        } else {
            camera_list.setVisibility(View.VISIBLE);
            nodataCamera.setVisibility(View.GONE);
        }
        //pad
        if (mypro.getVisibility() == 0) {
            nodataPad.setVisibility(View.GONE);
        } else if (pad.size() == 0) {
            pad_list.setVisibility(View.GONE);
            nodataPad.setVisibility(View.VISIBLE);
        } else {
            pad_list.setVisibility(View.VISIBLE);
            nodataPad.setVisibility(View.GONE);
        }
        //金融pos
        if (mypro.getVisibility() == 0) {
            nodataMoneypos.setVisibility(View.GONE);
        } else if (moneyPos.size() == 0) {
            moneypos_list.setVisibility(View.GONE);
            nodataMoneypos.setVisibility(View.VISIBLE);
        } else {
            moneypos_list.setVisibility(View.VISIBLE);
            nodataMoneypos.setVisibility(View.GONE);
        }
        //网络设备
        if (mypro.getVisibility() == 0) {
            nodataNetEquipment.setVisibility(View.GONE);
        } else if (netEquipment.size() == 0) {
            wangluo_list.setVisibility(View.GONE);
            nodataNetEquipment.setVisibility(View.VISIBLE);
        } else {
            wangluo_list.setVisibility(View.VISIBLE);
            nodataNetEquipment.setVisibility(View.GONE);
        }
        //电视
        if (mypro.getVisibility() == 0) {
            nodataTv.setVisibility(View.GONE);
        } else if (tv.size() == 0) {
            tv_list.setVisibility(View.GONE);
            nodataTv.setVisibility(View.VISIBLE);
        } else {
            tv_list.setVisibility(View.VISIBLE);
            nodataTv.setVisibility(View.GONE);
        }
        //电子秤
        if (mypro.getVisibility() == 0) {
            nodataElectronicScale.setVisibility(View.GONE);
        } else if (dianzicheng.size() == 0) {
            dianzicheng_list.setVisibility(View.GONE);
            nodataElectronicScale.setVisibility(View.VISIBLE);
        } else {
            dianzicheng_list.setVisibility(View.VISIBLE);
            nodataElectronicScale.setVisibility(View.GONE);
        }
        //软件
        if (mypro.getVisibility() == 0) {
            nodataSoft.setVisibility(View.GONE);
        } else if (soft.size() == 0) {
            soft_list.setVisibility(View.GONE);
            nodataSoft.setVisibility(View.VISIBLE);
        } else {
            soft_list.setVisibility(View.VISIBLE);
            nodataSoft.setVisibility(View.GONE);
        }
        //便民终端
        if (mypro.getVisibility() == 0) {
            nodataConveniencePeople.setVisibility(View.GONE);
        } else if (bianminzhongduan.size() == 0) {
            bianminzhongduan_list.setVisibility(View.GONE);
            nodataConveniencePeople.setVisibility(View.VISIBLE);
        } else {
            bianminzhongduan_list.setVisibility(View.VISIBLE);
            nodataConveniencePeople.setVisibility(View.GONE);
        }
        
        //各个界面的适配器
        xzhl0510_EquitmentPosAdapter = new XZHL0510_EquitmentPosAdapter(businessPos,
                this, this);
        pos_list.setAdapter(xzhl0510_EquitmentPosAdapter);
        xzhl0510_EquitmentMoneyPosAdapter=new XZHL0510_EquitmentMoneyPosAdapter(moneyPos, this, this);
        moneypos_list.setAdapter(xzhl0510_EquitmentMoneyPosAdapter);
        xzhl0510_EquitmentCameraAdapter = new XZHL0510_EquitmentCameraAdapter(
                camera, this, this);
        camera_list.setAdapter(xzhl0510_EquitmentCameraAdapter);
        xzhl0510_EquitmentPadAdapter = new XZHL0510_EquitmentPadAdapter(pad,
                this, this);
        pad_list.setAdapter(xzhl0510_EquitmentPadAdapter);
        // 定义适配器，分配数据
        xzhl0510_EquitmentWangLuoSheBeiAdapter=new XZHL0510_EquitmentWangLuoSheBeiAdapter(netEquipment, this, this);
        wangluo_list.setAdapter(xzhl0510_EquitmentWangLuoSheBeiAdapter);
        xzhl0510_EquitmentTVAdapter=new XZHL0510_EquitmentTVAdapter(tv, this, this);
        tv_list.setAdapter(xzhl0510_EquitmentTVAdapter);
        xzhl0510_EquitmentDianZiChengAdapter=new XZHL0510_EquitmentDianZiChengAdapter(dianzicheng, this, this);
        dianzicheng_list.setAdapter(xzhl0510_EquitmentDianZiChengAdapter);
        xzhl0510_EquitmentSoftAdapter=new XZHL0510_EquitmentSoftAdapter(soft, this, this);
        soft_list.setAdapter(xzhl0510_EquitmentSoftAdapter);
        xzhl0510_EquitmentBianMinZhongDuanAdapter=new XZHL0510_EquitmentBianMinZhongDuanAdapter(bianminzhongduan, this, this);
        bianminzhongduan_list.setAdapter(xzhl0510_EquitmentBianMinZhongDuanAdapter);
    }

    // 初始化控件
    private void Init() {
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        shuxinList = (Button) findViewById(R.id.shuxinList);
        shuxinList.setOnClickListener(this);
        viewPagers = (ViewPager) findViewById(R.id.viewpager);
        viewPagers.setVisibility(View.GONE);
        titleList = new ArrayList<String>();// 每个页面的Title数据 
        String[] equiptitlelist=getResources().getStringArray(R.array.equiptitlelist);
        for (int i = 0; i < equiptitlelist.length; i++) {
            titleList.add(equiptitlelist[i]); 
        }
        //viewPagers的指示器,设置字体颜色、背景色
        pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagertab); 
        pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.viewpage_line));  
        pagerTabStrip.setDrawFullUnderline(false); 
        pagerTabStrip.setBackgroundColor(getResources().getColor(R.color.black)); 
        pagerTabStrip.setTextColor(getResources().getColor(R.color.white));
//        pagerTabStrip.setTextSpacing(50);
        // 进度条
        mypro = (LinearLayout) findViewById(R.id.mypro);

        // ViewPage适配
        getLayoutInflater();
        LayoutInflater lf = LayoutInflater.from(this);
        //商业POS界面
        viewBusinessPos = lf.inflate(R.layout.xzhl0510_equipmentlist_pos, null);
        pos_list = (ListView) viewBusinessPos.findViewById(R.id.pos_list);
        nodataPos = (TextView) viewBusinessPos.findViewById(R.id.nodata_pos);
        //金融POS界面
        viewMmoneypos = lf.inflate(R.layout.xzhl0510_equipmentlist_moneypos, null);
        moneypos_list = (ListView) viewMmoneypos.findViewById(R.id.moneypos_list);
        nodataMoneypos = (TextView) viewMmoneypos.findViewById(R.id.nodata_moneypos);
        //摄像头界面
        viewCamera = lf.inflate(R.layout.xzhl0510_equipmentlist_camera, null);
        camera_list = (ListView) viewCamera.findViewById(R.id.camera_list);
        nodataCamera = (TextView) viewCamera.findViewById(R.id.nodata_camera);
        //PAD界面
        viewPad = lf.inflate(R.layout.xzhl0510_equipmentlist_pad, null);
        pad_list = (ListView) viewPad.findViewById(R.id.pad_list);
        nodataPad = (TextView) viewPad.findViewById(R.id.nodata_pad);
        //网络设备
        viewNet = lf.inflate(R.layout.xzhl0510_equipmentlist_wangluoshebei, null);
        wangluo_list = (ListView) viewNet.findViewById(R.id.wangluoshebei_list);
        nodataNetEquipment = (TextView) viewNet.findViewById(R.id.nodata_wangluoshebei);
        //电视
        viewTv = lf.inflate(R.layout.xzhl0510_equipmentlist_tv, null);
        tv_list = (ListView) viewTv.findViewById(R.id.tv_list);
        nodataTv = (TextView) viewTv.findViewById(R.id.nodata_tv);
        //便民设备
        viewConveniencePeople = lf.inflate(R.layout.xzhl0510_equipmentlist_bianninzhiongduan, null);
        bianminzhongduan_list = (ListView) viewConveniencePeople.findViewById(R.id.bianmin_list);
        nodataConveniencePeople = (TextView) viewConveniencePeople.findViewById(R.id.nodata_bianmin);
        //电子秤
        viewElectronicScale = lf.inflate(R.layout.xzhl0510_equipmentlist_dianzicheng, null);
        dianzicheng_list = (ListView) viewElectronicScale.findViewById(R.id.dianzicheng_list);
        nodataElectronicScale = (TextView) viewElectronicScale.findViewById(R.id.nodata_dianzicheng);
        //软件
        viewSoft = lf.inflate(R.layout.xzhl0510_equipmentlist_soft, null);
        soft_list = (ListView) viewSoft.findViewById(R.id.soft_list);
        nodataSoft = (TextView) viewSoft.findViewById(R.id.nodata_soft);
        // 把所有显示的view传递给ViewPage
        equiplist = new ArrayList<View>();
        equiplist.add(viewBusinessPos);
        equiplist.add(viewMmoneypos);
        equiplist.add(viewCamera);
        equiplist.add(viewPad);
        equiplist.add(viewNet);
        equiplist.add(viewTv);
        equiplist.add(viewElectronicScale);
        equiplist.add(viewSoft);
        equiplist.add(viewConveniencePeople);
        xzhl0510_EquipmentViewPage = new XZHL0510_EquipmentViewPage(equiplist,titleList);
        viewPagers.setAdapter(xzhl0510_EquipmentViewPage);
        // 设置POS为第一页面
        viewPagers.setCurrentItem(0);
    }
    //控件的点击事件
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        //返回键
        case R.id.back:
            finish();
            break;
        //刷新
        case R.id.shuxinList:
            if (!Commonutil.isNetworkAvailable(this)) {
                long time = System.currentTimeMillis();
                long timeD = time - lastClickTime;
                if (0 < timeD && timeD < 2800) {
                } else {
                    viewPagers.setVisibility(View.VISIBLE);
                    mypro.setVisibility(View.GONE);
                }
                lastClickTime = time;
            } else {
                //防止重复点击
                long time = System.currentTimeMillis();
                long timeD = time - lastClickTime;
                if (0 < timeD && timeD < 2800) {
                } else {
                    if(updataData(compareOldList, compareNewList)){
                        MessageUtil.commonToast(XZHL0510_EquitmentListActivity.this,
                                MessageUtil.getMessage(getApplicationContext(),Msg.I017, ""), Toast.LENGTH_SHORT);
                    }else{
                    viewPagers.setVisibility(View.GONE);
                    mypro.setVisibility(View.VISIBLE);
                    businessPos.clear();
                    camera.clear();
                    pad.clear();
                    moneyPos.clear();
                    netEquipment.clear();
                    tv.clear();
                    dianzicheng.clear();
                    soft.clear();
                    bianminzhongduan.clear();
                    PostNewloginNameAsyncTask dd = new PostNewloginNameAsyncTask();
                    dd.execute();
                    ii();
                    xzhl0510_EquitmentPosAdapter.notifyDataSetChanged();
                    xzhl0510_EquitmentCameraAdapter.notifyDataSetChanged();
                    xzhl0510_EquitmentPadAdapter.notifyDataSetChanged();
                    }
                }
                lastClickTime = time;
            }
            break;
        default:
            break;
        }
    }

    // 报修、查看历史记录的点击事件
    @Override
    public void onClick(View item, View widget, int position, int which) {
        XZHL0510_DeviceArray dsb = new XZHL0510_DeviceArray();
        Intent intent = new Intent(this, XZHL0530_Activity.class);
        Intent his = new Intent(this, XZHL0520_RecordListActivity.class);
        switch (which) {
        // Pos机-判断报修一列的显示文字
        // 如果tt.getText()的值是【报修】就可点击，跳转到下一页面
        case R.id.stateRepair:
            TextView tt = (TextView) item.findViewById(R.id.stateRepair);
            if (tt.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = businessPos.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // Pos机-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery:
            TextView ttt = (TextView) item.findViewById(R.id.sequence);
            
//            XZHL0510_DeviceArray dsb_his1 = new XZHL0510_DeviceArray();
            dsb = businessPos.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt.getText());
            startActivity(his);
            break;
        // 摄像机-判断报修一列的显示文字
        // 如果tt.getText()的值是【报修】就可点击，跳转到下一页面
        case R.id.stateRepair2:
            TextView tt2 = (TextView) item.findViewById(R.id.stateRepair2);
            if (tt2.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt2.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = camera.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 摄像机-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery2:
            TextView ttt2 = (TextView) item.findViewById(R.id.sequence2);
            dsb = camera.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt2.getText());
            startActivity(his);
            break;
        // Pad-判断报修一列的显示文字
        // 如果tt.getText()的值是【报修】就可点击，跳转到下一页面
        case R.id.stateRepair3:
            System.out.println(3);
            TextView tt3 = (TextView) item.findViewById(R.id.stateRepair3);
            if (tt3.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt3.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = pad.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // Pad-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery3:
            TextView ttt3 = (TextView) item.findViewById(R.id.sequence3);
            dsb = pad.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt3.getText());
            startActivity(his);
            break;
        //TODO 其他的显示，依据靠上面
        //金融pos
        case R.id.stateRepair4:
            TextView tt4 = (TextView) item.findViewById(R.id.stateRepair4);
            if (tt4.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt4.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = moneyPos.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 金融pos-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery4:
            TextView ttt4 = (TextView) item.findViewById(R.id.sequence4);
            dsb = moneyPos.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt4.getText());
            startActivity(his);
            break;
        // 网络设备
        case R.id.stateRepair5:
            TextView tt5 = (TextView) item.findViewById(R.id.stateRepair5);
            if (tt5.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt5.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = netEquipment.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 网络设备-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery5:
            TextView ttt5 = (TextView) item.findViewById(R.id.sequence5);
            dsb = netEquipment.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt5.getText());
            startActivity(his);
            break;
        // 电视
        case R.id.stateRepair6:
            TextView tt6 = (TextView) item.findViewById(R.id.stateRepair6);
            if (tt6.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt6.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = tv.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 电视-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery6:
            TextView ttt6 = (TextView) item.findViewById(R.id.sequence6);
            dsb = tv.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt6.getText());
            startActivity(his);
            break;
        // 电子秤
        case R.id.stateRepair7:
            TextView tt7 = (TextView) item.findViewById(R.id.stateRepair7);
            if (tt7.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt7.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = dianzicheng.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 电子秤-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery7:
            TextView ttt7 = (TextView) item.findViewById(R.id.sequence7);
            dsb = dianzicheng.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt7.getText());
            startActivity(his);
            break;
        // 软件
        case R.id.stateRepair8:
            TextView tt8 = (TextView) item.findViewById(R.id.stateRepair8);
            if (tt8.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt8.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = soft.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 软件-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery8:
            TextView ttt8 = (TextView) item.findViewById(R.id.sequence8);
            dsb = soft.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt8.getText());
            startActivity(his);
            break;
        // 便民终端
        case R.id.stateRepair9:
            TextView tt9 = (TextView) item.findViewById(R.id.stateRepair9);
            if (tt9.getText().equals(
                    getResources().getString(R.string.weixiuzhong))) {
                // 显示的信息
            } else if (tt9.getText().equals(
                    getResources().getString(R.string.stopuse))) {
                // 显示的信息
            } else {
                dsb = bianminzhongduan.get(position);
                intent.putExtra("equipbaoxiu", dsb);
                intent.setFlags(2);
                startActivity(intent);
            }
            break;
        // 便民终端-查报修历史查询一列,跳转到下一页面
        case R.id.historyquery9:
            TextView ttt9 = (TextView) item.findViewById(R.id.sequence9);
            dsb = bianminzhongduan.get(position);
            his.putExtra("equiplist", dsb);
            his.putExtra("hisNum", ttt9.getText());
            startActivity(his);
            break;
        default:
            break;
        }
    }
    //判断是否有新数据
    private boolean updataData(List<XZHL0510_DeviceArray> data1,List<XZHL0510_DeviceArray> data2){
        boolean result = false;
        int temp = 0;
        if(data1==null){
            
        }else{
        if(data1.size()==data2.size()){
            for(int i=0;i<data1.size();i++){
                for(int j=0;j<data2.size();j++){
                    if(data1.get(i).getDate().equals(data2.get(j).getDate())){
                        temp++;
                    }
                }
            }
            if(data1.size()*data1.size()==temp){
                result=true;
            }
        }else{
            result=false;
        }
        }
        return result;
    }
    //重新得到数据
    @Override
    protected void onRestart() {
        viewPagers.setVisibility(View.GONE);
        mypro.setVisibility(View.VISIBLE);
        if (!Commonutil.isNetworkAvailable(this)) {
        } else {
            businessPos.clear();
            camera.clear();
            pad.clear();
            moneyPos.clear();
            netEquipment.clear();
            tv.clear();
            dianzicheng.clear();
            soft.clear();
            bianminzhongduan.clear();
            PostNewloginNameAsyncTask dd = new PostNewloginNameAsyncTask();
            dd.execute();
            ii();
        }
        super.onRestart();
    }
}
