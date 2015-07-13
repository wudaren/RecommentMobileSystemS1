/*  XZHL1810_HotShowBean.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                          */
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

import java.util.List;

/**
 * 邮寄一览类的Bean
 *
 * 完成所有邮寄信息数据存储
 */
public class XZHL0620_MailInfoBean {

    // 是否成功
    private String success;

    // 数据集合
    private List<XZHL0620_MailInfoArray> mail = null;

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
    public List<XZHL0620_MailInfoArray> getMail() {
        return mail;
    }

    /**
     * 数据集合的set方法
     *
     * @param mail 数据
     */
    public void setMail(List<XZHL0620_MailInfoArray> mail) {
        this.mail = mail;
    }

}
