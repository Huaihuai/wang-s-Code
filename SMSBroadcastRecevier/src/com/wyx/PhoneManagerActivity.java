package com.wyx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
/**
 * @author wangyx
 * �绰����������
 */
public class PhoneManagerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//ֻ��Ҫ��һ������������ͼ
		Intent intent = new Intent();
		intent.setAction("android.intent.action.CALL");
		intent.addCategory("android.intent.category.DEFAULT");   //��ʡ�ԣ�������ע�ӣ�
		intent.setData(Uri.parse("tel:5556"));
		startActivity(intent);   //�ڲ����Զ�ΪIntent�����𣺡�android.intent.category.DEFAULT��
	}
}
