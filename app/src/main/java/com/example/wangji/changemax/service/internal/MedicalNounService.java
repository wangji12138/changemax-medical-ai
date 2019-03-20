package com.example.wangji.changemax.service.internal;

import android.content.Context;

import com.example.wangji.changemax.dao.internal.MedicalNounDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.MedicalNoun;

import java.util.List;

/**
 * Created by WangJi.
 */

public class MedicalNounService {
    private Context myContext;
    private MedicalNounDao medicalNounDao;

    public MedicalNounService(Context myContext) {
        this.myContext = myContext;
        medicalNounDao = new MedicalNounDao(myContext);
    }

    /**
     * 根据名称查询指定所有医疗词汇
     *
     * @param name
     * @return
     */
    public List<MedicalNoun> getMedicalNounByName(String name) {
        return medicalNounDao.getMedicalNounByName(name);
    }

    /**
     * 查询所有医疗词汇
     *
     * @return
     */
    public List<MedicalNoun> getAllMedicalNoun() {
        return medicalNounDao.getAllMedicalNoun();
    }

    /**
     * 模糊查询医疗词汇名称
     *
     * @param name
     * @return
     */
    public List<MatchAttribute> getMedicalNounByFuzzyName(String name) {
        return medicalNounDao.getMedicalNounByFuzzyName(name);
    }

}
