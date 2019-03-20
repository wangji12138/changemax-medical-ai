package com.example.wangji.changemax.service.external;

import android.content.Context;

import com.example.wangji.changemax.dao.external.QuestionDao;
import com.example.wangji.changemax.model.external.Question;

import java.util.List;

/**
 * Created by WangJi.
 */
public class QuestionService {
    private Context myContext;
    private QuestionDao questionDao;

    public QuestionService(Context myContext) {
        this.myContext = myContext;
        questionDao = new QuestionDao(myContext);
    }

    public void add(Question question) {
        questionDao.add(question);
    }

    public Question getQuestionByFuzzyQuestionString(String userMessage) {
        List<Question> questionList = questionDao.getQuestionByFuzzyQuestionString(userMessage);
        if (questionList != null && questionList.size() > 0) {
            return questionList.get(0);
        } else {
            return null;
        }

    }

    public Question read(String uid) {
        List<Question> questionList = questionDao.read(uid);
        if (questionList != null && questionList.size() > 0){
            return questionDao.read(uid).get(0);
        }else{
            return null;
        }
    }

    public List<Question> readAll() {
        return questionDao.readAll();
    }

}
