package com.wyx.intent;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
/**
 * @author wangyx
 * �������ݵ�RecDataActivity.java�������������ݵķ���(1.back�������������ݣ�2.ϵͳ��ť������������)
 */
public class NewActivity extends BaseActivity {
	private Intent intent;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
    }
    
    public void quitSys(View v){
//    	System.exit(0);
    	NewActivity.this.finish();
//      Intent intent = new Intent(NewActivity.this,MainActivity.class);
//    	android.os.Process.killProcess(android.os.Process.myPid());
    }
    
    public void passData(View v){
    	intent = new Intent(this,RecDataActivity.class);
    	Bundle bundle = new Bundle();
    	bundle.putString("Name", "wangyx");
    	bundle.putInt("Age", 25);
    	intent.putExtras(bundle);
    	intent.putExtra("No.", 01);
    	startActivityForResult(intent, 200);    //����������������ģ�ֻ��Ϊ��ʶ��������˭����ģ�
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_new, menu);
        return true;
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
			exitAll();
		}
		return super.onKeyDown(keyCode, event);
	}
    
    //Android 2.0�Ժ��ֶ���һ��ֱ�Ӵ����˸���ķ���
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		exitAll();
	}
	//Back Data
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == 200){
			String reString = data.getStringExtra("res");
			switch(resultCode){
			case 200:Toast.makeText(this, reString, Toast.LENGTH_LONG).show();
			break;	
			default :
		    //����back�����ش������¼�
			break;	
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    

	
    
}
