package com.wyx.bmap;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPlanNode;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.RouteOverlay;
import com.baidu.mapapi.TransitOverlay;
import com.wyx.common.BMapApiDemoApp;

public class RoutePlane extends MapActivity {

	Button mBtnDrive = null;
	Button mBtnTransit = null;
	Button mBtnWalk = null;
	
	MapView mapView = null;
	MKSearch mkSearch = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plane);
        
        BMapApiDemoApp app = new BMapApiDemoApp();
        if(app.mBMapMan == null){
        	app.mBMapMan = new BMapManager(this);
        	app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
        }
        app.mBMapMan.start();
        super.initMapActivity(app.mBMapMan);
        
        mapView = (MapView)findViewById(R.id.bmapView);
        mapView.setBuiltInZoomControls(true);
        mapView.setDrawOverlayWhenZooming(true);
//        mapView.getController();
        
        mkSearch = new MKSearch();
        mkSearch.init(app.mBMapMan, new MKSearchListener() {			
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int err) {
				// TODO Auto-generated method stub
				if (err != 0 || res == null) {
					Toast.makeText(RoutePlane.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(RoutePlane.this, mapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(res.getPlan(0).getRoute(0));
			    mapView.getOverlays().clear();
			    mapView.getOverlays().add(routeOverlay);
			    mapView.invalidate();
			    
			    mapView.getController().animateTo(res.getStart().pt);
			}
			
			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult res, int err) {
				// TODO Auto-generated method stub
				if(res == null || err != 0){
					Toast.makeText(RoutePlane.this, "Sorry!", Toast.LENGTH_SHORT);
					return;
				}
				TransitOverlay transitOverlay = new TransitOverlay(RoutePlane.this, mapView);
				transitOverlay.setData(res.getPlan(0));
				mapView.getOverlays().clear();
				mapView.getOverlays().add(transitOverlay);
				mapView.invalidate();
				
				mapView.getController().animateTo(res.getStart().pt);
			}
			
			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int err) {
				// TODO Auto-generated method stub
				if(err != 0||res == null){
					Toast.makeText(RoutePlane.this, "Sorry!", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(RoutePlane.this, mapView);
				routeOverlay.setData(res.getPlan(0).getRoute(0));
				mapView.getOverlays().clear();
				mapView.getOverlays().add(routeOverlay);
				mapView.invalidate();
				mapView.getController().animateTo(res.getStart().pt);
				
			}
			
			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
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
        
        // 设定搜索按钮的响应
        mBtnDrive = (Button)findViewById(R.id.drive);
        mBtnTransit = (Button)findViewById(R.id.transit);
        mBtnWalk = (Button)findViewById(R.id.walk);
        
        OnClickListener clickListener = new OnClickListener(){
			public void onClick(View v) {
					SearchButtonProcess(v);
			}
        };
        
        mBtnDrive.setOnClickListener(clickListener); 
        mBtnTransit.setOnClickListener(clickListener); 
        mBtnWalk.setOnClickListener(clickListener);
    }
    
    void SearchButtonProcess(View v) {
		// 处理搜索按钮响应
		EditText editSt = (EditText)findViewById(R.id.start);
		EditText editEn = (EditText)findViewById(R.id.end);
		
		// 对起点终点的name进行赋值，也可以直接对坐标赋值，赋值坐标则将根据坐标进行搜索
		MKPlanNode stNode = new MKPlanNode();
		stNode.name = editSt.getText().toString();
		MKPlanNode enNode = new MKPlanNode();
		enNode.name = editEn.getText().toString();

		// 实际使用中请对起点终点城市进行正确的设定
		if (mBtnDrive.equals(v)) {
			/**params:
			 * 1.起点城市
			 * 2.起点城市地点
			 * 3.终点城市
			 * 4.终点城市地点
			 */
			mkSearch.drivingSearch("北京", stNode, "上海", enNode);
		} else if (mBtnTransit.equals(v)) {
			/**params:
			 * 1.起点城市
			 * 2.起点城市地点
			 * 3.终点城市地点
			 */
			mkSearch.transitSearch("北京", stNode, enNode);
		} else if (mBtnWalk.equals(v)) {
			/**params:
			 * 1.起点城市
			 * 2.起点城市地点
			 * 3.终点城市
			 * 4.终点城市地点
			 */
			mkSearch.walkingSearch("北京", stNode, "北京", enNode);
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_route_plane, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
