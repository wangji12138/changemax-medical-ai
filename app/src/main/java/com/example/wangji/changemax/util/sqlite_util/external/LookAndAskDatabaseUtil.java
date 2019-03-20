package com.example.wangji.changemax.util.sqlite_util.external;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.LookAndAsk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class LookAndAskDatabaseUtil {
    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_look_and_ask";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "look_and_ask_id";
    private static final String KEY_UID = "look_and_ask_uid";
    private static final String KEY_DATE = "look_and_ask_date";
    private static final String KEY_SCORE = "look_and_ask_score";
    private static final String KEY_DIAGNOSTIC_RESULT = "look_and_ask_diagnostic_result";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private LookAndAskDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public LookAndAskDatabaseUtil(Context context) {
        mContext = context;
    }


    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new LookAndAskDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        try {
            mDatabase = mDbOpenHelper.getWritableDatabase();// 获取可写数据库
        } catch (SQLException e) {
            mDatabase = mDbOpenHelper.getReadableDatabase();// 获取只读数据库
        }
    }

    // 关闭数据库
    public void closeDataBase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    // 插入一条数据
    public long insertData(LookAndAsk lookAndAsk) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, lookAndAsk.getUid());
        values.put(KEY_DATE, lookAndAsk.getDate());

        return mDatabase.insert(TABLE_NAME, null, values);
    }


    // 删除一条数据
    public int deleteData(String uid) {
        return mDatabase.delete(TABLE_NAME, KEY_UID + "=" + uid, null);
    }

    // 更新一条数据
    public long updateData(String uid, String key, String content) {
        ContentValues values = new ContentValues();
        if (key.equals("S")) {
            values.put(KEY_SCORE, content);
        } else {
            values.put(KEY_DIAGNOSTIC_RESULT, content);
        }

        return mDatabase.update(TABLE_NAME, values, KEY_UID + "=" + uid, null);
    }

    // 查询一条数据
    public List<LookAndAsk> queryData(String date) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_DATE, KEY_SCORE, KEY_DIAGNOSTIC_RESULT},
                KEY_DATE + "=" + date,
                null, null, null, null);
        return convertToLookAndAsk(results);
    }


    // 查询所有数据
    public List<LookAndAsk> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_DATE, KEY_SCORE, KEY_DIAGNOSTIC_RESULT},
                null, null, null, null, null);
        return convertToLookAndAsk(results);
    }

    //将查询到的数据存储到List中return；
    private List<LookAndAsk> convertToLookAndAsk(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<LookAndAsk> mLookAndAskList = new ArrayList<LookAndAsk>();
        for (int i = 0; i < resultCounts; i++) {
            LookAndAsk lookAndAsk = new LookAndAsk();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            lookAndAsk.setUid(cursor.getString(cursor.getColumnIndex(KEY_UID)));
            lookAndAsk.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            lookAndAsk.setScore(cursor.getString(cursor.getColumnIndex(KEY_SCORE)));
            lookAndAsk.setDiagnosticResult(cursor.getString(cursor.getColumnIndex(KEY_DIAGNOSTIC_RESULT)));

            //在list中添加lookAndAsk对象
            mLookAndAskList.add(lookAndAsk);
            cursor.moveToNext();
        }
        //return；
        return mLookAndAskList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class LookAndAskDBOpenHelper extends SQLiteOpenHelper {

        public LookAndAskDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_UID + " text not null, "
                    + KEY_DATE + " text not null, "
                    + KEY_SCORE + " text, "
                    + KEY_DIAGNOSTIC_RESULT + " text "
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
