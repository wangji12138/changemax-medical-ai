package com.example.wangji.changemax.dao.internal;


import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.MedicalNoun;
import com.example.wangji.changemax.util.sqlite_util.internal.MedicalNounDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */


public class MedicalNounDao {
    private Context myContext;
    private MedicalNounDatabaseUtil databaseUtil;

    // 表的字段名
    private static final String KEY_ID = "noun_id";
    private static final String KEY_NAME = "noun_name";

    public MedicalNounDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new MedicalNounDatabaseUtil(myContext);
    }

    /**
     * 根据名称查询指定所有医疗词汇
     *
     * @param name
     * @return
     */
    public List<MedicalNoun> getMedicalNounByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<MedicalNoun> medicalNounList = databaseUtil.queryData(KEY_NAME, name);
        //  diseaseDU.closeDataBase();
        return medicalNounList;
    }

    /**
     * 查询所有医疗词汇
     *
     * @return
     */
    public List<MedicalNoun> getAllMedicalNoun() {
        databaseUtil.openDataBase();
        List<MedicalNoun> allMedicalNounList = databaseUtil.queryDataList();
        return allMedicalNounList;
    }

    /**
     * 模糊查询医疗词汇名称
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getMedicalNounByFuzzyName(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_NAME, fuzzyContent);
        //  diseaseDU.closeDataBase();
        return maList;
    }

}
