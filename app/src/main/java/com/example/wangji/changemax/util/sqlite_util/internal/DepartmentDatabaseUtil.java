package com.example.wangji.changemax.util.sqlite_util.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.internal.Department;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WangJi.
 */

public class DepartmentDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_department";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "department_id";
    private static final String KEY_NAME = "department_name";
    private static final String KEY_INTRO = "department_intro";
    private static final String KEY_ADDRESS = "department_address";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private DepartmentDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public DepartmentDatabaseUtil(Context context) {
        mContext = context;
    }

    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new DepartmentDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
    public long insertData(Department department) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, department.getDepartment_name());
        values.put(KEY_INTRO, department.getDepartment_intro());
        values.put(KEY_ADDRESS, department.getDepartment_address());

        return mDatabase.insert(TABLE_NAME, null, values);
    }


    // 删除一条数据
    public int deleteData(int id) {
        return mDatabase.delete(TABLE_NAME, KEY_ID + "=" + id, null);
    }

    // 更新一条数据
    public long updateData(int id, Department department) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, department.getDepartment_name());
        values.put(KEY_INTRO, department.getDepartment_intro());
        values.put(KEY_ADDRESS, department.getDepartment_address());

        return mDatabase.update(TABLE_NAME, values, KEY_ID + "=" + id, null);
    }

    //模糊查询
    public List<Department> fuzzyQueryData(String key, String fuzzyContent) {
        String[] selectioinArgs = {"%" + fuzzyContent + "%"};//注意：这里没有单引号
        String sql = "select " + KEY_ID + "," + KEY_NAME + "," + KEY_INTRO + "," + KEY_ADDRESS + " from " + TABLE_NAME
                + " where " + key + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);
        return convertToDepartment(results);
    }

    // 查询一条数据
    public List<Department> queryData(String key, String keyContent) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_INTRO, KEY_ADDRESS},
                key + "=" + keyContent,
                null, null, null, null);
        return convertToDepartment(results);
    }


    // 查询所有数据
    public List<Department> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_INTRO, KEY_ADDRESS},
                null, null, null, null, null);
        return convertToDepartment(results);
    }

    //将查询到的数据存储到List中return；
    private List<Department> convertToDepartment(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Department> mDepartmentList = new ArrayList<Department>();
        for (int i = 0; i < resultCounts; i++) {
            Department department = new Department();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            department.setDepartment_id(cursor.getInt(0));
            department.setDepartment_name(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            department.setDepartment_intro(cursor.getString(cursor.getColumnIndex(KEY_INTRO)));
            department.setDepartment_address(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));

            //在list中添加department对象
            mDepartmentList.add(department);
            cursor.moveToNext();
        }
        //return；
        return mDepartmentList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class DepartmentDBOpenHelper extends SQLiteOpenHelper {

        public DepartmentDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NAME + " text not null, "
                    + KEY_INTRO + " text not null, "
                    + KEY_ADDRESS + " text not null" +
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
