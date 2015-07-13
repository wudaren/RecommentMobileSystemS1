/*  XZHL0160_MemberAllQuery.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                       */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL0160                                                                                               */
/*  画面名 ：会员详细信息显示                                                                                         */
/*  实现功能 ：查询会员详细数据。                                                                                     */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/18   V01L01      FBSE)胡郑毅      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0160;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0110.XZHL0110_Utils;
import com.fbse.recommentmobilesystem.XZHL0140.XZHL0140_MemberModify;
import com.fbse.recommentmobilesystem.XZHL0150.XZHL0150_MemberQuery;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.common_entity.MemberBean;

/**
 * 会员详细信息画面后台类
 * 查询会员详细信息
 */
public class XZHL0160_MemberAllQuery extends Activity implements OnClickListener {

    // 标题栏回退图标
    private ImageView ivBack;

    // 文本显示框:会员姓名
    private TextView tvName;

    // 文本显示框:会员类型
    private TextView tvType;

    // 文本显示框:会员电话
    private TextView tvTel;

    // 文本显示框:会员性别
    private TextView tvSex;

    // 文本显示框:会员生日
    private TextView tvBirthday;

    // 文本显示框:会员地址
    private TextView tvAddress;

    // 文本显示框:会员备注
    private TextView tvRemark;

    // 图片显示框:会员照片
    private ImageView ivPhoto;
    
    private Button btnModfi;

    // 会员照片获取
    private Bitmap bm;

    // 会员
    private MemberBean bean;

    /**
     * 创建activity
     * @param savedInstanceState 捆绑的实例
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Log开始
        LogUtil.logStart();

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl0160_memberallquery);

        // 初始化数据
        initData();

        // 初始化界面
        initView();

        // 设置监听器
        addListener();

        // 绘制界面
        setUpView();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 绘制界面
     */
    private void setUpView() {

        // Log开始
        LogUtil.logStart();

        // 给会员姓名标签赋值
        tvName.setText(bean.getName());

        // 从资源文件中获取会员类型的数组
        String[] vipInfoMemberType = getResources().getStringArray(R.array.vipinfo_membertype);

        // 给会员类型标签赋值,0普通會員,1金卡,2銀卡,3夢卡,4其他
        if (CommonConst.FAILZERO.equals(bean.getType())) {
            tvType.setText(vipInfoMemberType[0]);
        } else if (CommonConst.ONE.equals(bean.getType())) {
            tvType.setText(vipInfoMemberType[1]);
        } else if (CommonConst.ROLETWO.equals(bean.getType())) {
            tvType.setText(vipInfoMemberType[2]);
        } else if (CommonConst.ROLETHREE.equals(bean.getType())) {
            tvType.setText(vipInfoMemberType[3]);
        } else if (CommonConst.FOUR.equals(bean.getType())) {
            tvType.setText(vipInfoMemberType[4]);
        } else {
            tvType.setText(CommonConst.SECRECT);
        }

        // 给会员电话标签赋值
        tvTel.setText(bean.getTel());

        // 给会员性别标签赋值,0為男,1為女
        if (CommonConst.ONE.equals(bean.getSex())) {
            tvSex.setText(CommonConst.FEMALE);
        } else if (CommonConst.FAILZERO.equals(bean.getSex())) {
            tvSex.setText(CommonConst.MALE);
        } else {
            tvSex.setText(CommonConst.SECRECT);
        }

        // 给会员生日标签赋值
        tvBirthday.setText(bean.getBirthday());

        // 给会员地址标签赋值
        tvAddress.setText(bean.getAddress());

        // 给会员备注标签赋值
        tvRemark.setText(bean.getRemarks());

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        // Log开始
        LogUtil.logStart();

        // 获得Intent对象
        Intent intent = getIntent();

        // 获得会员一览传递的数据
        bean = (MemberBean) intent.getSerializableExtra(CommonConst.MEMBERBEAN);
        new ImageTask().execute();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 回退图标点击事件监听
     */
    private void addListener() {

        // Log开始
        LogUtil.logStart();

        ivBack.setOnClickListener(this);
        btnModfi.setOnClickListener(this);
        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 初始化界面
     */
    private void initView() {

        // Log开始
        LogUtil.logStart();

        // 获得会员姓名控件
        tvName = (TextView) findViewById(R.id.tv_name_0160);

        // 获得会员类型控件
        tvType = (TextView) findViewById(R.id.tv_type_0160);

        // 获得会员电话控件
        tvTel = (TextView) findViewById(R.id.tv_tel_0160);

        // 获得会员性别控件
        tvSex = (TextView) findViewById(R.id.tv_sex_0160);

        // 获得会员生日控件
        tvBirthday = (TextView) findViewById(R.id.tv_birthday_0160);

        // 获得会员地址控件
        tvAddress = (TextView) findViewById(R.id.tv_address_0160);

        // 获得会员备注控件
        tvRemark = (TextView) findViewById(R.id.tv_remark_0160);

        // 获得会员照片控件
        ivPhoto = (ImageView) findViewById(R.id.iv_photo_0160);

        // 获得回退图标
        ivBack = (ImageView) findViewById(R.id.iv_back_0160);

        btnModfi=(Button)findViewById(R.id.btn_bianji_0160);
        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 点击退出当前页面
     * @param v 视图
     */
    @Override
    public void onClick(View v) {

        // Log开始
        LogUtil.logStart();

        switch (v.getId()) {
        case R.id.iv_back_0160:
            finish();
            break;
            
        case R.id.btn_bianji_0160:
            Intent intent = new Intent();
            intent.setClass(XZHL0160_MemberAllQuery.this, XZHL0140_MemberModify.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(CommonConst.MEMBERBEAN,bean );
            intent.putExtras(bundle);
            startActivity(intent);
            break;
        default:
            break;
        }

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 异步请求照片并初始化内部类
     * 异步执行获得照片并初期化显示
     */
    class ImageTask extends AsyncTask<String, Integer, Integer> {

        /**
         * 获得照片
         * @param params 传入的参数
         * @return 返回参数
         */
        @Override
        protected Integer doInBackground(String... params) {

            // Log开始
            LogUtil.logStart();
            bm = XZHL0110_Utils.loadBitmap(bean.getImage());

            // Log结束
            LogUtil.logEnd();
            return null;

        }

        /**
         * 初始化照片
         * @param result 传入的参数
         */
        @Override
        protected void onPostExecute(Integer result) {

            // Log开始
            LogUtil.logStart();
            ivPhoto.setImageBitmap(bm);

            // Log结束
            LogUtil.logEnd();
        }
    }
}
