package com.wyx.intent;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author wangyx
 * meta_dataʵ������startActivityForResult()�İ���������
 */
public class RecDataActivity extends Activity {
	private TextView tipTextView = null;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rec_data);
		
		tipTextView = (TextView)findViewById(R.id.myData);
		intent = this.getIntent();
		int no = intent.getIntExtra("No.", 00);
		Bundle bundle = intent.getExtras();
		int age = bundle.getInt("Age");
		String name = bundle.getString("Name");
		
		tipTextView.setText("Num:"+no+"  Name:"+name+"  Age:"+age);
		
		try {
			/**
			 * <meta-data>Ԫ�������ǣ�Ϊ�齨�ṩ��������ݣ��������Ϊ��Ԫ�أ���������<activity>��
			 * <application> ��<service>��<receiver>Ԫ���У�����ͬ�ĸ�Ԫ�أ���Ӧ��ʱ��ȡ�ķ���Ҳ��ͬ��
			 */
			ActivityInfo info = this.getPackageManager().getActivityInfo(
					new ComponentName(this, RecDataActivity.class), PackageManager.GET_META_DATA);
			int id = info.metaData.getInt("com.wyx.intent.id");
			String str = info.metaData.getString("com.wyx.intent.name");
			Toast.makeText(this, "ID:"+id+",Str:"+str, 2).show();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//����д�ڷ�����
//		Thread aaThread = new Thread(new Runnable(){
//			   public void run(){
//				   
//			   }
//			});
//		aaThread.start();
		
	}

	Handler handler = new Handler();
	Runnable delayRunnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try{
				Thread.sleep(3000);
				intent.putExtra("res", "I have gotten Data!");
//				intent = new Intent(RecDataActivity.this,NewActivity.class);
				setResult(300, intent);
//				startActivity(intent);
				RecDataActivity.this.finish();
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		handler.post(delayRunnable);
	}
	public void backResult(View v){
		handler.post(delayRunnable);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_rec_data, menu);
		return true;
	}

}
