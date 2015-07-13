package com.fbse.recommentmobilesystem.XZHL0210;

import java.util.Properties;

import android.content.Context;

import com.fbse.recommentmobilesystem.common.MessageUtil;
import com.fbse.recommentmobilesystem.common.Msg;


public class XZHL0210_Constants {
	public static String YICHAOCHANG="";
	public static String TIJIAOMA="";
	public static String FANGQI="";
	public static String QINGDAWAN="";
	public static String HAIMEIDA="";
	public static  String YIDAWAN="";
	public static  String ZANWU="";
	public static  String SERVRCEERROR="";
	public static  String LIEBIAOSHIBAI="";
	public static  String QUXML="";
	public static  String SENDINFO="";
    public static  String ERRORSENDANSWER="";
    public static  String GINFO="";
  	public static  String TINFO="";
	public static  String LISTACTION="";
	public static  String DETAILACTION="";
	public static  String SENDANSWER="";
	public static  String SHOPID="";
	public static  String USERNAME="";
	public static  String SERIAL="a001";
	public static  String TOKEN="";
	public static  String SERVER="";
	public static String QUEDING="";
	public static String QUXIAO="";
	public static String DAANING="";
	public static String NETERROR="";
	//读取raw下服务器的配置信息
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
	 //初始化action访问的数据
	 public static void initData(Properties pro){
		 String Host=pro.getProperty("xmppHost", "");
		 Host=Host.trim();
		 String port=pro.getProperty("tomcatPort", "");
		 port=port.trim();
		 String nameSpace=pro.getProperty("workspacename", "");
		 nameSpace=nameSpace.trim();
		 XZHL0210_Constants.SERVER="http://"+Host+":"+port+"/"+nameSpace+"/";
		 XZHL0210_Constants.LISTACTION=XZHL0210_Constants.SERVER+"wenjuanyilan.action";
		 XZHL0210_Constants.DETAILACTION="http://"+Host+":"+port+"/"+"FBSEMobileSystemS1/xmlFile/";
		 XZHL0210_Constants.SENDANSWER=XZHL0210_Constants.SERVER+"tijiaodaan.action";
		
	 }
	 //初始化需要用到的字符串常量
	 public static void initStrings(Context context){
		 XZHL0210_Constants.ZANWU=MessageUtil.getMessage(context, Msg.I014);
		 XZHL0210_Constants.YIDAWAN=MessageUtil.getMessage(context, Msg.W003);
		 XZHL0210_Constants.HAIMEIDA=MessageUtil.getMessage(context, Msg.W002);
		 XZHL0210_Constants.QINGDAWAN=MessageUtil.getMessage(context,Msg.W001);
		 XZHL0210_Constants.FANGQI=MessageUtil.getMessage(context, Msg.Q005);
		 XZHL0210_Constants.TIJIAOMA=MessageUtil.getMessage(context, Msg.Q004);
		 XZHL0210_Constants.YICHAOCHANG=MessageUtil.getMessage(context, Msg.E0017,new String[]{"30"});
	   XZHL0210_Constants.SERVRCEERROR=MessageUtil.getMessage(context, Msg.E0002);
	   XZHL0210_Constants.LIEBIAOSHIBAI=MessageUtil.getMessage(context, Msg.E0003);
	   XZHL0210_Constants.QUXML=MessageUtil.getMessage(context, Msg.I006);
	   XZHL0210_Constants.SENDINFO=MessageUtil.getMessage(context, Msg.I007);
	   XZHL0210_Constants.ERRORSENDANSWER=MessageUtil.getMessage(context, Msg.E0004);
	   XZHL0210_Constants.GINFO=MessageUtil.getMessage(context, Msg.I001);
	   XZHL0210_Constants.TINFO=MessageUtil.getMessage(context, Msg.I002);
	   XZHL0210_Constants.QUEDING=MessageUtil.getMessage(context, Msg.I012);
	   XZHL0210_Constants.QUXIAO=MessageUtil.getMessage(context, Msg.I005);
	   XZHL0210_Constants.DAANING=MessageUtil.getMessage(context, Msg.I015);
	   XZHL0210_Constants.NETERROR=MessageUtil.getMessage(context,Msg.E0008);
	 }
}
