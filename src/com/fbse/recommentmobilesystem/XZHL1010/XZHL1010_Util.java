/*  XZHL1010_Util.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                                 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL1010                                                                                               */
/*  画面名 ：自店产品广告一览                                                                                         */
/*  实现功能 ：自店产品广告工具类。                                                                                   */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/23   V01L01      FBSE)胡郑毅      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1010;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fbse.recommentmobilesystem.common.CommonConst;
import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 * 自店产品广告一览工具类
 * 自店产品广告一览工具类,解析json字符串
 */
public class XZHL1010_Util {

    /**
     * 解析json字符串为广告数据list
     * @param str 待解析字符串
     * @return 解析后的广告集合
     */
    public static List<XZHL1010_Bean> jsonToList(String str) {

        // Log开始
        LogUtil.logStart();
        List<XZHL1010_Bean> list = new ArrayList<XZHL1010_Bean>();
        try {
            JSONObject jb = new JSONObject(str);
            String success = jb.getString(CommonConst.SUCCESS);

            // 判断返回值,为1时正确返回数据,为0时上海服务器查询失败,为9时上海服务器异常,其他为未知失败
            if (success.equals(CommonConst.ONE)) {

                // 读取data数据内容
                String data = jb.getString(CommonConst.DATA);
                JSONObject jsonData = new JSONObject(data);
                JSONArray array = jsonData.getJSONArray(CommonConst.ADVERT);

                // 循环解析data数据内容到list集合中
                for (int i = 0; i < array.length(); i++) {
                    JSONObject json = array.getJSONObject(i);
                    XZHL1010_Bean bean = new XZHL1010_Bean();
                    bean.setAdv(json.getString(CommonConst.EXERCISEDECLARATION));
                    bean.setBeginDate(json.getString(CommonConst.TIMESTART));
                    bean.setEndDate(json.getString(CommonConst.TIMEEND));
                    bean.setId(json.getString(CommonConst.ADVID));
                    bean.setMethod(json.getString(CommonConst.PREFERENTIALTYPE));
                    bean.setMoney(json.getString(CommonConst.AMOUNT));
                    bean.setName(json.getString(CommonConst.EXERCISENAME));
                    bean.setProduct(json.getString(CommonConst.EXERCISEGOODS));
                    bean.setSetting(json.getString(CommonConst.PREFERENTIALSET));
                    bean.setTarget(json.getString(CommonConst.EXERCISETARGET));
                    list.add(bean);
                }
                return list;
            } else if (CommonConst.FAILZERO.equals(success)) {
                return list;
            } else if (CommonConst.TALENTERRORSTATES.equals(success)) {
                return null;
            } else {
                return null;
            }
        } catch (Exception e) {

            // 异常log输出
            LogUtil.logException(e);
            return null;
        } finally {

            // Log结束
            LogUtil.logEnd();
        }
    }

    /**
     * 格式化日期
     * @param str 格式化之前的日期字符串
     * @return 格式化之后的日期字符串
     */
    public static String getDate(String str) {

        // Log开始
        LogUtil.logStart();
        SimpleDateFormat sdf = new SimpleDateFormat(CommonConst.FORMAT);
        try {
            Date data = sdf.parse(str);
            sdf = new SimpleDateFormat(CommonConst.FORMAT1010);
            return sdf.format(data);
        } catch (Exception e) {

            // 异常log输出
            LogUtil.logException(e);
            return CommonConst.SPACE;
        } finally {

            // Log结束
            LogUtil.logEnd();
        }
    }

    /**
     * 格式化金額
     * @param str 格式化之前的金額字符串
     * @return 格式化之後的金額字符串
     */
    public static String getMoney(String str) {
        return new DecimalFormat(CommonConst.MONEYFORMAT).format(Double.parseDouble(str));
    }
}
