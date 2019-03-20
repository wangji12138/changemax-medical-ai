package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Disease;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangJi.
 */

public class DiseaseDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_disease";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "disease_id";// 疾病编号
    private static final String KEY_NAME = "disease_name";// 疾病名称
    private static final String KEY_TRANS = "disease_trans";// 疾病名称音译
    private static final String KEY_ALIAS = "disease_alias";// 疾病别名
    private static final String KEY_INTRO = "disease_intro";// 疾病简介
    private static final String KEY_INCIDENCE_SITE = "disease_incidence_site";// 疾病发病部位集 KEY_ = "disease_incidence_site
    private static final String KEY_CONTAGIOUS = "disease_contagious";// 疾病的传染性
    private static final String KEY_MULTIPLE_PEOPLE = "disease_multiple_people";// 疾病多发人群
    private static final String KEY_SYMPTOM_EARLY = "disease_symptom_early";// 疾病早期症状
    private static final String KEY_SYMPTOM_LATE = "disease_symptom_late";// 疾病晚期症状
    private static final String KEY_SYMPTOM_RELATED = "disease_symptom_related";// 疾病相关症状
    private static final String KEY_SYMPTOM_INTRO = "disease_symptom_intro";// 疾病相关症状介绍
    private static final String KEY_COMPLICATION = "disease_complication";// 疾病并发症
    private static final String KEY_COMPLICATION_INTRO = "disease_complication_intro";// 疾病并发症介绍
    private static final String KEY_VISIT_DEPARTMENT = "disease_visit_department";// 疾病就诊科室
    private static final String KEY_CURE_RATE = "disease_cure_rate";// 疾病治愈率

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private DiseaseDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public DiseaseDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new DiseaseDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
    public List<Disease> queryDisease(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{
                        KEY_ID, KEY_NAME, KEY_TRANS, KEY_ALIAS,
                        KEY_INTRO, KEY_INCIDENCE_SITE, KEY_CONTAGIOUS, KEY_MULTIPLE_PEOPLE,
                        KEY_SYMPTOM_EARLY, KEY_SYMPTOM_LATE, KEY_SYMPTOM_RELATED, KEY_SYMPTOM_INTRO,
                        KEY_COMPLICATION, KEY_COMPLICATION_INTRO, KEY_VISIT_DEPARTMENT, KEY_CURE_RATE
                },
                key + "=" + keyContent, null, null, null, null
        );
        return convertToDisease(results);
    }

    // 模糊查询名称
    public List<Disease> fuzzyByNameQueryData(String name) {
        String[] selectioinArgs = {"%" + name + "%"};//注意：这里没有单引号
        String sql = "select " + KEY_ID + " , " + KEY_ID + ", " + KEY_NAME + ", " + KEY_TRANS + ", " + KEY_ALIAS + ", " +
                KEY_INTRO + ", " + KEY_INCIDENCE_SITE + ", " + KEY_CONTAGIOUS + ", " + KEY_MULTIPLE_PEOPLE + ", " +
                KEY_SYMPTOM_EARLY + ", " + KEY_SYMPTOM_LATE + ", " + KEY_SYMPTOM_RELATED + ", " + KEY_SYMPTOM_INTRO + ", " +
                KEY_COMPLICATION + ", " + KEY_COMPLICATION_INTRO + ", " + KEY_VISIT_DEPARTMENT + ", " + KEY_CURE_RATE
                + " from " + TABLE_NAME + " where " + KEY_NAME + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);
        return convertToDisease(results);
    }

    // 通过id查询一条所有数据
    public List<Disease> queryDataById(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{
                        KEY_ID, KEY_NAME, KEY_TRANS, KEY_ALIAS,
                        KEY_INTRO, KEY_INCIDENCE_SITE, KEY_CONTAGIOUS, KEY_MULTIPLE_PEOPLE,
                        KEY_SYMPTOM_EARLY, KEY_SYMPTOM_LATE, KEY_SYMPTOM_RELATED, KEY_SYMPTOM_INTRO,
                        KEY_COMPLICATION, KEY_COMPLICATION_INTRO, KEY_VISIT_DEPARTMENT, KEY_CURE_RATE
                },
                KEY_ID + "=" + id,
                null, null, null, null);
        return convertToDisease(results);
    }


    // 通过id查询一条名称
    public List<MatchAttribute> queryNameDataById(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME},
                KEY_ID + "=" + id, null, null, null, null);
        return convertToMatchAttribute(results, KEY_NAME);
    }


    // 通过id查询一条并发症
    public List<MatchAttribute> queryComplicationDataById(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_COMPLICATION},
                KEY_ID + "=" + id, null, null, null, null);
        return convertToMatchAttribute(results, KEY_COMPLICATION);
    }


    // 查询所有数据
    public List<Disease> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{
                        KEY_ID, KEY_NAME, KEY_TRANS, KEY_ALIAS,
                        KEY_INTRO, KEY_INCIDENCE_SITE, KEY_CONTAGIOUS, KEY_MULTIPLE_PEOPLE,
                        KEY_SYMPTOM_EARLY, KEY_SYMPTOM_LATE, KEY_SYMPTOM_RELATED, KEY_SYMPTOM_INTRO,
                        KEY_COMPLICATION, KEY_COMPLICATION_INTRO, KEY_VISIT_DEPARTMENT, KEY_CURE_RATE
                },
                null, null, null, null, null);
        return convertToDisease(results);
    }

    // 查询记录的总数
    public int getCount(String key, String name) {
        String sql = "select count(*) from " + TABLE_NAME;
        if (!TextUtils.isEmpty(name)) {
            sql = "select count(*) from " + TABLE_NAME + " where " + key + " like '%" + name + "%'";
            //            sql = "select count(*) from " + "(select * from " + TABLE_NAME + " where " + KEY_NAME + " like '%" + name + "%')";
        }
        Cursor c = mDatabase.rawQuery(sql, null);
        c.moveToFirst();
        int length = c.getInt(0);
        c.close();
        return length;
    }

    /**
     * 分页查询
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示的记录
     * @return 当前页的记录
     */
    public List<Disease> getAllDiseaseByPagination(String key, String name, int currentPage, int pageSize) {
        int firstResult = (currentPage - 1) * pageSize;
        int maxResult = currentPage * pageSize;
        String sql = "select * from " + TABLE_NAME + " limit ?,?";
        if (!TextUtils.isEmpty(name)) {
            sql = "select * from " + TABLE_NAME + " where " + key + " like '%" + name + "%' limit ?,?";
        }
        Cursor results = mDatabase.rawQuery(sql, new String[]{String.valueOf(firstResult), String.valueOf(maxResult)});
        return convertToDisease(results);
    }


    private List<MatchAttribute> convertToMatchAttribute(Cursor cursor, String key) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<MatchAttribute> mDiseaseList = new ArrayList<MatchAttribute>();
        for (int i = 0; i < resultCounts; i++) {
            MatchAttribute ma = new MatchAttribute();
            ma.setObjectId(cursor.getInt(0));
            ma.setAttributeContent(cursor.getString(cursor.getColumnIndex(key)));
            mDiseaseList.add(ma);
            cursor.moveToNext();
        }
        //return；
        return mDiseaseList;
    }

    //将查询到的数据存储到List中return；
    private List<Disease> convertToDisease(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Disease> mDiseaseList = new ArrayList<Disease>();
        for (int i = 0; i < resultCounts; i++) {
            Disease disease = new Disease();
            disease.setDisease_id(cursor.getInt(0));
            disease.setDisease_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            disease.setDisease_trans(cursor.getString(cursor.getColumnIndex(KEY_TRANS)));
            disease.setDisease_alias(cursor.getString(cursor.getColumnIndex(KEY_ALIAS)));
            disease.setDisease_intro(cursor.getString(cursor.getColumnIndex(KEY_INTRO)));
            disease.setDisease_incidence_site(cursor.getString(cursor.getColumnIndex(KEY_INCIDENCE_SITE)));
            disease.setDisease_contagious(cursor.getString(cursor.getColumnIndex(KEY_CONTAGIOUS)));
            disease.setDisease_multiple_people(cursor.getString(cursor.getColumnIndex(KEY_MULTIPLE_PEOPLE)));
            disease.setDisease_symptom_early(cursor.getString(cursor.getColumnIndex(KEY_SYMPTOM_EARLY)));
            disease.setDisease_symptom_late(cursor.getString(cursor.getColumnIndex(KEY_SYMPTOM_LATE)));
            disease.setDisease_symptom_related(cursor.getString(cursor.getColumnIndex(KEY_SYMPTOM_RELATED)));
            disease.setDisease_symptom_intro(cursor.getString(cursor.getColumnIndex(KEY_SYMPTOM_INTRO)));
            disease.setDisease_complication(cursor.getString(cursor.getColumnIndex(KEY_COMPLICATION)));
            disease.setDisease_complication_intro(cursor.getString(cursor.getColumnIndex(KEY_COMPLICATION_INTRO)));
            disease.setDisease_visit_department(cursor.getString(cursor.getColumnIndex(KEY_VISIT_DEPARTMENT)));
            disease.setDisease_cure_rate(cursor.getString(cursor.getColumnIndex(KEY_CURE_RATE)));

            //在list中添加disease对象
            mDiseaseList.add(disease);
            cursor.moveToNext();
        }
        return mDiseaseList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class DiseaseDBOpenHelper extends SQLiteOpenHelper {

        public DiseaseDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_TRANS + " text not null, "
                    + KEY_ALIAS + " text, "
                    + KEY_INTRO + " text, "
                    + KEY_INCIDENCE_SITE + " text, "
                    + KEY_CONTAGIOUS + " text, "
                    + KEY_MULTIPLE_PEOPLE + " text, "
                    + KEY_SYMPTOM_EARLY + " text, "
                    + KEY_SYMPTOM_LATE + " text, "
                    + KEY_SYMPTOM_RELATED + " text, "
                    + KEY_SYMPTOM_INTRO + " text, "
                    + KEY_COMPLICATION + " text, "
                    + KEY_COMPLICATION_INTRO + " text, "
                    + KEY_VISIT_DEPARTMENT + " text, "
                    + KEY_CURE_RATE + " text "
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
