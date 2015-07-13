/*  XZHL1710_PassengerPageChangeListener.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                */
/*  画面ＩＤ ：XZHL1710                                                                                                */
/*  画面名   ：客流统计                                                                                                     */
/*  实现功能 ：客流统计界面切换监听                                                                                                 */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/06/03   V01L01      FBSE)高振川      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1710;

import com.fbse.recommentmobilesystem.common.LogUtil;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 客流统计显示监听类
 *
 * 实现客流统计的显示画面监听
 */
public class XZHL1710_PassengerPageChangeListener implements OnPageChangeListener {

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    /**
     * 界面切换选择事件
     * @param arg0 选择的界面id
     */
    @Override
    public void onPageSelected(int arg0) {
        LogUtil.logStart();
        Animation animation = null;
        if(arg0>2){
            arg0=arg0%3;
        }
        switch (arg0) {
        case 0:
            animation = new TranslateAnimation(arg0, 0, 0, 0);
            break;
        case 1:

            animation = new TranslateAnimation(arg0, 0, 0, 0);
            break;
        default:
            break;
        }
        // True:图片停在动画结束位置
        animation.setFillAfter(true);
        animation.setDuration(300);
        LogUtil.logEnd();

    }

}
