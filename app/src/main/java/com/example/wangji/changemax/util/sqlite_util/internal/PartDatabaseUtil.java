package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.internal.Part;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangJi.
 */
public class PartDatabaseUtil {
    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_part";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "part_id";
    private static final String KEY_NAME = "part_name";
    private static final String KEY_CONTAINED_ORGANS = "part_contained_organs";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private PartDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public PartDatabaseUtil(Context context) {
        mContext = context;
    }


    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new PartDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDbOpenHelper.getWritableDatabase();// 获取可写数据库
        } catch (SQLException e) {
            mDatabase = mDbOpenHelper.getReadableDatabase();// 获取只读数据库
        }

        //BuildingLibrary(mDatabase);//建表
    }

    // 关闭数据库
    public void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    // 插入一条数据
    public long insertData(Part part) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, part.getPart_name());
        values.put(KEY_CONTAINED_ORGANS, part.getPart_contained_organs());

        return mDatabase.insert(TABLE_NAME, null, values);
    }


    // 删除一条数据
    public int deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    // 更新一条数据
    public long updateData(int id, Part part) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, part.getPart_name());
        values.put(KEY_CONTAINED_ORGANS, part.getPart_contained_organs());

        return mDatabase.update(TABLE_NAME, values, KEY_ID + "=" + id, null);
    }

    // 查询一条数据
    public List<Part> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CONTAINED_ORGANS},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToPart(results);
    }


    // 查询所有数据
    public List<Part> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_CONTAINED_ORGANS},
                null, null, null, null, null);
        return convertToPart(results);
    }

    //将查询到的数据存储到List中return；
    private List<Part> convertToPart(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Part> mPartList = new ArrayList<Part>();
        for (int i = 0; i < resultCounts; i++) {
            Part part = new Part();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            part.setPart_id(cursor.getInt(0));
            part.setPart_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            part.setPart_contained_organs(cursor.getString(cursor.getColumnIndex(KEY_CONTAINED_ORGANS)));

            //在list中添加part对象
            mPartList.add(part);
            cursor.moveToNext();
        }
        //return；
        return mPartList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class PartDBOpenHelper extends SQLiteOpenHelper {

        public PartDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_CONTAINED_ORGANS + " text not null "
                    + ");";
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            final String sqlStr = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(sqlStr);
            onCreate(db);
        }
    }
}
