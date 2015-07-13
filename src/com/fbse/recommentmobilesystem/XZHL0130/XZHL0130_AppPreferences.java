package com.fbse.recommentmobilesystem.XZHL0130;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class XZHL0130_AppPreferences {

private SharedPreferences mPref = null;
	
	public static final String sPrefName 		= "FDRControlSamplePrefs";
	public static final String sHostKey 		= "Host";
	public static final String sPortKey 		= "Port";
	public static final String sUsernameKey 	= "Username";
	public static final String sPasswordKey 	= "Password";
	
	public XZHL0130_AppPreferences(Context context) {
		
		mPref = getAppPreferences(context);
	}
	
	public static SharedPreferences getAppPreferences(Context context) {

	    return context.getSharedPreferences(sPrefName, Context.MODE_PRIVATE);
	}
	
	public void setHost(String host) {

		Editor editor = mPref.edit();
		editor.putString(sHostKey, host);
		editor.commit();
	}
	
	public String getHost() {
		
		return mPref.getString(sHostKey, "");
	}
	
	public void setPort(int port) {

		Editor editor = mPref.edit();
		editor.putInt(sPortKey, port);
		editor.commit();
	}
	
	public int getPort() {
		
		return mPref.getInt(sPortKey, 80);
	}
	
	public void setUsername(String username) {

		Editor editor = mPref.edit();
		editor.putString(sUsernameKey, username);
		editor.commit();
	}
	
	public String getUsername() {
		
		return mPref.getString(sUsernameKey, "");
	}
	
	public void setPassword(String password) {

		Editor editor = mPref.edit();
		editor.putString(sPasswordKey, password);
		editor.commit();
	}
	
	public String getPassword() {
		
		return mPref.getString(sPasswordKey, "");
	}
}
