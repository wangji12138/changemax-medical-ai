package com.example.wangji.changemax.dao.internal;


import android.content.Context;

import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.SymptomOrPa;
import com.example.wangji.changemax.util.sqlite_util.internal.SymptomOrPaDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class SymptomOrPaDao {
    private Context myContext;
    private SymptomOrPaDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "symptom_name";
    private static final String KEY_TRANS = "symptom_trans";
    private static final String KEY_ORGAN_NAME = "organ_name";
    private static final String KEY_PART_NAME = "part_name";

    public SymptomOrPaDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new SymptomOrPaDatabaseUtil(myContext);
    }

    /**
     * 模糊查询症状名称
     *
     * @param fuzzyContent
     * @return
     */
    public List<MatchAttribute> getSymptomOrPaByFuzzyName(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<MatchAttribute> maList = databaseUtil.fuzzyQueryData(KEY_NAME, fuzzyContent);
        // diseaseDU.closeDataBase();
        return maList;
    }

    /**
     * 根据器官名称查询指定症状名称
     *
     * @param organ_name
     * @return
     */
    public List<SymptomOrPa> getSymptomOrPaByOrganName(String organ_name) {
        databaseUtil.openDataBase();
        organ_name = "'" + organ_name + "'";
        List<SymptomOrPa> list = databaseUtil.queryData(KEY_ORGAN_NAME, organ_name);
        //  SymptomDU.closeDataBase();
        return list;
    }

    /**
     * 根据部位查询指定症状名称
     *
     * @param
     * @return part_name
     */
    public List<SymptomOrPa> getSymptomOrPaByPartName(String part_name) {
        databaseUtil.openDataBase();
        part_name = "'" + part_name + "'";
        List<SymptomOrPa> list = databaseUtil.queryData(KEY_PART_NAME, part_name);
        //  SymptomDU.closeDataBase();
        return list;
    }

    /**
     * 查询所有症状名字
     *
     * @return
     */
    public List<SymptomOrPa> getAllSymptomOrPa() {
        databaseUtil.openDataBase();
        List<SymptomOrPa> allList = databaseUtil.queryDataList();
        return allList;
    }
}
