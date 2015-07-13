
package com.fbse.recommentmobilesystem.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.content.Context;
import android.util.Log;

public class WebServiceOfHttps {

    String soapAction = null;

    Properties properties = null;

    public String WSservers(Context context, String portname, String json) {

        properties = Commonutil.loadIPProperties(context);
        // String ip = properties.getProperty("ipofshanghai", "");
        // String port = properties.getProperty("portofshanghai", "");
        // String version = properties.getProperty("version", "");
        try {
            json = URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<String>();
        list.add(portname);
        list.add(json);
        WebService webService = new WebService();
        String webresult = webService.WSserversofdaili(context, "forward", "ForwardPort", list);
        Log.i("", list.toString());
        Log.i("", webresult);
        return webresult;

    }

    public String WSservers(Context context, String portname, String json, String page, String rongliang,
            String nameorphone, String type) {

        properties = Commonutil.loadIPProperties(context);
        // String ip = properties.getProperty("ipofshanghai", "");
        // String port = properties.getProperty("portofshanghai", "");
        // String version = properties.getProperty("version", "");
        try {
            json = URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<String>();
        list.add(portname);
        list.add(json);
        list.add(page);
        list.add(rongliang);
        list.add(nameorphone);
        list.add(type);
        WebService webService = new WebService();
        String webresult = webService.WSserversofdaili(context, "forward", "ForwardPort", list);
        Log.i("", list.toString());
        Log.i("", webresult);
        return webresult;

    }

    public String WSservers1(Context context, String portname, String json, String page, String rongliang,
            String nameorphone, String type) {

        properties = Commonutil.loadIPProperties(context);
        // String ip = properties.getProperty("ipofshanghai", "");
        // String port = properties.getProperty("portofshanghai", "");
        // String version = properties.getProperty("version", "");
        try {
            json = URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<String>();
        list.add(portname);
        list.add(json);
        list.add(page);
        list.add(rongliang);
        list.add(nameorphone);
        list.add(type);
        WebService webService = new WebService();
        String webresult = webService.WSserversofdaili1(context, "memberList", "MemberListPort", list);
        Log.i("", list.toString());
        Log.i("", webresult);
        return webresult;

    }

    public String WSservers2(Context context, String methame, String endPoint, List<String> property) {

        properties = Commonutil.loadIPProperties(context);
        // String ip = properties.getProperty("ipofshanghai", "");
        // String port = properties.getProperty("portofshanghai", "");
        // String version = properties.getProperty("version", "");
        try {
            property.set(1, URLEncoder.encode(property.get(1), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        WebService webService = new WebService();
        String webresult = webService.WSserversofdaili1(context, methame, endPoint, property);
        Log.i("", webresult);
        return webresult;

    }

    public String WSservers(Context context, String portname, String json, String start, String end) {

        properties = Commonutil.loadIPProperties(context);
        // String ip = properties.getProperty("ipofshanghai", "");
        // String port = properties.getProperty("portofshanghai", "");
        // String version = properties.getProperty("version", "");
        try {
            json = URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        ArrayList<String> list = new ArrayList<String>();
        list.add(portname);
        list.add(json);
        list.add(start);
        list.add(end);
        WebService webService = new WebService();
        String webresult = webService.WSserversofdaili(context, "forward", "ForwardPort", list);
        Log.i("", list.toString());
        Log.i("", webresult);
        return webresult;

    }

}
