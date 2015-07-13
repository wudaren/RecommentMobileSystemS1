
package com.fbse.recommentmobilesystem.XZHL0110;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fbse.recommentmobilesystem.common.common_entity.ShowInfo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Vibrator;

public class XZHL0110_Utils {

    // 内存软缓存

    public static synchronized InputStream getStreamFromURL(final String imageURL) {

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

    // 加载网络图片
    @SuppressLint("SdCardPath")
    public static synchronized Bitmap loadBitmap(final String imageURL) {

        // 初始化图片缓存

        // 获得图片名
        String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
        // 判断sd卡状况 如果sd卡异常 则不进行本地缓存
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            InputStream bitmapIs = getStreamFromURL(imageURL);

            Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
            return bitmap;
        } else {
            // 本地缓存设置
            File cacheDir = new File("/mnt/sdcard/RecommentMobileSystem/");
            if (!cacheDir.exists()) {
                // 如果不存在文件夹 则构造该文件夹
                cacheDir.mkdirs();
            }
            File[] cacheFiles = cacheDir.listFiles();
            int i = 0;

            for (; i < cacheFiles.length; i++) {
                if (bitmapName.equals(cacheFiles[i].getName())) {
                    break;
                }
            }
            // 本地缓存有信息 则直接获取
            if (i < cacheFiles.length) {
                return BitmapFactory.decodeFile("/mnt/sdcard/RecommentMobileSystem/" + bitmapName);
            } else {
                // 获得流
                InputStream bitmapIs = getStreamFromURL(imageURL);
                // 加载图片
                Bitmap bitmap = null;
                if (bitmapIs != null) {
                    bitmap = BitmapFactory.decodeStream(bitmapIs);

                    // 添加内存软缓存

                    // 本地缓存
                    File dir = new File("/mnt/sdcard/RecommentMobileSystem/");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    File bitmapFile = new File("/mnt/sdcard/RecommentMobileSystem/"
                            + imageURL.substring(imageURL.lastIndexOf("/") + 1));
                    if (!bitmapFile.exists()) {
                        try {
                            bitmapFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return bitmap;
                        }
                    }
                    FileOutputStream fos;
                    try {
                        fos = new FileOutputStream(bitmapFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        fos.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return bitmap;
                    }
                }
                return bitmap;

            }
        }
    }

    // 加载网络图片
    @SuppressLint("SdCardPath")
    public static synchronized Bitmap loadBitmapWithOutCash(final String imageURL) {

        // 初始化图片缓存

        // 获得图片名
        String bitmapName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
        // 判断sd卡状况 如果sd卡异常 则不进行本地缓存

        InputStream bitmapIs = getStreamFromURL(imageURL);

        Bitmap bitmap = BitmapFactory.decodeStream(bitmapIs);
        return bitmap;

    }

    /**
     * JSON解析
     * @param json
     * @return
     * @throws JSONException
     */
    public static synchronized List<ShowInfo> jiexiJSON(String json) throws JSONException {

        List<ShowInfo> list = new ArrayList<ShowInfo>();
        ShowInfo showInfo;
        JSONTokener jsonParser = new JSONTokener(json);

        JSONArray userlist = (JSONArray) jsonParser.nextValue();
        JSONObject user;
        for (int i = 0; i < userlist.length(); i++) {
            showInfo = new ShowInfo();
            user = userlist.getJSONObject(i);

            showInfo.setVipName(user.getString("VipName"));
            showInfo.setCardTypeName(user.getString("CardTypeName"));
            showInfo.setMobile(user.getString("Mobile"));
            showInfo.setImageUrl(user.getString("ImageUrl"));
            JSONArray ConTrendInfor = user.getJSONArray("ConTrendInfor");
            String[] strs = new String[ConTrendInfor.length()];
            for (int j = 0; j < ConTrendInfor.length(); j++) {
                strs[j] = ConTrendInfor.getString(j);
            }
            showInfo.setConTrendInfor(strs);
            JSONArray PromoInfor = user.getJSONArray("PromoInfor");
            String[] strss = new String[PromoInfor.length()];
            for (int j = 0; j < PromoInfor.length(); j++) {
                strss[j] = PromoInfor.getString(j);
            }
            showInfo.setPromoInfor(strss);

            list.add(showInfo);

        }
        return list;

    }

    // 设置响铃震动事件
    public static int PlaySound(final Context context) {

        NotificationManager mgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification nt = new Notification();
        nt.defaults = Notification.DEFAULT_SOUND;
        int soundId = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
        mgr.notify(soundId, nt);
        Vibrator vibrator;

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 100, 400 }; // 停止 开启 停止 开启
        vibrator.vibrate(pattern, -1);
        return soundId;

    }

    // 本地图片缓存清除
    @SuppressLint("SdCardPath")
    public static Boolean tempClean() {

        File cacheDir = new File("/mnt/sdcard/RecommentMobileSystem/");
        if (!cacheDir.exists() || !cacheDir.isDirectory()) {
            return false;
        }
        File[] files = cacheDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            files[i].delete();
        }

        return true;

    }

}
