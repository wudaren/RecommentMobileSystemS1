package com.fbse.recommentmobilesystem.XZHL0210;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fbse.recommentmobilesystem.common.HttpSubmit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

//Toast信息控制类
public class XZHL0210_Utils {
    private static Toast mToast;
    private static Toast mToast2;

    // 对应XZHL0210_ListQActivity的Toast
    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    // 对应XZHL0220_DetailActivity的Toast
    public static void showToast2(Context context, String msg, int duration) {
        if (mToast2 == null) {
            mToast2 = Toast.makeText(context, msg, duration);
        } else {
            mToast2.setText(msg);
        }
        mToast2.show();
    }

    /*
     * 检测网络环境是否良好
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<XZHL0210_Questioniare> parserBrief(String uri) throws JSONException {
        ArrayList<XZHL0210_Questioniare> map = null;
        try {
            List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
            parameters.add(new BasicNameValuePair("shopId", XZHL0210_Constants.SHOPID));
            String result = HttpSubmit.ListTest2(uri, parameters);
            if (result == null || result.length() == 0) {
                return null;
            }
            map = XZHL0210_Utils.jiexiJSON(result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return map;
    }

    public static boolean jiexiResult(String json) throws JSONException {
        JSONObject result;
        result = new JSONObject(json);
        if (!result.getString("result").equals("true")) {
            return false;
        } else {
            return true;
        }

    }

    public static ArrayList<XZHL0210_Questioniare> jiexiJSON(String json) {
        JSONObject user;
        ArrayList<XZHL0210_Questioniare> backList = null;
        try {
            user = new JSONObject(json);
            if (user.getString("result").equals("false")) {
                return null;
            } else if (user.getString("result").equals("100")) {
                backList = new ArrayList<XZHL0210_Questioniare>();
                return backList;
            } else {
                String listStr = user.getString("list");
                if (listStr == null) {
                    return null;
                }
                backList = new ArrayList<XZHL0210_Questioniare>();
                JSONTokener jsonParser = new JSONTokener(listStr);
                JSONArray list = (JSONArray) jsonParser.nextValue();
                JSONObject info;
                // backList = new ArrayList<XZHL0210>();
                for (int i = 0; i < list.length(); i++) {
                    XZHL0210_Questioniare brief = new XZHL0210_Questioniare();
                    info = list.getJSONObject(i);
                    brief.setWenjuanId(info.getString("wenjuanId"));
                    brief.setWenjuanName(info.getString("wenjuanName"));
                    brief.setWenjuanPoint(info.getString("wenjuanPoint"));
                    brief.setRenwuCount(info.getString("renwuCount"));
                    brief.setWanchengCount(info.getString("wanchengCount"));
                    backList.add(brief);
                }
            }
            // return backList;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Log.i("LGG", backList.toString());
        return backList;
    }

    public static List<XZHL0210_Questioniare> strToJsonList(String str) throws JSONException {
        List<XZHL0210_Questioniare> list = new ArrayList<XZHL0210_Questioniare>();
        JSONArray jsonArray = new JSONArray(str);
        for (int i = 0; i < jsonArray.length(); i++) {
            XZHL0210_Questioniare brief = new XZHL0210_Questioniare();
            brief.setWenjuanName(jsonArray.getJSONObject(i).getString("wenjuanName"));
            brief.setWenjuanPoint(jsonArray.getJSONObject(i).getString("wenjuanPoint"));
            brief.setRenwuCount(jsonArray.getJSONObject(i).getString("renwuCount"));
            brief.setWanchengCount(jsonArray.getJSONObject(i).getString("wanchengCount"));
            list.add(brief);
        }
        Log.i("conn", list.toString());
        return list;
    }
}
