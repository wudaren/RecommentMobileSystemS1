/*  XZHL0810_GoodsDBHelper.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                        */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL0810                                                                                           */
/*  画面名     ：商品信息查询                                                                                         */
/*  实现功能 ：显示商品列表数据库的存储                                                                               */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/22   V01L01      FBSE)高振川      新規作成                                                        */
/*                                                                                                                    */
package com.fbse.recommentmobilesystem.XZHL0810;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 *  商品列表数据库管理类
 *
 *  完成商品列表显示数据增删改查
 */
public class XZHL0810_GoodsManager {

    // 数据库帮助类
    private XZHL0810_GoodsDBHelper dbhelper;

    /**
     * 数据库构造方法
     * @param Activity上下文
     */
    public XZHL0810_GoodsManager(Context context) {
        super();
        dbhelper = new XZHL0810_GoodsDBHelper(context);
    }

    /**
     * 数据保存
     * @param list 商品列表
     */
    public void save(List<XZHL0810_GoodsItemBean> list) {
        LogUtil.logStart();
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        for(int i = 0; i < list.size();  i++){
            database.execSQL("insert into goods(id,realityPrice,price,goodsName,stocks," +
                 "supplier,goodsType,goodsPrice,goodsNumber) values (?,?,?,?,?,?,?,?,?)",
                 new Object[]{list.get(i).getId(),
                        list.get(i).getRealityPrice(), list.get(i).getPrice(), list.get(i).getGoodsName(),
                        list.get(i).getStocks(), list.get(i).getSupplier(), list.get(i).getGoodsType(),
                        list.get(i).getGoodsPrice(), list.get(i).getGoodsNumber()});
        }
        database.close();
        LogUtil.logEnd();
    }

    /**
     * 数据更新
     * @param list 商品列表
     */
    public void update(List<XZHL0810_GoodsItemBean> list) {
        LogUtil.logStart();
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        for(int i = 0; i<list.size(); i++){
            database.execSQL("insert into goods(id,realityPrice,price,goodsName,stocks" +
                 ",supplier,goodsType,goodsPrice,goodsNumber)"+
                         " values (?,?,?,?,?,?,?,?)", new Object[]{ list.get(i).getId(),
                             list.get(i).getRealityPrice(), list.get(i).getPrice(),
                             list.get(i).getGoodsName(), list.get(i).getStocks(),
                             list.get(i).getSupplier(), list.get(i).getGoodsType(), list.get(i).getGoodsPrice()
                    , list.get(i).getGoodsNumber()});
        }
        database.close();
        LogUtil.logEnd();
    }

    /**
     * 数据查询
     * @param goodname 商品名称
     * @return List<XZHL0810_GoodsItemBean> 商品列表
     */
    public List<XZHL0810_GoodsItemBean> selectGoodsList(String goodname){
        LogUtil.logStart();
        SQLiteDatabase database = dbhelper.getReadableDatabase();
        List<XZHL0810_GoodsItemBean> list = new ArrayList<XZHL0810_GoodsItemBean>();
        Cursor cursor = database.rawQuery("select * from goods where goodsName like ?", new String[]{"%"+goodname+"%"});
        XZHL0810_GoodsItemBean item = null;
        while (cursor.moveToNext()) {
            item = new XZHL0810_GoodsItemBean();
            item.setGoodsName(cursor.getString(cursor.getColumnIndex("goodsName")));
            item.setGoodsPrice(cursor.getString(cursor.getColumnIndex("goodsPrice")));
            item.setId(cursor.getString(cursor.getColumnIndex("id")));
            item.setStocks(cursor.getString(cursor.getColumnIndex("stocks")));
            item.setRealityPrice(cursor.getString(cursor.getColumnIndex("realityPrice")));
            item.setSupplier(cursor.getString(cursor.getColumnIndex("supplier")));
            item.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            item.setGoodsType(cursor.getString(cursor.getColumnIndex("goodsType")));
            item.setGoodsNumber(cursor.getString(cursor.getColumnIndex("goodsNumber")));
            list.add(item);
        }
        cursor.close();
        database.close();
        LogUtil.logEnd();
        return list;
    }

    /**
     * 数据清除
     */
    public void delete(){
        LogUtil.logStart();
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        database.execSQL("delete from goods");
        database.close();
        LogUtil.logEnd();
    }

    /**
     * 数据清除
     */
    public void deleteData(XZHL0810_GoodsItemBean bean){
        LogUtil.logStart();
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        database.execSQL("delete from goods where goodsName=? and price=?",new String[]{"%"+bean.getGoodsName()+"%","%"+bean.getPrice()+"%"});
        database.close();
        LogUtil.logEnd();
    }

}
