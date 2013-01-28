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
 * XML�����ĳ��÷���			
 */
//xmlPullParser.getAttributeCount();  //��ȡ�ýڵ����Ը���
//xmlPullParser.getAttributeName(index);  //��ȡ�ýڵ���������
//xmlPullParser.getAttributeType(index);  //��ȡ�ýڵ���������
//xmlPullParser.getAttributeValue(index);  //��ȡ�ýڵ�����ֵ
//xmlPullParser.getEventType();  //��ȡ�ڵ����ͣ�XmlPullParser.END_TAG��XmlPullParser.START_TAG��
                                 //XmlPullParser.START_DOCUMENT��XmlPullParser.END_DOCUMENT��
//xmlPullParser.nextText();      //��ȡ�ýڵ��ı�����
//xmlPullParser.next();          //��ȡ��һ���ڵ�
//xmlPullParser.nextTag();       //??
//xmlPullParser.getName();       //��ȡ�ýڵ�����
//xmlPullParser.getText();       //??

/**
 * @author wangyx
 * ����������XML�ļ���
 */
public class PullMXL {	
	public List<Student> list = null;
	private Student student = new Student();
	private InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Student.xml");
	public PullMXL(){
		
	}
	
	//��ȡXML�ļ������ݵķ���
	public List<Student> getPullXML(){
		try {
			//<1.ʵ����������
			//XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
			//XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
			//<2.ʵ����������
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(inputStream, "UTF-8");

            //��ȡ�ڵ�����
			int eventType = xmlPullParser.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT){
				//��ȡ�ڵ�����
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
	
	//д��XML�ļ��ķ���
	public void setPullXML(List<Student> stuList,FileOutputStream outputStream){
		XmlSerializer xmlSerializer = Xml.newSerializer();
		try {
			xmlSerializer.setOutput(outputStream, "UTF-8");
			/**
			 * @params:EnCoding���ĵ��Ƿ��������
			 */
			xmlSerializer.startDocument("UTF-8", true);
			/**
			 * @params�������ռ䣬�ڵ���
			 */
			xmlSerializer.startTag(null, "students");
			for(Student student:stuList){
				xmlSerializer.startTag(null, "student");
				xmlSerializer.attribute(null, "id", String.valueOf(student.getId()));
				
				//��ӱ�ע
				xmlSerializer.comment("***���ֽڵ�***");
				xmlSerializer.startTag(null, "name");
				xmlSerializer.text(student.getName());
				xmlSerializer.endTag(null, "name");
				
				xmlSerializer.comment("***����ڵ�***");
				xmlSerializer.startTag(null, "age");
				xmlSerializer.text(student.getName());
				xmlSerializer.endTag(null, "age");
				
				xmlSerializer.endTag(null, "student");
			}
			xmlSerializer.endTag(null, "students");
			xmlSerializer.endDocument();
			
			outputStream.flush();        //������д���ļ��е���һ�ַ�ʽ��ֻҪ�����ļ������Ӽ��ɣ�������һ�ַ�ʽ��write()��ʽд�롿��
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
