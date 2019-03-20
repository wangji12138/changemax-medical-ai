package com.example.wangji.changemax.dao.internal;


import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Symptom;
import com.example.wangji.changemax.util.sqlite_util.internal.SymptomDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class SymptomDao {
    private Context myContext;
    private SymptomDatabaseUtil databaseUtil;

    private static final String KEY_ID = "symptom_id";
    private static final String KEY_NAME = "symptom_name";
    private static final String KEY_TRANS = "symptom_trans";
    private static final String KEY_INTRO = "symptom_intro";
    private static final String KEY_CAUSE = "symptom_cause";
    private static final String KEY_SYMPTOMATIC_DETAILS_CONTENT = "symptomatic_details_content";
    private static final String KEY_SUGGESTED_TREATMENT_DEPARTMENT = "suggested_treatment_department";

    public SymptomDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new SymptomDatabaseUtil(myContext);
    }

    /**
     * 根据名称查询指定症状所有信息
     *
     * @param name
     * @return
     */
    public List<Symptom> getSymptomByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<Symptom> list = databaseUtil.querySymptom(KEY_NAME, name);
        databaseUtil.closeDataBase();
        return list;
    }

//    /**
//     * 根据名称查询指定症状名称
//     *
//     * @param name
//     * @return
//     */
//    public List<MatchAttribute> getDataByName(String name) {
//        databaseUtil.openDataBase();
//        name = "'" + name + "'";
//        List<MatchAttribute> maList = databaseUtil.queryData(KEY_NAME, name);
//        databaseUtil.closeDataBase();
//        return maList;
//    }


    /**
     * 根据名称查询指定症状简介
     *
     * @param name
     * @return
     */
    public List<MatchAttribute> getDataIntroByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<MatchAttribute> maList = databaseUtil.queryData(KEY_INTRO, name);
        databaseUtil.closeDataBase();
        return maList;
    }
    /**
     * 根据id查询指定症状所有信息
     *
     * @param id
     * @return
     */
    public List<Symptom> getSymptomById(int id) {
        databaseUtil.openDataBase();
        //        id = "'" + id + "'";
        List<Symptom> list = databaseUtil.queryDataById(id);
        databaseUtil.closeDataBase();
        return list;
    }


    /**
     * 根据id查询指定症状名称
     *
     * @param id
     * @return
     */
    public List<MatchAttribute> getSymptomNameById(int id) {
        databaseUtil.openDataBase();
        //        id = "'" + id + "'";
        List<MatchAttribute> list = databaseUtil.queryDataNameById(id);
        databaseUtil.closeDataBase();
        return list;
    }

    /**
     * 获取所有症状所有信息
     *
     * @return
     */
    public List<Symptom> getAllSymptom() {
        databaseUtil.openDataBase();
        List<Symptom> allList = databaseUtil.queryDataList();
        databaseUtil.closeDataBase();
        return allList;
    }

    /**
     * 模糊查询症状名称
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyName(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_NAME, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询症状简介
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyIntro(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_INTRO, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询症状起因
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyCause(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_CAUSE, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询症状诊断详述内容
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyDetails(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_SYMPTOMATIC_DETAILS_CONTENT, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

}
