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
        app.mBMapMan.start();                //启动地图引擎
        // 如果使用地图SDK，请初始化地图Activity
		long iTime = System.nanoTime();        //精确到纳秒的时间戳；
        super.initMapActivity(app.mBMapMan);
        iTime = System.nanoTime() - iTime;
        Log.d("MapViewDemo", "the init time is  " + iTime);
        mapView = (MapView)findViewById(R.id.bmapsView);
        mapView.setBuiltInZoomControls(true);             //调用系统缩放控件
        mapController = mapView.getController();
        geoPoint = new GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6));   //设置坐标点
        mapController.setCenter(geoPoint);      //设置地图中心点
        mapController.setZoom(12);           //设置地图的展示级别
        mapView.setDoubleClickZooming(false);   //双击进行地图的缩放
        mapView.setTraffic(true);             //是否显示交通道路图
//        mapView.setSatellite(true);           //卫星图
        
        //获取标记在地图上的图标
        Drawable marker = getResources().getDrawable(R.drawable.iconmarka);
        //将图层放入MapView中
        mapView.getOverlays().add(new OverItemT(marker,this));    //类当参数传入是需要实例；
        
        
        
        
//        BMapManager mBMapMan = new BMapManager(getApplication());
//        mBMapMan.init("FDA2E27D4B157B806FBC6E69D9D838DFEEA7BCB0", null);
//        super.initMapActivity(mBMapMan);
//         
//        MapView mMapView = (MapView) findViewById(R.id.bmapsView);
//        mMapView.setBuiltInZoomControls(true);  //设置启用内置的缩放控件
//         
//        MapController mMapController = mMapView.getController();  // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
//        GeoPoint point = new GeoPoint((int) (39.915 * 1E6),
//                (int) (116.404 * 1E6));  //用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
//        mMapController.setCenter(point);  //设置地图中心点
//        mMapController.setZoom(12);    //设置地图zoom级别
    }

    class OverItemT extends ItemizedOverlay<OverlayItem>{  
    	private List<OverlayItem> overlayList = new ArrayList<OverlayItem>();
    	private Drawable marker;
    	private Context mContext;
    	
    	private double mLat1 = 39.90923; // point1纬度
    	private double mLon1 = 116.357428; // point1经度

    	private double mLat2 = 39.90923;
    	private double mLon2 = 116.397428;

    	private double mLat3 = 39.90923;
    	private double mLon3 = 116.437428;
    	
    	public OverItemT(Drawable marker, Context context) {
    		// TODO Auto-generated constructor stub
    		super(boundCenterBottom(marker));
    		this.marker = marker;
    		this.mContext = context;
    		//用给定的经纬度构造GeoPoint
    		GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
    		GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
    		GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
    		
    		// 构造OverlayItem的三个参数依次为：item的位置，标题文本，文字片段
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
			// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
//			Projection projection = mapView.getProjection(); 
			for(int index = overlayList.size()-1;index>=0;index--){
				OverlayItem overlayItem = getItem(index);
				//绘制标记（并进行标记设置）
				Paint paint = new Paint();
				paint.setColor(Color.BLUE);
				paint.setTextSize(15);
				//将经纬度转换成屏幕像素坐标
				//Point point = projection.toPixels(geoPoint, null);
				Point point = mapView.getProjection().toPixels(overlayItem.getPoint(), null);
				canvas.drawText("★"+overlayItem.getTitle(), point.x, point.y, paint);
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
		//处理点击事件

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
//			// 消去弹出的气泡
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
