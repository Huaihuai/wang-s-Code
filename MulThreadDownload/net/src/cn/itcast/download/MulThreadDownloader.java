package cn.itcast.download;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/*多线程文件下载实例*/
public class MulThreadDownloader {

	public static void main(String[] args) throws Exception {
		String path = "http://192.168.1.100:8080/web/QQWubiSetup.exe";
		int threadsize = 3;
		new MulThreadDownloader().download(path, threadsize);

	}
	/**
	 * 下载文件
	 *  @param 网络路径，文件大小
	 */
	private void download(String path, int threadsize) throws Exception {
		URL downpath = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) downpath.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if(conn.getResponseCode() == 200){
			//@获取网络文件长度
			int length = conn.getContentLength();
			//@在本地创建一个与服务器相同的文件
			File file = new File(getFileName(path));   //只要不给定目录，文件将被创建于项目根目录下
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");//r；只读；rw：读写;rws:将文件的元数据和内容数据立刻同步
			                                                                //到设备中；rwd:只将内容数据同步到设备中。（一般情况下
			                                                                //是写到缓存当中,为防止手机突然断点，故必须使用同步数据）
			                                                                //注：元数据指文件的创建时间，作者等；内容数据值文件的
			                                                                //内容。
			//@设置文件对象长度
			accessFile.setLength(length);
			accessFile.close();
			//@计算每条线程负责下载的数据量
			int block = length % threadsize == 0 ? length / threadsize : length / threadsize +1;
			for(int threadid = 0 ; threadid < threadsize ; threadid++){
				/**
	             *  @开启文件下载线程
	             *  @param 线程ID，文件下载路径，每个线程负责下载数据量，本地文件对象
	             */
				new DownloadThread(threadid, downpath, block, file).start();
			}
		}
	}
	//下载线程的定义
	private final class DownloadThread extends Thread{
		private int threadid;
		private URL downpath;
		private int block;
		private File file;
		
		public DownloadThread(int threadid, URL downpath, int block, File file) {
			this.threadid = threadid;
			this.downpath = downpath;
			this.block = block;
			this.file = file;
		}
		public void run() {
			//计算该线程从网络文件起始下载位置
			int startposition = threadid * block;
			//计算下载到网络文件的终止位置
			int endposition = (threadid+1) * block - 1;
			try{
				RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
				accessFile.seek(startposition);//设定网络文件数据存放区域
				HttpURLConnection conn = (HttpURLConnection) downpath.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				//该字段设定从网络文件的某个区域下载数据
				conn.setRequestProperty("Range", "bytes="+ startposition+ "-"+ endposition);
				//分段请求数据的返回值为206
				if(conn.getResponseCode() == 206){
				   InputStream inStream = conn.getInputStream();
				   byte[] buffer = new byte[1024];
				   int len = 0;
				   while( (len = inStream.read(buffer)) != -1 ){
					   accessFile.write(buffer, 0, len);
				   }
				   accessFile.close();
				   inStream.close();
				   System.out.println("第"+ (threadid+1)+ "条线程下载完成");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 从路径中截取网络文件名称
	 */
	private static String getFileName(String path) {
		return path.substring(path.lastIndexOf("/")+ 1);
	}

}
