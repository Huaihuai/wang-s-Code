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
	
    //自定义通知框
	protected void showCustomizeNotificaiton() {
		// TODO Auto-generated method stub
		CharSequence tickText = "I am CustomizeNotification";
		Notification notification = new Notification(android.R.drawable.stat_notify_chat, tickText, System.currentTimeMillis()+3000);
		
		// 1、创建一个自定义的消息布局 view.xml  
        // 2、在程序代码中使用RemoteViews的方法来定义image和text。然后把RemoteViews对象传到contentView字段 
		RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.remoteviews);
	    remoteView.setImageViewResource(R.id.image, android.R.drawable.stat_notify_chat);
	    remoteView.setTextViewText(R.id.text, "通知类型为：自定义View");
	    notification.contentView = remoteView;
	    
	    PendingIntent pIntent = PendingIntent.getActivity(this, 100, new Intent(Intent.ACTION_CALL, Uri.parse("55566")), 100);
        notification.contentIntent = pIntent;
        
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
	}


	//调用默认通知框
	protected void showDefaultNotification() {
		// TODO Auto-generated method stub
		CharSequence tickText = "I am Notification";
		int icon =  android.R.drawable.stat_notify_chat;    //需要加入"android."这样才能加入系统资源
		long timeStamp = System.currentTimeMillis();
		/**
		 * icon:图标
		 * tickText:提示有通知时出来的文字
		 * timeStamp：通知发出时间
		 */
		Notification notification = new Notification(icon, tickText, timeStamp + 6000);
		
		/**
		 * 添加震动,后来得知需要添加震动权限 : <uses-permission android:name="android.permission.VIBRATE"/>  
         * mNotification.defaults |= Notification.DEFAULT_VIBRATE ;
		 */
		notification.defaults |= notification.DEFAULT_SOUND;
		
		/**
		 * 添加状态标志
		 * FLAG_AUTO_CANCEL          该通知能被状态栏的清除按钮给清除掉  
         * FLAG_NO_CLEAR                 该通知能被状态栏的清除按钮给清除掉  
         * FLAG_ONGOING_EVENT      通知放置在正在运行  
         * FLAG_INSISTENT                通知的音乐效果一直播放
		 */
		notification.flags = notification.FLAG_AUTO_CANCEL;
		//设置查看通知的意图(将该通知显示为默认View)
		PendingIntent pIntent = PendingIntent.getActivity(this, 100, new Intent(Intent.ACTION_CALL, Uri.parse("55566")), 100);
		/**
		 * this:上下文
		 * "Title":标题
		 * "TestContent":文本内容
		 * pIntent:查看通知后的触发的内容
		 */
		notification.setLatestEventInfo(this, "Title", "TestContnet", pIntent);
		
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		//注册此通知   
        // 如果该NOTIFICATION_ID的通知已存在，会显示最新通知的相关信息 ，比如tickerText 等
		/**
		 * 100:id，作为cancel()通知时使用；
		 * notification:需要启动的通知；
		 */
		notificationManager.notify(100, notification);
		
	}
	
	//移除通知对象
	private void removeNotification()  
    {  
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);  
        // 取消的只是当前Context的Notification  
        mNotificationManager.cancel(100);  
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
