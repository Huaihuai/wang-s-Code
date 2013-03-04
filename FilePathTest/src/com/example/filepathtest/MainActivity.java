package com.example.filepathtest;

import java.io.File;

import android.R.integer;
import android.R.string;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setContentView(R.layout.activity_main);
        if(Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
        	Log.i("sdcard", Environment.getExternalStorageDirectory().getPath());    //获取外部存储目录（即：/mnt/sdcard）
        	Log.i("android数据目录", Environment.getDataDirectory().getPath());   //获取android数据目录(即：/data)
        	Log.i("android的根目录", Environment.getRootDirectory().getPath());   //获取android的根目录(即：/system)
        	Log.i("android下载/缓存目录", Environment.getDownloadCacheDirectory().getPath());   //获取android下载/缓存目录(即：/cache)
        	
        	Log.i("当前程序路径", getApplicationContext().getFilesDir().getAbsolutePath());   //获取当前程序路径(即：/data/data/com.example.filepathtest/files)
        	Log.i("该程序安装包路径", getApplicationContext().getPackageResourcePath());      //获取该程序的安装包路径(即：/data/app/com.example.filepathtest-1.apk)
//        	Log.i("程序默认数据库路径", getApplicationContext().getDatabasePath(s).getAbsolutePath());    //获取程序默认数据库路径
        	
        	isExist("/newFile");
        }
    }
    
    public boolean isExist(String path){
    	String aaString = Environment.getExternalStorageDirectory().getPath() + path;
    	Log.i("aa", aaString);
    	File file = new File(Environment.getExternalStorageDirectory().getPath() + path);
    	
    	if(!file.exists()){
    		Log.i("aa", String.valueOf(file.exists()));
    		file.mkdirs();
    	}
    	Log.i("aa", String.valueOf(file.exists()));
    	return file.exists();
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
