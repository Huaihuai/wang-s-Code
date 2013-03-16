package com.wyx.phoneListener;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.wyx.aidl.StudentInfo;
/**
 * @author wangyx
 * 本地服务技术
 */
public class StudentInfoService extends Service {
	private String[] nameStrings = {"张飞","李小龙","关羽","唐僧"};
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
	 * 通过构造该方法封装调用内部方法的方式
	 * 赋值给binder，来返回给调用者
	 */
	private class StudentBinder extends Binder implements IStudent{

		@Override
		public String queryStudent(int no) {
			// TODO Auto-generated method stub
			return query(no);
		}
	}

}
