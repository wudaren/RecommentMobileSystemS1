package com.fbse.recommentmobilesystem.XZHL0001;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
//手机向服务器端利用jason发送请求
public class XZHL0001_JsonUtil1_HttpSubmitto {
	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;
	public static HttpEntity getEntity(String uri, List<BasicNameValuePair> params,
			int method) throws IOException {
		HttpEntity entity = null;
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				3000);
		HttpUriRequest request = null;
		switch (method) {
		case METHOD_GET:// get请求
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append('?');
				for (BasicNameValuePair pair : params) {
					sb.append(pair.getName()).append('=').append(pair.getValue())
							.append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			System.out.println(sb.toString());
			request = new HttpGet(sb.toString());
			break;

		case METHOD_POST:// post请求
			request = new HttpPost(uri);
			if (params != null && !params.isEmpty()) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params,"UTF-8");
				((HttpPost) request).setEntity(reqEntity);
			}
			break;
		}
		// 执行请求获得响应对象
		HttpResponse response = client.execute(request);
		// 判断响应码，如果为200，则获取响应实体
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
		}
		return entity;
	}
	public static InputStream getStream(HttpEntity entity) throws IOException {
		InputStream in = null;
		if (entity != null) {
			in = entity.getContent();
		}
		return in;
	}

	public static long getLength(HttpEntity entity) {
		long len = 0;
		if (entity != null)
			len = entity.getContentLength();
		return len;
	}

	// post提交方式
	public static String ListTest2(String path, List<BasicNameValuePair> parameters)
			throws ClientProtocolException, IOException {
		//请求地址
		//String path = "http://192.168.1.252:8080/FBSEMobileSystemS1/FBSEMobilSystem/login.action";
		
		 HttpClient httpClient = new DefaultHttpClient();  
		          //创建请求方式对象  path   
		          HttpPost httpPost = new HttpPost(path);  
		          //封装请求的参数集合   
//		          List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();  
//		          parameters.add(new BasicNameValuePair("shopId", "10000"));  
		          //parameters.add(new BasicNameValuePair("password", pass));  
		          //返回参数 
		          String result ="";
		          UrlEncodedFormEntity entity = null;  
		         
		              //封装请参数的实体对象   
		              entity = new UrlEncodedFormEntity(parameters, "UTF-8");  
		              //把参数设置到 httpPost中   
		              httpPost.setEntity(entity);  
		              //执行请求   
		              HttpResponse httpResponse = httpClient.execute(httpPost);  
		              
		              //判断响应是否成功   
		              if (httpResponse.getStatusLine().getStatusCode() == 200) {  
		                  //获取响应的内容   
		                  //InputStream is = httpResponse.getEntity().getContent();
		            	  //DataInputStream dis = new DataInputStream(httpResponse.getEntity().getContent());
		            	  //返回值接收
		            	 InputStream is = httpResponse.getEntity().getContent();
		            	  BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		            	  StringBuilder tempStr = new StringBuilder("{");
		                  while (rd.read() != -1) {
		                	  
		                      tempStr.append(rd.readLine());
		                      System.out.println(tempStr.toString());
		                  }
		              
		            	
		          		result = tempStr.toString();
		          		
		              }else{
		            	  return null;
		              }  
		         
		 return result;
	}



}

