package com.example.wangji.changemax.service.internal;


import android.content.Context;

import com.example.wangji.changemax.dao.internal.ComplicationDao;
import com.example.wangji.changemax.model.external.MatchAttribute;
import com.example.wangji.changemax.model.internal.Complication;

import java.util.List;

/**
 *
 * Created by WangJi.
 */

public class ComplicationService {
    private ComplicationDao complicationDao;
    private Context myContext;

    public ComplicationService(Context myContext) {
        this.myContext = myContext;
        complicationDao = new ComplicationDao(myContext);
    }

    // 通过疾病id查询可能并发疾病id，name集
    public List<MatchAttribute> getDiByDiId(int disease_id) {
        return complicationDao.getDiByDiId(disease_id);
    }

    /**
     * 根据疾病查询所有并发症
     *
     * @param csdn
     * @return
     */
    public List<Complication> getComplicationByAssociationDiseaseName(String csdn) {
        return complicationDao.getComplicationByAssociationDiseaseName(csdn);
    }

    /**
     * 获取所有并发症名字
     *
     * @return
     */
    public List<Complication> getAllComplication() {
        return complicationDao.getAllComplication();
    }

}
