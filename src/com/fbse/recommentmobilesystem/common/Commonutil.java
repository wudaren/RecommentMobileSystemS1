/**
 * 项目名称 ：信智互联
 * 类一览：
 * Commonutil       共通类
 */
package com.fbse.recommentmobilesystem.common;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * <dl>
 * <dd>功能名：共通工具包
 * <dd>功能说明：完成对字符的校验。
 * @version    V1.0    2014/05/15
 * @author     张宁
 */
public class Commonutil {

    /**
     * 读取res/raw/message文件
     * @param context 上下文
     * @return 返回Properties文件对象
     */
    public static Properties loadProperties(Context context) {

        // 创建Properties对象
        Properties props = new Properties();
        try {

            // 获得资源id
            int id = context.getResources().getIdentifier(CommonConst.MESSAGE, CommonConst.RAW,
                    context.getPackageName());

            // 加载资源文件
            props.load(context.getResources().openRawResource(id));
        } catch (Exception e) {

            e.printStackTrace();
            return props;
        }
        return props;
    }

    /**
     * 读取res/raw/androidpn文件
     * @param context 上下文
     * @return 返回Properties文件对象
     */
    public static Properties loadIPProperties(Context context) {

        // 创建Properties对象
        Properties props = new Properties();
        try {
            int id = context.getResources().getIdentifier(CommonConst.ANDROIDPN, CommonConst.RAW,
                    context.getPackageName());

            // 加载资源文件
            props.load(context.getResources().openRawResource(id));
        } catch (Exception e) {
            e.printStackTrace();
            return props;
        }
        return props;
    }

    /**
     * 检测网络环境是否良好
     * @param context 上下文
     * @return false
     */
    public static boolean isNetworkAvailable(Context context) {

        // 获得系统连接管理者
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (null != info) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        // 如果网络状态正常则返回true
                        return true;
                    }
                }
            }
        }

        // NETERROR
        Properties properties = Commonutil.loadProperties(context);

        // 提示是否网络状态异常
        MessageUtil.commonToast(context, properties.getProperty(Msg.E0008, CommonConst.SPACE), Toast.LENGTH_SHORT);
        return false;
    }

    /**
     * 加载网络图片，生成没有缓存的额图片
     * @param imageURL 图片url地址
     * @return bitmap
     */
    public static synchronized Bitmap loadBitmapWithOutCash(String imageURL) {

        // 转换地址为输入流
        InputStream bitmapIs = getStreamFromURL(imageURL);

        // 转换为Bitmap类型
        Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
        return bitmap;
    }

    /**
     * 将图片地址转换为流
     * @param imageURL 图片url地址
     * @return 图片流
     */
    public static synchronized InputStream getStreamFromURL(String imageURL) {

        InputStream in = null;
        try {
            URL url = new URL(imageURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            in = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;
    }

}
