package com.wyx.phoneListener;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.wyx.aidl.StudentInfo;
/**
 * @author wangyx
 * ���ط�����
 */
public class StudentInfoService extends Service {
	private String[] nameStrings = {"�ŷ�","��С��","����","��ɮ"};
	private IBinder binder = new StudentBinder();
	public String query(int no){
		if(no>0 && no<5)
			return nameStrings[no-1];
		return null;
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	/**
	 * @author wangyx
	 * ͨ������÷�����װ�����ڲ������ķ�ʽ
	 * ��ֵ��binder�������ظ�������
	 */
	private class StudentBinder extends Binder implements IStudent{

		@Override
		public String queryStudent(int no) {
			// TODO Auto-generated method stub
			return query(no);
		}
	}

}
