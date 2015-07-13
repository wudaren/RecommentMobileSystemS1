/*  XZHL0830_ShopAllDisplay.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD                            */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL0830                                                                                           */
/*  画面名     ：商品详细信息                                                                                         */
/*  实现功能 ：对商品信息进行显示。                                                                                   */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/20   V01L01      FBSE)尹晓超      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0830;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.XZHL0810.XZHL0810_GoodsItemBean;
import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 *  商品详细信息画面后台类
 *
 *  完成商品详细信息显示
 */
public class XZHL0830_ShopAllDisplay extends Activity  {

    // 后退按钮
    private Button btnBack;

    // 文本框商品名称
    private TextView tvName;

    // 文本框商品品类
    private TextView tvCateloge;

    // 文本框商品货号
    private TextView tvNum;

    // 文本框库存
    private TextView tvStocks;

    // 文本框供应商
    private TextView tvSupplier;

    // 文本框市场价格
    private TextView tvPrice;

    // 文本框实际销售价
    private TextView tvReatyPrice;

    /**
     * 创建activity
     * @param savedInstanceState 保存的activity实例化状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Log开始
        LogUtil.logStart();
        super.onCreate(savedInstanceState);

        // 无标题显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 加载layout布局文件
        setContentView(R.layout.xzhl0830_shopalldisplay);

        // 初始化界面
        initView();

        // 得到前台页面值
        setValue();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 输入框赋值
     */
    private void setValue() {

        // Log开始
        LogUtil.logStart();

        // 得到对象
        XZHL0810_GoodsItemBean goodsBean=(XZHL0810_GoodsItemBean) getIntent().getSerializableExtra("goodsArray");

        // 赋值
        tvName.setText(goodsBean.getGoodsName());
        tvCateloge.setText(goodsBean.getGoodsType());
        tvNum.setText(goodsBean.getGoodsNumber());
        tvStocks.setText(goodsBean.getStocks());
        tvSupplier.setText(goodsBean.getSupplier());
        tvPrice.setText(goodsBean.getPrice());
        tvReatyPrice.setText(goodsBean.getRealityPrice());

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 界面初始化
     */
    private void initView() {

        // Log开始
        LogUtil.logStart();

        // 返回按钮点击
        btnBack = (Button) findViewById(R.id.btn_gotomain_0830);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 取得画面控件
        tvName=(TextView) this.findViewById(R.id.tv_product_0830);
        tvCateloge=(TextView) this.findViewById(R.id.tv_catloge_0830);
        tvNum=(TextView) this.findViewById(R.id.tv_num_0830);
        tvStocks=(TextView) this.findViewById(R.id.tv_cun_0830);
        tvSupplier=(TextView) this.findViewById(R.id.tv_gongying_0830);
        tvPrice=(TextView) this.findViewById(R.id.tv_price_0830);
        tvReatyPrice=(TextView) this.findViewById(R.id.tv_shiji_0830);

        // Log结束
        LogUtil.logEnd();
    }

}
