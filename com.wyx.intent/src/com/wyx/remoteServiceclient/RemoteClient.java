package com.wyx.remoteServiceclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wyx.aidl.StudentInfo;
import com.wyx.intent.R;
/**
 * @author wangyx
 * 远程调用com.wyx.phoneListener.StudentInfoRemoteService，进行线程间数据传递
 */
public class RemoteClient extends Activity {

	private Button searchBtn;
	private TextView resultEdt;
	private EditText noTxt;
	private StudentConnection conn = new StudentConnection();
	private StudentInfo studentInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remote_client);
		
		resultEdt = (TextView)findViewById(R.id.display);
		noTxt = (EditText)findViewById(R.id.no);
		searchBtn = (Button)findViewById(R.id.search);
		searchBtn.setOnClickListener(new SearchClick());
		
		Intent service = new Intent("android.wyx.phoneListener.StudentInfoService");
		/**---绑定服务，启动服务---
		 * service：启动的服务
		 * conn：在绑定时候和解除绑定时候的操作
		 * Context.BIND_AUTO_CREATE：绑定石自动创建
		 */
		bindService(service, conn, Context.BIND_AUTO_CREATE);
	}
	
	private final class SearchClick implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String no = noTxt.getText().toString();
			
			try {
				resultEdt.setText(studentInfo.queryStudent(Integer.valueOf(no)));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//设置绑定时和解除绑定时的操作
	private final class StudentConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			studentInfo = StudentInfo.Stub.asInterface(service);
			/**
			 * 注意：这里直接new服务类也可以调用其中的方法，但是这样操作就不调用服务，只是当成普通类使用
			 * 在内存不足时或是activity被摧毁时会被干掉，失去了服务的特性。
			 */
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
            studentInfo = null;
		}
	}
	
	//销毁Activity前解除绑定
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(conn);
		super.onDestroy();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
