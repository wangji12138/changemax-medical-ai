package com.example.wangji.changemax.util.file_util;

import android.content.Context;

import com.example.wangji.changemax.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangJi.
 */
public class FileUtil {
    private Context myContext;

    public FileUtil(Context myContext) {
        this.myContext = myContext;
    }

    /**
     * 从assets中读取txt
     */
    public List<String> readFromAssets(String fileName) {
        try {
            InputStream inputStream = myContext.getAssets().open(fileName);

            return readTextFromSDcard(inputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从raw中读取txt
     */
    public List<String> readFromRaw(int name_id) {
        try {
            InputStream inputStream = myContext.getResources().openRawResource(name_id);

            return readTextFromSDcard(inputStream);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 按行读取txt
     *
     * @param inputStream
     * @return
     * @throws Exception
     */
    public List<String> readTextFromSDcard(InputStream inputStream) {

        List<String> nameList = null;

        try {
            nameList = new ArrayList<String>();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String name = null;
            while ((name = bufferedReader.readLine()) != null) {
                nameList.add(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nameList;
    }




}
