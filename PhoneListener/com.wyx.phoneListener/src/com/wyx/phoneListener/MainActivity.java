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
 * ���ط���ĵ�����
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
		/**---�󶨷�����������---
		 * service�������ķ���
		 * conn���ڰ�ʱ��ͽ����ʱ��Ĳ���
		 * Context.BIND_AUTO_CREATE����ʯ�Զ�����
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

	//���ð�ʱ�ͽ����ʱ�Ĳ���
	private final class StudentConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			iStudent = (IStudent)service;
			
			/**
			 * ע�⣺����ֱ��new������Ҳ���Ե������еķ������������������Ͳ����÷���ֻ�ǵ�����ͨ��ʹ��
			 * ���ڴ治��ʱ����activity���ݻ�ʱ�ᱻ�ɵ���ʧȥ�˷�������ԡ�
			 */
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			iStudent = null;
		}
	}
	
	//����Activityǰ�����
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
