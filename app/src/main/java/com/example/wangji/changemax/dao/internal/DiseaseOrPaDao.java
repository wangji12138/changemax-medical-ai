package com.example.wangji.changemax.dao.internal;

import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.DiseaseOrPa;
import com.example.wangji.changemax.util.sqlite_util.internal.DiseaseOrPaDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DiseaseOrPaDao {
    private Context myContext;
    private DiseaseOrPaDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "disease_name";
    private static final String KEY_TRANS = "disease_trans";
    private static final String KEY_ORGAN_NAME = "organ_name";
    private static final String KEY_PART_NAME = "part_name";

    public DiseaseOrPaDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new DiseaseOrPaDatabaseUtil(myContext);
    }


    /**
     * 模糊查询疾病名称
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getDiseaseOrPaByFuzzyName(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_NAME, fuzzyContent);
        //  diseaseDU.closeDataBase();
        return maList;
    }

    /**
     * 根据器官名称查询指定疾病名称
     *
     * @param organ_name
     * @return
     */
    public List<DiseaseOrPa> getDiseaseOrPaByOrganName(String organ_name) {
        databaseUtil.openDataBase();
        organ_name = "'" + organ_name + "'";
        List<DiseaseOrPa> list = databaseUtil.queryData(KEY_ORGAN_NAME, organ_name);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 根据部位查询指定疾病名称
     *
     * @param
     * @return part_name
     */
    public List<DiseaseOrPa> getDiseaseOrPaByPartName(String part_name) {
        databaseUtil.openDataBase();
        part_name = "'" + part_name + "'";
        List<DiseaseOrPa> list = databaseUtil.queryData(KEY_PART_NAME, part_name);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 查询所有部位所有信息
     *
     * @return
     */
    public List<DiseaseOrPa> getAlldiseaseOrPa() {
        databaseUtil.openDataBase();
        List<DiseaseOrPa> allList = databaseUtil.queryDataList();
        return allList;
    }
}
