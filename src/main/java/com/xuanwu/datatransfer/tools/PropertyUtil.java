package com.xuanwu.datatransfer.tools;

import com.xuanwu.datatransfer.ui.AppMainWindow;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zhouy on 2017/2/27.
 */
public class PropertyUtil {

    /**
     * 获取property
     * @param key
     * @return
     */
    public static String getProperty(String key){
        Properties pps = new Properties();
        try {
            InputStream in = new BufferedInputStream (AppMainWindow.class.getResourceAsStream("/config/zh-cn.properties"));
            pps.load(in);
            //String value = new String(pps.getProperty(key).getBytes("ISO8859-1"),"UTF-8");
            String value = pps.getProperty(key);
            return value;

        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
