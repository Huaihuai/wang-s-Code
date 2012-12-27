package com.wyx.bmap;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapActivity;
import com.baidu.mapapi.MapController;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.wyx.common.BMapApiDemoApp;

public class MyBMap extends MapActivity {
	private MapView mapView;
	private MapController mapController;
	private GeoPoint geoPoint;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bmap);
        
        BMapApiDemoApp app = new BMapApiDemoApp();
//        BMapManager mBMapMan = new BMapManager(getApplication());
//        mBMapMan.init("FDA2E27D4B157B806FBC6E69D9D838DFEEA7BCB0", null);
        if(app.mBMapMan == null){
        	app.mBMapMan = new BMapManager(getApplication());
        	app.mBMapMan.init(app.mStrKey, new BMapApiDemoApp.MyGeneralListener());
        }
        app.mBMapMan.start();                //������ͼ����
        // ���ʹ�õ�ͼSDK�����ʼ����ͼActivity
		long iTime = System.nanoTime();        //��ȷ�������ʱ�����
        super.initMapActivity(app.mBMapMan);
        iTime = System.nanoTime() - iTime;
        Log.d("MapViewDemo", "the init time is  " + iTime);
        mapView = (MapView)findViewById(R.id.bmapsView);
        mapView.setBuiltInZoomControls(true);             //����ϵͳ���ſؼ�
        mapController = mapView.getController();
        geoPoint = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));   //���������
        mapController.setCenter(geoPoint);      //���õ�ͼ���ĵ�
        mapController.setZoom(12);           //���õ�ͼ��չʾ����
        mapView.setDoubleClickZooming(false);   //˫�����е�ͼ������
        mapView.setTraffic(true);             //�Ƿ���ʾ��ͨ��·ͼ
//        mapView.setSatellite(true);           //����ͼ
        
        //��ȡ����ڵ�ͼ�ϵ�ͼ��
        Drawable marker = getResources().getDrawable(R.drawable.iconmarka);
        //��ͼ�����MapView��
        mapView.getOverlays().add(new OverItemT(marker,this));    //�൱������������Ҫʵ����
        
        
        
        
//        BMapManager mBMapMan = new BMapManager(getApplication());
//        mBMapMan.init("FDA2E27D4B157B806FBC6E69D9D838DFEEA7BCB0", null);
//        super.initMapActivity(mBMapMan);
//         
//        MapView mMapView = (MapView) findViewById(R.id.bmapsView);
//        mMapView.setBuiltInZoomControls(true);  //�����������õ����ſؼ�
//         
//        MapController mMapController = mMapView.getController();  // �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
//        GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
//                (int) (116.404 * 1E6));  //�ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
//        mMapController.setCenter(point);  //���õ�ͼ���ĵ�
//        mMapController.setZoom(12);    //���õ�ͼzoom����
    }

    class OverItemT extends ItemizedOverlay<OverlayItem>{  
    	private List<OverlayItem> overlayList = new ArrayList<OverlayItem>();
    	private Drawable marker;
    	private Context mContext;
    	
    	private double mLat1 = 39.90923; // point1γ��
    	private double mLon1 = 116.357428; // point1����

    	private double mLat2 = 39.90923;
    	private double mLon2 = 116.397428;

    	private double mLat3 = 39.90923;
    	private double mLon3 = 116.437428;
    	
    	public OverItemT(Drawable marker, Context context) {
    		// TODO Auto-generated constructor stub
    		super(boundCenterBottom(marker));
    		this.marker = marker;
    		this.mContext = context;
    		//�ø����ľ�γ�ȹ���GeoPoint
    		GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
    		GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
    		GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
    		
    		// ����OverlayItem��������������Ϊ��item��λ�ã������ı�������Ƭ��
    		overlayList.add(new OverlayItem(p1, "p1", "point1"));
    		overlayList.add(new OverlayItem(p2, "p2", "point2"));
    		overlayList.add(new OverlayItem(p3, "p3", "point3"));
    		
    		populate();       //??
    	}
    	
    	public void udateOverlay() {
			populate();
		}
    	
		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			// TODO Auto-generated method stub
			super.draw(canvas, mapView, shadow);
			// Projection�ӿ�������Ļ��������;�γ������֮��ı任
//			Projection projection = mapView.getProjection(); 
			for(int index = overlayList.size()-1;index>=0;index--){
				OverlayItem overlayItem = getItem(index);
				//���Ʊ�ǣ������б�����ã�
				Paint paint = new Paint();
				paint.setColor(Color.BLUE);
				paint.setTextSize(15);
				//����γ��ת������Ļ��������
				//Point point = projection.toPixels(geoPoint, null);
				Point point = mapView.getProjection().toPixels(overlayItem.getPoint(), null);
				canvas.drawText("��"+overlayItem.getTitle(), point.x, point.y, paint);
			}
			
			boundCenterBottom(marker);        //??
		}


		@Override
		protected OverlayItem createItem(int i) {
			// TODO Auto-generated method stub
			return overlayList.get(i);            //??
		}

		@Override
		public int size() {
			// TODO Auto-generated method stub
			return overlayList.size();
		}
		//�������¼�

		@Override
		public boolean onTap(int i) {
			// TODO Auto-generated method stub
//			setFocus(overlayList.get(i));
//			Toast.makeText(this.mContext, overlayList.get(i).getSnippet(), Toast.LENGTH_LONG);
//			
//			return true; 
			
			Toast.makeText(this.mContext, overlayList.get(i).getSnippet(),
	                Toast.LENGTH_SHORT).show();
	        return true;
		}
		
//		@Override
//		public boolean onTap(GeoPoint arg0, MapView arg1) {
//			// TODO Auto-generated method stub
//			// ��ȥ����������
////			ItemizedOverlayDemo.mPopView.setVisibility(View.GONE);
//			return super.onTap(arg0, arg1);
//		}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_my_bmap, menu);
        return true;
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
