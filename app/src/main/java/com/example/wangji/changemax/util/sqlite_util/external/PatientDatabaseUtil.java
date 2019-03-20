package com.example.wangji.changemax.util.sqlite_util.external;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.Patient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class PatientDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_patient";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "patient_id";
    private static final String KEY_UID = "patient_uid";
    private static final String KEY_DATE = "patient_date";
    private static final String KEY_NAME = "patient_name";
    private static final String KEY_GENDER = "patient_gender";
    private static final String KEY_AGE = "patient_age";
    private static final String KEY_PART_ORGAN = "patient_part_organ";
    private static final String KEY_SYMPTOM = "patient_symptom";
    private static final String KEY_DISEASE = "patient_disease";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private PatientDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public PatientDatabaseUtil(Context context) {
        mContext = context;
    }


    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new PatientDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
    public long insertData(Patient patient) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, patient.getUid());
        values.put(KEY_DATE, patient.getDate());
        values.put(KEY_NAME, patient.getPatientName());
        values.put(KEY_GENDER, patient.getPatientGender());
        values.put(KEY_AGE, patient.getPatientAge());
        values.put(KEY_PART_ORGAN, patient.getPatientPartOrgan());
        values.put(KEY_SYMPTOM, patient.getSymptomString());
        values.put(KEY_DISEASE, patient.getDiseaseString());

        return mDatabase.insert(TABLE_NAME, null, values);
    }


    // 删除一条数据
    public int deleteData(String uid) {
        return mDatabase.delete(TABLE_NAME, KEY_UID + "=" + uid, null);
    }

    // 更新一条数据
    public long updateData(Patient patient) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, patient.getUid());
        values.put(KEY_DATE, patient.getDate());
        values.put(KEY_NAME, patient.getPatientName());
        values.put(KEY_GENDER, patient.getPatientGender());
        values.put(KEY_AGE, patient.getPatientAge());
        values.put(KEY_PART_ORGAN, patient.getPatientPartOrgan());
        values.put(KEY_SYMPTOM, patient.getSymptomString());
        values.put(KEY_DISEASE, patient.getDiseaseString());

        return mDatabase.update(TABLE_NAME, values, KEY_UID + "=" + patient.getUid(), null);
    }

    // 查询一条数据
    public List<Patient> queryData(String uid) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_DATE, KEY_NAME, KEY_GENDER, KEY_AGE, KEY_PART_ORGAN, KEY_SYMPTOM, KEY_DISEASE},
                KEY_UID + "=" + uid,
                null, null, null, null);
        return convertToPatient(results);
    }


    // 查询所有数据
    public List<Patient> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_DATE, KEY_NAME, KEY_GENDER, KEY_AGE, KEY_PART_ORGAN, KEY_SYMPTOM, KEY_DISEASE},
                null, null, null, null, null);
        return convertToPatient(results);
    }

    //将查询到的数据存储到List中return；
    private List<Patient> convertToPatient(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Patient> mPatientList = new ArrayList<Patient>();
        for (int i = 0; i < resultCounts; i++) {
            Patient patient = new Patient();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            patient.setId(cursor.getInt(0));
            patient.setUid(cursor.getString(cursor.getColumnIndex(KEY_UID)));
            patient.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            patient.setPatientName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            patient.setPatientGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
            patient.setPatientAge(cursor.getString(cursor.getColumnIndex(KEY_AGE)));
            patient.setPatientPartOrgan(cursor.getString(cursor.getColumnIndex(KEY_PART_ORGAN)));
            patient.setSymptomString(cursor.getString(cursor.getColumnIndex(KEY_SYMPTOM)));
            patient.setDiseaseString(cursor.getString(cursor.getColumnIndex(KEY_DISEASE)));

            //在list中添加patient对象
            mPatientList.add(patient);
            cursor.moveToNext();
        }
        //return；
        return mPatientList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class PatientDBOpenHelper extends SQLiteOpenHelper {

        public PatientDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_UID + " text not null, "
                    + KEY_DATE + " text not null, "
                    + KEY_NAME + " text, "
                    + KEY_GENDER + " text, "
                    + KEY_AGE + " text, "
                    + KEY_PART_ORGAN + " text, "
                    + KEY_SYMPTOM + " text, "
                    + KEY_DISEASE + " text "
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
