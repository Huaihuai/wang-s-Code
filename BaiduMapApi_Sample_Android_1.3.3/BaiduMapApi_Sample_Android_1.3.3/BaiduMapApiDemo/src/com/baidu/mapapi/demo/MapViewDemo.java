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
        // ���ʹ�õ�ͼSDK�����ʼ����ͼActivity
		long iTime = System.nanoTime();        //��ȷ�������ʱ�����
        super.initMapActivity(app.mBMapMan);
        iTime = System.nanoTime() - iTime;
        Log.d("MapViewDemo", "the init time is  " + iTime);
        MapView mapView = (MapView)findViewById(R.id.bmapView);
        mapView.setBuiltInZoomControls(true);   //�����������ſؼ�
        MapController mapController = mapView.getController();
        GeoPoint point = new GeoPoint((int)(39.19*1E6), (int)(116.404*1E6));   //�������ĵ�����
        mapController.setCenter(point);         //�������ĵ�
        mapController.setZoom(12);              //���õ�ͼ���Ŵ�С
        //mapView.setDoubleClickZooming(false);
        //mapView.setTraffic(true);             //�Ƿ��ṩ��ͨ��ͼ
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
