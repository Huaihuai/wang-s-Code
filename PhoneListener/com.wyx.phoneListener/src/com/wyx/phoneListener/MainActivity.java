package com.wyx.phoneListener;


import android.R.integer;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
/**
 * @author wangyx
 * 本地服务的调用者
 */
public class MainActivity extends Activity {
	private Button searchBtn;
	private TextView resultEdt;
	private EditText noTxt;
	private StudentConnection conn = new StudentConnection();
	private IStudent iStudent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		resultEdt = (TextView)findViewById(R.id.display);
		noTxt = (EditText)findViewById(R.id.no);
		searchBtn = (Button)findViewById(R.id.search);
		searchBtn.setOnClickListener(new SearchClick());
		
		Intent service = new Intent(this, StudentInfoService.class);
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
			
			resultEdt.setText(iStudent.queryStudent(Integer.valueOf(no)));
		}
	}

	//设置绑定时和解除绑定时的操作
	private final class StudentConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			iStudent = (IStudent)service;
			
			/**
			 * 注意：这里直接new服务类也可以调用其中的方法，但是这样操作就不调用服务，只是当成普通类使用
			 * 在内存不足时或是activity被摧毁时会被干掉，失去了服务的特性。
			 */
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			iStudent = null;
		}
	}
	
	
	//实现接口的第二种方式
//	private ServiceConnection connection = new ServiceConnection() {
//		
//		@Override
//		public void onServiceDisconnected(ComponentName name) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		@Override
//		public void onServiceConnected(ComponentName name, IBinder service) {
//			// TODO Auto-generated method stub
//			
//		}
//	};
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
