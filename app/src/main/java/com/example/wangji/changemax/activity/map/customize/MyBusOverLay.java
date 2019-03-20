package com.example.wangji.changemax.activity.map.customize;


import android.util.Log;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.wangji.changemax.util.overlay_util.BusLineOverlay;

/**
 * 重写和设置公交路线的覆盖类
 * 用来处理搜索到的对象的信息数据
 * 把数据保存在每一个对应的Mark对象中
 */

public class MyBusOverLay extends BusLineOverlay {
    /**
     * 构造函数
     */
    PoiSearch busPoiSearch;

    public MyBusOverLay(BaiduMap baiduMap, PoiSearch busPoiSearch) {
        super(baiduMap);
        this.busPoiSearch = busPoiSearch;
    }

    @Override
    public boolean onBusStationClick(int index) {
        //获取点击的标记物的数据
        Log.e("TAG", "站点：" + mBusLineResult.getBusLineName());
        Log.e("TAG", "运营时间：" + mBusLineResult.getStartTime() + "---" + mBusLineResult.getEndTime());
        Log.e("TAG", "费用：" + mBusLineResult.getBasePrice() + "---" + mBusLineResult.getMaxPrice());
        //  发起一个详细检索,要使用uid
        busPoiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(mBusLineResult.getUid()));
        return true;
    }
}
