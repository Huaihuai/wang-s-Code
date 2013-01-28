package com.wyx.intent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author wangyx
 * 自定义公共Activity
 */
public class BaseActivity extends Activity{
	protected static List<Activity> actList = new ArrayList<Activity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		actList.add(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		actList.remove(this);
	}
	/**
	 * 实现完全退出程序
	 */
	protected void finishAll(){
		for(Activity act:actList){
			act.finish();
		}
		actList.clear();
	}
	
	protected void exitAll(){
		finishAll();
		//关掉程序进程
		System.exit(0);
	}
	

}
