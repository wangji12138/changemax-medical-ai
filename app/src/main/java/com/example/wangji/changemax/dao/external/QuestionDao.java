package com.example.wangji.changemax.dao.external;

import android.content.Context;

import com.example.wangji.changemax.model.external.Question;
import com.example.wangji.changemax.util.sqlite_util.external.QuestionDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */
public class QuestionDao {
    private Context myContext;
    private QuestionDatabaseUtil questionDatabaseUtil;

    public QuestionDao(Context myContext) {
        this.myContext = myContext;
        questionDatabaseUtil = new QuestionDatabaseUtil(myContext);
    }

    public void add(Question question) {
        questionDatabaseUtil.openDataBase();
        questionDatabaseUtil.insertData(question);
        questionDatabaseUtil.closeDataBase();
    }

    public List<Question> getQuestionByFuzzyQuestionString(String questionString) {
        questionDatabaseUtil.openDataBase();
        List<Question> questionList = questionDatabaseUtil.fuzzyQueryData(questionString);
        questionDatabaseUtil.closeDataBase();
        return questionList;

    }

    public List<Question> read(String uid) {
        questionDatabaseUtil.openDataBase();
        uid = "'" + uid + "'";
        List<Question> questionList = questionDatabaseUtil.queryData(uid);
        questionDatabaseUtil.closeDataBase();
        return questionList;

    }


    public List<Question> readAll() {
        questionDatabaseUtil.openDataBase();
        List<Question> questionList = questionDatabaseUtil.queryDataList();
        questionDatabaseUtil.closeDataBase();
        return questionList;
    }
}
