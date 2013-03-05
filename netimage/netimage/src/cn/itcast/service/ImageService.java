package cn.itcast.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.itcast.utils.StreamTool;

public class ImageService {
	/**
	 * 获取网络图片的数据
	 * @param path 网络图片路径
	 * @return
	 */
	public static byte[] getImage(String path) throws Exception{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();//基于HTTP协议连接对象
		conn.setConnectTimeout(5000);     //设置请求的超时时间
		conn.setRequestMethod("GET");     //设置请求的请求方式
		if(conn.getResponseCode() == 200){    //不管是否正确请求，服务器都会返回数据，故必须判断返回数据类型
			InputStream inStream = conn.getInputStream();
			return StreamTool.read(inStream);
		}
		return null;
	}

}
