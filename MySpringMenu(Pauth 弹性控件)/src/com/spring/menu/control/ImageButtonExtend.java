package com.spring.menu.control;

import com.spring.menu.animation.ZoomAnimation1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;

public class ImageButtonExtend extends ImageButton {
	private Animation animation;
	
	public ImageButtonExtend(Context context) {
		super(context);
	}
	
	public ImageButtonExtend(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageButtonExtend(Context context, AttributeSet attrs, int defStyle) {
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
		
		if ((this.animation instanceof ZoomAnimation1)) {
			setVisibility(((ZoomAnimation1) this.animation).direction 
					!= ZoomAnimation1.Direction.SHOW ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	protected void onAnimationStart() {
		super.onAnimationStart();
		
		if ((this.animation instanceof ZoomAnimation1)){
			setVisibility(View.VISIBLE);
		}
	}
}