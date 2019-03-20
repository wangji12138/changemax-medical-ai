package com.example.wangji.changemax.util.sqlite_util.internal;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Complication;

import java.util.ArrayList;
import java.util.List;

/**
 * 并发症—对象数据库操作工具类
 * Created by WangJi.
 */
public class ComplicationDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_complication";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "complication_id";
    private static final String KEY_T_ID = "t_disease_id";
    private static final String KEY_NAME = "complication_name";
    private static final String KEY_ASSOCIATION_DI_ID = "complication_association_disease_id";
    private static final String KEY_ASSOCIATION_DI_NAME = "complication_association_disease_name";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private ComplicationDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public ComplicationDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new ComplicationDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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

    // 通过疾病id查询可能并发疾病id集
    public List<MatchAttribute> queryDiDi(int disease_id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_T_ID, KEY_NAME},
                KEY_ASSOCIATION_DI_ID + "=" + disease_id, null, null, null, null
        );
        return convertToMatchAttribute(results);
    }

    //模糊查询
    public List<Complication> fuzzyQueryData(String key, String fuzzyContent) {
        String[] selectioinArgs = {"%" + fuzzyContent + "%"};//注意：这里没有单引号
        String sql = "select " + KEY_ID + "," + KEY_T_ID + "," + KEY_NAME + "," + KEY_ASSOCIATION_DI_ID + "," + KEY_ASSOCIATION_DI_NAME + " from " + TABLE_NAME
                + " where " + key + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);
        return convertToComplication(results);
    }

    // 查询一条数据
    public List<Complication> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_T_ID, KEY_NAME, KEY_ASSOCIATION_DI_ID, KEY_ASSOCIATION_DI_NAME},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToComplication(results);
    }


    // 查询所有数据
    public List<Complication> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_T_ID, KEY_NAME, KEY_ASSOCIATION_DI_ID, KEY_ASSOCIATION_DI_NAME},
                null, null, null, null, null);
        return convertToComplication(results);
    }

    //将查询到的数据存储到List中return；
    private List<Complication> convertToComplication(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Complication> mComplicationList = new ArrayList<Complication>();
        for (int i = 0; i < resultCounts; i++) {
            Complication complication = new Complication();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            complication.setComplication_id(cursor.getInt(0));
            complication.setT_disease_id(cursor.getInt(cursor.getColumnIndex(KEY_T_ID)));
            complication.setComplication_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            complication.setComplication_association_disease_id(cursor.getInt(cursor.getColumnIndex(KEY_ASSOCIATION_DI_ID)));
            complication.setComplication_association_disease_name(cursor.getString(cursor.getColumnIndex(KEY_ASSOCIATION_DI_NAME)));

            //在list中添加student对象
            mComplicationList.add(complication);
            cursor.moveToNext();
        }
        //return；
        return mComplicationList;
    }

    private List<MatchAttribute> convertToMatchAttribute(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<MatchAttribute> mDiseaseList = new ArrayList<MatchAttribute>();
        for (int i = 0; i < resultCounts; i++) {
            MatchAttribute ma = new MatchAttribute();
            ma.setObjectId(cursor.getInt(cursor.getColumnIndex(KEY_T_ID)));
            ma.setAttributeContent(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            mDiseaseList.add(ma);
            cursor.moveToNext();
        }
        //return；
        return mDiseaseList;
    }

    /**
     * 数据表打开帮助类
     */
    private static class ComplicationDBOpenHelper extends SQLiteOpenHelper {

        public ComplicationDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_ASSOCIATION_DI_ID + " integer not null, "
                    + KEY_ASSOCIATION_DI_NAME + " text not null" +
                    ");";
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            final String sqlStr = "DROP TABLE IF EXISTS " + TABLE_NAME;
            db.execSQL(sqlStr);
            onCreate(db);
        }
    }
}
