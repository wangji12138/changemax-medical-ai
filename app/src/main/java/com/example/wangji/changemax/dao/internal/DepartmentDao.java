package com.example.wangji.changemax.dao.internal;

import android.content.Context;

import com.example.wangji.changemax.model.internal.Department;
import com.example.wangji.changemax.util.sqlite_util.internal.DepartmentDatabaseUtil;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DepartmentDao {

    private Context myContext;
    private DepartmentDatabaseUtil databaseUtil;

    private static final String KEY_NAME = "department_name";
    private static final String KEY_INTRO = "department_intro";
    private static final String KEY_ADDRESS = "department_address";

    public DepartmentDao(Context myContext) {
        this.myContext = myContext;
        databaseUtil = new DepartmentDatabaseUtil(myContext);
    }

    /**
     * 根据名称查询指定科室所有信息
     *
     * @param name
     * @return
     */
    public List<Department> getDepartmentByName(String name) {
        databaseUtil.openDataBase();
        name = "'" + name + "'";
        List<Department> list = databaseUtil.queryData(KEY_NAME, name);
        //  diseaseDU.closeDataBase();
        return list;
    }

    /**
     * 获取所有科室所有信息
     * @return
     */
    public List<Department> getAllDepartment() {
        databaseUtil.openDataBase();
        List<Department> allList = databaseUtil.queryDataList();
        //  diseaseDU.closeDataBase();
        return allList;
    }

    /**
     * 模糊查询科室名称
     *
     * @param fuzzyContent
     * @return
     */
    public List<Department> getDepartmentByFuzzyName(String fuzzyContent) {
        databaseUtil.openDataBase();
        List<Department> list = databaseUtil.fuzzyQueryData(KEY_NAME, fuzzyContent);
        //  diseaseDU.closeDataBase();
        return list;
    }


}
