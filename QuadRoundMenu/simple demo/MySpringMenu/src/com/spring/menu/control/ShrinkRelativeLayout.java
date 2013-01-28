package com.spring.menu.control;

import com.spring.menu.animation.ZoomAnimation1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

/**
 * ʵ�ֶ�����RelativeLayout
 * @Description: ʵ�ֶ�����RelativeLayout

 * @File: ShrinkRelativeLayout.java

 * @Package com.spring.menu.control

 * @Author Hanyonglu

 * @Date 2012-10-24 ����10:17:54

 * @Version V1.0
 */
public class ShrinkRelativeLayout extends RelativeLayout {
	private Animation animation;

	public ShrinkRelativeLayout(Context context) {
		super(context);
	}
	
	public ShrinkRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ShrinkRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	public void startAnimation(Animation animation) {
		super.startAnimation(animation);
		this.animation = animation;
		getRootView().postInvalidate();
	}

	@Override
	protected void onAnimationEnd() {
		super.onAnimationEnd();
		
		if (this.animation instanceof ZoomAnimation1) {
			setVisibility(((ZoomAnimation1) animation).direction 
					!= ZoomAnimation1.Direction.SHOW ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	protected void onAnimationStart() {
		super.onAnimationStart();
		
		if (this.animation instanceof ZoomAnimation1) {
			setVisibility(View.VISIBLE);
		}
	}
}