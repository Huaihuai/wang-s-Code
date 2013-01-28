package com.baidu.mapapi.demo;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import android.os.Bundle;
import android.util.Log;

public class MapViewDemo extends MapActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.mapviewdemo);
        
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();   //??
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(getApplication());
			app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
		}
		app.mBMapMan.start();
        // 如果使用地图SDK，请初始化地图Activity
		long iTime = System.nanoTime();        //精确到纳秒的时间戳；
        super.initMapActivity(app.mBMapMan);
        iTime = System.nanoTime() - iTime;
        Log.d("MapViewDemo", "the init time is  " + iTime);
        MapView mapView = (MapView)findViewById(R.id.bmapView);
        mapView.setBuiltInZoomControls(true);   //启用内置缩放控件
        MapController mapController = mapView.getController();
        GeoPoint point = new GeoPoint((int)(39.19*1E6), (int)(116.404*1E6));   //设置中心点坐标
        mapController.setCenter(point);         //设置中心点
        mapController.setZoom(12);              //设置地图缩放大小
        //mapView.setDoubleClickZooming(false);
        //mapView.setTraffic(true);             //是否提供交通地图
	}

	@Override
	protected void onPause() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		if(app.mBMapMan != null )
			app.mBMapMan.stop();
		super.onPause();
	}
	@Override
	protected void onResume() {
		BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
		app.mBMapMan.start();
		super.onResume();
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

//	   @Override
//	    protected void onDestroy() {
//
//		   BMapApiDemoApp app = (BMapApiDemoApp)this.getApplication();
//			if (app.mBMapMan != null) {
//				app.mBMapMan.destroy();
//				app.mBMapMan = null;
//			}
//			super.onDestroy();
//	    }
}
