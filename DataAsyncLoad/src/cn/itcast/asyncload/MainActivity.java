package cn.itcast.asyncload;

import java.io.File;
import java.util.List;

import cn.itcast.adapter.ContactAdapter;
import cn.itcast.domain.Contact;
import cn.itcast.service.ContactService;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView listView;
	File cache;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			//异常处理放在自定义适配器中处理
			 listView.setAdapter(new ContactAdapter(MainActivity.this, (List<Contact>)msg.obj, 
					 R.layout.listview_item, cache));
		}		
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) this.findViewById(R.id.listView);
        
        //指定文件的路径和文件夹名称
        cache = new File(Environment.getExternalStorageDirectory(), "cache");
        if(!cache.exists()) cache.mkdirs();
        
        new Thread(new Runnable() {			
			public void run() {
				try {
					List<Contact> data = ContactService.getContacts();
					handler.sendMessage(handler.obtainMessage(22, data));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();       
    }

    //清楚缓存
	@Override
	protected void onDestroy() {
		//删除缓存目录下的文件（即清楚缓存）[cache.listFiles:获取cache文件加下的文件]
		for(File file : cache.listFiles()){
			file.delete();
		}
		//删除文件夹
		cache.delete();
		super.onDestroy();
	}
    
}