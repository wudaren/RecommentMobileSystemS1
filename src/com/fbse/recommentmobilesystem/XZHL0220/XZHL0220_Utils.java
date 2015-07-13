package com.fbse.recommentmobilesystem.XZHL0220;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.fbse.recommentmobilesystem.common.HttpSubmit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class XZHL0220_Utils {
    public static int setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }

    public static ArrayList<XZHL0220_Choice> getChoices(String uri) throws IOException, XmlPullParserException {
        ArrayList<XZHL0220_Choice> choices = null;
        HttpEntity entity = HttpSubmit.getEntity(uri, null, HttpSubmit.METHOD_GET);
        InputStream in = HttpSubmit.getStream(entity);
        choices = XZHL0220_Utils.parse(in);
        return choices;
    }

    public static ArrayList<XZHL0220_Choice> parse(InputStream in) throws XmlPullParserException, IOException {
        ArrayList<XZHL0220_Choice> choices = null;
        // 创建解析器
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
        // 设置输入源
        parser.setInput(new InputStreamReader(in));
        // 获取当前事件类型
        int eventType = parser.getEventType();
        // 当时间类型不是 end_DOCUMENT时，循环解析
        XZHL0220_Choice c = null;
        List<String> options = new ArrayList<String>();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            // 解析不同事件
            switch (eventType) {
            case XmlPullParser.START_DOCUMENT:
                choices = new ArrayList<XZHL0220_Choice>();
                break;
            case XmlPullParser.START_TAG:
                String tagName = parser.getName();
                if ("question".equals(tagName)) {
                    c = new XZHL0220_Choice();
                    c.setId(Integer.parseInt(parser.getAttributeValue(null, "id")));
                } else if ("title".equals(tagName)) {
                    c.setTitle(parser.nextText());
                } else if ("option".equals(tagName)) {
                    options.add(parser.nextText());
                } else if ("key".equals(tagName)) {
                    c.setKey(parser.nextText());
                }
                break;
            case XmlPullParser.END_TAG:
                if ("question".equals(parser.getName())) {
                    c.setOptions(options);
                    choices.add(c);
                    options = new ArrayList<String>();
                    System.out.println(choices.toString());
                }
                break;
            }
            // 驱动到下一事件，并获取下一事件的类型
            eventType = parser.next();
        }
        return choices;
    }

}
