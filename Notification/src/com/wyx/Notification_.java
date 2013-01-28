package com.wyx;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;



public class Notification_ extends Activity {
	private Button default_;
	private Button customer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		default_ = (Button)findViewById(R.id.default_);
		customer = (Button)findViewById(R.id.customer);
		
		OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.default_:
					showDefaultNotification();
					break;

				case R.id.customer:
					showCustomizeNotificaiton();
					break;
				default:
				    removeNotification();
				}
			}
		};
		
		default_.setOnClickListener(listener);
		customer.setOnClickListener(listener);
	}
	
    //�Զ���֪ͨ��
	protected void showCustomizeNotificaiton() {
		// TODO Auto-generated method stub
		CharSequence tickText = "I am CustomizeNotification";
		Notification notification = new Notification(android.R.drawable.stat_notify_chat, tickText, System.currentTimeMillis()+3000);
		
		// 1������һ���Զ������Ϣ���� view.xml  
        // 2���ڳ��������ʹ��RemoteViews�ķ���������image��text��Ȼ���RemoteViews���󴫵�contentView�ֶ� 
		RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.remoteviews);
	    remoteView.setImageViewResource(R.id.image, android.R.drawable.stat_notify_chat);
	    remoteView.setTextViewText(R.id.text, "֪ͨ����Ϊ���Զ���View");
	    notification.contentView = remoteView;
	    
	    PendingIntent pIntent = PendingIntent.getActivity(this, 100, new Intent(Intent.ACTION_CALL, Uri.parse("55566")), 100);
        notification.contentIntent = pIntent;
        
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
	}


	//����Ĭ��֪ͨ��
	protected void showDefaultNotification() {
		// TODO Auto-generated method stub
		CharSequence tickText = "I am Notification";
		int icon =  android.R.drawable.stat_notify_chat;    //��Ҫ����"android."�������ܼ���ϵͳ��Դ
		long timeStamp = System.currentTimeMillis();
		/**
		 * icon:ͼ��
		 * tickText:��ʾ��֪ͨʱ����������
		 * timeStamp��֪ͨ����ʱ��
		 */
		Notification notification = new Notification(icon, tickText, timeStamp + 6000);
		
		/**
		 * �����,������֪��Ҫ�����Ȩ�� : <uses-permission android:name="android.permission.VIBRATE"/>  
         * mNotification.defaults |= Notification.DEFAULT_VIBRATE ;
		 */
		notification.defaults |= notification.DEFAULT_SOUND;
		
		/**
		 * ���״̬��־
		 * FLAG_AUTO_CANCEL          ��֪ͨ�ܱ�״̬���������ť�������  
         * FLAG_NO_CLEAR                 ��֪ͨ�ܱ�״̬���������ť�������  
         * FLAG_ONGOING_EVENT      ֪ͨ��������������  
         * FLAG_INSISTENT                ֪ͨ������Ч��һֱ����
		 */
		notification.flags = notification.FLAG_AUTO_CANCEL;
		//���ò鿴֪ͨ����ͼ(����֪ͨ��ʾΪĬ��View)
		PendingIntent pIntent = PendingIntent.getActivity(this, 100, new Intent(Intent.ACTION_CALL, Uri.parse("55566")), 100);
		/**
		 * this:������
		 * "Title":����
		 * "TestContent":�ı�����
		 * pIntent:�鿴֪ͨ��Ĵ���������
		 */
		notification.setLatestEventInfo(this, "Title", "TestContnet", pIntent);
		
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		//ע���֪ͨ   
        // �����NOTIFICATION_ID��֪ͨ�Ѵ��ڣ�����ʾ����֪ͨ�������Ϣ ������tickerText ��
		/**
		 * 100:id����Ϊcancel()֪ͨʱʹ�ã�
		 * notification:��Ҫ������֪ͨ��
		 */
		notificationManager.notify(100, notification);
		
	}
	
	//�Ƴ�֪ͨ����
	private void removeNotification()  
    {  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
        // ȡ����ֻ�ǵ�ǰContext��Notification  
        mNotificationManager.cancel(100);  
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
