package com.example.wangji.changemax.service.internal;


import android.content.Context;
import android.text.TextUtils;

import com.example.wangji.changemax.dao.internal.DiseaseDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Disease;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseService {
    private Context myContext;
    private DiseaseDao diseaseDao;

    public DiseaseService(Context myContext) {
        this.myContext = myContext;
        diseaseDao = new DiseaseDao(myContext);
    }

    /**
     * 查询表中总记录数
     */
    public int getCountByName(String name) {
        return diseaseDao.getCountByName(name);
    }

    /**
     * 分页查询名字
     */
    public List<Disease> getAllDiseaseByPaginationName(String name, int currentPage, int pageSize) {
        return diseaseDao.getAllDiseaseByPaginationName(name, currentPage, pageSize);
    }

    /**
     * 查询表中总记录数
     */
    public int getCountByPartOrgan(String name) {
        return diseaseDao.getCountByPartOrgan(name);
    }

    /**
     * 分页查询名字
     */
    public List<Disease> getAllDiseaseByPaginationPartOrgan(String name, int currentPage, int pageSize) {
        return diseaseDao.getAllDiseaseByPaginationPartOrgan(name, currentPage, pageSize);
    }

    /**
     * 根据名称查询指定疾病所有信息
     *
     * @param name
     * @return
     */
    public Disease getDiseaseByName(String name) {
        List<Disease> diseaseList = diseaseDao.getDiseaseByName(name);
        if (diseaseList != null && diseaseList.size() > 0) {
            return diseaseList.get(0);
        } else {
            return null;
        }

    }


    /**
     * 根据名称查询指定疾病所有信息
     *
     * @param name
     * @return
     */
    public List<Disease> getDiseaseByFuzzyName(String name) {
        return diseaseDao.getDiseaseByFuzzyName(name);
    }


//    /**
//     * 根据名称查询指定疾病名称
//     *
//     * @param name
//     * @return
//     */
//    public List<MatchAttribute> getDataByName(String name) {
//        return diseaseDao.getDataByName(name);
//    }

    /**
     * 根据名称查询指定疾病简介
     *
     * @param name
     * @return
     */
    public String getDataIntroByName(String name) {
        List<MatchAttribute> matchAttributeList = diseaseDao.getDataIntroByName(name);
        if (matchAttributeList != null && matchAttributeList.size() > 0) {
            return matchAttributeList.get(0).getAttributeContent();
        } else {
            return "";
        }
    }

    /**
     * 根据id查询指定疾病所有信息
     *
     * @param id
     * @return
     */
    public Disease getDiseaseById(int id) {
        List<Disease> diseaseList = diseaseDao.getDiseaseById(id);
        if (diseaseList != null && diseaseList.size() > 0) {
            return diseaseList.get(0);
        }
        return null;
    }


    /**
     * 根据id查询指定疾病名称
     *
     * @param id
     * @return
     */
    public String getDiseaseNameById(int id) {
        List<MatchAttribute> diseaseList = diseaseDao.getDiseaseNameById(id);
        if (diseaseList != null && diseaseList.size() > 0) {
            return diseaseList.get(0).getAttributeContent();
        }
        return null;
    }

    /**
     * 根据id查询指定疾病并发症
     *
     * @param id
     * @return
     */
    public String getComplicationById(int id) {
        List<MatchAttribute> complicationMatchAttributeList = diseaseDao.getComplicationById(id);
        if (complicationMatchAttributeList != null && complicationMatchAttributeList.size() > 0) {
            return complicationMatchAttributeList.get(0).getAttributeContent();
        } else {
            return "";
        }
    }

    /**
     * 查询所有疾病所有信息
     *
     * @return
     */
    public List<Disease> getAllDisease() {
        return diseaseDao.getAllDisease();
    }

    /**
     * 模糊查询疾病名称
     *
     * @param name
     * @return
     */
    public List<MatchAttribute> getMaByFuzzyName(String name) {
        return diseaseDao.getMaByFuzzyName(name);
    }

    /**
     * 模糊查询疾病别名
     *
     * @param alias
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyAlias(String alias) {
        return diseaseDao.getDiseaseByFuzzyAlias(alias);
    }

    /**
     * 模糊查询疾病简介
     *
     * @param intro
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyIntro(String intro) {
        return diseaseDao.getDiseaseByFuzzyIntro(intro);
    }

    /**
     * 模糊查询疾病部位
     *
     * @param incidence_site
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyIncidenceSite(String incidence_site) {
        return diseaseDao.getDiseaseByFuzzyIncidenceSite(incidence_site);
    }

    /**
     * 模糊查询疾病多发人群
     *
     * @param multiple_people
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyMultiplePeople(String multiple_people) {
        return diseaseDao.getDiseaseByFuzzyMultiplePeople(multiple_people);
    }

    /**
     * 模糊查询早期症状
     *
     * @param symptom_early
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomEarly(String symptom_early) {
        return diseaseDao.getDiseaseByFuzzySymptomEarly(symptom_early);
    }

    /**
     * 模糊查询晚期症状
     *
     * @param symptom_late
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomLate(String symptom_late) {
        return diseaseDao.getDiseaseByFuzzySymptomLate(symptom_late);
    }

    /**
     * 模糊查询相关症状
     *
     * @param symptom_related
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomRelated(String symptom_related) {
        return diseaseDao.getDiseaseByFuzzySymptomRelated(symptom_related);
    }


    /**
     * 模糊查询相关症状简介
     *
     * @param symptom_intro
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzySymptomIntro(String symptom_intro) {
        return diseaseDao.getDiseaseByFuzzySymptomIntro(symptom_intro);
    }

    /**
     * 模糊查询相关并发症
     *
     * @param complication
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyComplication(String complication) {
        return diseaseDao.getDiseaseByFuzzyComplication(complication);
    }

    /**
     * 模糊查询相关并发症简介
     *
     * @param complication_intro
     * @return
     */
    public List<MatchAttribute> getDiseaseByFuzzyComplicationIntro(String complication_intro) {
        return diseaseDao.getDiseaseByFuzzyComplicationIntro(complication_intro);
    }

}