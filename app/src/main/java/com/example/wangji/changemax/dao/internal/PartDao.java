package com.example.wangji.changemax.dao.internal;

import android.content.Context;

import com.example.wangji.changemax.model.internal.Part;
import com.example.wangji.changemax.util.sqlite_util.internal.PartDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class PartDao {
    private Context myContext;
    private PartDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "part_name";
    private static final String KEY_CONTAINED_ORGANS = "part_contained_organs";

    public PartDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new PartDatabaseUtil(myContext);
    }

    /**
     * 根据名称查询指定部位所有信息
     *
     * @param name
     * @return
     */
    public List<Part> getPartByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<Part> list = databaseUtil.queryData(KEY_NAME, name);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 查询所有部位所有信息
     *
     * @return
     */
    public List<Part> getAllPart() {
        databaseUtil.openDataBase();
        List<Part> allList = databaseUtil.queryDataList();
        return allList;
    }

}
