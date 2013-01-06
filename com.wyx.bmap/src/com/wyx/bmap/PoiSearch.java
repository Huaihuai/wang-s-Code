package com.wyx.bmap;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.PoiOverlay;
import com.wyx.common.BMapApiDemoApp;

public class PoiSearch extends MapActivity {
	Button mBtnSearch = null;
	Button mSuggestionSearch = null;
	ListView mSuggestionList = null;
	public static String mStrSuggestions[] = {};
	
	MapView mMapView = null;
	MKSearch mSearch = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_search);
        
        BMapApiDemoApp app = new BMapApiDemoApp();
        if(app.mBMapMan==null){
        	app.mBMapMan = new BMapManager(getApplication());
        	app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
        }
        app.mBMapMan.start();
        super.initMapActivity(app.mBMapMan);
        
        mMapView = (MapView)findViewById(R.id.bmapView);
        //添加缩放键
        mMapView.setBuiltInZoomControls(true);
        //设置在缩放动画过程中也显示overlay,默认为不绘制
        mMapView.setDrawOverlayWhenZooming(true);
        
        // 初始化搜索模块，注册事件监听
        mSearch = new MKSearch();
        mSearch.init(app.mBMapMan, new MKSearchListener() {			
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int err) {
				// TODO Auto-generated method stub
				if (err != 0 || res == null) {
					Toast.makeText(PoiSearch.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
				}
				int nSize = res.getSuggestionNum();
				mStrSuggestions = new String[nSize];


				for (int i = 0; i < nSize; i++) {
					mStrSuggestions[i] = res.getSuggestion(i).city + res.getSuggestion(i).key;
				}
				ArrayAdapter<String> suggestionString = new ArrayAdapter<String>(PoiSearch.this, android.R.layout.simple_list_item_1,mStrSuggestions);
				mSuggestionList.setAdapter(suggestionString);
				Toast.makeText(PoiSearch.this, "suggestion callback", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// TODO Auto-generated method stub
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(PoiSearch.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
				}

			    // 将地图移动到第一个POI中心点
			    if (res.getCurrentNumPois() > 0) {
				    // 将poi结果显示到地图上
					PoiOverlay poiOverlay = new PoiOverlay(PoiSearch.this, mMapView);
					poiOverlay.setData(res.getAllPoi());
				    mMapView.getOverlays().clear();
				    mMapView.getOverlays().add(poiOverlay);
				    mMapView.invalidate();
			    	mMapView.getController().animateTo(res.getPoi(0).pt);
			    } else if (res.getCityListNum() > 0) {
			    	String strInfo = "在";
			    	for (int i = 0; i < res.getCityListNum(); i++) {
			    		strInfo += res.getCityListInfo(i).city;
			    		strInfo += ",";
			    	}
			    	strInfo += "找到结果";
					Toast.makeText(PoiSearch.this, strInfo, Toast.LENGTH_LONG).show();
			    }
			}
			
			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
    
        mSuggestionList = (ListView) findViewById(R.id.listView1);
        // 设定搜索按钮的响应
        mBtnSearch = (Button)findViewById(R.id.search);
        // 设定suggestion响应
        mSuggestionSearch = (Button)findViewById(R.id.suggestionsearch);
        
        OnClickListener clickListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mBtnSearch(v);
			}
		};
		mBtnSearch.setOnClickListener(clickListener);
		
		OnClickListener clickListener1 = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSuggestionSearch(v);
			}
		};
		mSuggestionSearch.setOnClickListener(clickListener1);
    }
    protected void mSuggestionSearch(View v) {
		// TODO Auto-generated method stub
    	EditText editSearchKey = (EditText)findViewById(R.id.suggestionkey);
    	
		mSearch.suggestionSearch( 
				editSearchKey.getText().toString());
	}
	protected void mBtnSearch(View v) {
		// TODO Auto-generated method stub
		if (mBtnSearch.equals(v)) {
//			Intent intent = null;
//			intent = new Intent(PoiSearch.this, MapViewDemo.class);
//			this.startActivity(intent);

			EditText editCity = (EditText)findViewById(R.id.city);
			EditText editSearchKey = (EditText)findViewById(R.id.searchkey);
			mSearch.poiSearchInCity(editCity.getText().toString(), 
					editSearchKey.getText().toString());
		}
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		BMapApiDemoApp app = new BMapApiDemoApp();
		app.mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		BMapApiDemoApp app = new BMapApiDemoApp();
//		app.mBMapMan.start();
		super.onResume();
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_poi_search, menu);
        return true;
    }
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
