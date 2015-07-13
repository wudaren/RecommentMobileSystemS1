/**
 * 项目名称 ：信智互联
 * 类一览：
 * MessageUtil       共通类
 */
package com.fbse.recommentmobilesystem.common;

import java.util.Properties;

import android.content.Context;
import android.widget.Toast;

/**
 * <dl>
 * <dd>功能名：弹出消息
 * <dd>功能说明：按照指定文件。
 * @version    V1.0    2014/05/14
 * @author     张宁，李鑫
 */
public class MessageUtil {
    private static Toast mToast;
    private static Context mcontext = null;
    private static String EMPTY="";

    public static String getMessage(Context context, String message) {
        Properties properties = Commonutil.loadProperties(context);
        return properties.getProperty(message, EMPTY);
    }

    public static String getMessage(Context context, String message,
            String content) {
        Properties properties = Commonutil.loadProperties(context);
        return content.toString() + properties.getProperty(message, EMPTY);
    }

    public static String getMessage(Context context, String message,
            String[] content) {
        Properties properties = Commonutil.loadProperties(context);
        String backMessage = properties.getProperty(message, EMPTY);
        for (int i = 0; i < content.length; i++) {
            backMessage = backMessage.replace("{" + i + "}", content[i]);
        }
        return backMessage;
    }

    /**
     * 显示最后一条消息
     * @param context 上下文
     * @param msg 内容
     * @param duration 响应时间
     */
    public static void commonToast(Context context, String msg, int duration) {
        if (mcontext == context) {
            if (mToast == null) {
                mToast = Toast.makeText(context, msg, duration);
            } else {
                mToast.setText(msg);
            }

        } else {
            mToast = Toast.makeText(context, msg, duration);
            mcontext = context;
        }
        mToast.show();
    }
}
