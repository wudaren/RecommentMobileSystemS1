package com.fbse.recommentmobilesystem.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.util.Log;

public class WebService {


    SoapObject sb = null;
    String soapAction = null;
    Properties properties = null;

    /**
     * 
     * @param context
     *            上下文
     * @param methodName
     *            访问的方法名
     * 
     * @param propert
     *            参数列表
     * @return 网络返回JSON
     */
    // 上传数据
    public String WSservers(Context context, String methodName,
            String endpoint, List<String> propert) {

        properties = Commonutil.loadIPProperties(context);

        String ip = properties.getProperty("ipofshanghai", "");
        String port = properties.getProperty("portofshanghai", "");
        String getend = properties.getProperty("endpointofshanghai", "");

        String namespace = properties.getProperty("namespace", "");

        String endPoint = " http://" + ip + ":" + port + "/" + getend + "/"
                + endpoint + "?wsdl";
        Log.i("endpoint", endPoint);
        // SOAP Action
        soapAction = namespace + "/" + methodName;

        // 指定WebService的命名空间和调用的方法名
        sb = new SoapObject(namespace, methodName);
        for (int i = 0; i < propert.size(); i++) {
            sb.addProperty("arg" + i, propert.get(i));
        }
        return doWebService(endPoint);
    }

    public static String WSserverss(String portname, String json) {
//      String urlDate ="http://210.51.51.164:9093/v1/"
         HttpURLConnection conn;
         URL url;
         InputStream is;
            String urlDate ="http://192.168.1.189:8080/v1/"+portname + json;
        StringBuffer sb = null;
       try {
            url = new URL(urlDate);
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                is = conn.getInputStream();
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                String line = null;
                sb = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String result = "error";
        if (sb != null) {
            return sb.toString();
        }
        return result;
    }
    
    public String WSserversoflocal(Context context, String methodName,
            String endpoint, List<String> propert) {

        properties = Commonutil.loadIPProperties(context);

        String ip = properties.getProperty("xmppHost", "");
        String port = properties.getProperty("tomcatPort", "");
        String getend = properties.getProperty("endpointofshanghai", "");

        String namespace = properties.getProperty("namespace", "");

        String endPoint = " http://" + ip + ":" + port + "/" + getend + "/"
                + endpoint + "?wsdl";
        Log.i("endpoint", endPoint);
        // SOAP Action
        soapAction = namespace + "/" + methodName;

        // 指定WebService的命名空间和调用的方法名
        sb = new SoapObject(namespace, methodName);
        for (int i = 0; i < propert.size(); i++) {
            sb.addProperty("arg" + i, propert.get(i));
        }
        return doWebService(endPoint);
    }

    public String WSserversofdaili(Context context, String methodName,
            String endpoint, List<String> propert) {

        properties = Commonutil.loadIPProperties(context);

        String ip = properties.getProperty("ipofdaili", "");
        String port = properties.getProperty("portofdaili", "");
        String getend = properties.getProperty("servicenameofdaili", "");

        String namespace = properties.getProperty("namespaceofdaili", "");

        String endPoint = " http://" + ip + ":" + port + "/" + getend + "/"
                + endpoint + "?wsdl";
        Log.i("0520", endPoint);
        // SOAP Action
        soapAction = namespace + "/" + methodName;

        // 指定WebService的命名空间和调用的方法名
        sb = new SoapObject(namespace, methodName);
        if (propert != null) {

            for (int i = 0; i < propert.size(); i++) {
                sb.addProperty("arg" + i, propert.get(i));
            }
        }
        return doWebService(endPoint);
    }
    

    public String WSserversofdaili1(Context context, String methodName,
            String endpoint, List<String> propert) {

        properties = Commonutil.loadIPProperties(context);

        String ip = properties.getProperty("ipofdaili", "");
        String port = properties.getProperty("portofdaili", "");
        String getend = properties.getProperty("servicenameofdaili", "");

        String namespace = properties.getProperty("namespaceofdaili", "");

        String endPoint = " http://" + ip + ":" + port + "/" + getend + "/"
                + endpoint + "?wsdl";
        endPoint="http://192.168.1.227:8080/FBSEMobileSystemS1/"+endpoint+"?wsdl";
        Log.i("0520", endPoint);
        // SOAP Action
        soapAction = namespace + "/" + methodName;

        // 指定WebService的命名空间和调用的方法名
        sb = new SoapObject(namespace, methodName);
        if (propert != null) {

            for (int i = 0; i < propert.size(); i++) {
                sb.addProperty("arg" + i, propert.get(i));
            }
        }
        return doWebService(endPoint);
    }
    
    
    

    private String doWebService(String endPoint) {
        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版�?
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = sb;
        // 设置是否调用的是.net的WebService
        envelope.dotNet = false;
        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(sb);
        (new MarshalBase64()).register(envelope);
        HttpTransportSE transport = new HttpTransportSE(endPoint);
        transport.debug = true;
        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 获取返回的数�?
        SoapObject object = null;
        if (envelope.bodyIn instanceof SoapObject) {
            object = (SoapObject) envelope.bodyIn;
        }
        // 获取返回的结�?
        String result = "{\"states\":\"99\"}";
        if (null != object) {
            result = object.getProperty(0).toString();
        }
        return result;
    }
}
