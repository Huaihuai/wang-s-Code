package com.wyx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * @author wangyx
 * �㲥������ʵ�ֵ绰���ع���
 */
public class PhoneBroadcastReceiver extends BroadcastReceiver {

	//�ӵ��㲥��Ĵ���ʽ
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String numberString = getResultData();  //getResultData()��BroadcastReceiver��API
		                                        //���չ㲥������ʱ���͵�����
		if("5556".equals(numberString)){
			setResultData(null);     //���������ó�null�ķ�ʽ������ֹ�㲥
		}else{
			numberString = "12593"+numberString;
			setResultData("5556");     //�޸Ĺ㲥��������
		}
	}

}
