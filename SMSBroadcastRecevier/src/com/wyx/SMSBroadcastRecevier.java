package com.wyx;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;
/**
 * @author wangyx
 * �㲥������ʵ�ֶ��ż�������
 */
public class SMSBroadcastRecevier extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for(Object p: pdus){
			byte[] pdu = (byte[]) p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			//ȡ�ö�������
			String content = message.getMessageBody();
			//ȡ�ö���ʱ�䣬����ת��Ϊʱ�����
			Date date = new Date(message.getTimestampMillis());
			//���ö���ʱ���ʽΪ��yyyy-mm-dd HH:mm:aa
			String receiveDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:aa").format(date);
			String senderNumber = message.getOriginatingAddress();
			
			if(senderNumber.equals("5556")){
				abortBroadcast();    //��ֹϵͳ�㲥��������Ӧ���еĹ㲥�޷��ڹ�������
			}
			System.out.print("���ݣ�"+content+"\nʱ�䣺"+receiveDateString+"\n��ַ��"+senderNumber);
		}
	}
}
