/* XZHL1240_Adapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/* 项目名称 ：信智互联                                                                                                 */
/* 画面ＩＤ ：XZHL1240                                                                                                 */
/* 画面名 ：各门店店员数汇总 适配器                                                                                     */
/* 实现功能 ：显示各个门店店员数一览表 的适配器                                                                          */
/*                                                                                                                    */
/* 变更历史                                                                                                            */
/* NO 日付 Ver 更新者 内容                                                                                             */
/* 1 2014/05/23 V01L01 FBSE）张宁 新規作成                                                                             */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/

package com.fbse.recommentmobilesystem.XZHL1240;

import java.util.List;
import java.util.Map;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.LogUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 将数据与页面绑定
 * ListView的适配器
 */
public class XZHL1240_Adapter extends BaseAdapter {

    // 布局容器
    private ViewHolder viewHolder;

    // 界面载入中介
    private LayoutInflater mInflater;

    // 数据存储list
    private List<Map<String, String>> list;

    // 构造方法
    public XZHL1240_Adapter(Context context, List<Map<String, String>> list) {

        LogUtil.logStart();
        mInflater = LayoutInflater.from(context);
        this.list = list;
        LogUtil.logEnd();
    }

    /**
     * 重写getCount方法获得控件数量
     * @return int控件数量
     */
    @Override
    public int getCount() {

        LogUtil.logStart();
        LogUtil.logEnd();
        return list.size();
    }

    /**
     * 获得单个对象
     * @param position 位置
     * @return Object 单个对象
     */
    @Override
    public Object getItem(int position) {

        LogUtil.logStart();
        LogUtil.logEnd();
        return list.get(position);

    }

    /**
     * 获得单个对象
     * @param position 位置
     * @return 获得id
     */
    @Override
    public long getItemId(int position) {

        LogUtil.logStart();
        LogUtil.logEnd();
        return position;
    }

    /**
     * 绑定数据与view
     * @param position 位置
     * @param convertView 控件容器
     * @param parent 控件
     * @return 获得view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LogUtil.logStart();
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.xzhl1240_item, null);
            viewHolder.tvnumber = (TextView) convertView.findViewById(R.id.dianpuxuhao);
            viewHolder.tvmendian = (TextView) convertView.findViewById(R.id.mendianmingcheng);
            viewHolder.tvshuliang = (TextView) convertView.findViewById(R.id.dianyuanshuliang);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvnumber.setText((position + 1) + CommonConst.SPACE);
        viewHolder.tvmendian.setText(list.get(position).get(CommonConst.MENDIAN));
        viewHolder.tvshuliang.setText(list.get(position).get(CommonConst.SHULIANG));
        LogUtil.logEnd();
        return convertView;
    }

    /**
     * 控件容器
     * 简单布局的容器
     */
    static class ViewHolder {

        // 序号
        private TextView tvnumber;

        // 门店
        private TextView tvmendian;

        // 数量
        private TextView tvshuliang;

    }
}
