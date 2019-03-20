package com.example.wangji.changemax.dao.internal;

import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.DiseaseSy;
import com.example.wangji.changemax.util.sqlite_util.internal.DiseaseSyDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseSyDao {
    private Context myContext;
    private DiseaseSyDatabaseUtil databaseUtil;

    private static final String KEY_ID = "disease_id";
    private static final String KEY_T_ID = "t_disease_id";
    private static final String KEY_NAME = "disease_name";
    private static final String KEY_ACCOM_SY = "disease_accom_sy";
    private static final String KEY_OSSIBLE_SY_ID = "disease_ossible_sy_id";
    private static final String KEY_OSSIBLE_SY_NAME = "disease_ossible_sy_name";

    public DiseaseSyDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new DiseaseSyDatabaseUtil(myContext);
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
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 根据指定症状查询疾病名称
     *
     * @param symptom_name
     * @return
     */
    public List<DiseaseSy> getDiseaseOrPaByOrganName(String symptom_name) {
        databaseUtil.openDataBase();
        symptom_name = "'" + symptom_name + "'";
        List<DiseaseSy> list = databaseUtil.queryData(KEY_OSSIBLE_SY_NAME, symptom_name);
        //  diseaseDU.closeDataBase();
        return list;
    }


    /**
     * 查询所有症状引起的疾病的所有项
     *
     * @return
     */
    public List<DiseaseSy> getAlldiseaseOrPa() {
        databaseUtil.openDataBase();
        List<DiseaseSy> allList = databaseUtil.queryDataList();
        return allList;
    }
}
