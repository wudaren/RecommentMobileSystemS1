/*  XZHL0810_GoodsDBHelper.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                       */
/*-------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                   */
/*  项目名称 ：信智互联                                                                                              */
/*  画面ＩＤ     ：XZHL0810                                                                                            */
/*  画面名     ：商品信息查询                                                                                          */
/*  实现功能 ：显示商品列表数据库存储                                                                                  */
/*                                                                                                                    */
/*  变更历史                                                                                                           */
/*      NO  日付                       Ver         更新者                 内容                                         */
/*      1   2014/05/22   V01L01      FBSE)高振川      新規作成                                                         */
/*                                                                                                                    */
package com.fbse.recommentmobilesystem.XZHL0810;

import com.fbse.recommentmobilesystem.common.LogUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *  商品列表显示画面
 *
 *  完成对商品列表的数据存数基类
 */
public class XZHL0810_GoodsDBHelper extends  SQLiteOpenHelper {

    // 数据库对象
    private SQLiteDatabase db;

    // 数据库名称
    private static final String DB_NAME = "recommentmobilesystem.db";

    // 商品列表表名
    private static final String TABLE_NAME = "goods";

    // 数据库版本
    private static final int VERSION = 1;

    // 创建数据库表语句
    private static final String NAME_TABLE_CREATE = "create table goods" +
            "("+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "id TEXT,"+"searchdate TEXT,"
            + "supplier TEXT," + "goodsNumber TEXT,"+ "goodsPrice TEXT,"+ "goodsType TEXT,"
            + "realityPrice TEXT,"+ "price TEXT,"+ "goodsName TEXT,"+ "stocks TEXT)";

    /**
     * 数据库构造方法
     * @param context Activity上下文
     */
    public XZHL0810_GoodsDBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    /**
     * 数据库构造方法
     * @param Activity上下文
     * @param 数据库名称
     * @param 游标工厂
     * @param 数据库版本
     */
    public XZHL0810_GoodsDBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, DB_NAME, factory, VERSION);
    }

    /**
     * 初始化数据库方法
     * @param db 数据库参数
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.logStart();
        this.db=db;
        if(!tabIsExist(TABLE_NAME)){
            db.execSQL(NAME_TABLE_CREATE);
        }
        LogUtil.logEnd();
    }

    /**
     * 数据库版本更新方法
     * @param db 数据库参数
     * @param oldVersion 旧版本号
     * @param newVersion 新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 判断表名是否存在
     * @param tabName 表名
     * @return false true已存在该表，false不存在
     */
    public boolean tabIsExist(String tabName){
        LogUtil.logStart();
        boolean result = false;
        if(tabName == null){
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master where type ='table'" +
                        " and name ='"+tabName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
        return result;
    }

}
