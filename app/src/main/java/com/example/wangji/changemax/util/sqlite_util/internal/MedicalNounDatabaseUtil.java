package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.MedicalNoun;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangJi.
 */

public class MedicalNounDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_medical_noun";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "noun_id";
    private static final String KEY_NAME = "noun_name";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private MedicalNounDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public MedicalNounDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new MedicalNounDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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

    //模糊查询
    public List<MatchAttribute>  fuzzyQueryData(String key, String fuzzyContent) {
        String[] selectioinArgs = {"%" + fuzzyContent + "%"};//注意：这里没有单引号
        String sql = "select " + KEY_ID + " , " + key + " from " + TABLE_NAME + " where " + key + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);

        return convertToMatchAttribute(results, key);
    }

    // 通过属性查询数据
    public List<MedicalNoun> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToMedicalNoun(results);
    }

    // 通过id查询一条数据
    public List<MedicalNoun> queryDataById(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME},
                KEY_ID + "=" + id,
                null, null, null, null);
        return convertToMedicalNoun(results);
    }

    // 查询所有数据
    public List<MedicalNoun> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME},
                null, null, null, null, null);
        return convertToMedicalNoun(results);
    }

    //将查询到的数据存储到List中return；
    private List<MatchAttribute> convertToMatchAttribute(Cursor cursor, String key) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<MatchAttribute> mMedicalNounList = new ArrayList<MatchAttribute>();
        for (int i = 0; i < resultCounts; i++) {
            MatchAttribute ma = new MatchAttribute();
            ma.setObjectId(cursor.getInt(0));
            ma.setAttributeContent(cursor.getString(cursor.getColumnIndex(key)));
            mMedicalNounList.add(ma);
            cursor.moveToNext();
        }
        //return；
        return mMedicalNounList;
    }

    //将查询到的数据存储到List中return；
    private List<MedicalNoun> convertToMedicalNoun(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<MedicalNoun> mMedicalNounList = new ArrayList<MedicalNoun>();
        for (int i = 0; i < resultCounts; i++) {
            MedicalNoun medicalNoun = new MedicalNoun();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            medicalNoun.setNoun_id(cursor.getInt(0));
            medicalNoun.setNoun_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));

            //在list中添加medicalNoun对象
            mMedicalNounList.add(medicalNoun);
            cursor.moveToNext();
        }
        //return；
        return mMedicalNounList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class MedicalNounDBOpenHelper extends SQLiteOpenHelper {

        public MedicalNounDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null "
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
