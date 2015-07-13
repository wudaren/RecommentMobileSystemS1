/**
 * XZHL0520_ListItemClickHelp.java 
 * XZHL0520_ListItemClickHelp
 * 版本信息 V1.0
 * 创建日期（2014-05-14）
 */
package com.fbse.recommentmobilesystem.XZHL0520;

import android.view.View;

/**
 * XZHL0520_ListItemClickHelp
 * @author gaozhenchuan 
 * 适配器事件接口
 * 创建日期（2014-05-14）
 * 修改者，修改日期（YYYY-MM-DD），修改内容
 */
public interface XZHL0520_ListItemClickHelp {

    /**
     * 报修记录选项触发事件
     * @param item 报修记录视图
     * @param widget 报修页面视图
     * @param position 报修记录位置下标
     * @param which 报修记录位置下标 
     */
    void onClick(View item,View widget,int position,int which);
}
