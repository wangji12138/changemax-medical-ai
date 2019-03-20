package com.example.wangji.changemax.dao.internal;


import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.SymptomDi;
import com.example.wangji.changemax.util.sqlite_util.internal.SymptomDiDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class SymptomDiDao {
    private Context myContext;
    private SymptomDiDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "symptom_name";
    private static final String KEY_ASSOCIATION_DISEASE_ID = "symptom_association_disease_id";
    private static final String KEY_ASSOCIATION_DISEASE_NAME = "symptom_association_disease_name";

    public SymptomDiDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new SymptomDiDatabaseUtil(myContext);
    }

    /**
     * 根据指定疾病id查询症状id，名称
     *
     * @param disease_id
     * @return
     */
    public List<MatchAttribute> getSyByDiId(int disease_id) {
        databaseUtil.openDataBase();
        //        symptom_id = "'" + symptom_id + "'";
        List<MatchAttribute> list = databaseUtil.queryDiSy(disease_id);
        databaseUtil.closeDataBase();
        return list;
    }


    /**
     * 根据指定症状id查询疾病id，名称
     *
     * @param symptom_id
     * @return
     */
    public List<MatchAttribute> getDiBySyId(int symptom_id) {
        databaseUtil.openDataBase();
        //        symptom_id = "'" + symptom_id + "'";
        List<MatchAttribute> list = databaseUtil.querySyDi(symptom_id);
        databaseUtil.closeDataBase();
        return list;
    }

    /**
     * 根据疾病名称查询所有可能存在的症状名称
     *
     * @param name
     * @return
     */
    public List<SymptomDi> getSymptomDiByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<SymptomDi> list = databaseUtil.queryData(KEY_ASSOCIATION_DISEASE_NAME, name);
        databaseUtil.closeDataBase();
        return list;
    }

    /**
     * 获取所有症状所有信息
     *
     * @return
     */
    public List<SymptomDi> getAllSymptom() {
        databaseUtil.openDataBase();
        List<SymptomDi> allList = databaseUtil.queryDataList();
        //  diseaseDU.closeDataBase();
        return allList;
    }

}
