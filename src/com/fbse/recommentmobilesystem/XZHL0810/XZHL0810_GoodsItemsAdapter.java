/*  XZHL0810_GoodsItemsAdapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                     */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                */
/*  画面ＩＤ     ：XZHL0810                                                                                               */
/*  画面名     ：商品信息查询                                                                                                  */
/*  实现功能 ：显示商品列表的适配器                                                                                              */
/*                                                                                                                    */
/*  变更历史                                                                                                             */
/*      NO  日付                       Ver         更新者                 内容                                          */
/*      1   2014/05/19   V01L01      FBSE)高振川      新規作成                                                              */
/*                                                                                                                    */
package com.fbse.recommentmobilesystem.XZHL0810;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 *  商品列表适配器类
 *
 *  完成商品列表显示
 */
public class XZHL0810_GoodsItemsAdapter extends BaseAdapter {

    // Activity上下文
    private Context context;

    // 商品一览列表
    private List<XZHL0810_GoodsItemBean> list;

    // 适配器显示列
    private ViewHolder viewHolder;

    /**
     * 适配器构造方法
     * @param Activity上下文
     * @param 商品一览列表
     */
    public XZHL0810_GoodsItemsAdapter(Context context,
            List<XZHL0810_GoodsItemBean> list) {
        this.list = list;
        this.context = context;
    }

    /**
     * 返回数据列数
     * @return 返回适配器数据的列数
     */
    @Override
    public int getCount() {
        return this.list.size();
    }

    /**
     * 返回特定下标的数据下标
     * @param position 选项位置
     * @return Object 返回特定下标的数据下标
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * 返回特定下标的数据
     * @param position 选项位置
     * @return 返回特定下标的数据
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 返回适配器适配的界面
     * @param position 选项位置
     * @param convertView 画面布局
     * @param parent 画面组id
     * @return 适配器适配的界面
     */
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LogUtil.logStart();
        if(convertView == null){
            convertView = View.inflate(context, R.layout.xzhl0810_shopdisplay_item, null);
        }
        viewHolder = new ViewHolder();
        // 界面实例化
        viewHolder.llLinear = (LinearLayout) convertView.findViewById(R.id.ll_item_0810);
        viewHolder.tvGoodsSequence = (TextView) convertView.findViewById(R.id.tv_goodsno_0810);
        viewHolder.tvGoodsName = (TextView) convertView.findViewById(R.id.tv_goodsname_0810);
        viewHolder.tvGoodsClass = (TextView) convertView.findViewById(R.id.tv_goodsclass_0810);
        viewHolder.tvGoodsPrice = (TextView) convertView.findViewById(R.id.tv_goodsprice_0810);

        // 初始化页面数据
        viewHolder.tvGoodsSequence.setText(""+(position+1));
        viewHolder.tvGoodsName.setText(list.get(position).getGoodsName());
        viewHolder.tvGoodsClass.setText(list.get(position).getGoodsType());
        viewHolder.tvGoodsPrice.setText(formatMoney(list.get(position).getPrice()));

        Log.v("MMM>>>getView", ""+ list.get(position).getGoodsName());
        convertView.setTag(viewHolder);
        LogUtil.logEnd();

        return convertView;
    }

    /**
     *  商品列表适配器类
     *
     *  完成商品列表显示组件
     */
    public static class ViewHolder{
        LinearLayout llLinear;
        TextView tvGoodsSequence;
        TextView tvGoodsName;
        TextView tvGoodsClass;
        TextView tvGoodsPrice;
    }

    /**
     * 格式化货币数据
     * @param money 数字
     * @return result 返回格式化货币
     */
    public String formatMoney(String money) {

        LogUtil.logStart();
        String result = null;
        money.replaceAll(",", "");
        if(money.indexOf(".")>0){
            if(Integer.parseInt(money.substring(money.indexOf(".")+1, money.length()))>0){
                DecimalFormat dat = new DecimalFormat(",###.00");
                result = dat.format(Double.parseDouble(money.replaceAll(",", "")));
            }else{
                result = money.substring(0, money.indexOf("."));
                DecimalFormat dat = new DecimalFormat(",###.00");
                result = dat.format(Double.parseDouble(result));
            }
        }else{
            DecimalFormat dat = new DecimalFormat(",###.00");
            result = dat.format(Double.parseDouble(money));
        }
        LogUtil.logEnd();
        return result;
    }

}
