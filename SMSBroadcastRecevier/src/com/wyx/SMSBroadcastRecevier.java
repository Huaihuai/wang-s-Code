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
 * 广播接收者实现短信监听功能
 */
public class SMSBroadcastRecevier extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Object[] pdus = (Object[]) intent.getExtras().get("pdus");
		for(Object p: pdus){
			byte[] pdu = (byte[]) p;
			SmsMessage message = SmsMessage.createFromPdu(pdu);
			//取得短信内容
			String content = message.getMessageBody();
			//取得短信时间，将其转换为时间对象
			Date date = new Date(message.getTimestampMillis());
			//设置短信时间格式为：yyyy-mm-dd HH:mm:aa
			String receiveDateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:aa").format(date);
			String senderNumber = message.getOriginatingAddress();
			
			if(senderNumber.equals("5556")){
				abortBroadcast();    //终止系统广播（即短信应用中的广播无法在工作）；
			}
			System.out.print("内容："+content+"\n时间："+receiveDateString+"\n地址："+senderNumber);
		}
	}
}
