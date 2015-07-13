/*  XZHL1230_util.java V01L01 Copyright＠QINGDAO FUBO SYSTEM ENGINEERING CO.,LTD 2012                                 */
/*--------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                    */
/*  项目名称 ：信智互联                                                                                               */
/*  画面ＩＤ ：XZHL1230                                                                                               */
/*  画面名 ：店员信息修改                                                                                             */
/*  实现功能 ：工具类                                                                                                 */
/*                                                                                                                    */
/*  变更历史                                                                                                          */
/*      NO  日付                       Ver             更新者                 内容                                    */
/*      1   2014/05/15   V01L01      FBSE)尹晓超      新規作成                                                        */
/*                                                                                                                    */
/*--------------------------------------------------------------------------------------------------------------------*/
package com.fbse.recommentmobilesystem.XZHL1230;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fbse.recommentmobilesystem.common.LogUtil;

/**
 * 店员情报修改画面工具类
 * 完成店员情报修改辅助类
 */
public class XZHL1230_util {

    /**
     * 检测密码正则表达式
     * @param userName 密码
     * @return 验证成功true；验证失败false
     */
    public static boolean checkPassword(String userName) {

        // Log开始
        LogUtil.logStart();

        // 正则表达式
        String regex = "([a-z]|[A-Z]|[0-9]|[\\,]|[\\.]|[\\@]|[\\$]|[\n]|[\f\r\t]|[\\%]|[\\!]|[\\*]|[\\#]|[\\^]|[\\(]|"
            + "[\\)]|[\\/]|[\\&]|[\\<]|[\\>]|[\\+]|[\\-]|[\\?]|[\\_])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userName);

        // Log结束
        LogUtil.logEnd();

        return m.matches();
    }

    /**
     * 检测用户名正则表达式
     * @param userName 用户名
     * @return 验证成功true；验证失败false
     */
    public static boolean checkUserName(String userName) {

        // Log开始
        LogUtil.logStart();

        // 正则表达式
        String regex = "([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5]|[\\_])+";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(userName);

        // Log结束
        LogUtil.logEnd();

        return m.matches();
    }
}
