package com.zty.springboot01login.Pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowTimeFormat {
    public static String NowTime(){
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }
}
