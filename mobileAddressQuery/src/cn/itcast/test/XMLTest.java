package cn.itcast.test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.itcast.utils.StreamTool;

import android.test.AndroidTestCase;

public class XMLTest extends AndroidTestCase {
	/*以实体方式发送XML数据(发送实体一定要使用POST方法)*/
	public void testSendXML() throws Exception{
		//src根目录下的文件，当该文件编译后可以由类加载器找到该文件
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("person.xml");
		byte[] data = StreamTool.read(inStream);
		String path = "http://192.168.1.100:8080/web/XmlServlet";
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		//如果以实体方式发送到服务器,必须要用“POST”方式
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		//这是只写给的服务器上的缓冲区上
		conn.getOutputStream().write(data);
		//取得任何部分换回的http协议，这样才能完成数据的完全发送到web应用
		if(conn.getResponseCode() == 200){
			System.out.println("·¢ËÍ³É¹¦");
		}else{
			System.out.println("·¢ËÍÊ§°Ü");
		}
	}
}
