package com.example.wangji.changemax.util.sqlite_util.external;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wangji.changemax.model.external.PersonChat;
import com.example.wangji.changemax.model.external.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class PersonChatDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_person_chat";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "person_chat_id";
    private static final String KEY_UID = "person_chat_uid";
    private static final String KEY_MESSAGE = "person_chat_message";
    private static final String KEY_IS_ME_SEND = "person_chat_is_me_send";
    private static final String KEY_DATE = "person_chat_date";
    private static final String KEY_READ_MESSAGE = "person_chat_read_message";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private PersonChatDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public PersonChatDatabaseUtil(Context context) {
        mContext = context;
    }


    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new PersonChatDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
    public long insertData(PersonChat personChat) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, personChat.getUid());
        values.put(KEY_MESSAGE, personChat.getChatMessage());
        if (personChat.isMeSend()) {
            values.put(KEY_IS_ME_SEND, "true");
        } else {
            values.put(KEY_IS_ME_SEND, "false");
        }
        values.put(KEY_DATE, personChat.getDate());
        values.put(KEY_READ_MESSAGE, personChat.getReadChatMessage());


        return mDatabase.insert(TABLE_NAME, null, values);
    }


    // 删除一条数据
    public int deleteData(String uid) {
        return mDatabase.delete(TABLE_NAME, KEY_UID + "=" + uid, null);
    }

    // 更新一条数据
    public long updateData(PersonChat personChat) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, personChat.getUid());
        values.put(KEY_MESSAGE, personChat.getChatMessage());
        if (personChat.isMeSend()) {
            values.put(KEY_IS_ME_SEND, "true");
        } else {
            values.put(KEY_IS_ME_SEND, "false");
        }
        values.put(KEY_DATE, personChat.getDate());
        values.put(KEY_READ_MESSAGE, personChat.getReadChatMessage());

        return mDatabase.update(TABLE_NAME, values, KEY_UID + "=" + personChat.getUid(), null);
    }


    // 查询一条数据
    public List<PersonChat> queryUserData(String userMessage) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_MESSAGE, KEY_IS_ME_SEND, KEY_DATE, KEY_READ_MESSAGE},
                KEY_MESSAGE + "=" + userMessage,
                null, null, null, null);
        return convertToPersonChat(results);
    }

    // 查询一条数据
    public List<PersonChat> queryData(String uid) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_MESSAGE, KEY_IS_ME_SEND, KEY_DATE, KEY_READ_MESSAGE},
                KEY_UID + "=" + uid,
                null, null, null, null);
        return convertToPersonChat(results);
    }

    // 查询一条数据
    public List<PersonChat> queryData(int id) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_MESSAGE, KEY_IS_ME_SEND, KEY_DATE, KEY_READ_MESSAGE},
                KEY_ID + "=" + id,
                null, null, null, null);
        return convertToPersonChat(results);
    }

    // 查询所有数据
    public List<PersonChat> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_MESSAGE, KEY_IS_ME_SEND, KEY_DATE, KEY_READ_MESSAGE},
                null, null, null, null, null);
        return convertToPersonChat(results);
    }

    //将查询到的数据存储到List中return；
    private List<PersonChat> convertToPersonChat(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<PersonChat> mPersonChatList = new ArrayList<PersonChat>();
        for (int i = 0; i < resultCounts; i++) {
            PersonChat personChat = new PersonChat();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            personChat.setId(cursor.getInt(0));
            personChat.setUid(cursor.getString(cursor.getColumnIndex(KEY_UID)));
            personChat.setChatMessage(cursor.getString(cursor.getColumnIndex(KEY_MESSAGE)));
            if (cursor.getString(cursor.getColumnIndex(KEY_IS_ME_SEND)).equals("true")) {
                personChat.setMeSend(true);
            } else if (cursor.getString(cursor.getColumnIndex(KEY_IS_ME_SEND)).equals("false")) {
                personChat.setMeSend(false);
            }
            personChat.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            personChat.setReadChatMessage(cursor.getString(cursor.getColumnIndex(KEY_READ_MESSAGE)));

            //在list中添加personChat对象
            mPersonChatList.add(personChat);
            cursor.moveToNext();
        }
        //return；
        return mPersonChatList;
    }


    /**
     * 数据表打开帮助类
     */
    private static class PersonChatDBOpenHelper extends SQLiteOpenHelper {

        public PersonChatDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_UID + " text not null, "
                    + KEY_MESSAGE + " text, "
                    + KEY_IS_ME_SEND + " text, "
                    + KEY_DATE + " text, "
                    + KEY_READ_MESSAGE + " text "
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
