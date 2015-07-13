/**
 * 项目名称 ：信智互联
 * 类一览：
 * DoubleClickReturn       共通类
 */
package com.fbse.recommentmobilesystem.common;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.fbse.recommentmobilesystem.XZHL0003.XZHL0003_PowerService;

/**
 * <dl>
 * <dd>功能名：双击退出
 * <dd>功能说明：双击退出。
 * @version    V1.0    2014/06/05
 * @author     FBSE
 */
public class DoubleClickReturn {

    // 标志
	public static boolean isExit = false;
	public static boolean delflg = false;
	
	// 消息等待时间为2000毫秒
	private static int WAITDOUBLE=2000;

	public static void doubleClickReturnEvent(Context context) {
		if (!delflg) {
			exit(context);
		} else {
			delflg = false;
		}
	}

	static Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}

	};

	public static void exit(Context context) {
		if (!isExit) {
			isExit = true;
			MessageUtil.commonToast(context, MessageUtil.getMessage(context, Msg.I003), Toast.LENGTH_SHORT);

			// 双击等待时间
			mHandler.sendEmptyMessageDelayed(0, WAITDOUBLE);
		}else{

		    // 停止屏幕常亮服务
			context.stopService(new Intent(context,XZHL0003_PowerService.class));

			// 退出程序
			System.exit(0);
		}
	}
}
