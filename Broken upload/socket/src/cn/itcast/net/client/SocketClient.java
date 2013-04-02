package cn.itcast.net.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.io.RandomAccessFile;
import java.net.Socket;

import cn.itcast.utils.StreamTool;

public class SocketClient {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 7878);
            OutputStream outStream = socket.getOutputStream();            
            String filename = "QQWubiSetup.exe";
            File file = new File(filename);
			//自己实现自定义服务（客户端发送协议信息给服务器，若ID不存在，服务器会生成唯一ID，即sourceid，
			//并发回给客户端,再传送一个position，通知客户端需要上传的文件位置）
            String head = "Content-Length="+ file.length() + ";filename="+ filename + ";sourceid=\r\n";
            outStream.write(head.getBytes());  //发送协议到服务器
            
            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());	//接受服务器返回的下载位置
			String response = StreamTool.readLine(inStream);
            System.out.println(response);
            String[] items = response.split(";");
			String position = items[1].substring(items[1].indexOf("=")+1);
			
			RandomAccessFile fileInStream = new RandomAccessFile(file, "r");
			fileInStream.seek(Integer.valueOf(position));   //从指定的断点位置向服务器传送数据
			byte[] buffer = new byte[1024];
			int len = -1;
			while( (len = fileInStream.read(buffer)) != -1){   //先写到内存中
				outStream.write(buffer, 0, len);                //再从内存中以Byte的形式发到输出流
			}
			fileOutStream.close();    //关闭所有流，socket是使用tcp/ip协议，较低层，没有使用缓存技术故不需要使用flush()
			outStream.close();
            inStream.close();
            socket.close();
        } catch (Exception e) {                    
            e.printStackTrace();
        }

	}
	/**
	* 读取流
	* @param inStream
	* @return 字节数组
	* @throws Exception
	*/
	public static byte[] readStream(InputStream inStream) throws Exception{
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while( (len=inStream.read(buffer)) != -1){
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			return outSteam.toByteArray();
	}
}
