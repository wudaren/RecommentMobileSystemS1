/* XZHL0150_Adapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/* 项目名称 ：信智互联                                                                                                */
/* 画面ＩＤ ：XZHL0510                                                                                                */
/* 画面名   ：会员信息查询                                                                                            */
/* 实现功能 ：显示会员信息查询                                                                                          */
/*                                                                                                                    */
/* 变更历史                                                                                                           */
/* NO 日付       Ver    更新者    内容                                                                                */
/* 1  2014/05/23 V01L01 FBSE)张宁 新規作成                                                                            */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/

package com.fbse.recommentmobilesystem.XZHL0150;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.LogUtil;
import com.fbse.recommentmobilesystem.common.common_entity.MemberBean;

/**
 * 适配器类
 * 加载适配器
 */
public class XZHL0150_Adapter extends BaseAdapter {

    // 布局容器
    private ViewHolder viewHolder;

    // 布局加载器
    private LayoutInflater mInflater;

    // 公共的list变量
    public List<MemberBean> list;

    public Boolean delflg;

    String[] array;

    /**
     * 构造方法
     * @param context上下文
     * @param list 相应数据输入
     */
    public XZHL0150_Adapter(Context context, List<MemberBean> list, boolean delflg) {

        LogUtil.logStart();
        array = context.getResources().getStringArray(R.array.vipinfo_select);
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.delflg = delflg;
        LogUtil.logEnd();
    }

    /**
     * 获得单个对象
     * @return int数量
     */
    @Override
    public int getCount() {

        LogUtil.logStart();
        LogUtil.logEnd();
        return list.size();
    }

    /**
     * 获得单个对象
     * @param arg0 位置
     * @return Object 单个对象
     */
    @Override
    public Object getItem(int arg0) {

        LogUtil.logStart();
        LogUtil.logEnd();
        return null;
    }

    /**
     * 获得单个对象
     * @param arg0 位置
     * @return 获得id
     */
    @Override
    public long getItemId(int arg0) {

        LogUtil.logStart();
        LogUtil.logEnd();
        return arg0;
    }

    /**
     * 绑定数据与view
     * @param position 位置
     * @param convertView 控件容器
     * @param parent 控件
     * @return View获得view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LogUtil.logStart();
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.xzhl0150_item, null);
            viewHolder.tvnumber = (TextView) convertView.findViewById(R.id.tv_xuhao_0150);
            viewHolder.tvname = (TextView) convertView.findViewById(R.id.tv_name_0150);
            viewHolder.tvphone = (TextView) convertView.findViewById(R.id.tv_phone_0150);
            viewHolder.tvviptype = (TextView) convertView.findViewById(R.id.tv_cardtype_0150);
            viewHolder.ivjiantou=(ImageView) convertView.findViewById(R.id.iv_jiantou_0510);
            viewHolder.cbdel=(CheckBox) convertView.findViewById(R.id.cb_del_0510);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvnumber.setText(position + 1 + CommonConst.SPACE);
        viewHolder.tvname.setText(list.get(position).getName());
        viewHolder.tvphone.setText(list.get(position).getTel());
        Log.i("", list.get(position).getType());
        Log.i("", array.toString());
        if (array.length >= Integer.parseInt(list.get(position).getType())) {
            viewHolder.tvviptype.setText(array[Integer.parseInt(list.get(position).getType()) + 1]);
        }
        if (delflg) {
            viewHolder.cbdel.setVisibility(View.VISIBLE);
            viewHolder.ivjiantou.setVisibility(View.GONE);
        } else {
            viewHolder.cbdel.setVisibility(View.GONE);
            viewHolder.ivjiantou.setVisibility(View.VISIBLE);
        }
        LogUtil.logEnd();
        return convertView;
    }

    /**
     * 控件容器类
     * 控件容器
     */
    class ViewHolder {

        // 序号
        public TextView tvnumber;

        // 姓名
        public TextView tvname;

        // 电话
        public TextView tvphone;

        // 卡类型
        public TextView tvviptype;

        // 删除选择框
        public CheckBox cbdel;

        public ImageView ivjiantou;

    }
}
