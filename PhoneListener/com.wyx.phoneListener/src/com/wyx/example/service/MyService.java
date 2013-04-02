package com.wyx.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service{
	private static final String TAG = "at Service";
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	@Override
	//2.0�Ժ�onStart()���滻��onStartCommand()
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
}
