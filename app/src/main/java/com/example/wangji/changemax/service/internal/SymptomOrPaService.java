package com.example.wangji.changemax.service.internal;

import android.content.Context;

import com.example.wangji.changemax.dao.internal.SymptomOrPaDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.SymptomOrPa;

import java.util.List;

/**
 * Created by WangJi.
 */

public class SymptomOrPaService {
    private Context myContext;
    private SymptomOrPaDao symptomOrPaDao;

    public SymptomOrPaService(Context myContext) {
        this.myContext = myContext;
        symptomOrPaDao = new SymptomOrPaDao(myContext);
    }

    /**
     * 模糊查询症状名称
     *
     * @param name
     * @return
     */
    public List<MatchAttribute> getSymptomOrPaByFuzzyName(String name) {
        return symptomOrPaDao.getSymptomOrPaByFuzzyName(name);
    }

    /**
     * 根据器官名称查询指定症状名称
     *
     * @param organ_name
     * @return
     */
    public List<SymptomOrPa> getSymptomOrPaByOrganName(String organ_name) {
        return symptomOrPaDao.getSymptomOrPaByOrganName(organ_name);
    }

    /**
     * 根据部位查询指定症状名称
     *
     * @param
     * @return part_name
     */
    public List<SymptomOrPa> getSymptomOrPaByPartName(String part_name) {
        return symptomOrPaDao.getSymptomOrPaByPartName(part_name);
    }

    /**
     * 查询所有症状名字
     *
     * @return
     */
    public List<SymptomOrPa> getAllSymptomOrPa() {
        return symptomOrPaDao.getAllSymptomOrPa();
    }
}
