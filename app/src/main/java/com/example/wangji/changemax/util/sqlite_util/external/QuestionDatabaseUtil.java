package com.example.wangji.changemax.util.sqlite_util.external;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.example.wangji.changemax.model.external.KeyWord;
import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.model.internal.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class QuestionDatabaseUtil {

    private static final String DB_NAME = "medical_library.db";// 数据库名称
    private static final String TABLE_NAME = "t_question";// 数据表名称
    private static final int DB_VERSION = 1;// 数据库版本

    // 表的字段名
    private static final String KEY_ID = "question_id";
    private static final String KEY_UID = "question_uid";
    private static final String KEY_QUESTION_STRING = "question_string";
    private static final String KEY_ANSWER_STRING = "answer_string";
    private static final String KEY_DATE = "question_date";
    private static final String KEY_ALL_WORD = "question_all_word";
    private static final String KEY_BURDEN_KEYWORD = "question_burden_keyword";
    private static final String KEY_MEDICINE_KEYWORD = "question_medicine_keyword";

    private SQLiteDatabase mDatabase;
    private Context mContext;
    private QuestionDBOpenHelper mDbOpenHelper;//数据库打开帮助类

    public QuestionDatabaseUtil(Context context) {
        mContext = context;
    }


    // 打开数据库
    public void openDataBase() {
        mDbOpenHelper = new QuestionDBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
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
    public long insertData(Question question) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, question.getUid());
        values.put(KEY_QUESTION_STRING, question.getQuestionString());
        values.put(KEY_ANSWER_STRING, question.getAnswerString());
        values.put(KEY_DATE, question.getDate());
        values.put(KEY_ALL_WORD, listToString(question.getAllWList()));
        values.put(KEY_BURDEN_KEYWORD, listToString(question.getBurdenKwList()));
        values.put(KEY_MEDICINE_KEYWORD, listToString(question.getMedicineKwList()));

        return mDatabase.insert(TABLE_NAME, null, values);
    }


    // 删除一条数据
    public int deleteData(String uid) {
        return mDatabase.delete(TABLE_NAME, KEY_UID + "=" + uid, null);
    }

    // 更新一条数据
    public long updateData(Question question) {
        ContentValues values = new ContentValues();
        values.put(KEY_UID, question.getUid());
        values.put(KEY_QUESTION_STRING, question.getQuestionString());
        values.put(KEY_ANSWER_STRING, question.getAnswerString());
        values.put(KEY_DATE, question.getDate());
        values.put(KEY_ALL_WORD, listToString(question.getAllWList()));
        values.put(KEY_BURDEN_KEYWORD, listToString(question.getBurdenKwList()));
        values.put(KEY_MEDICINE_KEYWORD, listToString(question.getMedicineKwList()));

        return mDatabase.update(TABLE_NAME, values, KEY_UID + "=" + question.getUid(), null);
    }

    // 查询一条数据
    public List<Question> queryData(String uid) {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_QUESTION_STRING, KEY_ANSWER_STRING, KEY_DATE, KEY_ALL_WORD, KEY_BURDEN_KEYWORD, KEY_MEDICINE_KEYWORD},
                KEY_UID + "=" + uid,
                null, null, null, null);
        return convertToQuestion(results);
    }


    //模糊查询
    public List<Question> fuzzyQueryData(String fuzzyContent) {
        String[] selectioinArgs = {"%" + fuzzyContent + "%"};//注意：这里没有单引号
        String sql = "select " + KEY_ID + "," + KEY_UID + "," + KEY_QUESTION_STRING + "," + KEY_ANSWER_STRING + "," + KEY_DATE + "," + KEY_ALL_WORD + "," + KEY_BURDEN_KEYWORD + "," + KEY_MEDICINE_KEYWORD
                + " from " + TABLE_NAME
                + " where " + KEY_QUESTION_STRING + " like ? ";
        Cursor results = mDatabase.rawQuery(sql, selectioinArgs);
        return convertToQuestion(results);
    }


    // 查询所有数据
    public List<Question> queryDataList() {
        Cursor results = mDatabase.query(TABLE_NAME, new String[]{KEY_ID, KEY_UID, KEY_QUESTION_STRING, KEY_ANSWER_STRING, KEY_DATE, KEY_ALL_WORD, KEY_BURDEN_KEYWORD, KEY_MEDICINE_KEYWORD},
                null, null, null, null, null);
        return convertToQuestion(results);
    }

    //将查询到的数据存储到List中return；
    private List<Question> convertToQuestion(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<Question> mQuestionList = new ArrayList<Question>();
        for (int i = 0; i < resultCounts; i++) {
            Question question = new Question();
            //存储student中的id,sId,name,cResult,mResult,eResutl
            question.setId(cursor.getInt(0));
            question.setUid(cursor.getString(cursor.getColumnIndex(KEY_UID)));
            question.setQuestionString(cursor.getString(cursor.getColumnIndex(KEY_QUESTION_STRING)));
            question.setAnswerString(cursor.getString(cursor.getColumnIndex(KEY_ANSWER_STRING)));
            question.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
            question.setAllWList(stringToList(cursor.getString(cursor.getColumnIndex(KEY_ALL_WORD))));
            question.setBurdenKwList(stringToList(cursor.getString(cursor.getColumnIndex(KEY_BURDEN_KEYWORD))));
            question.setMedicineKwList(stringToList(cursor.getString(cursor.getColumnIndex(KEY_MEDICINE_KEYWORD))));

            //在list中添加question对象
            mQuestionList.add(question);
            cursor.moveToNext();
        }
        //return；
        return mQuestionList;
    }

    private String listToString(List<KeyWord> list) {
        StringBuilder sb = new StringBuilder("");
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if ((i + 1) == list.size()) {
                    sb.append(list.get(i).toString());
                } else {
                    sb.append(list.get(i).toString() + "--");
                }
            }
        }
        return sb.toString();
    }

    private List<KeyWord> stringToList(String string) {
        if (TextUtils.isEmpty(string)) {
            return null;
        } else {
            List<KeyWord> list = new ArrayList<KeyWord>();
            String[] strings = string.split("--");
            for (String s : strings) {
                String[] ss = s.split("-");
                KeyWord keyWord = new KeyWord();
                keyWord.setId(Integer.parseInt(ss[0]));
                keyWord.setKeyWordString(ss[1]);
                keyWord.setKeyWordPartOfSpeech(ss[2]);
                list.add(keyWord);
            }
            return list;
        }
    }

    /**
     * 数据表打开帮助类
     */
    private static class QuestionDBOpenHelper extends SQLiteOpenHelper {

        public QuestionDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            final String sqlStr = "create table if not exists " + TABLE_NAME
                    + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_UID + " text not null, "
                    + KEY_QUESTION_STRING + " text, "
                    + KEY_ANSWER_STRING + " text, "
                    + KEY_DATE + " text not null, "
                    + KEY_ALL_WORD + " text, "
                    + KEY_BURDEN_KEYWORD + " text, "
                    + KEY_MEDICINE_KEYWORD + " text"
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
