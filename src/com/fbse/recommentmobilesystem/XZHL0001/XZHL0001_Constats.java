package com.fbse.recommentmobilesystem.XZHL0001;

import java.util.Properties;

import android.content.Context;
public class XZHL0001_Constats {
	public static  String SERVRCEERROR="";
		public static  String LIEBIAOSHIBAI="";
		public static  String NETERROR="";
		 public static  String SHURUMM="";
	    public static  String SHURUMZ="";
	  	public static  String SHURUZH="";
		public  static  String LOGINACTION="";
		public static  String XMLACTION="";
		public static String SERVER="";
		public static String NEWVERSION="";
		public static String DOWNLOAD="";
		public static String ZIFUXIANZHI="";
		public static String FEIFALOGIN="";
		public static String VERSIONFUNCTION="";
		public static String LOGINING="";
		
		//丛配置文件中读取信息
		 public static Properties loadIPProperties(Context context,String str) {
				Properties props = new Properties();
				try {
					int id = context.getResources().getIdentifier(str, "raw",
							context.getPackageName());
					props.load(context.getResources().openRawResource(id));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return props;
			}
		 //获取端口号，ip
		 public static void initData(Properties pro){
			 String Host=pro.getProperty("xmppHost", "");
			 Host=Host.trim();
			 String port=pro.getProperty("tomcatPort", "");
			 port=port.trim();
			 String nameSpace=pro.getProperty("workspacename", "");
			 nameSpace=nameSpace.trim();
			 
			 XZHL0001_Constats.SERVER="http://"+Host+":"+port+"/"+nameSpace+"/";
			 XZHL0001_Constats.LOGINACTION=XZHL0001_Constats.SERVER+"login.action";
			 XZHL0001_Constats.XMLACTION="http://"+Host+":"+port+"/"+"FBSEMobileSystemS1/MyXml.xml";
			
		 }
		 //获取message
		 public static void initStrings(Properties pro){
			 XZHL0001_Constats.SERVRCEERROR=pro.getProperty("E0002", "");
			 XZHL0001_Constats.LIEBIAOSHIBAI=pro.getProperty("E0003", "");
			XZHL0001_Constats.NETERROR=pro.getProperty("E0008", "");
			XZHL0001_Constats.SHURUMZ=pro.getProperty("SHURUMZ", "");
			XZHL0001_Constats.SHURUZH=pro.getProperty("SHURUZH", "");
			XZHL0001_Constats.SHURUMM=pro.getProperty("SHURUMM", "");
			XZHL0001_Constats.NEWVERSION=pro.getProperty("I011", "");
			XZHL0001_Constats.DOWNLOAD=pro.getProperty("DOWNLOAD", "");
			XZHL0001_Constats.ZIFUXIANZHI=pro.getProperty("ZIFUXIANZHI", "");
			XZHL0001_Constats.FEIFALOGIN=pro.getProperty("FEIFALOGIN", "");
			XZHL0001_Constats.VERSIONFUNCTION=pro.getProperty("VERSIONFUNCTION", "");
			XZHL0001_Constats.LOGINING=pro.getProperty("LOGINING", "");
		 }
	}



