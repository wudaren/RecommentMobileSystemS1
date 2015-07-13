/**
 * 项目名称 ：信智互联
 * 类一览：
 * HttpSubmit       共通类
 */
package com.fbse.recommentmobilesystem.common;

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

/**
 * <dl>
 * <dd>功能名：服务端请求
 * <dd>功能说明：手机向服务器端利用jason发送请求。
 * @version    V1.0    2014/05/21
 * @author     张守宁
 */
public class HttpSubmit {

    // GET请求
    public static final int METHOD_GET = 1;

    // POST请求
    public static final int METHOD_POST = 2;

    // 超时时间
    private static int WAIT=3000;

    // 字符编码值
    private static String PAGEENCODING="utf-8";

    /**
     * HttpEntity服务请求
     * @param uri 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return entity
     */
    public static HttpEntity getEntity(String uri,
            List<BasicNameValuePair> params, int method) throws IOException {
        HttpEntity entity = null;
        HttpClient client = new DefaultHttpClient();

        // 设置服务器超时时间
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, WAIT);
        HttpUriRequest request = null;
        switch (method) {

        // get请求
        case METHOD_GET:
            StringBuilder sb = new StringBuilder(uri);
            if (params != null && !params.isEmpty()) {
                sb.append('?');
                for (BasicNameValuePair pair : params) {
                    sb.append(pair.getName()).append('=').append(pair.getValue()).append('&');
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            request = new HttpGet(sb.toString());
            break;

        // post请求
        case METHOD_POST:
            request = new HttpPost(uri);
            if (params != null && !params.isEmpty()) {
                UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params, PAGEENCODING);
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

    /**
     * http实体转换为流
     * @param entity 请求实体
     * @return 输入流
     * @throws IOException
     */
    public static InputStream getStream(HttpEntity entity) throws IOException {
        InputStream in = null;
        if (entity != null) {
            in = entity.getContent();
        }
        return in;
    }

    /**
     * 获取请求长度
     * @param enti 请求实体
     * @return 请求长度
     */
    public static long getLength(HttpEntity entity) {
        long len = 0;
        if (entity != null)
            len = entity.getContentLength();
        return len;
    }

    // ---------------------------------------------------------------------------------------------------------
    /**
     * post提交方式
     * @param path 地址
     * @param parameters 请求参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String ListTest2(String path,
            List<BasicNameValuePair> parameters) throws ClientProtocolException, IOException {

        // 请求地址
        HttpClient httpClient = new DefaultHttpClient();

        // 创建请求方式对象 path
        HttpPost httpPost = new HttpPost(path);
        String result = "";
        UrlEncodedFormEntity entity = null;

        // 封装请参数的实体对象
        entity = new UrlEncodedFormEntity(parameters, PAGEENCODING);

        // 把参数设置到 httpPost中
        httpPost.setEntity(entity);

        // 执行请求
        HttpResponse httpResponse = httpClient.execute(httpPost);

        // 判断响应是否成功
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            InputStream is = httpResponse.getEntity().getContent();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder tempStr = new StringBuilder();
            while(rd.read() != -1){
                tempStr.append(rd.readLine());
            }
            result = tempStr.toString();
        } else {
            return null;
        }
        return result;
    }

}
