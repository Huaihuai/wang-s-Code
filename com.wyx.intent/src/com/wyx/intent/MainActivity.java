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
 * 文件存储，Intent间的数据传送及生命周期
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
     * 打开Intent的多种方式
     */
    //方法一定要公有（私有方法无法实现）
    public void clickToNew(View v){
    	//-------第一种（显式）-------
    	//1>
    	intent = new Intent(MainActivity.this, NewActivity.class);
    	//2>intent.setClass(MainActivity.this, NewActivity.class);
    	//3>intent.setClassName(MainActivity.this, "com.wyx.intent.NewActivity");
    	//4>intent.setComponent(new ComponentName(MainActivity.this, NewActivity.class));
    	//-------第二种（显式）-------
    	//intent = new Intent();
    	//intent.setClass(MainActivity.this, NewActivity.class);
    	//-------第二种（隐式）-------
    	/**
    	 * 只要Activity的过滤数据数据包含intent的设置数据
    	 */
    	Intent intent = new Intent();
    	intent.setAction("com.wyx.intent");
    	intent.addCategory("com.wyx.java");
    	intent.setData(Uri.parse("wyx://www.wyx.cn/android"));
    	//intent.setType("image/gif");
    	//若setData()和setType()同时存在时，setType()会清空setData()设置时的数据；故需要按照如下方法写：
    	//intent.setDataAndType(Uri.parse("wyx://www.wyx.cn/android"), "image/gif");
    	startActivity(intent);
    }
    
    public void clickPop(View v){
    	intent = new Intent(MainActivity.this, PopActivity.class);
    	startActivity(intent);
    }
    
    /**
     * 存入数据到文件中
     */
    public void clickSave(View v) throws IOException{
    	/**
    	 *Context.MODE_APPEND  //1.只能被本应用是使用；2.追加式添加
    	 *Context.MODE_PRIVATE //1.只能被本应用使用；2.覆盖式添加
    	 *Context.Context.MODE_WORLD_READABLE  //1.外部应用读取；
    	 *Context.MODE_WORLD_WRITEABLE  //1.外部应用写入；
    	 *注：如果外部的数据需要追加式写入，可以将流
    	 *   设置成：FileOutputStream outputStream = new FileOutputStream(file, append);
    	 */
    	FileOutputStream outputStream = getApplicationContext().openFileOutput("myTestFile", MODE_PRIVATE);
    	outputStream.write(contentEditText.getText().toString().getBytes());
    	outputStream.close();
    }
    /**
     * 从文件中读取数据
     */
    public void clickRead(View v) throws IOException{
    	FileInputStream inputStream = this.openFileInput("myTestFile");
    	byte[] buffer = new byte[1024];
    	int len = 0;
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	while((len = inputStream.read(buffer)) != -1){
    		outputStream.write(buffer,0,len);      //积攒数据
    	}
    	byte[] data = outputStream.toByteArray();
    	String str = new String(data);
    	Log.i("From RAM", str);
    }
    /**
     * 操作：将数据存入SDCard
     * 首先需要加入权限：android.permission.WRITE_EXTERNAL_STORAGE      （可写）
                     android.permission.MOUNT_UNMOUNT_FILESYSTEMS  （可删可建）
     */
    public void clickSaveToSDcard(View v) throws IOException{
    	//检测文件是否存在
    	if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
    	    //取得问价路径（不建议使用绝对路径，因为在1.5、1.6中sdcard的位置：“/sdcard”,而以后的版本的位置：“/mnt/sdcard”）
    		File sdcardFile = new File(Environment.getExternalStorageDirectory(), "myFile");     //在固定路径下创建文件
    		try {
				FileOutputStream outputStream = new FileOutputStream(sdcardFile);         //将流与问价相连接
				outputStream.write("I am coming!".getBytes());                      //将数据通过流存入数据到文件
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
