package com.example.wangji.changemax.util.sqlite_util.external;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by WangJi.
 */
public class DBManager {
    private Context myContext;

    public DBManager(Context myContext) {
        this.myContext = myContext;
    }

    /**
     * 复制和加载区域数据库中的数据
     *
     * @param SqliteFileName
     * @return
     */
    public String CopySqliteFileFromRawToDatabases(String SqliteFileName) {
        // 第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/<db_name>
        File dir = new File("data/data/" + myContext.getPackageName() + "/databases");
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        File file = new File(dir, SqliteFileName);
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
        if (!file.exists()) {
            try {
                file.createNewFile();
                inputStream = myContext.getClass().getClassLoader().getResourceAsStream("assets/" + SqliteFileName);
                outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return file.getPath();
    }

    /**
     * 删除数据库
     *
     * @param SqliteName
     */
    public void deleteSqlite(String SqliteName) {

    }

}