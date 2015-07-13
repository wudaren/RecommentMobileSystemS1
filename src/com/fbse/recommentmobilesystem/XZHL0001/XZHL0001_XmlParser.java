package com.fbse.recommentmobilesystem.XZHL0001;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class XZHL0001_XmlParser {
	/**
	 * 
	 * @param in
	 * @return
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	//解析服务器端xml数据
	public ArrayList<XZHL0001_Servexml> parse(InputStream in)
			throws XmlPullParserException, IOException {
		ArrayList<XZHL0001_Servexml> Servexmls = null;
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = factory.newPullParser();
		parser.setInput(new InputStreamReader(in));
		int eventType = parser.getEventType();
		XZHL0001_Servexml m = null;
		//开始文档
       while (eventType != XmlPullParser.END_DOCUMENT) {

			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				Servexmls = new ArrayList<XZHL0001_Servexml>();
				break;
				//开始节点
			case XmlPullParser.START_TAG:
				String tagName = parser.getName();

				if ("version".equals(tagName)) {
					m = new XZHL0001_Servexml();
					m.setId(Integer.parseInt(parser.getAttributeValue(null,
							"id")));

				} else if ("name".equals(tagName)) {

					m.setName(parser.nextText());

				} else if ("url".equals(tagName)) {
					m.setUrl(parser.nextText());

				}
				break;
				//结束标记
			case XmlPullParser.END_TAG:
				if ("version".equals(parser.getName())) {
					Servexmls.add(m);

				}
				break;
			}
			eventType = parser.next();
		}
		return Servexmls;
	}
}
