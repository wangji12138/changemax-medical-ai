package com.example.wangji.changemax.dao.internal;


import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Disease;
import com.example.wangji.changemax.util.sqlite_util.internal.DiseaseDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseDao {
    private Context myContext;
    private DiseaseDatabaseUtil databaseUtil;

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

    public DiseaseDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new DiseaseDatabaseUtil(myContext);
    }

    /**
     * 根据疾病名称读取数据总数
     *
     * @param name
     * @return
     */
    public int getCountByName(String name) {
        databaseUtil.openDataBase();
        int count = databaseUtil.getCount(KEY_NAME, name);
        databaseUtil.closeDataBase();
        return count;
    }

    /**
     * 分页查询名字
     *
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<Disease> getAllDiseaseByPaginationName(String name, int currentPage, int pageSize) {
        databaseUtil.openDataBase();
        List<Disease> diseasesList = databaseUtil.getAllDiseaseByPagination(KEY_NAME, name, currentPage, pageSize);
        databaseUtil.closeDataBase();
        return diseasesList;
    }

    /**
     * 根据疾病名称读取数据总数
     *
     * @param partOrgan
     * @return
     */
    public int getCountByPartOrgan(String partOrgan) {
        databaseUtil.openDataBase();
        int count = databaseUtil.getCount(KEY_INCIDENCE_SITE, partOrgan);
        databaseUtil.closeDataBase();
        return count;
    }

    /**
     * 分页查询名字
     *
     * @param partOrgan
     * @param currentPage
     * @param pageSize
     * @return
     */
    public List<Disease> getAllDiseaseByPaginationPartOrgan(String partOrgan, int currentPage, int pageSize) {
        databaseUtil.openDataBase();
        List<Disease> diseasesList = databaseUtil.getAllDiseaseByPagination(KEY_INCIDENCE_SITE, partOrgan, currentPage, pageSize);
        databaseUtil.closeDataBase();
        return diseasesList;
    }

    /**
     * 根据名称查询指定疾病所有信息
     *
     * @param name
     * @return
     */
    public List<Disease> getDiseaseByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<Disease> diseasesList = databaseUtil.queryDisease(KEY_NAME, name);
        databaseUtil.closeDataBase();
        return diseasesList;
    }

    /**
     * 根据名称模糊查询指定疾病所有信息
     *
     * @param name
     * @return
     */
    public List<Disease> getDiseaseByFuzzyName(String name) {
        databaseUtil.openDataBase();
        List<Disease> diseaseList = databaseUtil.fuzzyByNameQueryData(name);
        databaseUtil.closeDataBase();
        return diseaseList;
    }

//    /**
//     * 根据名称查询指定疾病名称
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
     * 根据名称查询指定疾病简介
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
     * 根据id查询指定疾病所有信息
     *
     * @param id
     * @return
     */
    public List<Disease> getDiseaseById(int id) {
        databaseUtil.openDataBase();
        //        id = "'" + id + "'";
        List<Disease> list = databaseUtil.queryDataById(id);
        databaseUtil.closeDataBase();
        return list;
    }


    /**
     * 根据id查询指定症状名称
     *
     * @param id
     * @return
     */
    public List<MatchAttribute> getDiseaseNameById(int id) {
        databaseUtil.openDataBase();
        List<MatchAttribute> nameMatchAttributeList = databaseUtil.queryNameDataById(id);
        databaseUtil.closeDataBase();
        return nameMatchAttributeList;

    }

    /**
     * 根据id查询指定症状并发症
     *
     * @param id
     * @return
     */
    public List<MatchAttribute> getComplicationById(int id) {
        databaseUtil.openDataBase();
        List<MatchAttribute> complicationMatchAttributeList = databaseUtil.queryComplicationDataById(id);
        databaseUtil.closeDataBase();
        return complicationMatchAttributeList;

    }


    /**
     * 查询所有疾病所有信息
     *
     * @return
     */
    public List<Disease> getAllDisease() {
        databaseUtil.openDataBase();
        List<Disease> allDiseaseList = databaseUtil.queryDataList();
        databaseUtil.closeDataBase();
        return allDiseaseList;
    }

    /**
     * 模糊查询疾病名称
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getMaByFuzzyName(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_NAME, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询疾病别名
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyAlias(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_ALIAS, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询疾病简介
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyIntro(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_INTRO, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询疾病部位
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyIncidenceSite(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_INCIDENCE_SITE, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询疾病多发人群
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyMultiplePeople(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_MULTIPLE_PEOPLE, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询早期症状
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomEarly(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_SYMPTOM_EARLY, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询晚期症状
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomLate(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_SYMPTOM_LATE, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询相关症状
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomRelated(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_SYMPTOM_RELATED, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询相关症状简介
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomIntro(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_SYMPTOM_INTRO, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询相关并发症
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyComplication(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_COMPLICATION, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }

    /**
     * 模糊查询相关并发症简介
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyComplicationIntro(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_COMPLICATION_INTRO, fuzzyContent);
        databaseUtil.closeDataBase();
        return maList;
    }
}
