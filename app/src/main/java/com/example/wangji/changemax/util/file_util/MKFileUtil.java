package com.example.wangji.changemax.util.file_util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by WangJi.
 */
public class MKFileUtil {
    private Context myContext;

    public MKFileUtil(Context myContext) {
        this.myContext = myContext;
    }


    /**
     * 从raw中读取指定行数的txt
     */
    public String readDesignationFromRaw(int name_id, int row) {
        try {
            InputStream inputStream = myContext.getResources().openRawResource(name_id);

            return readDesignationTextFromSDcard(inputStream, row);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从raw中读取 txt的总行数
     */
    public int readTotalFromRaw(int name_id) {

        int totalRow = 0;
        try {
            InputStream inputStream = myContext.getResources().openRawResource(name_id);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.readLine() != null) {
                totalRow++;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return totalRow;
    }

    /**
     * 读取指定行
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public String readDesignationTextFromSDcard(InputStream inputStream, int row) {
        int currentRow = 1;
        try {
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String text = null;
            while ((text = bufferedReader.readLine()) != null) {
                if (currentRow == row) {
                    return text;
                }
                currentRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
