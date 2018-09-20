package com.assemble.ad.util;

import java.text.SimpleDateFormat;


public class TimeUtil {

    /**
     * 获取格式化的时间
     *
     * @param format    yyyy-MM-dd HH:mm; MM-dd HH:mm;HH:mm;yyyy-MM-dd;yyyy年M月d日
     *                  HH:mm;M月d日 HH:mm;H:mm;...
     * @param timeMills
     * @return
     */
    public static String getFormatedTime(String format, long timeMills) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(timeMills);
    }
}
