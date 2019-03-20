package com.example.wangji.changemax.service.internal;

import android.content.Context;

import com.example.wangji.changemax.dao.internal.DepartmentDao;
import com.example.wangji.changemax.model.internal.Department;

import java.util.List;

/**
 * Created by WangJi.
 */

public class DepartmentService {

    private Context myContext;
    private DepartmentDao departmentDao;

    public DepartmentService(Context myContext) {
        this.myContext = myContext;
        departmentDao = new DepartmentDao(myContext);
    }

    /**
     * 根据名称查询指定科室所有信息
     *
     * @param name
     * @return
     */
    public List<Department> getDepartmentByName(String name) {
        return departmentDao.getDepartmentByName(name);
    }

    /**
     * 获取所有科室所有信息
     *
     * @return
     */
    public List<Department> getAllDepartment() {
        return departmentDao.getAllDepartment();
    }

    /**
     * 模糊查询科室名称
     *
     * @param department_name
     * @return
     */
    public List<Department> getDepartmentByFuzzyName(String department_name) {
        return departmentDao.getDepartmentByFuzzyName(department_name);
    }
}
