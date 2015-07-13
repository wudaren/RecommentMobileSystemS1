/*  XZHL140_MemberModify.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL140                                                                                            */
/*  画面名     ：会员信息修改                                                                                         */
/*  实现功能 ：对会员信息进行修改并保存                                                                               */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李鑫      新規作成                                                          */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0140;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
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
import com.fbse.recommentmobilesystem.common.common_entity.MemberBean;

/**
 *  会员情报修改画面后台类
 *
 *  完成会员情报修改
 */
public class XZHL0140_MemberModify extends Activity implements OnClickListener {

    // 登陆者id
    private String id;

    // 退出按钮
    private Button btnBack;

    // 修改按钮
    private Button btnSubmitVipInfo;

    // 会员头像图片获取
    private Bitmap bit;

    // 会员图片
    private ImageView ivImage;

    // 文本输入框：姓名
    private EditText etName;

    // 文本输入框：手机
    private EditText etTel;

    // 文本输入框：生日
    private TextView etBirthday;

    // 文本输入框：住址
    private EditText etAddress;

    // 文本输入框：备注
    private EditText etRemarks;

    // 单选按钮：男
    private RadioButton rbMen;

    // 单选按钮：女
    private RadioButton rbWomen;

    // 下拉列表
    private Spinner spType;

    // 照片url
    private String image;

    // 姓名
    private String name;

    // 类型
    private String type;

    // 手机
    private String tel;

    // 性别
    private String sex;

    // 生日
    private String birthday;

    // 地址
    private String address;

    // 街道备注
    private String remarks;

    // 接受会员类型的值
    private int memberType;

    // 接受性别的值
    private int memberSex;

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

    // 性别组-0代表男
    private static final int MAN=0;

    // 1代表女
    private static final int WOMAN=1;

    // 数组适配器
    private ArrayAdapter<String> adapter;

    // 从一览画面接收的对象
    private MemberBean vipInfoBean;

    // 判断文本长
    private int zero = 0;

    // 接口对象
    private WebServiceOfHttps webservice = new WebServiceOfHttps();

    // 接口返回参数的结果集
    private MemberBean resultValue;

    // 空的常量
    private static final String KONG = "";
    // 配置文件
    private Properties properties = null;

    // 照相机、图片选择常量，照相机为2
    private static final int CAMERA=2;

    // 图库为3
    private static final int PICTURE=3;

    // 获取一个日历对象
    Calendar date = Calendar.getInstance(Locale.CHINA);

    /**
     * 完成界面初始化
     * @param savedInstanceState 页面状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Log开始
    	LogUtil.logStart();

        super.onCreate(savedInstanceState);

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl0140_membermodify);

        // 初始化界面
        initView();

        // 添加后退按钮点击事件
        btnBack.setOnClickListener(this);

        // 添加确认按钮点击事件
        btnSubmitVipInfo.setOnClickListener(this);

        // 生日的点击事件
        etBirthday.setOnClickListener(this);

        ivImage.setOnClickListener(this);

        // 设置页面初始化显示的值
        setValues();

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 按钮点击事件
     * @param v 视图
     */
    @Override
    public void onClick(View v) {

        // Log开始
    	LogUtil.logStart();

        switch (v.getId()) {

        // 返回按钮
        case R.id.btn_gotoback_xzhl0140:

            // 设置是否弹出对话框
            isChanged();
            break;

        // 提交按钮
        case R.id.btn_submitvipinfo_xzhl0140:
            btnSubmitVipInfo.setEnabled(false);
            getValues();
            judgment();
            break;

        // 生日
        case R.id.et_vipbirthday_xzhl0140:
            new DatePickerDialog(this, beginData, date.get(Calendar.YEAR),
                    date.get(Calendar.MONTH), WOMAN).show();
            break;
        case R.id.iv_vippicture_xzhl0140:

            // 调用图片选择方法
            selectPicture();
            break;
        default:
            break;
        }

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     *  自定义一个图片选择器
     */
    private void selectPicture() {

        // Log开始
    	LogUtil.logStart();

        // 弹出对话框选择
        AlertDialog.Builder selfBuild = new AlertDialog.Builder(this);
        CharSequence[] items = getResources().getStringArray(R.array.pictureselectdialog);
        selfBuild.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                case MAN:

                    // 跳转到照相机
                    Intent getCamera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(getCamera, CAMERA);
                    break;
                case WOMAN:

                    // 跳转到图库
                    Intent getPicture = new Intent(Intent.ACTION_GET_CONTENT);
                    getPicture.setType("image/*");
                    startActivityForResult(getPicture, PICTURE);
                    break;
                default:
                    break;
                }
            }
        });
        selfBuild.show();

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 返回值，选择照片之后，判断
     * @param requestCode 继承自父类
     * @param resultCode 继承自父类
     * @param data 继承自父类
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Log开始
    	LogUtil.logStart();

        if(null!=data){
            switch (requestCode) {
            case CAMERA:

                // 检测sd是否可用
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    return;
                }else{

                    // 获取相机返回的数据，并转换为Bitmap图片格式
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    FileOutputStream b = null;
                    String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
                    File file = new File(sdPath+"FILE");
                    if(!file.exists()){
                        boolean fil=file.mkdirs();
                        if(fil){
                            return;
                        }
                    }

                    // 获取当前时间，进一步转化为字符串
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
                    long result = 0L+resultCode;
                    Date date = new Date(result);

                    // 路径加文件名称
                    String str = file+"/"+format.format(date);
                    String fileName =  str + ".png";
                    try {

                        // 把数据写入文件
                        b = new FileOutputStream(fileName);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 80, b);
                        b.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally{
                        try {
                            if(b!=null){
                        	b.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // 图片显示
                    ivImage.setImageBitmap(bitmap);
                }
                break;
            case PICTURE:

                // 设置图库图片显示
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                // 图片显示
                ivImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                break;
            default:
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 控件初始化
     */
    private void initView(){

        // Log开始
    	LogUtil.logStart();

        // 获得Intent对象
        Intent intent = getIntent();
        vipInfoBean = (MemberBean) intent.getSerializableExtra(CommonConst.MEMBERBEAN);

        properties = Commonutil.loadProperties(XZHL0140_MemberModify.this);

        SharedPreferences sp = getSharedPreferences(CommonConst.DATA, Context.MODE_PRIVATE);
        id = sp.getString(CommonConst.ID, KONG);

        btnBack = (Button) findViewById(R.id.btn_gotoback_xzhl0140);
        btnSubmitVipInfo = (Button) findViewById(R.id.btn_submitvipinfo_xzhl0140);
        ivImage = (ImageView) findViewById(R.id.iv_vippicture_xzhl0140);
        etName = (EditText) findViewById(R.id.et_vipname_xzhl0140);
        etTel = (EditText) findViewById(R.id.et_vipmoblie_xzhl0140);
        etBirthday = (TextView) findViewById(R.id.et_vipbirthday_xzhl0140);
        etAddress = (EditText) findViewById(R.id.et_vipaddress_xzhl0140);
        etRemarks = (EditText) findViewById(R.id.et_remarks_xzhl0140);

        // 输入框的文本长度监听事件
        etName.addTextChangedListener(new TextWatcherListenner(NAME));
        etTel.addTextChangedListener(new TextWatcherListenner(PHONE));
        etAddress.addTextChangedListener(new TextWatcherListenner(ADDRESS));
        etRemarks.addTextChangedListener(new TextWatcherListenner(REMARK));
        RadioGroup radio=(RadioGroup) findViewById(R.id.rd_radiogroupsex_xzhl0140);

        // 性别选择组
        radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // 判断性别选择
                if(checkedId==R.id.rd_radiobuttonmale_xzhl0140){

                    // 设置为男
                    memberSex=MAN;
                }else{

                    // 设置为女
                    memberSex=WOMAN;
                }
            }
        });
        rbMen = (RadioButton) findViewById(R.id.rd_radiobuttonmale_xzhl0140);
        rbWomen = (RadioButton) findViewById(R.id.rd_radiobuttonfemale_xzhl0140);
        spType = (Spinner) findViewById(R.id.sp_viptypespinner_xzhl0140);

        // 会员类型选择监听
        spType.setOnItemSelectedListener(new OnItemSelectedListener() {

            // 选择方法
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3) {
                memberType=arg2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 图片加载，显示
     */
    class ImageTask extends AsyncTask<String, Integer, Integer> {

        // 加载图片
        @Override
        protected Integer doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();

            bit = XZHL0110_Utils.loadBitmap(vipInfoBean.getImage());

            Log.v("imageUrl", "imageUrl>>>>"+vipInfoBean.getImage());

            // Log结束
            LogUtil.logEnd();
            return null;
        }

        // 设置图片
        @Override
        protected void onPostExecute(Integer result) {

            // Log开始
            LogUtil.logStart();

            ivImage.setImageBitmap(bit);

            // Log结束
            LogUtil.logEnd();
        }
    }

    /**
     * 设置页面控件初始值
     */
    private void setValues(){

        // Log开始
    	LogUtil.logStart();

        if(!KONG.equals(vipInfoBean.getImage())){
            ImageTask it = new ImageTask();
            it.execute();
        }

        // 会员姓名赋值
        etName.setText(vipInfoBean.getName());

        // 设置会员姓名光标位置
        etName.setSelection(etName.getText().length());

        // 从资源文件中获取会员类型的数组
        String[] vipInfoMemberType=getResources().getStringArray(R.array.vipinfo_membertype);

        // Spinner适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, vipInfoMemberType);
        spType.setAdapter(adapter);
        spType.setSelection(Integer.parseInt(vipInfoBean.getType()), true);
        etTel.setText(vipInfoBean.getTel());
        if((vipInfoBean.getSex()).equals(String.valueOf(MAN))){
            rbMen.setChecked(true);

            // 设置为男
            memberSex=MAN;
        }else{
            rbWomen.setChecked(true);

            // 设置为女
            memberSex=WOMAN;
        }
        etBirthday.setText(vipInfoBean.getBirthday());
        etAddress.setText(vipInfoBean.getAddress());
        etRemarks.setText(vipInfoBean.getRemarks());

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 获取页面的值
     */
    private void getValues(){

        // Log开始
    	LogUtil.logStart();

        id = vipInfoBean.getId();
        image = vipInfoBean.getImage();
        name = etName.getText().toString().trim();
        type = memberType+KONG;
        tel = etTel.getText().toString().trim();
        sex=memberSex+KONG;
        birthday = etBirthday.getText().toString();
        address = etAddress.getText().toString().trim();
        remarks = etRemarks.getText().toString().trim();

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 验证
     */
    private void judgment(){

        // Log开始
    	LogUtil.logStart();

        // 姓名不能为空
        if (name.length() == zero) {
            MessageUtil.commonToast(getApplicationContext()
                , MessageUtil.getMessage(this, Msg.E0016, new String[]{CommonConst.MEMBERNAME}), Toast.LENGTH_SHORT);
            etName.requestFocus();
            btnSubmitVipInfo.setEnabled(true);
            return;
        }

        // 电话不能为空
        if (tel.length() == zero) {
            MessageUtil.commonToast(getApplicationContext()
                , MessageUtil.getMessage(this, Msg.E0016, new String[]{CommonConst.MEMBERPHONE}), Toast.LENGTH_SHORT);
            etTel.requestFocus();
            btnSubmitVipInfo.setEnabled(true);
            return;
        }

        // 构造提交前的对话框
        showDig();

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 判断界面是否修改
     */
    private void isChanged(){

        // Log开始
    	LogUtil.logStart();

        // 比较前后数据变化
        if(vipInfoBean.getName().equals(etName.getText().toString())&&
                vipInfoBean.getType().equals(String.valueOf(memberType))&&
                vipInfoBean.getTel().equals(etTel.getText().toString())&&
                vipInfoBean.getSex().equals(String.valueOf(memberSex))&&
                vipInfoBean.getBirthday().equals(etBirthday.getText().toString())&&
                vipInfoBean.getAddress().equals(etAddress.getText().toString())&&
                vipInfoBean.getRemarks().equals(etRemarks.getText().toString())){

            // 关闭页面
            finish();
        }else{

            // 弹出对话框
            showDialg();
        }

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 显示构造对话框
     */
    private void showDialg() {

        // Log开始
    	LogUtil.logStart();

        AlertDialog.Builder builder=new AlertDialog.Builder(XZHL0140_MemberModify.this);
        builder.setMessage(properties.getProperty(Msg.Q013, CommonConst.SPACE));

        // 确定按钮点击
        builder.setPositiveButton(properties.getProperty(Msg.I012, CommonConst.SPACE),
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    XZHL0140_MemberModify.this.finish();
                }

               // 取消按钮点击
            }).setNegativeButton(properties.getProperty(Msg.I005, CommonConst.SPACE),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // 显示对话框
        builder.create().show();

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     * 构造提交前的对话框
     */
    private void showDig() {

        // Log开始
    	LogUtil.logStart();

        btnSubmitVipInfo.setEnabled(true);
        AlertDialog.Builder builder=new AlertDialog.Builder(XZHL0140_MemberModify.this);
        builder.setMessage(MessageUtil
                        .getMessage(getApplicationContext(), Msg.Q011,
                                        new String[] { CommonConst.XINXI }));

        // 确定按钮点击
        builder.setPositiveButton(properties.getProperty(Msg.I012, KONG), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                  // 判断网络是否异常
                if (Commonutil.isNetworkAvailable(getApplicationContext())) {
                    Modigy modigy = new Modigy();
                    modigy.execute();
                }
            }

        // 取消按钮点击
        }).setNegativeButton(properties.getProperty(Msg.I005, KONG), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 显示对话框
        builder.create().show();

        // Log结束
    	LogUtil.logEnd();
    }

    /**
     *  会员情报修改画面后台类
     *
     *  异步完成提交修改信息
     */
    private class Modigy extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();

            // 初期值设置
            int flag = 0;

            try {
                // 接口请求参数
                String[] key = { "id", "image", "name", "type", "tel", "sex" , "birthday", "address", "remarks"};
                String[] value = { id, image, name, type, tel, sex, birthday, address, remarks};
                String json = webservice.WSservers(XZHL0140_MemberModify.this, "member/update",
                    JsonUtil.DataToJson("a001", JsonUtil.DataToJson(key, value), id,
                        JsonUtil.getHaveToken("a001", JsonUtil.DataToJson(key, value), id)));

                // 接口返回参数的结果集
                resultValue = JsonUtil.jsonToMember(json);

                // 接口返回参数success
                String success = resultValue.getSuccess();

                // 修改失败
                if ((String.valueOf(MAN)).equals(success)) {
                    flag = MAN;
                }

                // 修改成功
                else if ((String.valueOf(WOMAN)).equals(success)) {
                    flag = WOMAN;
                }

            } catch (Exception e) {
                // 异常log输出
                LogUtil.logException(e);
            }

            // Log结束
            LogUtil.logEnd();

            // 返回值
            return flag;
        }

        /**
         * 开启异步前主线程操作
         */
        @Override
        protected void onPreExecute() {

            // Log开始
            LogUtil.logStart();

            super.onPreExecute();

            // Log结束
            LogUtil.logEnd();
        }

        /**
         * 主线程完成信息提示
         * @param result 结果
         */
        @Override
        protected void onPostExecute(Integer result) {

            // Log开始
            LogUtil.logStart();

            switch (result) {

            // 修改失败
            case MAN:
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                                .getMessage(getApplicationContext(), Msg.E0024,
                                                new String[] { CommonConst.XINXI }), Toast.LENGTH_SHORT);
                btnSubmitVipInfo.setEnabled(true);
                break;

            // 修改成功
            case WOMAN:
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                                .getMessage(getApplicationContext(), Msg.I020,
                                                new String[] { CommonConst.XINXI }), Toast.LENGTH_SHORT);
                btnSubmitVipInfo.setEnabled(true);

                // 关闭画面
                finish();
                break;

            // 其他情况
            default:
                MessageUtil.commonToast(getApplicationContext(), MessageUtil
                                .getMessage(getApplicationContext(), Msg.E0024,
                                                new String[] { CommonConst.XINXI }), Toast.LENGTH_SHORT);
                btnSubmitVipInfo.setEnabled(true);
                break;
            }

            // Log结束
            LogUtil.logEnd();
        }

        @Override
        protected void onCancelled() {

        // Log开始
            LogUtil.logStart();

            super.onCancelled();

            // Log结束
            LogUtil.logEnd();
        }
    }

     // 当点击DatePickerDialog控件的设置按钮时设置开始日期，调用该方法
    DatePickerDialog.OnDateSetListener beginData = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {

            // Log开始
            LogUtil.logStart();

            // 这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // 将页面TextView的显示更新为最新时间
            updateMonthBegin();

            // Log结束
            LogUtil.logEnd();
        }
    };

    /**
     * 更新开始日期的TextView
     * */
    private void updateMonthBegin() {

        // Log开始
    	LogUtil.logStart();

        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        String str = temp.format(date.getTime());
        etBirthday.setText(str);

        // Log结束
    	LogUtil.logEnd();

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

        // 构造方法
        public TextWatcherListenner(int text) {
            this.text=text;
        }

        // 文字改变之后的方法
        @Override
        public void afterTextChanged(Editable s) {

            // log打印开始
            LogUtil.logStart();
            switch (text) {

            // 会员姓名长度判断
            case NAME:
                selectionStart = etName.getSelectionStart();
                selectionEnd = etName.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>NAMELENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                            MessageUtil.getMessage(XZHL0140_MemberModify.this,  Msg.E0026), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etName.setText(s);
                    etName.setSelection(tempSelection);
                }
                break;

            // 手机号码长度判断
            case PHONE:
                selectionStart = etTel.getSelectionStart();
                selectionEnd = etTel.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>PHONELENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(XZHL0140_MemberModify.this,  Msg.E0026), Toast.LENGTH_SHORT);
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etTel.setText(s);
                    etTel.setSelection(tempSelection);
                }
                break;

            // 住址长度判断
            case ADDRESS:
                selectionStart = etAddress.getSelectionStart();
                selectionEnd = etAddress.getSelectionEnd();

                // 超过长度就提示
                if(temp.length()>ADDRESSLENGTH){
                    MessageUtil.commonToast(getApplicationContext(),
                        MessageUtil.getMessage(XZHL0140_MemberModify.this,  Msg.E0026), Toast.LENGTH_SHORT);
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
                        MessageUtil.getMessage(XZHL0140_MemberModify.this,  Msg.E0026), Toast.LENGTH_SHORT);
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

        // 文字改变之前的方法
        @Override
        public void beforeTextChanged(CharSequence s,  int start,  int count,
                int after) {
        }

        // 文字改变中的方法
        @Override
        public void onTextChanged(CharSequence s,  int start,  int before,
                int count) {
            temp=s;
        }
    }
    /**
     * 重写的键盘按下监听
     * @param keyCode 键盘码
     * @param event 事件
     * @return 真或假
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // Log开始
    	LogUtil.logStart();

        // 返回按键按下
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            // 页面数据变化的判断
            isChanged();
            return true;
        }

        // Log结束
    	LogUtil.logEnd();

        return super.onKeyDown(keyCode, event);
    }
}
