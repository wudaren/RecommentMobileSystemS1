/*  XZHL0130_VipInfoActivity.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO., LTD 2012                      */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ ：XZHL0130                                                                                                    */
/*  画面名     ：会员注册                                                                                                     */
/*  实现功能 ：进行会员注册。                                                                                                     */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/14   V01L01      FBSE)张海静      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0130;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0110.XZHL0110_Utils;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.JsonUtil;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;
import com.fbse.recommentmobilesystem.common.WebServiceOfHttps;

/**
 *  会员注册画面类
 *
 *  完成会员注册
 */
public class XZHL0130_VipInfoActivity extends Activity implements OnClickListener {

    // TAG标签
    private static final String TAG="XZHL0130_VipInfoActivity";
    // 会员头像图片获取
    private Bitmap bitMap;

    // 会员头像图片
    private ImageView ivPhotoView;

    // 返回按钮
    private Button btnGoBack;

    // 提交按钮
    private Button btnSubmit;

    // 单按钮组
    private RadioGroup rgGroupSex;

    // 权限下拉选项
    private Spinner typeSpinner;

    // 用户信息 会员姓名
    private EditText etVipName;

    // 会员电话
    private EditText etMoblieNo;

    // 生日
    private TextView tvBirthday;

    // 生日旁边的日历
    private ImageView ivBirthday;

    // 地址
    private EditText etAddress;

    // 备注
    private EditText etRemarks;

    // 会员头像图片Url
    private String url;

    // 数组适配器
    private ArrayAdapter<String> adapter;

    // 共享信息
    private SharedPreferences sharedPerf;

    // 存放注册信息控件的值，会员姓名
    private String memberNameText;

    // 会员类型
    private String memberTypeText;

    // 手机号码
    private String memberPhoneText;

    // 性别
    private String memberSexText;

    // 生日
    private String memberBirthText;

    // 住址
    private String memberAddressText;

    // 备注
    private String memberRemarkText;

    // 判断返回或者提交按钮的声明
    private int temp;

    // 文件保存的文件夹
    private static final String FILEPATH = "FILE";

    // 数据常量, 0为返回按钮
    private static final int TEMPZERO = 0;

    // 1为提交按钮
    private static final int TEMPONE = 1;

    // 照相机、图片选择常量，照相机为2
    private static final int CAMERA = 2;

    // 图库为3
    private static final int PICTURE = 3;

    // 会员姓名的常量
    private static final int NAME = 4;

    // 手机号码的常量
    private static final int PHONE = 5;

    // 住址的常量
    private static final int ADDRESS = 6;

    // 备注的常量
    private static final int REMARK = 7;

    // 会员姓名的长度
    private static final int NAMELENGTH = 50;

    // 手机号码的长度
    private static final int PHONELENGTH = 50;

    // 住址的长度
    private static final int ADDRESSLENGTH = 50;

    // 备注的常量
    private static final int REMARKLENGTH = 50;

    // 空的常量
    private static final String KONG = "";

    // 共享Name
    private static final String DATA = "data";

    // 共享键值
    private static final String KEY = "ID";

    // url传值
    private static final String IMAGEURL = "imageurl";

    // 图库选择器
    private static final String IMAGE = "image/*";

    // 路径斜线
    private static final String LINE = "/";

    // 图片格式
    private static final String IMGPNG = ".png";

    // 获取一个日历对象
    Calendar date = Calendar.getInstance(Locale.CHINA);

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
        sharedPerf = this.getSharedPreferences(DATA,  Context.MODE_PRIVATE);

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xzhl0130_vipinfo);

        // 引用初始化界面组件方法
        initView();

        // 引用初始化用户头像方法
        initImage();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 初始化用户头像
     */
    private void initImage() {

        // log打印开始
        LogUtil.logStart();

        // 从前页面得url值
        Intent intent = getIntent();
        url = intent.getStringExtra(IMAGEURL);

        // 判断url是否有值, 有值代表是从【导购】画面进行的注册
        if (url != null) {
            ImageTask imageTask = new ImageTask();
            imageTask.execute();
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 初始化界面组件
     */
    private void initView() {

        // log打印开始
        LogUtil.logStart();

        // 会员图片
        ivPhotoView = (ImageView) findViewById(R.id.iv_vippicture_xzhl0130);

        // 会员姓名
        etVipName = (EditText) findViewById(R.id.et_vipname_xzhl0130);

        // 手机号码
        etMoblieNo = (EditText) findViewById(R.id.et_vipmoblie_xzhl0130);

        // 生日
        tvBirthday = (TextView) findViewById(R.id.tv_vipbirthday_xzhl0130);

        // 小日历
        ivBirthday=(ImageView) findViewById(R.id.iv_vipbirthday_xzhl0130);

        // 住址
        etAddress = (EditText) findViewById(R.id.et_vipaddress_xzhl0130);

        // 备注
        etRemarks = (EditText) findViewById(R.id.et_remarks_xzhl0130);

        // 返回
        btnGoBack = (Button) findViewById(R.id.btn_gotoback_xzhl0130);

        // 提交
        btnSubmit = (Button) findViewById(R.id.btn_submitvipinfo_xzhl0130);

        // 会员类型
        typeSpinner = (Spinner) findViewById(R.id.sp_viptypespinner_xzhl0130);

        // 性别单选按钮组
        rgGroupSex = (RadioGroup) findViewById(R.id.rd_radiogroupsex_xzhl0130);

        // 会员图片、返回、提交按钮的点击监听
        ivPhotoView.setOnClickListener(this);

        // 生日的点击监听
        tvBirthday.setOnClickListener(this);

        // 小日历点击
        ivBirthday.setOnClickListener(this);

        // 返回按钮的点击监听
        btnGoBack.setOnClickListener(this);

        // 提交按钮的点击监听
        btnSubmit.setOnClickListener(this);

        // 从资源文件中获取会员类型的数组
        String[] vipInfoMemberType = getResources().getStringArray(R.array.vipinfo_membertype);

        // Spinner适配器
        adapter = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item,  vipInfoMemberType);

        // 设置下拉列表
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 将typetypeSpinner 添加到typeSpinner
        typeSpinner.setAdapter(adapter);

        // 设置默认
        typeSpinner.setVisibility(View.VISIBLE);

        // 调用文本长度监听方法
        textLength();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 输入框文本长度监听
     */
    private void textLength(){

        // log打印开始
        LogUtil.logStart();

        // 会员姓名长度监听
        etVipName.addTextChangedListener(new TextWatcherListenner(NAME));

        // 手机号码长度监听
        etMoblieNo.addTextChangedListener(new TextWatcherListenner(PHONE));

        // 住址长度监听
        etAddress.addTextChangedListener(new TextWatcherListenner(ADDRESS));

        // 备注长度监听
        etRemarks.addTextChangedListener(new TextWatcherListenner(REMARK));

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 从导购页面进行注册显示照片的异步类
     *
     * 完成从本地获取的缓存图片显示
     */
    private class ImageTask extends AsyncTask<String,  Integer,  Integer> {

        /**
         * 后台取数据
         * @param params 参数
         * @return null
         */
        @Override
        protected Integer doInBackground(String... params) {

            // log打印开始
            LogUtil.logStart();

            // url转换为Bitmap
            bitMap = XZHL0110_Utils.loadBitmap(url);

            // log打印结束
            LogUtil.logEnd();
            return null;
        }

        /**
         * 从doInBackground方法中得到值作相应判断
         * @param result 返回值
         */
        @Override
        protected void onPostExecute(Integer result) {

            // log打印开始
            LogUtil.logStart();

            // 设置图片显示
            ivPhotoView.setImageBitmap(bitMap);

            // log打印结束
            LogUtil.logEnd();
        }
    }

    /**
     * 每个控件的点击事件会在这里汇总
     * @param arg0 获得点击的控件
     */
    @Override
    public void onClick(View arg0) {

        // log打印开始
        LogUtil.logStart();
        switch (arg0.getId()) {

        // 返回按钮
        case R.id.btn_gotoback_xzhl0130:

            // 根据输入框是否有值作出判断
            dataDialog();
            break;

        // 提交按钮
        case R.id.btn_submitvipinfo_xzhl0130:

            // 获取文本框信息方法
            getDefaultData();

            // 判断文本框是否为空
            if (KONG.equals(memberNameText) || KONG.equals(memberPhoneText)) {

                // 会员姓名为空
                if (KONG.equals(memberNameText)) {
                    MessageUtil.commonToast(getApplicationContext(),  MessageUtil.getMessage(this,  Msg.E0016,
                                    new String[] { CommonConst.MEMBERNAME }),  Toast.LENGTH_SHORT);
                }else

                    // 手机号码为空
                    if (KONG.equals(memberPhoneText)) {
                        MessageUtil.commonToast(getApplicationContext(), MessageUtil.getMessage(this,  Msg.E0016,
                                        new String[] { CommonConst.MEMBERPHONE }), Toast.LENGTH_SHORT);
                    }
            } else {

                // 会员姓名超出长度
                if(memberNameText.length()>NAMELENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        CommonConst.MEMBERNAME+MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,
                            Msg.E0026), Toast.LENGTH_SHORT);

                 // 手机号码名超出长度
                }else if(memberPhoneText.length()>PHONELENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        CommonConst.MEMBERPHONE+MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,
                            Msg.E0026), Toast.LENGTH_SHORT);

                 // 住址超出长度
                }else if(memberAddressText.length()>ADDRESSLENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        CommonConst.MEMBERADDRESS+MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,
                            Msg.E0026), Toast.LENGTH_SHORT);

                 // 备注超出长度
                }else if(memberRemarkText.length()>REMARKLENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        CommonConst.MEMBERREMARK+MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,
                            Msg.E0026), Toast.LENGTH_SHORT);
                }else{

                    // 弹出提交对话框
                    registerMember(MessageUtil.getMessage(this,  Msg.Q010, new String[] { CommonConst.MEMBER }));
                    // 设置临时值为0
                    temp = TEMPONE;
                }
            }
            break;

        // 图片点击
        case R.id.iv_vippicture_xzhl0130:

            // 调用图片选择方法
            selectPicture();
            break;

        // 生日点击
        case R.id.tv_vipbirthday_xzhl0130:

            // 弹出日历
            new DatePickerDialog(this,  beginData,  date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),  1).show();
            break;

        // 小日历点击
        case R.id.iv_vipbirthday_xzhl0130:

            // 弹出日历
            new DatePickerDialog(this,  beginData,  date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),  1).show();
            break;
        default:
            break;
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 输入框数据判断
     */
    private void dataDialog() {

        // log打印开始
        LogUtil.logStart();

        // 获取文本框信息方法
        getDefaultData();
        if (KONG.equals(memberNameText) && KONG.equals(memberPhoneText)&& KONG.equals(memberBirthText)
                && KONG.equals(memberAddressText)) {

            // 关闭本画面
            finish();
        } else {

            // 弹出取消信息的对话框
            registerMember(MessageUtil.getMessage(this,  Msg.Q013));

            // 设置临时值为0
            temp = TEMPZERO;
        }

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 当点击DatePickerDialog控件的设置按钮时设置开始日期，调用该方法
     */
    DatePickerDialog.OnDateSetListener beginData = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view,  int year,  int monthOfYear,
                int dayOfMonth) {

            // log打印开始
            LogUtil.logStart();

            // 这里的year, monthOfYear, dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            date.set(Calendar.YEAR,  year);
            date.set(Calendar.MONTH,  monthOfYear);
            date.set(Calendar.DAY_OF_MONTH,  dayOfMonth);

            // 将页面TextView的显示更新为最新时间
            updateMonthBegin();

            // log打印结束
            LogUtil.logEnd();
        }
    };

     /**
      * 更新开始日期的TextView
      */
    private void updateMonthBegin() {

        // log打印开始
        LogUtil.logStart();

        // 日期格式化
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String str = temp.format(date.getTime());

        // 将日期赋值
        tvBirthday.setText(str);

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 得到界面显示的文本框的值
     */
    private void getDefaultData() {

        // log打印开始
        LogUtil.logStart();

        // 得到会员姓名的值
        memberNameText = etVipName.getText().toString().trim();

        // 得到会员类型的值
        String[] vipInfoMemberType = getResources().getStringArray(R.array.vipinfo_membertype);
        for (int i = 0; i < vipInfoMemberType.length; i++) {
            if(vipInfoMemberType[i].equals(typeSpinner.getSelectedItem().toString())){
                memberTypeText=i+KONG;
            }
        }

        // 得到手机号码
        memberPhoneText = etMoblieNo.getText().toString();

        // 得到单选按钮的ID值
        if(rgGroupSex.getCheckedRadioButtonId()==R.id.rd_radiobuttonmale_xzhl0130){

            // 0
            memberSexText=TEMPZERO+KONG;
        }else{
            memberSexText=TEMPONE+KONG;
        }

        // 得到生日的值
        memberBirthText = tvBirthday.getText().toString();

        // 得到住址的值
        memberAddressText = etAddress.getText().toString();

        // 得到备注的值
        memberRemarkText = etRemarks.getText().toString();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 注册会员的异步类
     *
     * 向服务器提交数据，完成会员的注册
     */
    private class RegisterMemberAsyncTask extends AsyncTask<String,  Integer,  String> {

        /**
         * 后台取数据
         * @param params 参数
         * @return info
         */
        @Override
        protected String doInBackground(String... params) {

            // log打印开始
            LogUtil.logStart();

            // 提交数据, 封装数据
            String portName = CommonConst.MEMBERADD;
            String serial = CommonConst.SERIALNUM;
            String[] key=XZHL0130_VipInfoActivity.this.getResources().getStringArray(R.array.memberadd);
            String[] value = { "", memberNameText,  memberTypeText,  memberPhoneText,
                memberSexText,  memberBirthText,  memberAddressText, memberRemarkText };
            String data = JsonUtil.DataToJson(key,  value);
            String id = sharedPerf.getString(KEY,  KONG);

            // 得到值解析成Json
            String jSon = JsonUtil.DataToJson(serial,  data,  id, JsonUtil.getSign(serial,  data,  id));

            // 调用网络方法
            WebServiceOfHttps webSO = new WebServiceOfHttps();
            String info = webSO.WSservers(getApplicationContext(),  portName, jSon);

            // log打印结束
            LogUtil.logEnd();
            return info;
        }

        /**
         * 从doInBackground方法中得到值作相应判断
         * @param result 结果
         */
        @Override
        protected void onPostExecute(String result) {

            // log打印开始
            LogUtil.logStart();

            // 判断服务器是否异常
            if(CommonConst.TALENTERRORSTATES.equals(result)||CommonConst.HOSTERRORSTATES.equals(result)){
                MessageUtil.commonToast(getApplicationContext(),
                    MessageUtil.getMessage(getApplicationContext(), Msg.E0002), Toast.LENGTH_SHORT);
            }else{

                // 解析Jason中success的值
                String resultInfo=JsonUtil.successJSON(result);

                // 1代表成功，0代表失败
                if(String.valueOf(TEMPONE).equals(resultInfo)){

                    // 会员注册成功
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(),  Msg.I019,
                            new String[] { CommonConst.MEMBER }), Toast.LENGTH_SHORT);

                    // 返回到前画面
                    finish();
                }else{

                    // 会员注册失败
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(getApplicationContext(),  Msg.E0005,
                            new String[] { CommonConst.MEMBER }), Toast.LENGTH_SHORT);
                }
            }

            // 打印log
            Log.v(TAG,  result);

            super.onPostExecute(result);

            // log打印开结束
            LogUtil.logEnd();
        }
    }

    /**
     * 根据title判断是在哪个控件的点击下弹出
     * @param title 标题
     */
    private void registerMember(String title) {

        // log打印开始
        LogUtil.logStart();

        // 定义对话框
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setIcon(null);
        build.setMessage(title);

        // 确定按钮
        build.setPositiveButton(MessageUtil.getMessage(getApplicationContext(),  Msg.I012),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,  int which) {

                        // log打印开开始
                        LogUtil.logStart();
                        switch (temp) {
                        case 0:

                            // 返回按钮的弹窗消失
                            dialog.dismiss();

                            // 结束当前页面
                            finish();
                            break;
                        case 1:

                            // 判断网络是否异常
                            if (Commonutil.isNetworkAvailable(XZHL0130_VipInfoActivity.this)) {

                                // 没有异常提交数据
                                RegisterMemberAsyncTask regMasTask = new RegisterMemberAsyncTask();
                                regMasTask.execute();
                            }
                            break;
                        default:
                            break;
                        }

                        // log打印开结束
                        LogUtil.logEnd();
                    }
                });

        // 取消按钮
        build.setNegativeButton(
                MessageUtil.getMessage(getApplicationContext(),  Msg.I005),
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,  int which) {

                        // log打印开始
                        LogUtil.logStart();

                        // 取消对话框
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
     *  自定义一个图片选择器
     */
    private void selectPicture() {

        // log打印开始
        LogUtil.logStart();

        // 弹出对话框选择
        AlertDialog.Builder selfBuild = new AlertDialog.Builder(this);
        CharSequence[] items = getResources().getStringArray(R.array.pictureselectdialog);
        selfBuild.setItems(items,  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,  int which) {

                // log打印开始
                LogUtil.logStart();
                switch (which) {
                case 0:

                    // 跳转到照相机
                    //Intent getCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Intent getCamera=new Intent();
                    getCamera.setClass(getApplicationContext(), XZHL0130_MainActivity.class);
                    //startActivity(getCamera);
                    startActivityForResult(getCamera,  CAMERA);
                    break;
                case 1:

                    // 跳转到图库
                    Intent getPicture = new Intent(Intent.ACTION_GET_CONTENT);
                    getPicture.setType(IMAGE);
                    startActivityForResult(getPicture,  PICTURE);
                    break;
                default:
                    break;
                }

                // log打印结束
                LogUtil.logEnd();
            }
        });
        selfBuild.show();

        // log打印结束
        LogUtil.logEnd();
    }

    /**
     * 返回值，选择照片之后，判断
     *
     * @param requestCode 请求code
     * @param resultCode 结果code
     * @param data 数据
     */
    @Override
    protected void onActivityResult(int requestCode,  int resultCode,  Intent data) {

        // log打印开始
        LogUtil.logStart();

        if(null!=data){
            switch (requestCode) {
            case CAMERA:

                // 检测sd是否可用
                String sdStatus = Environment.getExternalStorageState();
                if (!Environment.MEDIA_MOUNTED.equals(sdStatus)) {
                    return;
                }else{
                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    Bitmap bitmap=null;
                    try {
                        bitmap = BitmapFactory.decodeStream(XZHL0130_VipInfoActivity.this.getAssets()
                                .open(CommonConst.NVZHUANG));
                    } catch (IOException e1) {
                        e1.printStackTrace();

                        // Log异常
                        LogUtil.logException(e1);
                    }
                    FileOutputStream b = null;
                    String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath()+LINE;
                    File file = new File(sdPath+FILEPATH);
                    if(!file.exists()){
                        boolean fil=file.mkdirs();
                        if(fil){
                            return;
                        }
                    }

                    // 获取当前时间，进一步转化为字符串
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                    Date date = new Date(resultCode);

                    // 路径加文件名称
                    String str = file+LINE+format.format(date);
                    String fileName =  str + IMGPNG;
                    try {

                        // 把数据写入文件
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.PNG,  80,  b);
                        b.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtil.logException(e);
                    }finally{
                        try {
                            b.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            LogUtil.logException(e);
                        }
                    }

                    // 加载图片
                    ivPhotoView.setImageBitmap(bitmap);
                }
                break;
            case PICTURE:

                // 从图库中选择照片，然后把照片传给ivPhotoView
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,  null,  null,  null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                // 加载图片
                ivPhotoView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                break;
            default:
                break;
            }
        }
        super.onActivityResult(requestCode,  resultCode,  data);

        // log打印结束
        LogUtil.logEnd();
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

            // 根据输入框是否有值作出判断
            dataDialog();
        }

        // log打印结束
        LogUtil.logEnd();
        return super.onKeyDown(keyCode,  event);
    }

    /**
     *  界面文本框长度内部类
     *
     *  完成文本框长度的判断
     */
    private class TextWatcherListenner implements TextWatcher{

        // 判断值
        private int text;

        private CharSequence temp;
        private int selectionStart;
        private int selectionEnd;

        /**
         * 构造方法
         * @param text 传入的文本值
         */
        public TextWatcherListenner(int text) {
            this.text=text;
        }

        /**
         * 文字改变之后的方法
         * @param s 传入的可编辑对象
         */
        @Override
        public void afterTextChanged(Editable s){

            // log打印开始
            LogUtil.logStart();
            switch (text) {

            // 会员姓名长度判断
            case NAME:
                selectionStart = etVipName.getSelectionStart();
                selectionEnd = etVipName.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>NAMELENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                            MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,  Msg.E0026), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etVipName.setText(s);
                    etVipName.setSelection(tempSelection);
                }
                break;

            // 手机号码长度判断
            case PHONE:
                selectionStart = etMoblieNo.getSelectionStart();
                selectionEnd = etMoblieNo.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>PHONELENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,  Msg.E0026), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etMoblieNo.setText(s);
                    etMoblieNo.setSelection(tempSelection);
                }
                break;

            // 住址长度判断
            case ADDRESS:
                selectionStart = etAddress.getSelectionStart();
                selectionEnd = etAddress.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>ADDRESSLENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,  Msg.E0026), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etAddress.setText(s);
                    etAddress.setSelection(tempSelection);
                }
                break;

            // 备注长度判断
            case REMARK:
                selectionStart = etRemarks.getSelectionStart();
                selectionEnd = etRemarks.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>REMARKLENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(XZHL0130_VipInfoActivity.this,  Msg.E0026), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etRemarks.setText(s);
                    etRemarks.setSelection(tempSelection);
                }
                break;
            default:
                break;
            }

            // log打印结束
            LogUtil.logEnd();
        }

        /**
         * 文字改变之前的方法
         * @param s 改变之前的字符串
         * @param start 开始的位置
         * @param count 个数
         * @param after 结束的位置
         */
        @Override
        public void beforeTextChanged(CharSequence s,  int start,  int count,
                int after) {
        }

        /**
         * 文字改变中的方法
         * @param s 改变之后的字符串
         * @param start 开始的位置
         * @param before 前面有几个
         * @param count 个数
         */
        @Override
        public void onTextChanged(CharSequence s,  int start,  int before,
                int count) {
            temp=s;
        }
    }

}
