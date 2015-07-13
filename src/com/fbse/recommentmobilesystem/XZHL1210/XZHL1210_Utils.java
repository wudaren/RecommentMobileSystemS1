/*  XZHL1210_Utils.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                                */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ     ：XZHL1210                                                                                           */
/*  画面名     ：店员一览                                                                                             */
/*  实现功能 ：对店员信息进行一览的工具类。                                                                           */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver         更新者                 内容                                        */
/*      1   2014/05/19   V01L01      FBSE)李国刚      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1210;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 * 对店员信息进行一览的工具类
 */
public class XZHL1210_Utils {

    /**
     * 根据json字符串解析出需要的Bean
     * @param json 传入的带待解析的json字符串
     * @return 解析封装好的XZHL1210_UserList Bean的集合
     */
    public static XZHL1210_UserList jsonToUserList(String json) {

        // Log开始
        LogUtil.logStart();
        XZHL1210_UserList userList = null;
        try {
            JSONObject jb = new JSONObject(json);
            userList = new XZHL1210_UserList();

            // 获取json中key为success的状态值
            String success = jb.getString(XZHL1210_Constants.SUCCESS);
            // 如果值是success
            if (success.equals(XZHL1210_Constants.SUCCESS)) {
                userList.setSuccess(jb.getString(XZHL1210_Constants.SUCCESS));

                // 获取json中key为data的状态值
                JSONArray userArray = jb.getJSONArray(XZHL1210_Constants.DATA);
                List<XZHL1210_DianYuan> user = new ArrayList<XZHL1210_DianYuan>();
                for (int i = 0; i < userArray.length(); i++) {
                    // 解析data
                    JSONObject userStr = userArray.getJSONObject(i);
                    XZHL1210_DianYuan u = new XZHL1210_DianYuan();
                    u.setId(userStr.getString(XZHL1210_Constants.ID));
                    u.setLoginName(userStr.getString(XZHL1210_Constants.USERNAME));
                    u.setName(userStr.getString(XZHL1210_Constants.REALNAME));
                    u.setRole(userStr.getString(XZHL1210_Constants.ROLE));
                    u.setTel(userStr.getString(XZHL1210_Constants.TEL));
                    u.setShops(userStr.getString(XZHL1210_Constants.SHOPS));
                    u.setStore(userStr.getString(XZHL1210_Constants.JSONSTORE));
                    user.add(u);
                }
                userList.setUser(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            // Log异常
            LogUtil.logException(e);
        }

        // Log结束
        LogUtil.logEnd();
        return userList;
    }

    /**
     * 根据json字符串解析返回结果
     * @param json 传入的待解析的json字符串
     * @return 解析json后的结果
     */
    public static String jsonParser(String json) {

        // Log开始
        LogUtil.logStart();
        String result = null;
        if (json == null) {
            return result;
        }
        try {
            JSONObject jb = new JSONObject(json);

            // 获取json中key为success的状态值
            result = jb.getString(XZHL1210_Constants.SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();

            // Log异常
            LogUtil.logException(e);
        }

        // Log结束
        LogUtil.logEnd();
        return result;
    }

}
