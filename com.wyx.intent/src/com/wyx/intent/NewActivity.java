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
 * 发出数据到RecDataActivity.java，并接收其数据的返回(1.back键触发返回数据；2.系统按钮触发返回数据)
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
    	startActivityForResult(intent, 200);    //这里请求码是任意的，只是为了识别数据是谁发起的；
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
    
    //Android 2.0以后又多了一种直接处理退格键的方法
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
		    //按下back键返回触发的事件
			break;	
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    

	
    
}
