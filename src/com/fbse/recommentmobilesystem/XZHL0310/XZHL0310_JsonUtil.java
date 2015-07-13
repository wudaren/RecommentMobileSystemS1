package com.fbse.recommentmobilesystem.XZHL0310;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class XZHL0310_JsonUtil {
    
    /**
     * 解析返回JSON
     * @param String json 出入JSON字符串
     * @return String 字符串解析结果 
     */
    public static String JSON2Result(String json) {
        StringBuilder result = new StringBuilder();
        try {
            JSONObject user = new JSONObject(json);
            result.append(user.getString("result"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * 检测用户名正则表达式
     * @param String userName 出入字符串用户名
     * @return 检测用户名是否符合正则表达式规范的布尔值
     */
    public static boolean checkUserName(String userName) { 
        String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5]|[\\_])+"; 
        Pattern p = Pattern.compile(regex); 
        Matcher m = p.matcher(userName); 
        return m.matches();
    }

    /**
     * 检测密码正则表达式
     * @param String passWord 出入字符串密码
     * @return 检测密码是否符合正则表达式规范的布尔值
     */
    public static boolean checkPassword(String passWord) { 
        String regex = "([a-z]|[A-Z]|[0-9]|[\\,]|[\\.]|[\\@]|[\\$]|[\\%]|[\\!]|[\\*]|[\\#]|[\\^]|[\\(]|[\\)]|[\\/]|[\\&]|[\\<]|[\\>]|[\\+]|[\\-]|[\\?]|[\\_])+"; 
        Pattern p = Pattern.compile(regex); 
        Matcher m = p.matcher(passWord); 
        return m.matches();
    }

    /**
     * 检测网络环境是否良好
     * @param Context context 传入上下文参数
     * @return 返回是否检测到网络的布尔值
     */
    public static boolean isNetworkAvailable(Context context) { 
        ConnectivityManager connectivity = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE); 
        if(connectivity != null) {  
           NetworkInfo[] info = connectivity.getAllNetworkInfo(); 
              if(info != null){ 
                  for(int i = 0;i < info.length;i++) { 
                       if(info[i].getState() == NetworkInfo.State.CONNECTED){ 
                          return true; 
                       } 
                    } 
                  } 
            } 
        return false; 
    }
}
