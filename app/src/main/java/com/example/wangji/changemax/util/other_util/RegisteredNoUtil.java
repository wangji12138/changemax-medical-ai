package com.example.wangji.changemax.util.other_util;

import java.util.Calendar;

/**
 * Created by WangJi.
 */

public class RegisteredNoUtil {
    private String initTime = " ";
    private Calendar calendar = null;
    private int i = 1000;
    private char c = 65;

    public void RetrunNoDemo() {
        calendar = Calendar.getInstance();// 可以对每个时间域单独修改

    }

    public String returnNo() {
        String stringNo = "";

        if (isDayToDay()) {
            stringNo = c + "" + i;
        } else {
            i = 1000;
            c = 65;
            stringNo = c + "" + i;
        }
        i++;
        if (i == 9999) {
            c++;
            i = 1000;
        }

        return stringNo;
    }

    private boolean isDayToDay() {
        calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        String nowTime = year + ":" + month + ":" + date;
        boolean blag = true;

        if (!initTime.equals(nowTime)) {
            initTime = nowTime;
            blag = false;
        }
        return blag;
    }
}
