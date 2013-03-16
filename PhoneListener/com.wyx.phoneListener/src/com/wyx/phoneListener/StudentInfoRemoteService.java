package com.wyx.phoneListener;

import com.wyx.aidl.StudentInfo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
/**
 * @author wangyx
 * IPC����
 * ��Զ�̷����ɿͻ���com.wyx.remoteServiceclient.RemoteClient����
 * ������1.����AIDL�ļ�����ӿ����ƣ���׺Ϊaidl��ͬʱ��gen�ļ�����������ӦXXX.java��
 *      2.�����ڲ��࣬�̳�XXX.stub(���ص���ʱ�̳�Bindler��)
 * ע�����������뱾�ط�����÷�ʽ��ͬ
 */
public class StudentInfoRemoteService extends Service {
	private IBinder binder = new StudentRemoteBinder();
	
	String[] stuName = {"����","��˹","����"};
	private String queryString(int no){
		if(no>0 && no<4){
			return stuName[no-1];
		}
		return "�����ݡ�����";
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
