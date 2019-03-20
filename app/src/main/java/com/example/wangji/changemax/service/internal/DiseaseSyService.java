package com.example.wangji.changemax.service.internal;


import android.content.Context;

import com.example.wangji.changemax.dao.internal.DiseaseSyDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.DiseaseSy;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseSyService {
    private Context myContext;
    private DiseaseSyDao diseaseSyDao;

    public DiseaseSyService(Context myContext) {
        this.myContext = myContext;
        diseaseSyDao = new DiseaseSyDao(myContext);
    }

    /**
     * 根据指定症状id查询疾病id，名称
     *
     * @param symptom_id
     * @return
     */
    public List<MatchAttribute> getDiBySyId(int symptom_id) {
        return diseaseSyDao.getDiBySyId(symptom_id);
    }

    /**
     * 根据指定症状查询疾病名称
     *
     * @param symptom_name
     * @return
     */
    public List<DiseaseSy> getDiseaseOrPaByOrganName(String symptom_name) {
        return diseaseSyDao.getDiseaseOrPaByOrganName(symptom_name);
    }

    /**
     * 查询所有部位所有信息
     *
     * @return
     */
    public List<DiseaseSy> getAlldiseaseOrPa() {
        return diseaseSyDao.getAlldiseaseOrPa();
    }
}
