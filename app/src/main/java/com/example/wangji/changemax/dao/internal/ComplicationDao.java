package com.example.wangji.changemax.dao.internal;


import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Complication;
import com.example.wangji.changemax.util.sqlite_util.internal.ComplicationDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class ComplicationDao {

    private Context myContext;
    private ComplicationDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "complication_name";
    private static final String KEY_ASSOCIATION_DI_ID = "complication_association_disease_id";
    private static final String KEY_ASSOCIATION_DI_NAME = "complication_association_disease_name";

    public ComplicationDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new ComplicationDatabaseUtil(myContext);
    }


    // 通过疾病id查询可能并发疾病id，name集
    public List<MatchAttribute> getDiByDiId(int disease_id) {
        databaseUtil.openDataBase();
        //        disease_id = "'" + disease_id + "'";
        List<MatchAttribute> list = databaseUtil.queryDiDi(disease_id);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 根据疾病查询所有并发症
     *
     * @param complication_association_disease_name
     * @return
     */
    public List<Complication> getComplicationByAssociationDiseaseName(String complication_association_disease_name) {
        databaseUtil.openDataBase();
        complication_association_disease_name = "'" + complication_association_disease_name + "'";
        List<Complication> list = databaseUtil.queryData(KEY_ASSOCIATION_DI_NAME, complication_association_disease_name);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 获取所有并发症名字
     *
     * @return
     */
    public List<Complication> getAllComplication() {
        databaseUtil.openDataBase();
        List<Complication> allList = databaseUtil.queryDataList();
        //  diseaseDU.closeDataBase();
        return allList;
    }


}
