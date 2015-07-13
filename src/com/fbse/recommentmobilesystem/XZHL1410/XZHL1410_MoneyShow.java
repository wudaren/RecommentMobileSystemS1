/*  XZHL0160_MoneyShow.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                                                                                                                                                       */
/*  画面ＩＤ     ：XZHL1410                                                                                                  */
/*  画面名     ：金融数据查询                                                                                                                                                                                                                               */
/*  实现功能 ：查询金融数据。                                                                                                                                                                                                    */
/*                                                                                                                    */
/*  变更历史                                                                                                                                                                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                                                                                                                                       */
/*      1   2014/05/18   V01L01      FBSE)胡郑毅      新規作成                                                                                                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1410;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.fbse.recommentmobilesystem.R;

/**
 * 金融数据查询画面后台类
 * 
 * 查询金融数据详细信息
 */
public class XZHL1410_MoneyShow extends Activity implements OnClickListener {

    // 标题栏回退图标
    private ImageView ivBack;

    /**
     * 创建activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文
        setContentView(R.layout.xzhl1410_moneyshow);

        // 获得Intent对象
        // Intent intent = getIntent();

        // 获得会员一览传递的数据
        // dates = intent.getCharSequenceArrayExtra("data");

        // 初始化界面
        initView();

        // 设置监听器
        addListener();

        // 初始化数据
        initData();

        // 绘制界面
        setUpView();
    }

    /**
     * 绘制界面
     */
    private void setUpView() {

        // TODO Auto-generated method stub

    }

    /**
     * 初始化数据
     */
    private void initData() {

        // TODO Auto-generated method stub

    }

    /**
     * 回退图标点击事件监听
     */
    private void addListener() {

        ivBack.setOnClickListener(this);
    }

    /**
     * 初始化界面
     */
    private void initView() {

        // 获得回退图标
        ivBack = (ImageView) findViewById(R.id.iv_back_1410);
    }

    /**
     * 点击退出当前页面
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        case R.id.iv_back_1410:
            finish();
            break;
        default:
            break;
        }

    }
}
