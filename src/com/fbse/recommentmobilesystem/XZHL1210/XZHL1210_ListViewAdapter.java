/*  XZHL1210_ListViewAdapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                      */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1210                                                                                           */
/*  画面名     ：店员一览                                                                                             */
/*  实现功能 ：对店员信息一览显示的自定义视图适配器。                                                                 */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1210;

import java.util.ArrayList;
import java.util.List;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.LogUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 自定义XZHL1210 activity的listView的适配器
 */
public class XZHL1210_ListViewAdapter extends BaseAdapter {

    // 商家管理员字符串常量
    private static final String ADMIN="商家管理员";

    // 门店店长字符串常量
    private static final String DIANZHANG="门店店长";

    // 门店店员字符串常量
    private static final String DIANYUAN="门店店员";

    // 默认的字符串常量
    private static final String DEFAULT="";

    // 视图填充器
    private LayoutInflater inflater;

    // 店员信息列表
    private List<XZHL1210_DianYuan> list;

    // 判断角色的第一个状态量
    private static final int TAG_ADMIN=1;

    // 判断角色的第二个状态量
    private static final int TAG_DIANZHANG=2;

    // 判断角色的第三个状态量
    private static final int TAG_DIANYUAN=3;

    /**
     * 构造器初始化参数
     * @param context XZHL1210_ClerkDisplayActivity的上下文
     * @param list 店员信息列表数据
     */
    public XZHL1210_ListViewAdapter(Context context, List<XZHL1210_DianYuan> list) {

        // Log开始
        LogUtil.logStart();
        this.inflater = LayoutInflater.from(context);
        this.list = list;

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 改变数据源之后更新适配器
     * @param list 店员信息列表更新的数据
     */
    public void changeData(List<XZHL1210_DianYuan> list) {

        // Log开始
        LogUtil.logStart();
        this.setList(list);
        this.notifyDataSetChanged();

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 更改适配器数据的方法
     * @param list 店员信息列表更新的数据
     */
    public void setList(List<XZHL1210_DianYuan> list) {

        // Log开始
        LogUtil.logStart();
        if(list != null){
            this.list = list;
        }
        else{
            this.list = new ArrayList<XZHL1210_DianYuan>();
        }

        // Log结束
        LogUtil.logEnd();
    }

    /**
     * 重写自定义配置器的getCount方法
     * @return 返回条目个数
     */
    @Override
    public int getCount() {

        // Log开始
        LogUtil.logStart();

        // Log结束
        LogUtil.logEnd();
        return list.size();
    }

    /**
     * 重写自定义配置器的getItem方法
     * @param position 条目的位置
     * @return 返回对应位置的对象
     */
    @Override
    public XZHL1210_DianYuan getItem(int position) {

        // Log开始
        LogUtil.logStart();

        // Log结束
        LogUtil.logEnd();
        return list.get(position);
    }

    /**
     * 重写自定义配置器的getItemId方法
     * @param position 条目位置
     * @return 返回条目id
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 轮询给Item条目附上数据
     * @param position 条目位置
     * @param convertView 转换的视图对象
     * @param parent 视图树
     * @return 转换的视图显示
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Log开始
        LogUtil.logStart();
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();

           // 加载listView的自定义Item视图
            convertView = inflater.inflate(R.layout.xzhl1210_listviewitem, null);
            holder.tvXuhao = (TextView) convertView.findViewById(R.id.tv_qnumber_1210);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_xingming_1210);
            holder.tvLoginName = (TextView) convertView.findViewById(R.id.tv_dengluming_1210);
            holder.tvQuanxian = (TextView) convertView.findViewById(R.id.tv_quanxian_1210);
            holder.tvId = (TextView) convertView.findViewById(R.id.tv_dianyuanyilanid_1210);
            holder.tvTel = (TextView) convertView.findViewById(R.id.tv_dianhua_1210);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

            // 给自定一个的Item视图上的控件附上数据
        holder.tvXuhao.setText((position+1) + XZHL1210_Constants.DEFAULT);
        holder.tvName.setText(getItem(position).getName());
        holder.tvLoginName.setText(getItem(position).getLoginName());
        int role=Integer.parseInt(getItem(position).getRole());
        switch(role){
        case XZHL1210_ListViewAdapter.TAG_ADMIN:
            holder.tvQuanxian.setText(XZHL1210_ListViewAdapter.ADMIN);
            break;
        case XZHL1210_ListViewAdapter.TAG_DIANZHANG:
            holder.tvQuanxian.setText(XZHL1210_ListViewAdapter.DIANZHANG);
            break;
        case XZHL1210_ListViewAdapter.TAG_DIANYUAN:
            holder.tvQuanxian.setText(XZHL1210_ListViewAdapter.DIANYUAN);
            break;
        default:
            holder.tvQuanxian.setText(XZHL1210_ListViewAdapter.DEFAULT);
            break;
        }
        holder.tvId.setText(getItem(position).getId());
        holder.tvTel.setText(getItem(position).getTel());

        // Log结束
        LogUtil.logEnd();
        return convertView;
    }

    /**
     * item视图控件的对应类
     */
    static class ViewHolder {
        TextView tvXuhao;
        TextView tvName;
        TextView tvLoginName;
        TextView tvQuanxian;
        TextView tvId;
        TextView tvTel;
    }

}
