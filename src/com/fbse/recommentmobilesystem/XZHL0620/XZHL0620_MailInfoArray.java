/*  XZHL1810_HotShowArray.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                         */
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

import java.io.Serializable;

/**
 * 邮寄一览类的item
 *
 * 完成所有邮寄信息item
 */
public class XZHL0620_MailInfoArray implements Serializable {

    // vsesion ID
    private static final long serialVersionUID = 1L;

    // id
    private int id;

    // 序号
    private String number;

    // 寄件人姓名
    private String senderName;

    // 寄件人电话
    private String senderTel;

    // 寄件人地址
    private String senderAddress;

    // 寄件人街道
    private String senderStreet;

    // 收件人姓名
    private String addresseeName;

    // 收件人电话
    private String addresseeTel;

    // 收件人地址
    private String addresseeAddress;

    // 收件人街道
    private String addresseeStreet;

    // 状态
    private int state;

    /**
     * 空构造方法
     */
    public XZHL0620_MailInfoArray() {
    }

    /**
     * id的get方法
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * id的set方法
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * vsesion ID的get方法
     *
     * @return serialVersionUID
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 序号的get方法
     *
     * @return number 序号
     */
    public String getNumber() {
        return number;
    }

    /**
     * 序号的set方法
     *
     * @param number 订单号
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 寄件人姓名的get方法
     *
     * @return senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * 寄件人姓名的set方法
     *
     * @param senderName 寄件人姓名
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * 寄件人电话的get方法
     *
     * @return senderTel
     */
    public String getSenderTel() {
        return senderTel;
    }

    /**
     * 寄件人电话的set方法
     *
     * @param senderTel 寄件人电话
     */
    public void setSenderTel(String senderTel) {
        this.senderTel = senderTel;
    }

    /**
     * 寄件人地址的get方法
     *
     * @return senderAddress
     */
    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * 寄件人地址的set方法
     *
     * @param senderAddress 寄件人地址
     */
    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * 寄件人街道的get方法
     *
     * @return senderStreet
     */
    public String getSenderStreet() {
        return senderStreet;
    }

    /**
     * 寄件人街道的set方法
     *
     * @param senderStreet 寄件人街道
     */
    public void setSenderStreet(String senderStreet) {
        this.senderStreet = senderStreet;
    }

    /**
     * 收件人姓名的get方法
     *
     * @return addresseeName
     */
    public String getAddresseeName() {
        return addresseeName;
    }

    /**
     * 收件人姓名的set方法
     *
     * @param addresseeName 收件人姓名
     */
    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    /**
     * 收件人电话的get方法
     *
     * @return addresseeTel
     */
    public String getAddresseeTel() {
        return addresseeTel;
    }

    /**
     * 收件人电话的set方法
     *
     * @param addresseeTel 收件人电话
     */
    public void setAddresseeTel(String addresseeTel) {
        this.addresseeTel = addresseeTel;
    }

    /**
     * 收件人地址的get方法
     *
     * @return addresseeAddress
     */
    public String getAddresseeAddress() {
        return addresseeAddress;
    }

    /**
     * 收件人地址的set方法
     *
     * @param addresseeAddress 收件人地址
     */
    public void setAddresseeAddress(String addresseeAddress) {
        this.addresseeAddress = addresseeAddress;
    }

    /**
     * 收件人街道的get方法
     *
     * @return addresseeStreet
     */
    public String getAddresseeStreet() {
        return addresseeStreet;
    }

    /**
     * 收件人街道的set方法
     *
     * @param addresseeStreet 收件人街道
     */
    public void setAddresseeStreet(String addresseeStreet) {
        this.addresseeStreet = addresseeStreet;
    }

    /**
     * 状态的get方法
     *
     * @return state
     */
    public int getState() {
        return state;
    }

    /**
     * 状态的set方法
     *
     * @param state 状态
     */
    public void setState(int state) {
        this.state = state;
    }

}
