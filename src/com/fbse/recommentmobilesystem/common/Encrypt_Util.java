/**
 * 项目名称 ：信智互联
 * 类一览：
 * Encrypt_Util       共通类
 */
package com.fbse.recommentmobilesystem.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * <dl>
 * <dd>功能名：MD5加密
 * <dd>功能说明：完成对字符的加密。
 * @version    V1.0    2014/05/21
 * @author     张守宁
 */
public class Encrypt_Util {

    // MD5参数
    private static String MD5 = "MD5";

    // 字符编码值
    private static String PAGEENCODING="utf-8";

    // 空值
    private static String KONG="";

    // md5加密附加值
    private static String ZERO="0";

    // md5双加密附加值
    private static String MD5APPEND="fbse0802fbse";

    /**
     * 完成对字符串的md5加密
     * @param str 参数
     * @return md5加密值
     */
    public static String md5(String str) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance(MD5);
        } catch (Exception e) {
            e.printStackTrace();
            return KONG;
        }
        byte[] btInput = null;
        try {
            btInput = str.getBytes(PAGEENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] md5Bytes = md5.digest(btInput);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)

        {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append(ZERO);
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 完成对字符串的双加密
     * @param value 传入值
     * @return md5值
     */
    public static String encrypt(String value) {
        return md5(md5(value) + MD5APPEND + md5(value));
    }
}
