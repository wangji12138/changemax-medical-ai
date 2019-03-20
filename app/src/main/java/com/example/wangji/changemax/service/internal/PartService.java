package com.example.wangji.changemax.service.internal;


import android.content.Context;

import com.example.wangji.changemax.dao.internal.PartDao;
import com.example.wangji.changemax.model.internal.Part;

import java.util.List;

/**
 * Created by WangJi.
 */

public class PartService {
    private Context myContext;
    private PartDao partDao;

    public PartService(Context myContext) {
        this.myContext = myContext;
        partDao = new PartDao(myContext);
    }

    /**
     * 根据名称查询指定部位所有信息
     *
     * @param name
     * @return
     */
    public Part getPartByName(String name) {
        List<Part> partList = partDao.getPartByName(name);
        if (partList != null && partList.size() > 0) {
            return partDao.getPartByName(name).get(0);
        } else {
            return null;
        }

    }

    /**
     * 查询所有部位所有信息
     *
     * @return
     */
    public List<Part> getAllPart() {
        return partDao.getAllPart();
    }

}
