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
			//�Լ�ʵ���Զ�����񣨿ͻ��˷���Э����Ϣ������������ID�����ڣ�������������ΨһID����sourceid��
			//�����ظ��ͻ���,�ٴ���һ��position��֪ͨ�ͻ�����Ҫ�ϴ����ļ�λ�ã�
            String head = "Content-Length="+ file.length() + ";filename="+ filename + ";sourceid=\r\n";
            outStream.write(head.getBytes());  //����Э�鵽������
            
            PushbackInputStream inStream = new PushbackInputStream(socket.getInputStream());	//���ܷ��������ص�����λ��
			String response = StreamTool.readLine(inStream);
            System.out.println(response);
            String[] items = response.split(";");
			String position = items[1].substring(items[1].indexOf("=")+1);
			
			RandomAccessFile fileInStream = new RandomAccessFile(file, "r");
			fileInStream.seek(Integer.valueOf(position));   //��ָ���Ķϵ�λ�����������������
			byte[] buffer = new byte[1024];
			int len = -1;
			while( (len = fileInStream.read(buffer)) != -1){   //��д���ڴ���
				outStream.write(buffer, 0, len);                //�ٴ��ڴ�����Byte����ʽ���������
			}
			fileOutStream.close();    //�ر���������socket��ʹ��tcp/ipЭ�飬�ϵͲ㣬û��ʹ�û��漼���ʲ���Ҫʹ��flush()
			outStream.close();
            inStream.close();
            socket.close();
        } catch (Exception e) {                    
            e.printStackTrace();
        }

	}
	/**
	* ��ȡ��
	* @param inStream
	* @return �ֽ�����
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
