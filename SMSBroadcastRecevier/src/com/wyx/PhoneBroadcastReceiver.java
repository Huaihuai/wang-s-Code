package com.wyx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * @author wangyx
 * 广播接收者实现电话拦截功能
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver {

	//接到广播后的处理方式
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String numberString = getResultData();  //getResultData()是BroadcastReceiver的API
		                                        //接收广播发出来时发送的数据
		if("5556".equals(numberString)){
			setResultData(null);     //将号码设置成null的方式，来终止广播
		}else{
			numberString = "12593"+numberString;
			setResultData("5556");     //修改广播发送数据
		}
	}

}
