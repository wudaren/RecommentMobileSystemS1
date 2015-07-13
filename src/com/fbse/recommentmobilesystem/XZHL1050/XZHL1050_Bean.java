/*  XZHL1050_Bean.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                                 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL1050                                                                                               */
/*  画面名 ：商城产品广告一览                                                                                         */
/*  实现功能 ：商城产品广告一览的数据bean。                                                                           */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/21   V01L01      FBSE)尹小超      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1050;

import java.io.Serializable;

/**
 * 商城产品广告一览数据bean类
 * 商城产品广告一览数据bean
 */
public class XZHL1050_Bean implements Serializable {

    // 唯一序列号
    private static final long serialVersionUID = -137828657772019208L;

    // 广告标识
    private String id;

    // 活动名称
    private String name;

    // 活动开始时间
    private String beginDate;

    // 活动结束时间
    private String endDate;

    // 活动金额
    private String money;

    // 活动对象
    private String target;

    // 活动商品
    private String product;

    // 优惠方式
    private String method;

    // 优惠设置
    private String setting;

    // 活动宣言
    private String adv;

    /**
     * 获得广告标识
     * @return 广告标识
     */
    public String getId() {

        return id;
    }

    /**
     * 设置广告标识
     * @param id 广告标识
     */
    public void setId(String id) {

        this.id = id;
    }

    /**
     * 获得活动名称
     * @return 活动名称
     */
    public String getName() {

        return name;
    }

    /**
     * 设置活动名称
     * @param name 活动名称
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * 获得活动开始时间
     * @return 活动开始时间
     */
    public String getBeginDate() {

        return beginDate;
    }

    /**
     * 设置活动开始时间
     * @param beginDate 活动开始时间
     */
    public void setBeginDate(String beginDate) {

        this.beginDate = beginDate;
    }

    /**
     * 获得活动结束时间
     * @return 活动结束时间
     */
    public String getEndDate() {

        return endDate;
    }

    /**
     * 设置活动结束时间
     * @param endDate 活动结束时间
     */
    public void setEndDate(String endDate) {

        this.endDate = endDate;
    }

    /**
     * 获得活动金额
     * @return 活动金额
     */
    public String getMoney() {

        return money;
    }

    /**
     * 设置活动金额
     * @param money 活动金额
     */
    public void setMoney(String money) {

        this.money = money;
    }

    /**
     * 获得活动对象
     * @return 活动对象
     */
    public String getTarget() {

        return target;
    }

    /**
     * 设置活动对象
     * @param target 活动对象
     */
    public void setTarget(String target) {

        this.target = target;
    }

    /**
     * 获得活动商品
     * @return 活动商品
     */
    public String getProduct() {

        return product;
    }

    /**
     * 设置活动商品
     * @param product 活动商品
     */
    public void setProduct(String product) {

        this.product = product;
    }

    /**
     * 获得优惠方式
     * @return 优惠方式
     */
    public String getMethod() {

        return method;
    }

    /**
     * 设置优惠方式
     * @param method 优惠方式
     */
    public void setMethod(String method) {

        this.method = method;
    }

    /**
     * 获得优惠设置
     * @return 优惠设置
     */
    public String getSetting() {

        return setting;
    }

    /**
     * 设置优惠设置
     * @param setting 优惠设置
     */
    public void setSetting(String setting) {

        this.setting = setting;
    }

    /**
     * 获得活动宣言
     * @return 活动宣言
     */
    public String getAdv() {

        return adv;
    }

    /**
     * 设置活动宣言
     * @param adv 活动宣言
     */
    public void setAdv(String adv) {

        this.adv = adv;
    }

}
