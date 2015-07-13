/*  XZHL0810_GoodsItemBean.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                        */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                       */
/*  画面ＩＤ     ：XZHL0810                                                                                               */
/*  画面名     ：商品信息查询                                                                                                */
/*  实现功能 ：商品列表的基类                                                                                            */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                         */
/*      1   2014/05/19   V01L01      FBSE)高振川      新規作成                                                      */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL0810;

/**
 *  商品列表的Bean类
 *
 *  完成对商品列表的数据的封装
 */
import java.io.Serializable;

/**
 *  商品列表显示画面
 *
 *  完成对商品列表的显示
 */
public class XZHL0810_GoodsItemBean implements Serializable{

     // 基类标号
    private static final long serialVersionUID = -6818473572165209325L;

    // 商品id
    private String id;

    // 商品实际销售价
    private String realityPrice;

    // 商品市场价格
    private String price;

    // 商品货号
    private String goodsNumber;

    // 商品库存
    private String stocks;

    // 商品供应商
    private String supplier;

    // 商品价格
    private String goodsPrice;

    // 商品名称
    private String goodsName;

    // 商品品类
    private String goodsType;

    /**
     * 获取商品id
     * @return id 返回商品id
     */
    public String getId() {
        return id;
    }

    /**
     * 设定 商品id
     * @param id 商品id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取商品实际销售价
     * @return id 返回 商品实际销售价
     */
    public String getRealityPrice() {
        return realityPrice;
    }

    /**
     * 设定  商品实际销售价
     * @param realityPrice 商品实际销售价
     */
    public void setRealityPrice(String realityPrice) {
        this.realityPrice = realityPrice;
    }

    /**
     * 获取 商品市场价格
     * @return id 返回   商品市场价格
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设定 商品市场价格
     * @param price 商品市场价格
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 获取  商品货号
     * @return id 返回  商品货号
     */
    public String getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * 设定 商品货号
     * @param goodsNumber 商品货号
     */
    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * 获取  商品库存
     * @return id 返回  商品库存
     */
    public String getStocks() {
        return stocks;
    }

    /**
     * 设定 商品库存
     * @param stocks 商品库存
     */
    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    /**
     * 获取 商品供应商
     * @return id 返回 商品供应商
     */
    public String getSupplier() {
        return supplier;
    }

    /**
     * 设定 商品供应商
     * @param supplier 商品供应商
     */
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    /**
     * 获取  商品价格
     * @return id 返回  商品价格
     */
    public String getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 设定 商品价格
     * @param goodsPrice 商品价格
     */
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 获取  商品名称
     * @return id 返回  商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设定 商品名称
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取  商品品类
     * @return id 返回  商品品类
     */
    public String getGoodsType() {
        return goodsType;
    }

    /**
     * 设定 商品品类
     * @param goodsType 商品品类
     */
    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 获取  类标识
     * @return id 返回  类标识
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
