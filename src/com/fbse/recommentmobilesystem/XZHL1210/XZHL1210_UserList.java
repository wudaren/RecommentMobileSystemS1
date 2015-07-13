/*  XZHL1210_UserList.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                             */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1210                                                                                           */
/*  画面名     ：店员一览                                                                                             */
/*  实现功能 ：对店员信息进行一览的实体类。                                                                           */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1210;

import java.util.List;

/**
 * 封装状态量和店员信息的实体类
 */
public class XZHL1210_UserList {

    // 返回的状态量
    private String success;

    // 店员信息集合
    private List<XZHL1210_DianYuan> user;

    /**
     * 获取success
     * @return success
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置success
     * @param success success 设置值
     */
    public void setSuccess(String success) {
        this.success = success;
    }

    /**
     * 获取user
     * @return user
     */
    public List<XZHL1210_DianYuan> getUser() {
        return user;
    }

    /**
     * 设置user
     * @param user user 设置值
     */
    public void setUser(List<XZHL1210_DianYuan> user) {
        this.user = user;
    }

}
