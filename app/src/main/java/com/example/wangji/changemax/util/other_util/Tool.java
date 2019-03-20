package com.example.wangji.changemax.util.other_util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by WangJi.
 */

public class Tool {

    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     *
     * @return
     */
    public String getUUID() {
        /*UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
		return temp;*/

        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 获取当前系统日期
     *
     * @return
     */
    public String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}
