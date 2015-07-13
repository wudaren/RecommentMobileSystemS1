/*  XZHL1050_ItemsAdapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                         */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL1050                                                                                               */
/*  画面名 ：商城产品广告一览                                                                                         */
/*  实现功能 ：商城产品广告数据item。                                                                                 */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/21   V01L01      FBSE)尹小超      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1050;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 * 商城产品广告一览数据呈现类
 * 商城产品广告一览数据呈现类
 */
public class XZHL1050_ItemsAdapter extends BaseAdapter {

    // 数据对象
    private List<XZHL1050_Bean> list;

    // 绑定数据的内部类
    private LayoutInflater inflater;

    /**
     * 构造器
     */
    public XZHL1050_ItemsAdapter(Context context, List<XZHL1050_Bean> list) {

        // Log开始
        LogUtil.logStart();
        this.list = list;
        this.inflater = LayoutInflater.from(context);

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 返回传入数据的长度
     * @return 返回的长度
     */
    @Override
    public int getCount() {

        return this.list.size();
    }

    /**
     * 依据位置获得itente对象
     * @param position 传入位置
     * @return 返回对象
     */
    @Override
    public XZHL1050_Bean getItem(int position) {

        return list.get(position);
    }

    /**
     * 获得元素id
     * @param position 传入位置
     * @return 元素id
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * 循环展示数据
     * @param position 位置
     * @param convertView 转化视图
     * @param parent 视图数
     * @return 视图
     */
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        // Log开始
        LogUtil.logStart();

        // 当转换视图为空,构造转换视图并缓存,否则读取缓存的转换视图
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.xzhl1050_item, null);
            viewHolder = new ViewHolder();

            // 界面实例化
            viewHolder.no = (TextView) convertView.findViewById(R.id.tv_no_1050);
            viewHolder.hdmc = (TextView) convertView.findViewById(R.id.tv_hdmc_1050);
            viewHolder.cjje = (TextView) convertView.findViewById(R.id.tv_cjje_1050);
            viewHolder.hdsj = (TextView) convertView.findViewById(R.id.tv_hdsj_1050);
            viewHolder.kssj = (TextView) convertView.findViewById(R.id.tv_kssj_1050);
            viewHolder.jssj = (TextView) convertView.findViewById(R.id.tv_jssj_1050);
            viewHolder.hddx = (TextView) convertView.findViewById(R.id.tv_hddx_1050);
            viewHolder.hdsp = (TextView) convertView.findViewById(R.id.tv_hdsp_1050);
            viewHolder.yhfs = (TextView) convertView.findViewById(R.id.tv_yhfs_1050);
            viewHolder.yhsz = (TextView) convertView.findViewById(R.id.tv_yhsz_1050);
            viewHolder.hdxy = (TextView) convertView.findViewById(R.id.tv_hdxy_1050);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.tv_id_1050);

            // 缓存转换视图
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 给条目赋值
        viewHolder.no.setText((position + 1) + CommonConst.SPACE);
        viewHolder.hdmc.setText(list.get(position).getName());
        viewHolder.cjje.setText(list.get(position).getMoney());
        viewHolder.hdsj.setText(XZHL1050_Util.getDate(list.get(position).getBeginDate()) + CommonConst.CONNECT
            + XZHL1050_Util.getDate(list.get(position).getEndDate()));
        viewHolder.kssj.setText(list.get(position).getBeginDate());
        viewHolder.jssj.setText(list.get(position).getEndDate());
        viewHolder.hddx.setText(list.get(position).getTarget());
        viewHolder.hdsp.setText(list.get(position).getProduct());
        viewHolder.yhfs.setText(list.get(position).getMethod());
        viewHolder.yhsz.setText(list.get(position).getSetting());
        viewHolder.hdxy.setText(list.get(position).getAdv());
        viewHolder.tvId.setText(list.get(position).getId());

        // Log结束
        LogUtil.logEnd();
        return convertView;
    }

    /**
     * 绑定数据到控件上
     * 绑定数据到商城产品广告一览画面上
     */
    static class ViewHolder {

        // 广告序号
        TextView no;

        // 活动名称
        TextView hdmc;

        // 成交金额
        TextView cjje;

        // 活动时间
        TextView hdsj;

        // 开始时间
        TextView kssj;

        // 结束时间
        TextView jssj;

        // 活动对象
        TextView hddx;

        // 活动商品
        TextView hdsp;

        // 优惠方式
        TextView yhfs;

        // 优惠设置
        TextView yhsz;

        // 活动宣言
        TextView hdxy;

        // id
        TextView tvId;
    }

    /**
     * 改变数据源之后更新适配器
     * @param list2 数据源
     */
    public void changeData(List<XZHL1050_Bean> list2) {

        // Log开始
        LogUtil.logStart();
        this.setList(list2);
        this.notifyDataSetChanged();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 更改适配器数据的方法
     * @param list2 数据源
     */
    private void setList(List<XZHL1050_Bean> list2) {

        // Log开始
        LogUtil.logStart();
        if (list != null) {
            this.list = list2;
        } else {
            this.list = new ArrayList<XZHL1050_Bean>();
        }

        // Log结束
        LogUtil.logEnd();
    }

}
