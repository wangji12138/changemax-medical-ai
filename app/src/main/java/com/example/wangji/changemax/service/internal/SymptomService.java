package com.example.wangji.changemax.service.internal;


import android.content.Context;

import com.example.wangji.changemax.dao.internal.SymptomDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Symptom;

import java.util.List;

/**
 * Created by WangJi.
 */

public class SymptomService {
    private Context myContext;
    private SymptomDao symptomDao;

    public SymptomService(Context myContext) {
        this.myContext = myContext;
        symptomDao = new SymptomDao(myContext);
    }


//    /**
//     * 根据名称查询指定症状名称
//     *
//     * @param name
//     * @return
//     */
//    public List<MatchAttribute> getDataByName(String name) {
//        return symptomDao.getDataByName(name);
//    }

    /**
     * 根据名称查询指定症状简介
     *
     * @param name
     * @return
     */
    public String getDataIntroByName(String name) {
        List<MatchAttribute> matchAttributeList = symptomDao.getDataIntroByName(name);
        if (matchAttributeList != null && matchAttributeList.size() > 0) {
            return matchAttributeList.get(0).getAttributeContent();
        } else {
            return "";
        }
    }

    /**
     * 根据名称查询指定症状所有信息
     *
     * @param name
     * @return
     */
    public Symptom getSymptomByName(String name) {
        List<Symptom> symptomList = symptomDao.getSymptomByName(name);
        if (symptomList != null && symptomList.size() > 0) {
            return symptomList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据id查询指定症状所有信息
     *
     * @param id
     * @return
     */
    public Symptom getSymptomById(int id) {
        List<Symptom> symptomList = symptomDao.getSymptomById(id);
        if (symptomList != null && symptomList.size() > 0) {
            return symptomList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据id查询指定症状名称
     *
     * @param id
     * @return
     */
    public String getSymptomNameById(int id) {
        List<MatchAttribute> matchAttributeList = symptomDao.getSymptomNameById(id);
        if (matchAttributeList != null && matchAttributeList.size() > 0) {
            return matchAttributeList.get(0).getAttributeContent();
        } else {
            return null;
        }
    }

    /**
     * 获取所有症状所有信息
     *
     * @return
     */
    public List<Symptom> getAllSymptom() {
        return symptomDao.getAllSymptom();
    }

    /**
     * 模糊查询症状名称
     *
     * @param name
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyName(String name) {
        return symptomDao.getSymptomByFuzzyName(name);
    }

    /**
     * 模糊查询症状简介
     *
     * @param intro
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyIntro(String intro) {
        return symptomDao.getSymptomByFuzzyIntro(intro);
    }

    /**
     * 模糊查询症状起因
     *
     * @param cause
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyCause(String cause) {
        return symptomDao.getSymptomByFuzzyCause(cause);
    }

    /**
     * 模糊查询症状诊断详述内容
     *
     * @param details
     * @return
     */
    public List<MatchAttribute> getSymptomByFuzzyDetails(String details) {
        return symptomDao.getSymptomByFuzzyDetails(details);
    }
}
