package com.example.wangji.changemax.dao.internal;

import android.content.Context;

import com.example.wangji.changemax.model.internal.Organ;
import com.example.wangji.changemax.util.sqlite_util.internal.OrganDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class OrganDao {
    private Context myContext;
    private OrganDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "organ_name";
    private static final String KEY_OWN_PART = "organ_own_part";

    public OrganDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new OrganDatabaseUtil(myContext);
    }

    /**
     * 根据部位名称查询该部位所有器官信息
     *
     * @param name
     * @return
     */
    public List<Organ> getOrgantyName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<Organ> list = databaseUtil.queryData(KEY_OWN_PART, name);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 查询所有器官所有信息
     *
     * @return
     */
    public List<Organ> getAllPart() {
        databaseUtil.openDataBase();
        List<Organ> allList = databaseUtil.queryDataList();
        return allList;
    }

}
