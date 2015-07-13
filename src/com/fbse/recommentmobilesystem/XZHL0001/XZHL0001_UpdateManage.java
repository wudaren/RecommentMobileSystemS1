package com.fbse.recommentmobilesystem.XZHL0001;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.fbse.recommentmobilesystem.R;
import com.fbse.recommentmobilesystem.common.Commonutil;
import com.fbse.recommentmobilesystem.common.HttpSubmit;
import com.fbse.recommentmobilesystem.common.Msg;

@SuppressLint("HandlerLeak")
public class XZHL0001_UpdateManage {

	/** --------------------------- */
	/* 下载中 */
	private static final int DOWNLOAD = 1;
	/* 下载结束 */
	private static final int DOWNLOAD_FINISH = 2;
	/* 保存解析的XML信息 */
	ArrayList<XZHL0001_Servexml> list;
	/* 下载保存路径 */
	private String mSavePath;
	/* 记录进度条数量 */
	private int progress;
	/* 是否取消更新 */
	private boolean cancelUpdate = false;

	private Context mContext;
	/* 更新进度条 */
	private ProgressBar mProgress;
	private Dialog mDownloadDialog;
	public static Properties properties;
	 Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				mProgress.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
		//
		//
		//
	};
	

	public XZHL0001_UpdateManage(Context context) {
		this.mContext = context;
	}

	/**
	 * 检测软件更新
	 */
	public void checkUpdate() {
		if (true)

		{
			// 显示提示对话框
			showNoticeDialog();

		}
	}

	/**
	 * 检查软件是否有更新版本
	 * 
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public boolean isUpdate() throws XmlPullParserException, IOException {
		// 获取当前软件版本
		int versionCode = getVersionCode(mContext);
		
		// 把version.xml放到网络上，然后获取文件信息
		HttpEntity entity;
		InputStream inStream = null;
		try {
				//String path="http://192.168.1.252:8080/FBSEMobileSystemS1/MyXml.xml";
			String path=XZHL0001_Constats.XMLACTION;
			entity = HttpSubmit.getEntity(path, null, 1);
			if(entity==null){
				
				return false;
			}
            Log.i("1111", path);
			inStream = HttpSubmit.getStream(entity);
		
			XZHL0001_XmlParser music = new XZHL0001_XmlParser();
			
			ArrayList<XZHL0001_Servexml> xml = music.parse(inStream);
			
			list = xml;
			
			int serviceCode = Integer.valueOf(xml.get(0).getId());

			// 版本判断
			if (serviceCode > versionCode) {
				return true;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}

		return false;
	}

	/**
	 * 获取软件版本号
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// 获取软件版本号，对应AndroidManifest.xml下android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 显示软件更新对话框
	 */
	private void showNoticeDialog() {
		Properties properties = Commonutil.loadProperties(mContext);
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		builder.setTitle(XZHL0001_Constats.NEWVERSION);
		builder.setCancelable(false);
		builder.setMessage(XZHL0001_Constats.VERSIONFUNCTION);
		// 更新
		builder.setPositiveButton(properties.getProperty(Msg.I012, ""),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 显示下载对话框

						showDownloadDialog();
					}
				});
		// 稍后更新
		builder.setNegativeButton(properties.getProperty(Msg.I005, ""),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		builder.create().show();

	}

	/**
	 * 显示软件下载对话框
	 * 
	 */
	private void showDownloadDialog() {
		// 构造软件下载对话框
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(XZHL0001_Constats.DOWNLOAD);
		builder.setCancelable(false);
		// 给下载对话框增加进度条
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.xzhl0001_progressbar, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progressBar1);
		builder.setView(v);

		mDownloadDialog = builder.create();

		mDownloadDialog.show();
		// 现在文件

		downloadApk();
	}

	/**
	 * 下载apk文件
	 */
	private void downloadApk() {
		// 启动新线程下载软件
		
		new downloadApkThread().start();
	}
	  public void loging()
	   {
	       // 构造软件下载对话框
	       AlertDialog.Builder builder = new Builder(mContext);
	       builder.setCancelable(false);
	       builder.setTitle(XZHL0001_Constats.LOGINING);
	       // 给下载对话框增加进度条
	       final LayoutInflater inflater = LayoutInflater.from(mContext);
	        View v = inflater.inflate(R.layout.xzhl0001_logining, null);
	       mProgress = (ProgressBar) v.findViewById(R.id.progressBar2);
	        builder.setView(v);
	       
	     mDownloadDialog = builder.create();
	       
	       mDownloadDialog.show();
	       
	    denglu();
	     
	   }
	   private void denglu()
	   {
	       // 启动新线程下载软件
		 
		   String name = mContext.getSharedPreferences("name",0 ).toString().trim();
		   if (name.length()!=0 && name!=null) {
			   mHandler.sendEmptyMessage(3);
			
		}
		   
	   }
	/**
	 * 下载文件线程
	 * 
	 * @author coolszy
	 * @date 2012-4-26
	 * @blog http://blog.92coding.com
	 */
	private class downloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED))
					if (true) {
						// 获得存储卡的路径
						
						String sdpath = Environment
								.getExternalStorageDirectory().getPath() + "/";
						System.out.println(Environment
								.getExternalStorageDirectory());

						mSavePath = sdpath + "download";
						String uri = list.get(0).getUrl();
						System.out.println(uri);
						URL url = new URL(uri);
					
						// 创建连接
						HttpURLConnection conn = (HttpURLConnection) url
								.openConnection();
						conn.connect();

						// 获取文件大小
						int length = conn.getContentLength();
						// 创建输入流
						InputStream is = conn.getInputStream();

						File file = new File(mSavePath);
						// 判断文件目录是否存在
						if (!file.exists()) {
							file.mkdir();
						}
						File apkFile = new File(mSavePath, list.get(0)
								.getName());
						FileOutputStream fos = new FileOutputStream(apkFile);
						int count = 0;
						// 缓存
						byte buf[] = new byte[1024];
					
						// 写入到文件中
						do {
							int numread = is.read(buf);
							count += numread;
							// 计算进度条位置
							progress = (int) (((float) count / length) * 100);
							// 更新进度
							mHandler.sendEmptyMessage(DOWNLOAD);

							if (numread <= 0) {
								// 下载完成
								mHandler.sendEmptyMessage(DOWNLOAD_FINISH);

								break;
							}
							// 写入文件
							fos.write(buf, 0, numread);
						} while (!cancelUpdate);// 点击取消就停止下载.
						fos.close();
						is.close();
					}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			mDownloadDialog.dismiss();
			
		}
	};

	/**
	 * 安装APK文件
	 */
	private void installApk() {
		File apkfile = new File(mSavePath, list.get(0).getName());
		if (!apkfile.exists()) {
			
			return;
		}

		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
	}

}
