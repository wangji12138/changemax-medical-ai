package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.DiseaseOrPa;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangJi.
 */

public class DiseaseOrPaDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_disease_or_pa";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "disease_id";
    private static final String KEY_T_ID = "t_disease_id";
    private static final String KEY_NAME = "disease_name";
    private static final String KEY_TRANS = "disease_trans";
    private static final String KEY_ORGAN_NAME = "organ_name";
    private static final String KEY_PART_NAME = "part_name";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private DiseaseOrPaDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public DiseaseOrPaDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new DiseaseOrPaDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
    public List<MatchAttribute> fuzzyQueryData(String key, String fuzzyContent) {
        String[] selectioinArgs = {"%" + fuzzyContent + "%"};//注意：这里没有单引号
        String sql = "select " + KEY_T_ID + " , " + key + " from " + TABLE_NAME + " where " + key + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);

        return convertToMatchAttribute(results, key);
    }


    // 查询一条数据
    public List<DiseaseOrPa> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_T_ID, KEY_NAME, KEY_TRANS, KEY_ORGAN_NAME, KEY_PART_NAME},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToDiseaseOrPa(results);
    }


    // 查询所有数据
    public List<DiseaseOrPa> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_T_ID, KEY_NAME, KEY_TRANS, KEY_ORGAN_NAME, KEY_PART_NAME},
                null, null, null, null, null);
        return convertToDiseaseOrPa(results);
    }

    //将查询到的数据存储到List中return；
    private List<DiseaseOrPa> convertToDiseaseOrPa(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<DiseaseOrPa> mDiseaseOrPaList = new ArrayList<DiseaseOrPa>();
        for (int i = 0; i < resultCounts; i++) {
            DiseaseOrPa diseaseOrPa = new DiseaseOrPa();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            diseaseOrPa.setDisease_id(cursor.getInt(0));
            diseaseOrPa.setT_disease_id(cursor.getInt(cursor.getColumnIndex(KEY_T_ID)));
            diseaseOrPa.setDisease_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            diseaseOrPa.setDisease_trans(cursor.getString(cursor.getColumnIndex(KEY_TRANS)));
            diseaseOrPa.setOrgan_name(cursor.getString(cursor.getColumnIndex(KEY_ORGAN_NAME)));
            diseaseOrPa.setPart_name(cursor.getString(cursor.getColumnIndex(KEY_PART_NAME)));

            //在list中添加diseaseOrPa对象
            mDiseaseOrPaList.add(diseaseOrPa);
            cursor.moveToNext();
        }
        //return；
        return mDiseaseOrPaList;
    }

    //将查询到的数据存储到List中return；
    private List<MatchAttribute> convertToMatchAttribute(Cursor cursor, String key) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<MatchAttribute> mSymptomList = new ArrayList<MatchAttribute>();
        for (int i = 0; i < resultCounts; i++) {
            MatchAttribute ma = new MatchAttribute();
            ma.setObjectId(cursor.getInt(0));
            ma.setAttributeContent(cursor.getString(cursor.getColumnIndex(key)));
            mSymptomList.add(ma);
            cursor.moveToNext();
        }
        //return；
        return mSymptomList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class DiseaseOrPaDBOpenHelper extends SQLiteOpenHelper {

        public DiseaseOrPaDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_T_ID + " integer, "
                    + KEY_NAME + " text, "
                    + KEY_TRANS + " text, "
                    + KEY_ORGAN_NAME + " text, "
                    + KEY_PART_NAME + " text "
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