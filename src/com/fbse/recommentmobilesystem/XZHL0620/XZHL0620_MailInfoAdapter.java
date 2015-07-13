/*  XZHL0620_MailInfoAdapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                       */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ ：XZHL0620                                                                                                    */
/*  画面名     ：邮寄信息一览                                                                                                   */
/*  实现功能 ：显示所有的邮寄信息。                                                                                                  */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/20   V01L01      FBSE)张海静      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0620;

import java.util.ArrayList;
import java.util.List;

import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.LogUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 邮寄一览类的适配器
 *
 * 完成所有邮寄信息的存储
 */
public class XZHL0620_MailInfoAdapter extends BaseAdapter {

    // 邮寄信息List
    List<XZHL0620_MailInfoArray> mailBean = new ArrayList<XZHL0620_MailInfoArray>();

    // 上下文
    private Context context;

    // 已发货状态
    private static final String STATESONE = "已发货";

    // 发货状态中
    private static final String STATESTWO = "发货中";

    // 待发货状态
    private static final String STATESTHREE = "待发货";

    // 已收货状态
    private static final String STATESFOUR = "已收货";

    // 空的常量
    private static final String KONG = "";

    // 已发货状态
    private static final int ONE = 1;

    // 发货状态中
    private static final int TWO = 2;

    // 待发货状态
    private static final int THREE = 3;

    // 已收货状态
    private static final int FOUR = 4;

    /**
     * 内部构造器
     *
     * @param mailBean 数据
     * @param context 上下文
     */
    public XZHL0620_MailInfoAdapter(List<XZHL0620_MailInfoArray> mailBean,
            Context context) {
        this.mailBean = mailBean;
        this.context = context;
    }

    /**
     * 数据大小
     * @return mailBean.size()
     */
    @Override
    public int getCount() {
        return mailBean.size();
    }

    /**
     * 得到每一项数据的get方法
     *
     * @param arg0 数值
     * @return mailBean.get(arg0)
     */
    @Override
    public Object getItem(int arg0) {
        return mailBean.get(arg0);
    }

    /**
     * 得到每一项数据的id的get方法
     *
     * @param arg0 id
     * @return arg0
     */
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    /**
     * 完成数据的封装兵线提交各前台的ListView
     *
     * @param arg0 id
     * @param view 视图
     * @param vGroup group
     * @return view
     */
    @Override
    public View getView(int arg0, View view, ViewGroup vGroup) {

        // log打印开始
        LogUtil.logStart();

        // 加载item
        view = View.inflate(context, R.layout.xzhl0620_mailitem, null);
        if (view != null) {

            // 序号的赋值
            TextView mailId = (TextView) view.findViewById(R.id.tv_mailid_xzhl0620);
            mailId.setText(arg0 + ONE + KONG);

            // 订单号的赋值
            TextView mailNumber = (TextView) view.findViewById(R.id.tv_mailnumber_xzhl0620);
            mailNumber.setText(mailBean.get(arg0).getNumber());

            // 会员名
            TextView mailName = (TextView) view.findViewById(R.id.tv_mailname_xzhl0620);
            mailName.setText(mailBean.get(arg0).getSenderName());

            // 发货状态
            TextView mailStatus = (TextView) view.findViewById(R.id.tv_mailstatus_xzhl0620);

            // 判断发货状态
            switch (mailBean.get(arg0).getState()) {

            // 状态为1
            case ONE:
                mailStatus.setText(STATESONE);
                break;

            // 状态为2
            case TWO:
                mailStatus.setText(STATESTWO);
                break;

            // 状态为3
            case THREE:
                mailStatus.setText(STATESTHREE);
                break;

            // 状态为4
            case FOUR:
                mailStatus.setText(STATESFOUR);
                break;
            default:
                break;
            }
        }

        // log打印
        LogUtil.logEnd();
        return view;
    }

}
