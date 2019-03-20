package com.example.wangji.changemax.service.internal;


import android.content.Context;

import com.example.wangji.changemax.dao.internal.DiseaseOrPaDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.DiseaseOrPa;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseOrPaService {
    private Context myContext;
    private DiseaseOrPaDao diseaseOrPaDao;

    public DiseaseOrPaService(Context myContext) {
        this.myContext = myContext;
        diseaseOrPaDao = new DiseaseOrPaDao(myContext);
    }


    /**
     * 模糊查询疾病名称
     *
     * @param name
     * @return
     */
    public List<MatchAttribute> getDiseaseOrPaByFuzzyName(String name) {
        return diseaseOrPaDao.getDiseaseOrPaByFuzzyName(name);
    }

    /**
     * 根据器官名称查询指定疾病名称
     *
     * @param organ_name
     * @return
     */
    public List<DiseaseOrPa> getDiseaseOrPaByOrganName(String organ_name) {
        return diseaseOrPaDao.getDiseaseOrPaByOrganName(organ_name);
    }

    /**
     * 根据部位查询指定疾病名称
     *
     * @param
     * @return part_name
     */
    public List<DiseaseOrPa> getDiseaseOrPaByPartName(String part_name) {
        return diseaseOrPaDao.getDiseaseOrPaByPartName(part_name);
    }

    /**
     * 查询所有部位所有信息
     *
     * @return
     */
    public List<DiseaseOrPa> getAlldiseaseOrPa() {
        return diseaseOrPaDao.getAlldiseaseOrPa();
    }

}
