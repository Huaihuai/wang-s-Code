package com.wyx.phoneListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * @author wangyx
 * �����ֻ������Ĺ㲥��Ȼ�󴥷�PhoneService��������ֻ�¼�����ܣ�
 */
public class BroadsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		/**
		 * context:�����Ķ���
		 * PhoneService.class����Ҫ����������
		 */
		Intent service = new Intent(context,PhoneService.class);
		context.startService(service);   //Intent����������
	}

}
