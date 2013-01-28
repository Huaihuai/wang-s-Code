package com.wyx;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * @author wangyx
 * ���ŷ���ʵ��
 * ��Ҫ�ࣺ
 * android.telephony.SmsManager����ȡ���ŷ��͵����棻
 * android.telephony.SmsMessage����ȡ���ŵ����ݣ�
 */
public class SmsManagerActivity extends Activity {
	private String content;
	private String number;
	private Button sendMessage;
	private EditText messageContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_manager);
		
		sendMessage = (Button)findViewById(R.id.sendMessage);
		messageContent = (EditText)findViewById(R.id.messageContent);
		content = messageContent.getText().toString();
		sendMessage.setOnClickListener(new onClickListener());
	}

	private final class onClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			SmsManager smsManager = SmsManager.getDefault();   //��ȡĬ�϶���ʵ��
			ArrayList<String> text = smsManager.divideMessage(content);   //��������������࣬����ֳɶ������ŷ���
			for(String txt:text){
				/**
				 * @params
				 * ����Ŀ�ĵأ����룩
				 * �����ϵ�ַ��Ĭ�����ƶ���
				 * ��������
				 * ���͵�״̬���ж϶����Ƿ�ɹ�����,����ɹ����򴥷�Pending Intent��
				 * ����״̬���ж϶����Ƿ����,����ɹ����򴥷�Pending Intent��
				 * ע��PendingIntent pi = PendingIntent.
                   getBroadcast(Sms.this,0,new Intent(),0); 
				 */
				smsManager.sendTextMessage("5556", null, txt, null, null);
			}
			Toast.makeText(SmsManagerActivity.this, "���ͳɹ���", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sms_manager, menu);
		return true;
	}

}
