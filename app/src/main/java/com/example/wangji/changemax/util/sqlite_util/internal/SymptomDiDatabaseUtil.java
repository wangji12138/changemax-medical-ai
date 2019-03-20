package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.SymptomDi;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangJi.
 */

public class SymptomDiDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_symptom_di";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "symptom_id";
    private static final String KEY_T_ID = "t_symptom_id";
    private static final String KEY_NAME = "symptom_name";
    private static final String KEY_ASSOCIATION_DISEASE_ID = "symptom_association_disease_id";
    private static final String KEY_ASSOCIATION_DISEASE_NAME = "symptom_association_disease_name";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private SymptomDiDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public SymptomDiDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new SymptomDiDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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

    // 通过疾病id查询可能症状id集
    public List<MatchAttribute> queryDiSy(int disease_id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_T_ID, KEY_NAME},
                KEY_ASSOCIATION_DISEASE_ID + "=" + disease_id, null, null, null, null
        );
        return convertToMatchAttribute(results);
    }


    // 通过症状id查询可能疾病id集
    public List<MatchAttribute> querySyDi(int symptom_id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ASSOCIATION_DISEASE_ID, KEY_ASSOCIATION_DISEASE_NAME},
                KEY_T_ID + "=" + symptom_id, null, null, null, null
        );
        return convertToMatchAttribute2(results);
    }

    // 查询一条数据
    public List<SymptomDi> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_ASSOCIATION_DISEASE_ID, KEY_ASSOCIATION_DISEASE_NAME},
                key + "=" + keyContent,
                null, null, null, null);

        return convertToSymptomDi(results);
    }


    // 查询所有数据
    public List<SymptomDi> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_ASSOCIATION_DISEASE_ID, KEY_ASSOCIATION_DISEASE_NAME},
                null, null, null, null, null);
        return convertToSymptomDi(results);
    }

    //将查询到的数据存储到List中return；
    private List<SymptomDi> convertToSymptomDi(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<SymptomDi> mSymptomDiList = new ArrayList<SymptomDi>();
        for (int i = 0; i < resultCounts; i++) {
            SymptomDi symptomDi = new SymptomDi();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            symptomDi.setSymptom_id(cursor.getInt(0));
            symptomDi.setSymptom_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            symptomDi.setSymptom_association_disease_id(cursor.getInt(cursor.getColumnIndex(KEY_ASSOCIATION_DISEASE_ID)));
            symptomDi.setSymptom_association_disease_name(cursor.getString(cursor.getColumnIndex(KEY_ASSOCIATION_DISEASE_NAME)));

            //在list中添加symptomDi对象
            mSymptomDiList.add(symptomDi);
            cursor.moveToNext();
        }
        //return；
        return mSymptomDiList;
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

    private List<MatchAttribute> convertToMatchAttribute2(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<MatchAttribute> mDiseaseList = new ArrayList<MatchAttribute>();
        for (int i = 0; i < resultCounts; i++) {
            MatchAttribute ma = new MatchAttribute();
            ma.setObjectId(cursor.getInt(cursor.getColumnIndex(KEY_ASSOCIATION_DISEASE_ID)));
            ma.setAttributeContent(cursor.getString(cursor.getColumnIndex(KEY_ASSOCIATION_DISEASE_NAME)));
            mDiseaseList.add(ma);
            cursor.moveToNext();
        }
        //return；
        return mDiseaseList;
    }

    /**
     * 数据表打开帮助类
     */
    private static class SymptomDiDBOpenHelper extends SQLiteOpenHelper {

        public SymptomDiDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_ASSOCIATION_DISEASE_ID + " integer not null, "
                    + KEY_ASSOCIATION_DISEASE_NAME + " text not null "
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
