package com.wyx.intent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.wyx.remoteServiceclient.RemoteClient;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.R.integer;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author wangyx
 * �ļ��洢��Intent������ݴ��ͼ���������
 */
public class MainActivity extends BaseActivity {
    Intent intent;
    EditText contentEditText;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        contentEditText = (EditText)findViewById(R.id.content1);
    }
    /**
     * ��Intent�Ķ��ַ�ʽ
     */
    //����һ��Ҫ���У�˽�з����޷�ʵ�֣�
    public void clickToNew(View v){
    	//-------��һ�֣���ʽ��-------
    	//1>
    	intent = new Intent(MainActivity.this, NewActivity.class);
    	//2>intent.setClass(MainActivity.this, NewActivity.class);
    	//3>intent.setClassName(MainActivity.this, "com.wyx.intent.NewActivity");
    	//4>intent.setComponent(new ComponentName(MainActivity.this, NewActivity.class));
    	//-------�ڶ��֣���ʽ��-------
    	//intent = new Intent();
    	//intent.setClass(MainActivity.this, NewActivity.class);
    	//-------�ڶ��֣���ʽ��-------
    	/**
    	 * ֻҪActivity�Ĺ����������ݰ���intent����������
    	 */
    	Intent intent = new Intent();
    	intent.setAction("com.wyx.intent");
    	intent.addCategory("com.wyx.java");
    	intent.setData(Uri.parse("wyx://www.wyx.cn/android"));
    	//intent.setType("image/gif");
    	//��setData()��setType()ͬʱ����ʱ��setType()�����setData()����ʱ�����ݣ�����Ҫ�������·���д��
    	//intent.setDataAndType(Uri.parse("wyx://www.wyx.cn/android"), "image/gif");
    	startActivity(intent);
    }
    
    public void clickPop(View v){
    	intent = new Intent(MainActivity.this, PopActivity.class);
    	startActivity(intent);
    }
    
    /**
     * �������ݵ��ļ���
     */
    public void clickSave(View v) throws IOException{
    	/**
    	 *Context.MODE_APPEND  //1.ֻ�ܱ���Ӧ����ʹ�ã�2.׷��ʽ���
    	 *Context.MODE_PRIVATE //1.ֻ�ܱ���Ӧ��ʹ�ã�2.����ʽ���
    	 *Context.Context.MODE_WORLD_READABLE  //1.�ⲿӦ�ö�ȡ��
    	 *Context.MODE_WORLD_WRITEABLE  //1.�ⲿӦ��д�룻
    	 *ע������ⲿ��������Ҫ׷��ʽд�룬���Խ���
    	 *   ���óɣ�FileOutputStream outputStream = new FileOutputStream(file, append);
    	 */
    	FileOutputStream outputStream = getApplicationContext().openFileOutput("myTestFile", MODE_PRIVATE);
    	outputStream.write(contentEditText.getText().toString().getBytes());
    	outputStream.close();
    }
    /**
     * ���ļ��ж�ȡ����
     */
    public void clickRead(View v) throws IOException{
    	FileInputStream inputStream = this.openFileInput("myTestFile");
    	byte[] buffer = new byte[1024];
    	int len = 0;
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	while((len = inputStream.read(buffer)) != -1){
    		outputStream.write(buffer,0,len);      //��������
    	}
    	byte[] data = outputStream.toByteArray();
    	String str = new String(data);
    	Log.i("From RAM", str);
    }
    /**
     * �����������ݴ���SDCard
     * ������Ҫ����Ȩ�ޣ�android.permission.WRITE_EXTERNAL_STORAGE      ����д��
                     android.permission.MOUNT_UNMOUNT_FILESYSTEMS  ����ɾ�ɽ���
     */
    public void clickSaveToSDcard(View v) throws IOException{
    	//����ļ��Ƿ����
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
    	    //ȡ���ʼ�·����������ʹ�þ���·������Ϊ��1.5��1.6��sdcard��λ�ã���/sdcard��,���Ժ�İ汾��λ�ã���/mnt/sdcard����
    		File sdcardFile = new File(Environment.getExternalStorageDirectory(), "myFile");     //�ڹ̶�·���´����ļ�
    		try {
				FileOutputStream outputStream = new FileOutputStream(sdcardFile);         //�������ʼ�������
				outputStream.write("I am coming!".getBytes());                      //������ͨ�����������ݵ��ļ�
				outputStream.close();
				Toast.makeText(this, "Success!", 3).show();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }
    
    public void clickTestRemoteService(View v){
    	Intent intent = new Intent(this,RemoteClient.class);
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
