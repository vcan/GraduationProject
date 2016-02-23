package com.zszdevelop.planman.utils;

/**
 * Created by ShengZhong on 2015/12/27.
 * 时间戳转换工具
 */
public class TimeUtil {
//    String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(Unix timestamp * 1000))
    public static String timestampToData(long timestamp){

        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date(timestamp * 1000));
        return date;
    }

    public static String timeGap(long bigTime,long smallTime){
        String gapTime = "";
        if (bigTime==0||smallTime == 0){
            return "未知";
        }




        if (bigTime<smallTime){
            return "未知";
        }

        Long s = bigTime - smallTime;
        if (s<60){
            gapTime =s+"秒前";
        }else if (60<s && s<60*60){
            gapTime =s/60+"分钟前";
        }else if (60*60 < s && s < 60*60*24){
            gapTime =s/60/60+"小时前";
        }
        else if (60*60*24 < s && s < 60*60*24*30){
            gapTime =s/60/60/24+"天前";
        }
        else if (60*60*24*30 < s){
            gapTime = s/60/60/24/30+"月前";
        }
        return gapTime;
    }

}
