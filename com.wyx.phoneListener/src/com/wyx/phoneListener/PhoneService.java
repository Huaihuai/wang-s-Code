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
 * �绰¼��ʵ��
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
		//ע��绰״̬�������ص�PhoneListener()
		/**
		 * new PhoneListener():��������ص��ĺ���
		 * PhoneStateListener.LISTEN_CALL_STATE:������״̬�������״̬ʱ�������ص���
		 */
		telephonyManager.listen(new PhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
	}
	//�ڲ���
	private final class PhoneListener extends PhoneStateListener{
		private String incomingNumber;
		private MediaRecorder mediaRecorder;
		private File file;
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			try {
				switch(state){
				case TelephonyManager.CALL_STATE_RINGING:       //����״̬
					this.incomingNumber = incomingNumber;
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:       //ͨ��״̬
					file = new File(Environment.getExternalStorageDirectory(), 
							incomingNumber+System.currentTimeMillis()+".3gp");        //�����ļ���ע���ļ�����ѽ���Ϻ�׺   
					mediaRecorder = new MediaRecorder();
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);            //������Դ  
					mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);       //�������뷽ʽ
					mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);    //������ʽ
					mediaRecorder.setOutputFile(file.getAbsolutePath());                    //�������õ�·��
					mediaRecorder.prepare();    //��¼����׼��
					mediaRecorder.start();      //��ʼ¼��
					break;
				case TelephonyManager.CALL_STATE_IDLE:          //�һ�״̬
					if(mediaRecorder != null){
						mediaRecorder.stop();     //ֹͣ¼��
						mediaRecorder.release();  //�ͷ�¼����Դ
						mediaRecorder = null;
						//�ϴ�������
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
