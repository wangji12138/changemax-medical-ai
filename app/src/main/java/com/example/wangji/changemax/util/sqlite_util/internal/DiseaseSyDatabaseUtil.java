package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.DiseaseSy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseSyDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_disease_sy";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "disease_id";
    private static final String KEY_T_ID = "t_disease_id";
    private static final String KEY_NAME = "disease_name";
    private static final String KEY_ACCOM_SY = "disease_accom_sy";
    private static final String KEY_OSSIBLE_SY_ID = "disease_ossible_sy_id";
    private static final String KEY_OSSIBLE_SY_NAME = "disease_ossible_sy_name";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private DiseaseSyDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public DiseaseSyDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new DiseaseSyDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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

    // 通过症状id查询可能疾病id集
    public List<MatchAttribute> querySyDi(int symptom_id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_T_ID, KEY_NAME},
                KEY_OSSIBLE_SY_ID + "=" + symptom_id, null, null, null, null
        );
        if (results == null) {
            return null;
        }
        return convertToMatchAttribute(results);
    }

    // 查询一条数据
    public List<DiseaseSy> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_T_ID, KEY_NAME, KEY_ACCOM_SY, KEY_OSSIBLE_SY_ID, KEY_OSSIBLE_SY_NAME},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToDiseaseSy(results);
    }


    // 查询所有数据
    public List<DiseaseSy> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_T_ID, KEY_NAME, KEY_ACCOM_SY, KEY_OSSIBLE_SY_ID, KEY_OSSIBLE_SY_NAME},
                null, null, null, null, null);
        return convertToDiseaseSy(results);
    }

    //将查询到的数据存储到List中return；
    private List<DiseaseSy> convertToDiseaseSy(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<DiseaseSy> mDiseaseSyList = new ArrayList<DiseaseSy>();
        for (int i = 0; i < resultCounts; i++) {
            DiseaseSy diseaseSy = new DiseaseSy();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            diseaseSy.setDisease_id(cursor.getInt(0));
            diseaseSy.setT_disease_id(cursor.getInt(cursor.getColumnIndex(KEY_T_ID)));
            diseaseSy.setDisease_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            diseaseSy.setDisease_accom_sy(cursor.getString(cursor.getColumnIndex(KEY_ACCOM_SY)));
            diseaseSy.setDisease_ossible_sy_id(cursor.getInt(cursor.getColumnIndex(KEY_OSSIBLE_SY_ID)));
            diseaseSy.setDisease_ossible_sy_name(cursor.getString(cursor.getColumnIndex(KEY_OSSIBLE_SY_NAME)));

            //在list中添加diseaseSy对象
            mDiseaseSyList.add(diseaseSy);
            cursor.moveToNext();
        }
        //return；
        return mDiseaseSyList;
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
    private static class DiseaseSyDBOpenHelper extends SQLiteOpenHelper {

        public DiseaseSyDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_T_ID + " integer not null, "
                    + KEY_NAME + " text not null, "
                    + KEY_ACCOM_SY + " text, "
                    + KEY_OSSIBLE_SY_ID + " integer not null, "
                    + KEY_OSSIBLE_SY_NAME + " text not null"
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
