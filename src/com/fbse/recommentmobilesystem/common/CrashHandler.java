/**
 * 项目名称 ：信智互联
 * 类一览：
 * CrashHandler       共通类
 */
package com.fbse.recommentmobilesystem.common;

import java.lang.Thread.UncaughtExceptionHandler;

import android.os.Looper;
import android.util.Log;

/**
 * <dl>
 * <dd>功能名：捕获异常
 * <dd>功能说明：捕获应用运行时异常。
 * @version    V1.0    2014/05/23
 * @author     张宁
 */
public class CrashHandler implements UncaughtExceptionHandler {

    // TAG标签
    public static final String TAG = "CrashHandler";

    private static CrashHandler INSTANCE = new CrashHandler();

    @SuppressWarnings("unused")
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 空构造器
     */
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     */
    public void init() {
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        // 捕获异常
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 程序异常之后的操作
     */
    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {

        System.out.println("uncaughtException");

        // 开市起线程打印异常
        new Thread() {

            @Override
            public void run() {

                Looper.prepare();
                Log.e(TAG, ex.getMessage(), ex);
                LogUtil.logException((Exception) ex);
                Looper.loop();
            }
        }.start();
    }

}
