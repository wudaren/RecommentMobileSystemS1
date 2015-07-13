package com.fbse.recommentmobilesystem.XZHL0003;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;


public class XZHL0003_PowerService extends Service{

	private PowerManager pm ;
	private PowerManager.WakeLock mWakeLock;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag"); 
		mWakeLock.acquire(); 
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		mWakeLock.release();
		super.onDestroy();
	}

}
