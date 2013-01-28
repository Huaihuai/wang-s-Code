package com.wyx.pullXml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.conn.DefaultClientConnection;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.wyx.domain.Student;

import android.util.Xml;
/**
 * XML解析的常用方法			
 */
//xmlPullParser.getAttributeCount();  //获取该节点属性个数
//xmlPullParser.getAttributeName(index);  //获取该节点属性名字
//xmlPullParser.getAttributeType(index);  //获取该节点属性类型
//xmlPullParser.getAttributeValue(index);  //获取该节点属性值
//xmlPullParser.getEventType();  //获取节点类型（XmlPullParser.END_TAG；XmlPullParser.START_TAG；
                                 //XmlPullParser.START_DOCUMENT；XmlPullParser.END_DOCUMENT）
//xmlPullParser.nextText();      //获取该节点文本内容
//xmlPullParser.next();          //获取下一个节点
//xmlPullParser.nextTag();       //??
//xmlPullParser.getName();       //获取该节点名字
//xmlPullParser.getText();       //??

/**
 * @author wangyx
 * 解析及生成XML文件类
 */
public class PullMXL {	
	public List<Student> list = null;
	private Student student = new Student();
	private InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Student.xml");
	public PullMXL(){
		
	}
	
	//获取XML文件中数据的方法
	public List<Student> getPullXML(){
		try {
			//<1.实例解析对象
			//XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
			//XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
			//<2.实例解析对象
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(inputStream, "UTF-8");

            //获取节点类型
			int eventType = xmlPullParser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				//获取节点名字
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<Student>();
					break;
				case XmlPullParser.START_TAG:
					if("student".equals(nodeName)){
                        //student = new Student();
						student.setId(Integer.valueOf(xmlPullParser.getAttributeValue(0)));
						student.setGroup(Integer.parseInt(xmlPullParser.getAttributeValue(1)));
					}else if ("name".equals(nodeName)) {
						student.setName(xmlPullParser.nextText());
					}else if("sex".equals(nodeName)){
						student.setSex(xmlPullParser.nextText());
					}else if("age".equals(nodeName)){
						student.setAge(Integer.parseInt(xmlPullParser.nextText()));
					}else if("email".equals(nodeName)){
						student.setEmail(xmlPullParser.nextText());
					}else if("birthday".equals(nodeName)){
						student.setBirthday(xmlPullParser.nextText());
					}else if("memo".equals(nodeName)){
						student.setMemo(xmlPullParser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if("student".equals(nodeName)){
						list.add(student);
					}
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return this.list;
	}
	
	//写入XML文件的方法
	public void setPullXML(List<Student> stuList,FileOutputStream outputStream){
		XmlSerializer xmlSerializer = Xml.newSerializer();
		try {
			xmlSerializer.setOutput(outputStream, "UTF-8");
			/**
			 * @params:EnCoding，文档是否独立存在
			 */
			xmlSerializer.startDocument("UTF-8", true);
			/**
			 * @params：命名空间，节点名
			 */
			xmlSerializer.startTag(null, "students");
			for(Student student:stuList){
				xmlSerializer.startTag(null, "student");
				xmlSerializer.attribute(null, "id", String.valueOf(student.getId()));
				
				//添加备注
				xmlSerializer.comment("***名字节点***");
				xmlSerializer.startTag(null, "name");
				xmlSerializer.text(student.getName());
				xmlSerializer.endTag(null, "name");
				
				xmlSerializer.comment("***年龄节点***");
				xmlSerializer.startTag(null, "age");
				xmlSerializer.text(student.getName());
				xmlSerializer.endTag(null, "age");
				
				xmlSerializer.endTag(null, "student");
			}
			xmlSerializer.endTag(null, "students");
			xmlSerializer.endDocument();
			
			outputStream.flush();        //将数据写入文件中的另一种方式（只要流与文件相连接即可）【另外一种方式是write()方式写入】；
			                             //outputStream.write(content.getBytes[]);
			outputStream.close();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
