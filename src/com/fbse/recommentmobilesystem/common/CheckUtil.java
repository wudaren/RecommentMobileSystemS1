/**
 * 项目名称 ：信智互联
 * 类一览：
 * CheckUtil       共通类
 */
package com.fbse.recommentmobilesystem.common;

/**
 * <dl>
 * <dd>功能名：字符校验
 * <dd>功能说明：完成对字符的校验。
 * @version    V1.0    2014/05/23
 * @author     张守宁
 */
public class CheckUtil {

    /**
     * 完成对字符的校验
     * @param obj 参数
     * @return result
     */
    public static boolean isEmpty(Object obj) {

        // 设置初始标志位false
        boolean result = false;

        // 如果参数不为null
        if (null != obj) {

            // 如果参数为空字符串
            if (CommonConst.SPACE.equals(obj.toString().trim())) {

                // 将标志设为ture
                result = true;
            }
        } else {
            result = true;
        }
        return result;
    }

}
