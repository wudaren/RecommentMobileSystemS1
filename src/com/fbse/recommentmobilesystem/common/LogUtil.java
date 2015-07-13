/**
 * 项目名称 ：信智互联
 * 类一览：
 * LogUtil       共通类
 */
package com.fbse.recommentmobilesystem.common;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.os.Environment;
import android.util.Log;

/**
 * <dl>
 * <dd>功能名：后台打印log
 * <dd>功能说明：程序运行当中。
 * @version    V1.0    2014/05/14
 * @author     张宁，李鑫
 */
public class LogUtil {

    // 时间格式
    private static SimpleDateFormat DJ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);

    // 获得系统时间
    private static String TIME = DJ.format(new Date());

    // SD路径
    private static String path = Environment.getExternalStorageDirectory().getAbsolutePath();

    // 文件夹
    private static String FOLDER="/LogText";

    // 文件
    private static String FILE = FOLDER + "/LogRecord.txt";

    // 格式化日期格式
    private static String FORMATDATA="yyyy-MM-dd";

    // 打印log开关-true为开,false为关
    private static boolean isdebug = false;
    
    // 方法开始标签
    private static String methodBengin="方法开始";

    // 方法结束标签
    private static String methodEnd="方法结束";

    /**
     * 方法开始时调用 打印方法开始
     */
    public static void logStart() {

        if (isdebug) {

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                // 文件反射获取调用类和方法名
                StackTraceElement[] stack = (new Throwable()).getStackTrace();

                StackTraceElement ste = stack[1];

                // 获取调用此方法的类名
                String file = completion(ste.getFileName());

                // 获取调用此方法的方法名
                String method = completion(ste.getMethodName());

                // 拼写写入内容
                String content = rules(file, method, "开始");

                // 后台log打印
                Log.i(file, method + methodBengin);

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                    // 读取文件 判断文件存在时间 超过三天 文件被删除
                    readFile();
                    print(content);
                }
            }
        }
    }

    /**
     * 方法结束时调用 打印方法结束
     */
    public static void logEnd() {

        if (isdebug) {

            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            StackTraceElement ste = stack[1];
            String file = completion(ste.getFileName());
            String method = completion(ste.getMethodName());
            String content = rules(file, method, "结束");
            Log.i(ste.getFileName(), ste.getMethodName() + methodEnd);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                // 读取文件 判断文件存在时间 超过三天 文件被删除
                readFile();
                print(content);
            }
        }
    }

    // log打印规则
    private static String rules(String file, String method,String tag) {
        String content = TIME + "\t" + file + "\t\t" + method + "\t\t\t+tag+\r\n";
        return content;
    }

    // 方法异常时调用 打印方法异常
    public static void logException(Exception exception) {

        // 检测文件是否存在 不存在则创建文件
        if (isdebug) {
            StackTraceElement[] stack = (new Throwable()).getStackTrace();
            StackTraceElement ste = stack[1];
            String file = completion(ste.getFileName());
            String method = completion(ste.getMethodName());
            String content = rules(file, method, "发生异常");
            Log.i(ste.getFileName(), ste.getMethodName() + "方法发生异常");

            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                // 读取文件 判断文件存在时间 超过三天 文件被删除
                readFile();
                print(content);
                print(exception + "\r\n");
            }
        }
    }

    /**
     * 写入内容格式化
     * @param str 内容
     * @return 格式化后的内容
     */
    private static String completion(String str) {

        int bit = str.length();
        StringBuffer sb = new StringBuffer(str);
        if (bit < 30) {
            for (int i = 0; i < (30 - bit); i++) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 创建并写入文件
     * @param content 内容
     */
    private static void print(String content) {

        FileWriter writer = null;
        try {
            File file = new File(path + FOLDER);
            if (!file.exists()) {
                file.mkdirs();
            }
            File fileName = new File(path + FILE);
            if (!fileName.exists()) {
                if (!fileName.createNewFile()) {
                    Log.i("IOException", "文件创建失败");
                }
            } else {
                writer = new FileWriter(fileName, true);
                writer.write(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件 判断文件存储时间 超过三天被删除
     */
    private static void readFile() {

        // 获取文件中写入的第一条信息的时间
        StringBuffer time = new StringBuffer();
        InputStream in = null;
        try {
            FileReader fr = new FileReader(path + FILE);
            int ch = 0;

            // 字节流获取第一条信息的时间
            while ((ch = fr.read()) != -1) {
                time.append((char) ch);
                if (time.length() > 9) {
                    break;
                }
            }

            // 时间格式化
            SimpleDateFormat df = new SimpleDateFormat(FORMATDATA,Locale.CHINA);
            Date date = df.parse(time.toString());
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);

            // 5是以提天为单位 3加的数值
            gc.add(5, 3);

            // 将gc转换为Date格式进行时间大小比较
            String timeBefore3Str = df.format(gc.getTime());
            Date timeBefore3 = df.parse(timeBefore3Str);
            Date dateNow = new java.util.Date();

            // 判断文件创建时间后的三天是否是当前系统日期 大于系统日期 则被删除
            if (timeBefore3.before(dateNow)) {
                deleteFile();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    // 删除文件
    private static void deleteFile() {

        File fileName = new File(path + FILE);
        if (fileName.exists()) {
            fileName.delete();
        }
    }

}
