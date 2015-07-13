
package com.fbse.recommentmobilesystem.common;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static String DB_NAME = "recommentmobilesystem.db";

    private static String TABLE_NAME_GOOD = "goods";

    private static int VERSION = 1;

    private static final String NAME_TABLE_CREATE = "create table goods" + "("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "id TEXT," + "searchdate TEXT," + "supplier TEXT,"
            + "goodsNumber TEXT," + "goodsPrice TEXT," + "goodsType TEXT," + "realityPrice TEXT," + "price TEXT,"
            + "stocks TEXT)";

    private static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS users" + "(id VARCHAR,"
            + " name VARCHAR," + " image VARCHAR," + " type VARCHAR," + " tel VARCHAR," + " sex VARCHAR,"
            + " birthday VARCHAR," + " address VARCHAR," + " remarks VARCHAR)";

    public DBHelper(Context context) {

        super(context, DB_NAME, null, VERSION);
    }

    public DBHelper(Context context, String name, CursorFactory factory, int version) {

        super(context, DB_NAME, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;
        if (!tabIsExist(TABLE_NAME_GOOD)) {
            db.execSQL(NAME_TABLE_CREATE);
        }

        db.execSQL(USER_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean tabIsExist(String tabName) {

        boolean result = false;
        if (tabName == null) {
            return false;
        }
        Cursor cursor = null;
        try {

            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tabName.trim()
                    + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }

}
