package cn.itcast.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {
	/**
	 * 读取流中的数据
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public static byte[] read(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		//写入数据到buffer（内存），直至写满[无数据写入buffer时，len为-1]
		while( (len = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
        //以二进制格式输出
		return outStream.toByteArray();
	}

}

//流的转换过程：输入流-（read到）>buffer-(write到)>输出流
//流与文件等都是属于外部数据存储，只有定义的数据容器才是内存中的数据容器
