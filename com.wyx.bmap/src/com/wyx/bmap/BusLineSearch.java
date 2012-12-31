package com.wyx.bmap;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKAddrInfo;
import com.baidu.mapapi.MKBusLineResult;
import com.baidu.mapapi.MKDrivingRouteResult;
import com.baidu.mapapi.MKPoiInfo;
import com.baidu.mapapi.MKPoiResult;
import com.baidu.mapapi.MKSearch;
import com.baidu.mapapi.MKSearchListener;
import com.baidu.mapapi.MKSuggestionResult;
import com.baidu.mapapi.MKTransitRouteResult;
import com.baidu.mapapi.MKWalkingRouteResult;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.RouteOverlay;
import com.wyx.common.BMapApiDemoApp;

public class BusLineSearch extends MapActivity {
	Button mButton = null;
	
	MapView mapView;
	MKSearch mSearch;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_line_search);
		final EditText editCity = (EditText)findViewById(R.id.city);
		final EditText editSearchKey = (EditText)findViewById(R.id.searchkey);
		
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
        
        mSearch = new MKSearch();
        mSearch.init(app.mBMapMan, new MKSearchListener() {
			
        	/**返回poi搜索结果。
 			 * 参数：
			 * result - 搜索结果
			 * type - 返回结果类型: MKSearch.TYPE_POI_LIST, MKSearch.TYPE_AREA_POI_LIST,MKSearch.TYPE_AREA_MULTI_POI_LIST,MKSearch.TYPE_CITY_LIST
			 * iError - 错误号，0表示正确返回
 			 */
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(BusLineSearch.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
		        }
				
				// 找到公交路线poi node
                MKPoiInfo curPoi = null;
                int totalPoiNum  = res.getNumPois();
				for( int idx = 0; idx < totalPoiNum; idx++ ) {
					Log.d("busline", "the busline is " + idx);
                    curPoi = res.getPoi(idx);
                    if ( 2 == curPoi.ePoiType ) {
                    	break;
                    }
				}

				mSearch.busLineSearch(editCity.getText().toString(), curPoi.uid);
			}
			/**
			 * 返回公交车详情信息搜索结果。
			 * 参数：
			 * result - 搜索结果
			 * iError - 错误号，0表示正确返回，错误码请参考MKEvent类中的定义
			 */
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
				if (iError != 0 || result == null) {
					Toast.makeText(BusLineSearch.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
		        }

				RouteOverlay routeOverlay = new RouteOverlay(BusLineSearch.this, mapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(result.getBusRoute());
			    mapView.getOverlays().clear();
			    mapView.getOverlays().add(routeOverlay);
			    mapView.invalidate();   //刷新view(使其无效，故需要重新绘制图层)
			    
			    mapView.getController().animateTo(result.getBusRoute().getStart());
			}
			/**返回驾乘路线搜索结果。
             * 参数：
             * result - 搜索结果
             * iError - 错误号，0表示正确返回，当返回MKEvent.ERROR_ROUTE_ADDR时，表示起点或终点有歧义，调用MKDrivingRouteResult的getAddrResult方法获取推荐的起点或终点信息
			 */
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
			}
			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
			}
			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
			}
			/**返回地址信息搜索结果
			 * 参数：
             * result - 搜索结果
             * iError - 错误号，0表示结果正确，result中有相关结果信息；100表示结果正确，无相关地址信息
			 */
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
        
        // 设定搜索按钮的响应 
        mButton = (Button)findViewById(R.id.search);
        mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**params:
				 * 1.起点城市
				 * 2.Key
				 */
				mSearch.poiSearchInCity(editCity.getText().toString(), editSearchKey.getText().toString());
			}
		});
        
          //多控件事件处理集合
//        OnClickListener clickListener = new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				click(v);
//			}
//		}; 
//		mButton.setOnClickListener(clickListener);
    }

    
      //多控件事件处理集合
//    protected void click(View v) {
//		// TODO Auto-generated method stub
//        switch(){
//        case mButton: break;
//        default :break;
//        }
//	}



	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_bus_line_search, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
