package cn.itcast.service;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import cn.itcast.utils.FormFile;
import cn.itcast.utils.SocketHttpRequester;

public class NewsService {
	/**
	 * 保存数据
	 * @param title 标题
	 * @param length 时长
	 * @return
	 */
	public static boolean save(String title, String length) {
		String path = "http://192.168.0.168:8080/web/ManageServlet";
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("timelength", length);
		try {
			return sendHttpClientPOSTRequest(path, params, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 通过HttpClient发送Post请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @param encoding 编码
	 * @return 请求是否成功
	 */
	private static boolean sendHttpClientPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
		//HttpClient使用NameValuePair类型存放请求参数
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();//存放请求参数
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				//NameValuePair接口使用BasicNameValuePair参数类实现
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
		HttpPost httpPost = new HttpPost(path);
		httpPost.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if(response.getStatusLine().getStatusCode() == 200){
			return true;
		}
		return false;
	}
	/**
	 * 发送Post请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @param encoding 编码
	 * @return 请求是否成功
	 */
	private static boolean sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
		//  title=liming&timelength=90
		StringBuilder data = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				data.append(entry.getKey()).append("=");
				data.append(URLEncoder.encode(entry.getValue(), encoding));
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		byte[] entity = data.toString().getBytes();//生成实体数据
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		//允许对外输出数据（必须要写）
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
		OutputStream outStream = conn.getOutputStream();
		//此时数据只是写到了内部的缓存中（HttpURLConnection中使用了换粗技术）
		outStream.write(entity);
		//只用调用这个参数才会真正发送给Web应用
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}
	/**
	 * 发送GET请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @param encoding 编码
	 * @return 请求是否成功
	 */
	private static boolean sendGETRequest(String path, Map<String, String> params, String ecoding) throws Exception{
		// http://192.168.1.100:8080/web/ManageServlet?title=xxx&timelength=90
		StringBuilder url = new StringBuilder(path);
		url.append("?");
		for(Map.Entry<String, String> entry : params.entrySet()){
			url.append(entry.getKey()).append("=");
			//对传入的url进行编码
			url.append(URLEncoder.encode(entry.getValue(), ecoding));
			url.append("&");
		}
		url.deleteCharAt(url.length() - 1);
		HttpURLConnection conn = (HttpURLConnection)new URL(url.toString()).openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}
	
	//Http协议上传文件到网络服务器
	public static boolean upLoader(String title, String length, File uploadFile) {
		String path = "http://192.168.0.168:8080/web/ManageServlet";
		Map<String, String> params = new HashMap<String, String>();
		params.put("title", title);
		params.put("timelength", length);
		FormFile formFile = new FormFile(uploadFile, "videofile", "image/gif");
		try {
			return SocketHttpRequester.post(path, params, formFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
