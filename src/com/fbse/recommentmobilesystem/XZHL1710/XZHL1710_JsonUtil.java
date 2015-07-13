/*  XZHL1710_JsonUtil.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                              */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                                        */
/*  画面ＩＤ     ：XZHL1710                                                                                                */
/*  画面名     ：客流统计                                                                                                     */
/*  实现功能 ：客流统计 解析类                                                                                                  */
/*                                                                                                                    */
/*  变更历史                                                                                                              */
/*      NO  日付                       Ver         更新者                 内容                                               */
/*      1   2014/05/30   V01L01      FBSE)高振川      新規作成                                                               */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1710;

import org.json.JSONException;
import org.json.JSONObject;

import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 *  客流统计解析类
 *
 *  完成客流统计
 */
public class XZHL1710_JsonUtil {

    // 获取返回值信息成功
    private static final String SUCCESS = "1";

    /**
     * 解析返回JSON
     * @param json 出入JSON字符串
     * @return Category
     */
    public static String jsonToCategory(String json) {
        LogUtil.logStart();
        String result = null;
        try {
            JSONObject jb = new JSONObject(json);
            String success = jb.getString("success");
            if (SUCCESS.equals(success)) {
                result = jb.getString("data").toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            LogUtil.logException(e);
        }
        LogUtil.logEnd();
        return result;
    }

}
