package cn.itcast.drag;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private ImageView imageView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        imageView = (ImageView) this.findViewById(R.id.imageView);
        imageView.setOnTouchListener(new TouchListener());
    }
    
    private final class TouchListener implements OnTouchListener{
    	private PointF startPoint = new PointF();
    	private Matrix matrix = new Matrix();
    	private Matrix currentMatrix = new Matrix();  //存放当前照片的移动位置
    	private int mode = 0;
    	private static final int DRAG = 1;
    	private static final int ZOOM = 2;
    	private float startDis;//拉拽前距离
    	private PointF midPoint;//中间点坐标
    	
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN://手指压屏幕
				mode = DRAG;
				currentMatrix.set(imageView.getImageMatrix());//记录imageView当前的移动位置
				startPoint.set(event.getX(), event.getY());
				break;
            //核心事件，其他为辅助的设置事件
			case MotionEvent.ACTION_MOVE://手指在屏幕上移动
				if(mode == DRAG){
					float dx = event.getX() - startPoint.x;//得到在X轴上的移动距离
					float dy = event.getY() - startPoint.y;//得到在y轴上的移动距离
					matrix.set(currentMatrix);//在上一次位置没有进行移动时的位置（一定要在设置了当前的位置后，并在此基础上进行移动）
					matrix.postTranslate(dx, dy);//拓展到该坐标
				}else if(mode == ZOOM){//缩放模式
					float endDis = distance(event);//拉拽后距离
					if(endDis > 10f){
						float scale = endDis / startDis;//缩放倍数
						matrix.set(currentMatrix);
						matrix.postScale(scale, scale, midPoint.x, midPoint.y);
					}
				}				
				break;
				
			case MotionEvent.ACTION_UP://手指从屏幕抬起
			case MotionEvent.ACTION_POINTER_UP://在屏幕上已有一根手指按屏幕后，再有一根手指按屏
				mode = 0;
				break;
				
			case MotionEvent.ACTION_POINTER_DOWN://在屏幕上有两根手指按屏幕后，其中有一根手从屏幕抬起
				mode = ZOOM;
				startDis = distance(event);//开始距离
				if(startDis > 10f){
					midPoint = mid(event);
					currentMatrix.set(imageView.getImageMatrix());//记录imageView当前的移动位置
				}
				break;
			}
			imageView.setImageMatrix(matrix);
			return true;
		}
    	
    }
    /**
     * 计算两点间的距离
     * @param event
     * @return
     */
	public static float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);  //getX(1):后一个按下去的点的坐标，getX(0):前一个按下去的点的坐标
		float dy = event.getY(1) - event.getY(0);
		return FloatMath.sqrt(dx*dx + dy*dy);
	}
	/**
	 * 计算中心点位置
	 * @param event
	 * @return
	 */
	public static PointF mid(MotionEvent event){
		float midX = (event.getX(1) + event.getX(0)) / 2;
		float midY = (event.getY(1) + event.getY(0)) / 2;
		return new PointF(midX, midY);
	}
}