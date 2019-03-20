package com.example.wangji.changemax.dao.external;

import android.content.Context;

import com.example.wangji.changemax.model.external.LookAndAsk;
import com.example.wangji.changemax.util.sqlite_util.external.LookAndAskDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */
public class LookAndAskDao {
    private Context myContext;
    private LookAndAskDatabaseUtil laaDatabaseUtil;

    public LookAndAskDao(Context myContext) {
        this.myContext = myContext;
        laaDatabaseUtil = new LookAndAskDatabaseUtil(myContext);
    }

    public void createLaa(LookAndAsk lookAndAsk) {
        laaDatabaseUtil.openDataBase();
        laaDatabaseUtil.insertData(lookAndAsk);
        laaDatabaseUtil.closeDataBase();
    }

    public void updateAnalysisResult(String uid, String analysisResult) {
        uid = "'" + uid + "'";
        laaDatabaseUtil.openDataBase();
        laaDatabaseUtil.updateData(uid, "R", analysisResult);
        laaDatabaseUtil.closeDataBase();
    }

    public void updateAnalysisResultScore(String uid, String score) {
        uid = "'" + uid + "'";
        laaDatabaseUtil.openDataBase();
        laaDatabaseUtil.updateData(uid, "S", score);
        laaDatabaseUtil.closeDataBase();
    }


    public LookAndAsk read(String uid) {
        return null;
    }

    public   List<LookAndAsk>  readAll() {

        laaDatabaseUtil.openDataBase();
        List<LookAndAsk> laaList = laaDatabaseUtil.queryDataList();
        laaDatabaseUtil.closeDataBase();
        return laaList;
    }
}
