package com.wyx.phoneListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * @author wangyx
 * 接收手机启动的广播，然后触发PhoneService服务（完成手机录音功能）
 */
public class BroadsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/**
		 * context:上下文对象；
		 * PhoneService.class：需要激活的组件；
		 */
		Intent service = new Intent(context,PhoneService.class);
		context.startService(service);   //Intent激活服务组件
	}

}
