package com.example.wangji.changemax.service.internal;


import android.content.Context;

import com.example.wangji.changemax.dao.internal.OrganDao;
import com.example.wangji.changemax.model.internal.Organ;

import java.util.List;

/**
 * Created by WangJi.
 */

public class OrganService {
    private Context myContext;
    private OrganDao organDao;

    public OrganService(Context myContext) {
        this.myContext = myContext;
        organDao = new OrganDao(myContext);
    }

    /**
     * 根据部位名称查询该部位所有器官信息
     *
     * @param name
     * @return
     */
    public List<Organ> getOrgantyName(String name) {
        return organDao.getOrgantyName(name);
    }

    /**
     * 查询所有器官所有信息
     *
     * @return
     */
    public List<Organ> getAllPart() {
        return organDao.getAllPart();
    }
}
