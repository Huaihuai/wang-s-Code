package com.wyx.intent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author wangyx
 * �Զ��幫��Activity
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
	 * ʵ����ȫ�˳�����
	 */
	protected void finishAll(){
		for(Activity act:actList){
			act.finish();
		}
		actList.clear();
	}
	
	protected void exitAll(){
		finishAll();
		//�ص��������
		System.exit(0);
	}
	

}
