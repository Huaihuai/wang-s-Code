package com.wyx;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
/**
 * @author wangyx
 * 电话拨号器案例
 */
public class PhoneManagerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//只需要打开一个拨号器的意图
		Intent intent = new Intent();
		intent.setAction("android.intent.action.CALL");
		intent.addCategory("android.intent.category.DEFAULT");   //可省略（见下条注视）
		intent.setData(Uri.parse("tel:5556"));
		startActivity(intent);   //内部会自动为Intent添加类别：“android.intent.category.DEFAULT”
	}
}
