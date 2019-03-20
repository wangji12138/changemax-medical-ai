package com.example.wangji.changemax.service.internal;

import android.content.Context;

import com.example.wangji.changemax.dao.internal.SymptomDiDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.SymptomDi;

import java.util.List;

/**
 * Created by WangJi.
 */

public class SymptomDiService {
    private Context myContext;
    private SymptomDiDao symptomDiDao;

    public SymptomDiService(Context myContext) {
        this.myContext = myContext;
        symptomDiDao = new SymptomDiDao(myContext);
    }

    /**
     * 根据指定疾病id查询症状id，名称
     *
     * @param disease_id
     * @return
     */
    public List<MatchAttribute> getSyByDiId(int disease_id) {
        return symptomDiDao.getSyByDiId(disease_id);
    }

    /**
     * 根据指定症状id查询疾病id，名称
     *
     * @param symptom_id
     * @return
     */
    public List<MatchAttribute> getDiBySyId(int symptom_id) {
        return symptomDiDao.getDiBySyId(symptom_id);
    }


    /**
     * 根据疾病名称查询所有可能存在的症状名称
     *
     * @param name
     * @return
     */
    public List<SymptomDi> getSymptomDiByName(String name) {
        return symptomDiDao.getSymptomDiByName(name);
    }

    /**
     * 获取所有症状所有信息
     *
     * @return
     */
    public List<SymptomDi> getAllSymptom() {
        return symptomDiDao.getAllSymptom();
    }

}
