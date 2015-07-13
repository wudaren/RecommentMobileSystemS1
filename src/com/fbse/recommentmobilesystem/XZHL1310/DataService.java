package com.fbse.recommentmobilesystem.XZHL1310;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataService {

	 

    public static List<HashMap<String, Object>> getData(int offset, int maxResult){//分页limit 0,20

        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

        for(int i=offset ; i < offset+maxResult ; i++){
        	HashMap<String, Object> hashMap=new HashMap<String, Object>();
        	hashMap.put("msg", "系统通知"+i);
        	hashMap.put("msgdetail", "最经的顾客您好"+i);
        	hashMap.put("time",timeaa());
            data.add(hashMap);

        }

        return data;

    }
    private static String timeaa() {
		SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd    HH:mm     ");       
		Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间        
		String    str    =    formatter.format(curDate);       
		return str;

	}
}

