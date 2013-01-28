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
 * 短信发送实例
 * 主要类：
 * android.telephony.SmsManager：获取短信发送的引擎；
 * android.telephony.SmsMessage：获取短信的内容；
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
			SmsManager smsManager = SmsManager.getDefault();   //获取默认短信实例
			ArrayList<String> text = smsManager.divideMessage(content);   //如果短信字数过多，将拆分成多条短信发送
			for(String txt:text){
				/**
				 * @params
				 * 发送目的地（号码）
				 * 服务上地址（默认是移动）
				 * 短信内容
				 * 发送的状态（判断短信是否成功发送,如果成功，则触发Pending Intent）
				 * 接收状态（判断短信是否接收,如果成功，则触发Pending Intent）
				 * 注：PendingIntent pi = PendingIntent.
                   getBroadcast(Sms.this,0,new Intent(),0); 
				 */
				smsManager.sendTextMessage("5556", null, txt, null, null);
			}
			Toast.makeText(SmsManagerActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_sms_manager, menu);
		return true;
	}

}
