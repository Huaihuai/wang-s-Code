package com.wyx.bmap;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.VersionInfo;
import com.wyx.common.BMapApiDemoApp;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MapDemo extends Activity {
	private ListView mListView = null;
    private String mListName[] = {
    	"MyBMap",
    	"BusLineSearch",
    	"PoiSearch",
    	"RoutePlane"
    };
    Class<?> mActivities[] = {
    		MyBMap.class,
    		RoutePlane.class,
    		BusLineSearch.class,
    		PoiSearch.class,
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        String versionInfoString = VersionInfo.getApiVersion();
        mListView = (ListView)findViewById(R.id.listView);
        List<String> data = new ArrayList<String>();
        int j=0;
        for(String i:mListName){
        	data.add(mListName[j]);
        	j++;
        }
//        for (int i = 0; i < mListName.length; i++) {
//			data.add(mListName[i]);
//		}
        mListView.setAdapter((ListAdapter) new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,data));
        mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				// TODO Auto-generated method stub
//				onListItemClick(index);
				onListItemClick(index);
			}
		});
    }

	private void onListItemClick(int index) {
		// TODO Auto-generated method stub
		if(index <0||index>=mActivities.length)
			return;
		if(index == mActivities.length){
			
		}
		
		Intent intent = new Intent(this,mActivities[index]);
		startActivity(intent);
	}
	
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
    	if(app.mBMapMan != null){
    		app.mBMapMan.destroy();
    		app.mBMapMan = null;
    	}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		super.onResume();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_map_demo, menu);
        return true;
    }
}
