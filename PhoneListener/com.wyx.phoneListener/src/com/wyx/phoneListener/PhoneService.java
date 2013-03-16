package com.wyx.phoneListener;

import java.io.File;
import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
/**
 * @author wangyx
 * 电话录音实例
 */
public class PhoneService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		//注册电话状态监听，回调PhoneListener()
		/**
		 * new PhoneListener():监听到后回到的函数
		 * PhoneStateListener.LISTEN_CALL_STATE:监听的状态（到达该状态时，发生回调）
		 */
		telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	//内部类
	private final class PhoneListener extends PhoneStateListener{
		private String incomingNumber;
		private MediaRecorder mediaRecorder;
		private File file;
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			try {
				switch(state){
				case TelephonyManager.CALL_STATE_RINGING:       //来电状态
					this.incomingNumber = incomingNumber;
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:       //通话状态
					file = new File(Environment.getExternalStorageDirectory(), 
							incomingNumber+System.currentTimeMillis()+".3gp");        //创建文件，注意文件名需呀加上后缀   
					mediaRecorder = new MediaRecorder();
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);            //声音来源  
					mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);       //声音编码方式
					mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);    //声音格式
					mediaRecorder.setOutputFile(file.getAbsolutePath());                    //声音放置的路径
					mediaRecorder.prepare();    //做录音的准备
					mediaRecorder.start();      //开始录音
					break;
				case TelephonyManager.CALL_STATE_IDLE:          //挂机状态
					if(mediaRecorder != null){
						mediaRecorder.stop();     //停止录音
						mediaRecorder.release();  //释放录音资源
						mediaRecorder = null;
						//上传到网上
//						update(file);
					}
					break;
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.onCallStateChanged(state, incomingNumber);
		}
		
	}

	
}
