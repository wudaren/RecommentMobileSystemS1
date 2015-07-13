/*  XZHL1710_ViewPagerAdapter.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                */
/*  画面ＩＤ ：XZHL1710                                                                                                */
/*  画面名   ：客流统计                                                                                                     */
/*  实现功能 ：客流统计画面适配                                                                                                    */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/06/03   V01L01      FBSE)高振川      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1710;

import java.util.List;

import com.fbse.recommentmobilesystem.common.LogUtil;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 客流统计适配器类
 *
 * 实现客流统计的显示
 */
public class XZHL1710_ViewPagerAdapter extends PagerAdapter {

    // 显示画面列表
    private List<View> mListViews;

    public XZHL1710_ViewPagerAdapter(List<View> mListViews) {
        super();
        this.mListViews = mListViews;
    }

    /**
     * 界面消亡
     * @param container 界面组
     * @param position 位置
     * @param object 类
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        LogUtil.logStart();
        // 删除页卡
        container.removeView(mListViews.get(position));
        LogUtil.logEnd();
    }

    /**
     * 界面初始化
     * @param container 界面组
     * @param position  位置
     * @return mListViews.get(position) View列表
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtil.logStart();
        // 添加页卡
        container.addView(mListViews.get(position), 0);
        LogUtil.logEnd();
        return mListViews.get(position);
    }

    /**
     * 界面选项大小
     * @return mListViews.size() 返回界面选项的大小
     */
    @Override
    public int getCount() {
        LogUtil.logStart();
        LogUtil.logEnd();
        // 返回页卡的数量
        return mListViews.size();
    }

    /**
     * 界面切换
     * @param arg0 界面
     * @param arg1 界面类
     * @return arg0==arg1
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        LogUtil.logStart();
        LogUtil.logEnd();
        // 官方提示这样写
        return arg0==arg1;
    }

    /**
     * 界面切换选择事件
     * @param object 选择的界面id
     * @return super.getItemPosition(object)
     */
    @Override
    public int getItemPosition(Object object) {
        LogUtil.logStart();
        LogUtil.logEnd();
        return super.getItemPosition(object);
    }
}
