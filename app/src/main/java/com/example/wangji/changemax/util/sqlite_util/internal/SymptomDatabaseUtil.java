package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Symptom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class SymptomDatabaseUtil {
    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_symptom";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "symptom_id";
    private static final String KEY_NAME = "symptom_name";
    private static final String KEY_TRANS = "symptom_trans";
    private static final String KEY_INTRO = "symptom_intro";
    private static final String KEY_CAUSE = "symptom_cause";
    private static final String KEY_SYMPTOMATIC_DETAILS_CONTENT = "symptomatic_details_content";
    private static final String KEY_SUGGESTED_TREATMENT_DEPARTMENT = "suggested_treatment_department";


    private SQLiteDatabase mDatabase;
    private Context mContext;
    private SymptomDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public SymptomDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new SymptomDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
        String sql = "select " + KEY_ID + " , " + key + " from " + TABLE_NAME + " where " + key + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);

        return convertToMatchAttribute(results, key);
    }


    // 通过属性查询当前数据
    public List<MatchAttribute> queryData(String key, String name) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, key},
                KEY_NAME + "=" + name, null, null, null, null
        );
        return convertToMatchAttribute(results, key);
    }


    // 查询一条所有数据
    public List<Symptom> querySymptom(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_TRANS, KEY_INTRO, KEY_CAUSE, KEY_SYMPTOMATIC_DETAILS_CONTENT, KEY_SUGGESTED_TREATMENT_DEPARTMENT},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToSymptom(results);
    }

    // 通过id查询一条所有数据
    public List<Symptom> queryDataById(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_TRANS, KEY_INTRO, KEY_CAUSE, KEY_SYMPTOMATIC_DETAILS_CONTENT, KEY_SUGGESTED_TREATMENT_DEPARTMENT},
                KEY_ID + "=" + id,
                null, null, null, null);
        if (results == null) {
            return null;
        }
        return convertToSymptom(results);
    }


    // 通过id查询一条症状名称
    public List<MatchAttribute> queryDataNameById(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME},
                KEY_ID + "=" + id,
                null, null, null, null);
        if (results == null) {
            return null;
        }
        return convertToMatchAttribute(results, KEY_NAME);
    }

    // 查询所有数据
    public List<Symptom> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_TRANS, KEY_INTRO, KEY_CAUSE, KEY_SYMPTOMATIC_DETAILS_CONTENT, KEY_SUGGESTED_TREATMENT_DEPARTMENT},
                null, null, null, null, null);
        return convertToSymptom(results);
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

    //将查询到的数据存储到List中return；
    private List<Symptom> convertToSymptom(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Symptom> mSymptomList = new ArrayList<Symptom>();
        for (int i = 0; i < resultCounts; i++) {
            Symptom symptom = new Symptom();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            symptom.setSymptom_id(cursor.getInt(0));
            symptom.setSymptom_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            symptom.setSymptom_trans(cursor.getString(cursor.getColumnIndex(KEY_TRANS)));
            symptom.setSymptom_intro(cursor.getString(cursor.getColumnIndex(KEY_INTRO)));
            symptom.setSymptom_cause(cursor.getString(cursor.getColumnIndex(KEY_CAUSE)));
            symptom.setSymptomatic_details_content(cursor.getString(cursor.getColumnIndex(KEY_SYMPTOMATIC_DETAILS_CONTENT)));
            symptom.setSuggested_treatment_department(cursor.getString(cursor.getColumnIndex(KEY_SUGGESTED_TREATMENT_DEPARTMENT)));

            //在list中添加symptom对象
            mSymptomList.add(symptom);
            cursor.moveToNext();
        }
        //return；
        return mSymptomList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class SymptomDBOpenHelper extends SQLiteOpenHelper {

        public SymptomDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_TRANS + " text not null, "
                    + KEY_INTRO + " text, "
                    + KEY_CAUSE + " text, "
                    + KEY_SYMPTOMATIC_DETAILS_CONTENT + " text, "
                    + KEY_SUGGESTED_TREATMENT_DEPARTMENT + " text "
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
