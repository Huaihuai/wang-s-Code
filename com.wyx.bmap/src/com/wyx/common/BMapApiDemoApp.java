package com.wyx.common;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.*;

public class BMapApiDemoApp extends Application {
	
	static BMapApiDemoApp mDemoApp;
	
	//�ٶ�MapAPI�Ĺ�����
	public BMapManager mBMapMan = null;
	
	// ��ȨKey
	// TODO: ����������Key,
	// �����ַ��http://dev.baidu.com/wiki/static/imap/key/
	public String mStrKey = "FDA2E27D4B157B806FBC6E69D9D838DFEEA7BCB0";
	boolean m_bKeyRight = true;	// ��ȨKey��ȷ����֤ͨ��
	
	// �����¼���������������ͨ�������������Ȩ��֤�����
	public static class MyGeneralListener implements MKGeneralListener {
		@Override
		public void onGetNetworkState(int iError) {
			Log.d("MyGeneralListener", "onGetNetworkState error is "+ iError);
			Toast.makeText(BMapApiDemoApp.mDemoApp.getApplicationContext(), "���������������",
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPermissionState(int iError) {
			Log.d("MyGeneralListener", "onGetPermissionState error is "+ iError);
			if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
				// ��ȨKey����
				Toast.makeText(BMapApiDemoApp.mDemoApp.getApplicationContext(), 
						"����BMapApiDemoApp.java�ļ�������ȷ����ȨKey��",
						Toast.LENGTH_LONG).show();
				BMapApiDemoApp.mDemoApp.m_bKeyRight = false;
			}
		}
	}

	@Override
    public void onCreate() {
		Log.v("BMapApiDemoApp", "onCreate");
		mDemoApp = this;
		mBMapMan = new BMapManager(this);
		mBMapMan.init(this.mStrKey, new MyGeneralListener());
		mBMapMan.getLocationManager().setNotifyInternal(10, 5);
//		if (mBMapMan != null) {
//			mBMapMan.destroy();
//			mBMapMan = null;
//		}
		
		super.onCreate();
	}

	@Override
	//��������app���˳�֮ǰ����mapadpi��destroy()�����������ظ���ʼ��������ʱ������
	public void onTerminate() {
		// TODO Auto-generated method stub
		if (mBMapMan != null) {
			mBMapMan.destroy();    //������
			mBMapMan = null;
		}
		super.onTerminate();
	}

}