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
			
        	/**����poi���������
 			 * ������
			 * result - �������
			 * type - ���ؽ������: MKSearch.TYPE_POI_LIST, MKSearch.TYPE_AREA_POI_LIST,MKSearch.TYPE_AREA_MULTI_POI_LIST,MKSearch.TYPE_CITY_LIST
			 * iError - ����ţ�0��ʾ��ȷ����
 			 */
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					Toast.makeText(BusLineSearch.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_LONG).show();
					return;
		        }
				
				// �ҵ�����·��poi node
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
			 * ���ع�����������Ϣ���������
			 * ������
			 * result - �������
			 * iError - ����ţ�0��ʾ��ȷ���أ���������ο�MKEvent���еĶ���
			 */
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
				if (iError != 0 || result == null) {
					Toast.makeText(BusLineSearch.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_LONG).show();
					return;
		        }

				RouteOverlay routeOverlay = new RouteOverlay(BusLineSearch.this, mapView);
			    // �˴���չʾһ��������Ϊʾ��
			    routeOverlay.setData(result.getBusRoute());
			    mapView.getOverlays().clear();
			    mapView.getOverlays().add(routeOverlay);
			    mapView.invalidate();   //ˢ��view(ʹ����Ч������Ҫ���»���ͼ��)
			    
			    mapView.getController().animateTo(result.getBusRoute().getStart());
			}
			/**���ؼݳ�·�����������
             * ������
             * result - �������
             * iError - ����ţ�0��ʾ��ȷ���أ�������MKEvent.ERROR_ROUTE_ADDRʱ����ʾ�����յ������壬����MKDrivingRouteResult��getAddrResult������ȡ�Ƽ��������յ���Ϣ
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
			/**���ص�ַ��Ϣ�������
			 * ������
             * result - �������
             * iError - ����ţ�0��ʾ�����ȷ��result������ؽ����Ϣ��100��ʾ�����ȷ������ص�ַ��Ϣ
			 */
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
        
        // �趨������ť����Ӧ 
        mButton = (Button)findViewById(R.id.search);
        mButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/**params:
				 * 1.������
				 * 2.Key
				 */
				mSearch.poiSearchInCity(editCity.getText().toString(), editSearchKey.getText().toString());
			}
		});
        
          //��ؼ��¼�������
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

    
      //��ؼ��¼�������
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
