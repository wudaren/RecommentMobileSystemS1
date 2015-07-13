/*  XZHL0840_ShopModifyActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                   */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL0840                                                                                           */
/*  画面名     ：商品修改                                                                                             */
/*  实现功能 ：对商品信息修改。                                                                                       */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/23   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0840;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0210.XZHL0210_Constants;
import com.fbse.recommentmobilesystem.XZHL0810.XZHL0810_GoodsItemBean;
import com.fbse.recommentmobilesystem.XZHL1210.XZHL1210_Constants;
import com.fbse.recommentmobilesystem.XZHL1210.XZHL1210_Utils;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 * 商品信息修改后台类
 *
 * 完成商品信息修改功能
 */
public class XZHL0840_ShopModifyActivity extends Activity {

    // 商品供应商下拉框
    private Spinner spProvider;

    // 保存修改按钮
    private Button btnSubmit;

    // 返回按钮
    private Button btnBack;

    // 商品名称输入框
    private EditText etGoodsName;

    // 商品类别输入框
    private Spinner spGoodsCategory;

    // 商品货号输入框
    private EditText etGoodsNumber;

    // 商品库存输入框
    private EditText etCount;

    // 商品市场价格输入框
    private EditText etPrice;

    // 商品实际销售价格输入框
    private EditText etSalePrice;

    // 保存上一个画面传递过来的商品信息的实体类
    private XZHL0810_GoodsItemBean bean;

    // 商品提供商判断字符串
    private String suProdiver;

    // 商品种类判断字符串
    private String suType;

    // 画面初始化焦点的位置控件
    private TextView tvFoucs;

    // 管理画面用户输入信息
    private List<EditText> etList;

    // 管理从上一个画面传递过来的信息
    private List<String> strList;

    // 匹配从上一个画面传递过来的信息
    private List<String> strMatchList;

    DecimalFormat df;
    
    String strOne[];
    String strTwo[];
    String etPriceStr;
    String etSalePriceStr;
    
//    String globaltemp1;
//    String globaltemp2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Log开始
        LogUtil.logStart();

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0840_shopmodify);

        // 初始化上一个画面传递过来的数据
        initData();

        // 初始化布局视图控件
        setupView();

        // 给各个控件附上数据
        initView();

        // 执行异步任务加载下拉选框的数据
        new AsyGetCategory(this).execute("");

        // Log结束
        LogUtil.logEnd();
        
        new Thread() {
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etPriceStr=etPrice.getText()+"";
                           // if(!etPriceStr.equals(globaltemp1)){
                                
                            etPriceStr=etPriceStr.replaceAll(",", "");
                            if(isDecimal(etPriceStr)){
                            	strOne[0]=etPriceStr.substring(0, etPriceStr.indexOf('.'));
                                strOne[1]=etPriceStr.substring(etPriceStr.indexOf('.')+1,etPriceStr.length());
                                
                                String temp="";
                                int count=0;
                                for(int i=strOne[0].length()-1;i>=0;i--){
                                    temp+=(strOne[0].charAt(i));
                                    count++;
                                    if(count%3==0 && count<strOne[0].length()){
                                        temp+=",";
                                    }
                                }
                                String temp2="";
                                for(int i=temp.length()-1;i>=0;i--){
                                	temp2+=temp.charAt(i);
                                }
                                String temp1="";
                                if(strOne[1].length()>2){
                                    if(Integer.parseInt(strOne[1].charAt(2)+"")>=5){
                                        temp1+=(strOne[1].charAt(0));
                                        temp1+=(Integer.parseInt(strOne[1].charAt(1)+"")+1);
                                    }else{
                                        temp1+=strOne[1].charAt(0);
                                        temp1+=strOne[1].charAt(1);
                                    }
                                }else{
                                    for(int i=0;i<strOne[1].length();i++){
                                        temp1+=strOne[1].charAt(i);
                                    }
                                }
                                String format=temp2+"."+temp1;
                                int position=etPrice.getSelectionStart();
                                int a=0;
                                int b=0;
                                for(int i=0;i<etPrice.getText().length();i++){
                                	if(etPrice.getText().charAt(i)==','){
                                		a++;
                                	}
                                }
                                for(int i=0;i<format.length();i++){
                                	if(format.charAt(i)==','){
                                		b++;
                                	}
                                }
                                etPrice.setText(format);
                                //globaltemp1=format;
                                if(position>format.length()){
                                	etPrice.setSelection(position-1);
                                }else if(b>a){
                                	etPrice.setSelection(position+(b-a));
                                }else{
                                	etPrice.setSelection(position);
                                }
                            }else{
                            	String temp="";
                                int count=0;
                                for(int i=etPriceStr.length()-1;i>=0;i--){
                                    temp+=(etPriceStr.charAt(i));
                                    count++;
                                    if(count%3==0 && count<etPriceStr.length()){
                                        temp+=",";
                                    }
                                }
                                String temp2="";
                                for(int i=temp.length()-1;i>=0;i--){
                                	temp2+=temp.charAt(i);
                                }
                                
                                int position=etPrice.getSelectionStart();
                                int a=0;
                                int b=0;
                                for(int i=0;i<etPrice.getText().length();i++){
                                	if(etPrice.getText().charAt(i)==','){
                                		a++;
                                	}
                                }
                                for(int i=0;i<temp2.length();i++){
                                	if(temp2.charAt(i)==','){
                                		b++;
                                	}
                                }
                                
                                etPrice.setText(temp2);
                                //globaltemp1=temp2;
                                if(position>temp2.length()){
                                	etPrice.setSelection(position-1);
                                }else if(b>a){
                                	etPrice.setSelection(position+(b-a));
                                }else{
                                	etPrice.setSelection(position);
                                }
                                
                            }
//                            }else{
//                                Log.i("0603", "uuuuuu");
//                            }
                            
                            
                            etSalePriceStr=etSalePrice.getText()+"";
                            //if(!etSalePriceStr.equals(globaltemp2)){
                                
                            etSalePriceStr=etSalePriceStr.replaceAll(",", "");
                            if(isDecimal(etSalePriceStr)){
                            	strTwo[0]=etSalePriceStr.substring(0, etSalePriceStr.indexOf('.'));
                                strTwo[1]=etSalePriceStr.substring(etSalePriceStr.indexOf('.')+1,etSalePriceStr.length());
                                
                                String temp="";
                                int count=0;
                                for(int i=strTwo[0].length()-1;i>=0;i--){
                                    temp+=(strTwo[0].charAt(i));
                                    count++;
                                    if(count%3==0 && count<strTwo[0].length()){
                                        temp+=",";
                                    }
                                }
                                String temp2="";
                                for(int i=temp.length()-1;i>=0;i--){
                                	temp2+=temp.charAt(i);
                                }
                                String temp1="";
                                if(strTwo[1].length()>2){
                                    if(Integer.parseInt(strTwo[1].charAt(2)+"")>=5){
                                        temp1+=(strTwo[1].charAt(0));
                                        temp1+=(Integer.parseInt(strTwo[1].charAt(1)+"")+1);
                                    }else{
                                        temp1+=strTwo[1].charAt(0);
                                        temp1+=strTwo[1].charAt(1);
                                    }
                                }else{
                                    for(int i=0;i<strTwo[1].length();i++){
                                        temp1+=strTwo[1].charAt(i);
                                    }
                                }
                                String format=temp2+"."+temp1;
                                int position=etSalePrice.getSelectionStart();
                                int a=0;
                                int b=0;
                                for(int i=0;i<etSalePrice.getText().length();i++){
                                	if(etSalePrice.getText().charAt(i)==','){
                                		a++;
                                	}
                                }
                                for(int i=0;i<format.length();i++){
                                	if(format.charAt(i)==','){
                                		b++;
                                	}
                                }
                                etSalePrice.setText(format);
                                //globaltemp2=format;
                                if(position>format.length()){
                                	etSalePrice.setSelection(position-1);
                                }else if(b>a){
                                	etSalePrice.setSelection(position+(b-a));
                                }else{
                                	etSalePrice.setSelection(position);
                                }
                            }else{
                            	String temp="";
                                int count=0;
                                for(int i=etSalePriceStr.length()-1;i>=0;i--){
                                    temp+=(etSalePriceStr.charAt(i));
                                    count++;
                                    if(count%3==0 && count<etSalePriceStr.length()){
                                        temp+=",";
                                    }
                                }
                                String temp2="";
                                for(int i=temp.length()-1;i>=0;i--){
                                	temp2+=temp.charAt(i);
                                }
                                
                                int position=etSalePrice.getSelectionStart();
                                int a=0;
                                int b=0;
                                for(int i=0;i<etSalePrice.getText().length();i++){
                                	if(etSalePrice.getText().charAt(i)==','){
                                		a++;
                                	}
                                }
                                for(int i=0;i<temp2.length();i++){
                                	if(temp2.charAt(i)==','){
                                		b++;
                                	}
                                }
                                
                                etSalePrice.setText(temp2);
                               // globaltemp2=temp2;
                                if(position>temp2.length()){
                                	etSalePrice.setSelection(position-1);
                                }else if(b>a){
                                	etSalePrice.setSelection(position+(b-a));
                                }else{
                                	etSalePrice.setSelection(position);
                                }
                                
                            }
//                            }else{
//                                Log.i("0603", "uuuuu");
//                            }
                           
                        }

                        
                    });
                }
            };
        }.start();
    }

    private boolean isDecimal(String etPriceStr) {
        // TODO Auto-generated method stub
        for(int i=0;i<etPriceStr.length();i++){
            if(etPriceStr.charAt(i)=='.'){
                return true;
            }
        }
        return false;
    }
    /**
     * 给各个控件初始化数据
     */
    private void initView() {

        // Log开始
        LogUtil.logStart();
        tvFoucs.setFocusable(true);
        etGoodsName.setText(bean.getGoodsName());
        etGoodsNumber.setText(bean.getGoodsNumber());
        etCount.setText(bean.getStocks());
        etPrice.setText(FormatString(bean.getPrice()));
        etSalePrice.setText(FormatString(bean.getRealityPrice()));
        suProdiver = bean.getSupplier();
        suType = bean.getGoodsType();
        strList.add(bean.getGoodsName());
        strList.add(bean.getGoodsType());
        strList.add(bean.getGoodsNumber());
        strList.add(bean.getStocks());
        strList.add(bean.getSupplier());
        strList.add(bean.getPrice());
        strList.add(bean.getRealityPrice());

        // Log结束
        LogUtil.logEnd();

    }

    private CharSequence FormatString(String price) {
        // TODO Auto-generated method stub
        price=price.replaceAll(",", "");
        double data=0;
        if(isDecimal(price)){
            strOne[0]=price.substring(0, price.indexOf('.'));
            strOne[1]=price.substring(price.indexOf('.')+1,price.length());
            for(int i=0;i<strOne[0].length();i++){
                data=data+Integer.parseInt(strOne[0].charAt(i)+"")*Math.pow(10, strOne[0].length()-1-i);
            }
            for(int j=0;j<strOne[1].length();j++){
                data=data+Integer.parseInt(strOne[1].charAt(j)+"")*Math.pow(10, -(j+1));
            }
            String str=df.format(data);
            return str;
           
        }else{
            for(int i=0;i<price.length();i++){
                data=data+Integer.parseInt(price.charAt(i)+"")*Math.pow(10, price.length()-1-i);
            }
            String str=df.format(data);
            return str;
            
        }
        
    }

    /**
     * 设置商品供应商和商品类别的选项状态
     * @param list 商品品类下拉选
     * @param listPro 商品供应商下拉选
     */
    private void selectSpinner(List<String> list, List<String> listPro) {

        // Log开始
        LogUtil.logStart();
        if(list==null || list.size()==0 || listPro==null || listPro.size()==0){
            MessageUtil.commonToast(this, MessageUtil.getMessage(this, Msg.E0002), Toast.LENGTH_SHORT);

            // Log结束
            LogUtil.logEnd();
            return;
        }
        for(int i=0; i<listPro.size(); i++){
            if(listPro.get(i).equals(suProdiver)){
                spProvider.setSelection(i);
                break;
            }else{
                spProvider.setSelection(listPro.size()-1);
            }
        }
        for(int i=0; i<list.size(); i++){
            if(list.get(i).equals(suType)){
                spGoodsCategory.setSelection(i);
                break;
            }else{
                spGoodsCategory.setSelection(list.size()-1);
            }
        }

        // 给各个控件加上监听器
        addListener();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 取出上一个画面传递过来的数据
     */
    private void initData() {

        // Log开始
        LogUtil.logStart();
        Intent intent = getIntent();
        bean = (XZHL0810_GoodsItemBean) intent.getSerializableExtra(CommonConst.GOODINFO);
        etList = new ArrayList<EditText>();
        strList = new ArrayList<String>();
        strMatchList = new ArrayList<String>();
        df = new DecimalFormat(",###.00");
        strOne=new String[2];
        strTwo=new String[2];
        etPriceStr="";
        etSalePriceStr="";
        // Log结束
        LogUtil.logEnd();
    }

    // 给手机返回键注册监听器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Log开始
        LogUtil.logStart();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            strMatchList.clear();
            int a = 0;
            for (int i = 0; i < strList.size(); i++) {
                if (i == 1) {
                    strMatchList
                            .add(((TextView) spGoodsCategory.getSelectedView().findViewById(android.R.id.text1))
                                    .getText().toString());
                } else if (i == 4) {
                    strMatchList.add(((TextView) spProvider.getSelectedView().findViewById(android.R.id.text1))
                            .getText().toString());
                } else {
                    strMatchList.add(etList.get(a++).getText().toString().trim());
                }
            }
            for (int i = 0; i < strList.size(); i++) {
                if (!strList.get(i).equals(strMatchList.get(i))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(XZHL0840_ShopModifyActivity.this);
                    builder.setTitle(MessageUtil.getMessage(XZHL0840_ShopModifyActivity.this, Msg.Q013))
                            .setCancelable(true)
                            .setPositiveButton(CommonConst.SURE, new DialogInterface.OnClickListener() {

                                // 点击确认按钮触发的事件
                                public void onClick(DialogInterface dialog, int id) {

                                    XZHL0840_ShopModifyActivity.this.finish();
                                }
                            }).setNegativeButton(CommonConst.CANCLE, new DialogInterface.OnClickListener() {

                                // 点击取消按钮触发的事件
                                public void onClick(DialogInterface dialogIn, int id) {
                                }
                            });
                    builder.create().show();

                    // Log结束
                    LogUtil.logEnd();
                    return super.onKeyDown(keyCode, event);
                }
            }
            XZHL0840_ShopModifyActivity.this.finish();
        }

        // Log结束
        LogUtil.logEnd();
        return super.onKeyDown(keyCode, event);
    }
    /**
     * 给返回按钮和提交按钮添加监听器事件
     */
    private void addListener() {

        // Log开始
        LogUtil.logStart();

        // 返回按钮的监听事件
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();
                strMatchList.clear();
                int a = 0;
                for (int i = 0; i < strList.size(); i++) {
                    if (i == 1) {
                        strMatchList
                                .add(((TextView) spGoodsCategory.getSelectedView().findViewById(android.R.id.text1))
                                        .getText().toString());
                    } else if (i == 4) {
                        strMatchList.add(((TextView) spProvider.getSelectedView().findViewById(android.R.id.text1))
                                .getText().toString());
                    } else {
                        strMatchList.add(etList.get(a++).getText().toString().trim());
                    }
                }
                for (int i = 0; i < strList.size(); i++) {
                    if (!strList.get(i).equals(strMatchList.get(i))) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(XZHL0840_ShopModifyActivity.this);
                        builder.setTitle(MessageUtil.getMessage(XZHL0840_ShopModifyActivity.this, Msg.Q013))
                                .setCancelable(true)
                                .setPositiveButton(CommonConst.SURE, new DialogInterface.OnClickListener() {

                                    // 点击确认按钮触发的事件
                                    public void onClick(DialogInterface dialog, int id) {

                                        XZHL0840_ShopModifyActivity.this.finish();
                                    }
                                }).setNegativeButton(CommonConst.CANCLE, new DialogInterface.OnClickListener() {

                                    // 点击取消按钮触发的事件
                                    public void onClick(DialogInterface dialogIn, int id) {
                                    }
                                });
                        builder.create().show();

                        // Log结束
                        LogUtil.logEnd();
                        return;
                    }
                }
                XZHL0840_ShopModifyActivity.this.finish();

                // Log结束
                LogUtil.logEnd();
            }
        });

        // 提交按钮的事件
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // Log开始
                LogUtil.logStart();

                // 提交更改的商品信息
                submitInfo();

                // Log结束
                LogUtil.logEnd();
            }
        });

        // 给各个文本输入框添加最大输入长度限制
        for (int i = 0; i < etList.size(); i++) {
            etList.get(i).addTextChangedListener(new EditTextListener(etList.get(i)));
        }

        // Log结束
        LogUtil.logEnd();
    }
   
    
    /**
     * 文本输入框最大输入长度限制类
     */
    class EditTextListener implements android.text.TextWatcher {
        CharSequence temp;
        EditText et;

        public EditTextListener(EditText et) {
            this.et = et;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            // Log开始
            LogUtil.logStart();
            temp = s;

            // Log结束
            LogUtil.logEnd();
        }

        @Override
        public void afterTextChanged(Editable s) {

            // Log开始
            LogUtil.logStart();
            if (temp.length() > 50) {
                MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(), Msg.E0026), Toast.LENGTH_SHORT);
                int length = et.getText().length();
                s.delete(50, length);
                et.setText(s);
                et.setSelection(et.getText().length());
            }
            
            // Log结束
            LogUtil.logEnd();
        }

    }

    /**
     * 提交更改的商品信息
     */
    @SuppressWarnings("unchecked")
    protected void submitInfo() {

        // Log开始
        LogUtil.logStart();

        // 如果修改页面上至少有一个文本输入框是空则不允许提交修改
        for (int i = 0; i < etList.size(); i++) {
            String str = etList.get(i).getText().toString().trim();
            if (CommonConst.DEFAULT.equals(str)) {
                MessageUtil.commonToast(
                        this,
                        MessageUtil.getMessage(this, Msg.E0016,
                                new String[] { this.getResources().getStringArray(R.array.names)[i] }),
                        Toast.LENGTH_SHORT);

                // Log结束
                LogUtil.logEnd();
                return;
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(MessageUtil.getMessage(this, Msg.Q011, new String[] { CommonConst.INFO })).setCancelable(true)
                .setPositiveButton(CommonConst.SURE, new DialogInterface.OnClickListener() {

                    // 点击确认按钮触发的事件
                    public void onClick(DialogInterface dialog, int id) {

                        // 开启异步任务修改商品信息
                        new AsyUpdateGoodsInfo(XZHL0840_ShopModifyActivity.this).execute(etList);
                    }
                }).setNegativeButton(CommonConst.CANCLE, new DialogInterface.OnClickListener() {

                    // 点击取消按钮触发的事件
                    public void onClick(DialogInterface dialogIn, int id) {
                    }
                });
        builder.create().show();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 初始化控件视图
     */
    private void setupView() {

        // Log开始
        LogUtil.logStart();
        tvFoucs = (TextView) findViewById(R.id.tv_shangpinmingcheng_0840);
        btnSubmit = (Button) findViewById(R.id.btn_tijiao_0840);
        btnBack = (Button) findViewById(R.id.btn_gotomain_0840);
        etGoodsName = (EditText) findViewById(R.id.et_shangpinmingcheng_0840);
        etList.add(etGoodsName);
        etGoodsNumber = (EditText) findViewById(R.id.et_shangpinhuohao_0840);
        etList.add(etGoodsNumber);
        etCount = (EditText) findViewById(R.id.et_kucun_0840);
        etList.add(etCount);
        etPrice = (EditText) findViewById(R.id.et_shichangjiage_0840);
        etList.add(etPrice);
        etSalePrice = (EditText) findViewById(R.id.et_shijixiaoshoujia_0840);
        etList.add(etSalePrice);
        spProvider = (Spinner) findViewById(R.id.spinner_gongyingshang_0840);
        spGoodsCategory = (Spinner) findViewById(R.id.spinner_shangpinpinlei_0840);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 初始化商品提供商下拉选的内容
     *
     * @param list 商品提供商的信息
     */
    public void getData(List<String> list) {

        // Log开始
        LogUtil.logStart();
        if(list==null||list.size()==0){
            MessageUtil.commonToast(this, MessageUtil.getMessage(this, Msg.E0002), Toast.LENGTH_SHORT);

            // Log结束
            LogUtil.logEnd();
            return;
        }
        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
        for(int i=0; i<list.size(); i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(CommonConst.GONGYING, list.get(i));
            listData.add(map);
        }

        // 商品供应商下拉选初始化适配器
        SimpleAdapter simpleAdapter = new SimpleAdapter(XZHL0840_ShopModifyActivity.this, listData,
                android.R.layout.simple_list_item_1, new String[] { CommonConst.GONGYING },
                new int[] { android.R.id.text1, });
        spProvider.setAdapter(simpleAdapter);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 初始化商品品类下拉选的内容
     *
     * @param list 商品品类的信息
     */
    public void getData2(List<String> list) {

        // Log开始
        LogUtil.logStart();
        if(list==null||list.size()==0){
            MessageUtil.commonToast(this, MessageUtil.getMessage(this, Msg.E0002), Toast.LENGTH_SHORT);

            // Log结束
            LogUtil.logEnd();
            return;
        }
        List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
        for(int i=0; i<list.size(); i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(CommonConst.TYPE, list.get(i));
            listData.add(map);
        }

        // 商品品类下拉选初始化适配器
        SimpleAdapter simpleAdapter2 = new SimpleAdapter(XZHL0840_ShopModifyActivity.this, listData,
                android.R.layout.simple_list_item_1, new String[] { CommonConst.TYPE },
                new int[] { android.R.id.text1, });
        spGoodsCategory.setAdapter(simpleAdapter2);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 异步获取商品品类和商品供应商的内容
     */
    class AsyGetCategory extends AsyncTask<String, String, String[]>{

        XZHL0840_ShopModifyActivity context;

        public AsyGetCategory(XZHL0840_ShopModifyActivity context){
            this.context=context;
        }
        @Override
        protected String[] doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();
            String[] resultData = new String[2];
            SharedPreferences sp = getSharedPreferences("data", 0);
            String token = sp.getString("ID", "");
            String[] keys=new String[]{"timestamp"};
            String[] values=new String[]{"0"};
            WebServiceOfHttps woh = new WebServiceOfHttps();
            String json = woh.WSservers(this.context, "category",
                            JsonUtil.DataToJson("a001",
                            JsonUtil.DataToJson(keys, values), token,
                            JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(keys, values), token)));
            resultData[0]=json;
            // 访问服务器修改商品信息
            String json2 = woh.WSservers(this.context, "supplier",
                            JsonUtil.DataToJson("a001",
                            JsonUtil.DataToJson(keys, values), token,
                            JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(keys, values), token)));
            resultData[1]=json2;

            // Log结束
            LogUtil.logEnd();
            return resultData;
        }

        @Override
        protected void onPostExecute(String[] result) {

            // Log开始
            LogUtil.logStart();
            super.onPostExecute(result);
            List<String> list=jsonParser(result[0]);
            List<String> listPro=jsonParserPro(result[1]);
            getData2(list);
            getData(listPro);

            // 设置商品供应商和商品类别的选项状态
            selectSpinner(list, listPro);

            // Log结束
            LogUtil.logEnd();
        }

    }

    /**
     * 商品信息修改后台类
     *
     * 完成商品信息修改功能
     */
    class AsyUpdateGoodsInfo extends AsyncTask<List<EditText>, String, String> {

        // XZHL0840_ShopModifyActivity的上下文
        XZHL0840_ShopModifyActivity context;

        public AsyUpdateGoodsInfo(XZHL0840_ShopModifyActivity context) {
            this.context = context;
        }

        /**
         * 后台任务的核心方法,发送修改后的信息到服务器执行修改操作
         * @param params 传递进来的若干参数
         * @return 返回服务器的响应数据
         */
        @Override
        protected String doInBackground(List<EditText>... params) {

            // Log开始
            LogUtil.logStart();
            String result = null;
            String serial = XZHL0210_Constants.SERIAL;
            String token = XZHL0210_Constants.TOKEN;

            // 拼接发送的请求数据
            String[] keys = this.context.getResources().getStringArray(R.array.keys);
            String[] values = new String[] {
                    bean.getId(),
                    params[0].get(0).getText().toString(),
                    ((TextView) spGoodsCategory.getSelectedView().findViewById(android.R.id.text1)).getText()
                            .toString(), params[0].get(1).getText().toString(), params[0].get(2).getText().toString(),
                    ((TextView) spProvider.getSelectedView().findViewById(android.R.id.text1)).getText().toString(),
                    params[0].get(3).getText().toString(), params[0].get(4).getText().toString() };
            String data = JsonUtil.DataToJson(keys, values);
            String json = JsonUtil.DataToJson(serial, data, token, JsonUtil.getSign(serial, data, token));
            String portName = CommonConst.UPDATE;

            // 访问服务器修改商品信息
            WebServiceOfHttps ws = new WebServiceOfHttps();
            result = ws.WSservers(context, portName, json);
            result = XZHL1210_Utils.jsonParser(result);

            // Log结束
            LogUtil.logEnd();
            return result;
        }

        /**
         * 根据服务器返回的数据决定对界面作何操作
         * @param result 服务器返回的数据
         */
        @Override
        protected void onPostExecute(String result) {

            // Log开始
            LogUtil.logStart();
            super.onPostExecute(result);

            // Log结束
            LogUtil.logEnd();

            // 更新界面
            this.context.updateView(result);
        }

    }

    /**
     * 根据服务器返回的数据决定对界面作何操作
     * @param result 服务器返回的数据
     */
    public void updateView(String result) {

        // Log开始
        LogUtil.logStart();
        // 判断服务器是否正常处理请求
        if (result == null || CommonConst.DEFAULT.equals(result)) {
            MessageUtil.commonToast(this, MessageUtil.getMessage(this, Msg.E0002), Toast.LENGTH_SHORT);

            // Log结束
            LogUtil.logEnd();
            return;
        }

        // 当返回的success值是1,就表示修改成功
        if (CommonConst.ONE.equals(result)) {
            MessageUtil.commonToast(this, MessageUtil.getMessage(this, Msg.I020, new String[] { CommonConst.INFO }),
                    Toast.LENGTH_SHORT);
            MyTask task = new MyTask();
            Timer t = new Timer(false);
            t.schedule(task, 800);
        }

        // 修改失败
        else {
            MessageUtil.commonToast(this, MessageUtil.getMessage(this, Msg.E0024, new String[] { CommonConst.INFO }),
                    Toast.LENGTH_SHORT);
        }

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 一秒后跳转页面的定时任务
     */
    class MyTask extends TimerTask {

        @Override
        public void run() {

            // Log开始
            LogUtil.logStart();
            XZHL0840_ShopModifyActivity.this.finish();

            // Log结束
            LogUtil.logEnd();
        }

    }

    /**
     * 根据json字符串解析返回结果
     * @param json 传入的待解析的json字符串
     * @return 解析json后的结果
     */
    public static List<String> jsonParser(String json) {

        // Log开始
        LogUtil.logStart();
        List<String> result = null;
        if (json == null) {

            // Log结束
            LogUtil.logEnd();
            return result;
        }
        try {
            JSONObject jb = new JSONObject(json);

            // 获取json中key为success的状态值
            String success = jb.getString(XZHL1210_Constants.SUCCESS);
            if(CommonConst.SUCCESSONE.equals(success)){
                result=new ArrayList<String>();
                JSONObject jb2 = new JSONObject(jb.getString("data"));
                JSONArray userArray = jb2.getJSONArray("category");
                for(int i=0; i<userArray.length(); i++){
                    JSONObject userStr = userArray.getJSONObject(i);
                    result.add(userStr.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

            // Log异常
            LogUtil.logException(e);
        }

        // Log结束
        LogUtil.logEnd();
        return result;
    }

    /**
     * 解析服务器返回的提供商数据
     * @param json 服务器返回的数据
     * @return 商品提供商数据
     */
    public List<String> jsonParserPro(String json) {

        // Log开始
        LogUtil.logStart();
        List<String> result = null;
        if (json == null) {

            // Log结束
            LogUtil.logEnd();
            return result;
        }
        try {
            JSONObject jb = new JSONObject(json);

            // 获取json中key为success的状态值
            String success = jb.getString(XZHL1210_Constants.SUCCESS);
            if(CommonConst.SUCCESSONE.equals(success)){
                result=new ArrayList<String>();
                JSONObject jb2 = new JSONObject(jb.getString("data"));
                JSONArray userArray = jb2.getJSONArray("supplier");
                for(int i=0; i<userArray.length(); i++){
                    JSONObject userStr = userArray.getJSONObject(i);
                    result.add(userStr.getString("name"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

            // Log异常
            LogUtil.logException(e);
        }

        // Log结束
        LogUtil.logEnd();
        return result;
    }
}
