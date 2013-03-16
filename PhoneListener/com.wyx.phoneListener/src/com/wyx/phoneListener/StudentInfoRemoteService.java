package com.wyx.phoneListener;

import com.wyx.aidl.StudentInfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
/**
 * @author wangyx
 * IPC技术
 * 该远程服务由客户端com.wyx.remoteServiceclient.RemoteClient调用
 * 做法：1.建立AIDL文件（与接口相似，后缀为aidl，同时在gen文件夹下生成相应XXX.java）
 *      2.构建内部类，继承XXX.stub(本地调用时继承Bindler类)
 * 注：其他做法与本地服务调用方式相同
 */
public class StudentInfoRemoteService extends Service {
	private IBinder binder = new StudentRemoteBinder();
	
	String[] stuName = {"张三","李斯","王五"};
	private String queryString(int no){
		if(no>0 && no<4){
			return stuName[no-1];
		}
		return "无数据。。。";
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	private final class StudentRemoteBinder extends StudentInfo.Stub{

		@Override
		public String queryStudent(int no) throws RemoteException {
			// TODO Auto-generated method stub
			return queryString(no);
		}
		
	}

}
