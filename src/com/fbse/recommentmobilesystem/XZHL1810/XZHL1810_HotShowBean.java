/*  XZHL1810_HotShowBean.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO., LTD 2012                          */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ ：XZHL1810                                                                                                    */
/*  画面名   ：热区分布显示                                                                                                     */
/*  实现功能 ：进行热区显示。                                                                                                     */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/06/03   V01L01      FBSE)张海静      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1810;

import java.util.List;

/**
 * 热区分布显示类的Bean
 *
 * 完成设备的显示
 */
public class XZHL1810_HotShowBean {

    // 是否成功
    private String success;

    // 数据集合
    private List<XZHL1810_HotShowArray> hot = null;

    /**
     * 成功的get方法
     *
     * @return success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 成功的set方法
     *
     * @param success 成功
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * 数据集合的get方法
     *
     * @return mail
     */
    public List<XZHL1810_HotShowArray> getHot() {
        return hot;
    }

    /**
     * 数据集合的set方法
     *
     * @param hot 数据
     */
    public void setHot(List<XZHL1810_HotShowArray> hot) {
        this.hot = hot;
    }

}
