package com.spring.menu.animation;

import com.spring.menu.control.ImageButtonExtend;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * �ֲ��˵����غ���������
 * @Description: �ֲ��˵����غ���������

 * @File: SpringAnimation.java

 * @Package com.spring.menu.animation

 * @Author Hanyonglu

 * @Date 2012-10-25 ����12:18:39

 * @Version V1.0
 */
public class SpringAnimation extends ZoomAnimation1 {
	private static int[] size;
	private static int xOffset = 210;
	private static int yOffset = -15;
	public static final int	DURATION = 300;
	
	/**
	 * ������
	 * @param direction
	 * @param duration
	 * @param view
	 */
	public SpringAnimation(Direction direction, long duration, View view) {
		super(direction, duration, new View[] { view });
		SpringAnimation.xOffset = SpringAnimation.size[0] / 2 - 30;
	}

	/**
	 * ��ʼ��ʾ����Ч��
	 * @param viewgroup
	 * @param direction
	 * @param size
	 */
	public static void startAnimations(ViewGroup viewgroup,
			ZoomAnimation1.Direction direction, int[] size) {
		SpringAnimation.size = size;
		
		switch (direction) {
		case HIDE:
			startShrinkAnimations(viewgroup);
			break;
		case SHOW:
			startEnlargeAnimations(viewgroup);
			break;
		}
	}

	/**
	 * ��ʼ���ֲ˵�
	 * @param viewgroup
	 */
	private static void startEnlargeAnimations(ViewGroup viewgroup) {
		for (int i = 0; i < viewgroup.getChildCount(); i++) {
			if (viewgroup.getChildAt(i) instanceof ImageButtonExtend) {
				ImageButtonExtend inoutimagebutton = (ImageButtonExtend) viewgroup
						.getChildAt(i);
				SpringAnimation animation = new SpringAnimation(
						ZoomAnimation1.Direction.HIDE, DURATION, inoutimagebutton);
				animation.setStartOffset((i * 200)
						/ (-1 + viewgroup.getChildCount()));
				animation.setInterpolator(new OvershootInterpolator(4F));
				inoutimagebutton.startAnimation(animation);
			}
		}
	}

	/**
	 * ��ʼ���ز˵�
	 * @param viewgroup
	 */
	private static void startShrinkAnimations(ViewGroup viewgroup) {
		for (int i = 0; i < viewgroup.getChildCount(); i++) {
			if (viewgroup.getChildAt(i) instanceof ImageButtonExtend) {
				ImageButtonExtend inoutimagebutton = (ImageButtonExtend) viewgroup
						.getChildAt(i);
				SpringAnimation animation = new SpringAnimation(
						ZoomAnimation1.Direction.SHOW, DURATION,
						inoutimagebutton);
				animation.setStartOffset((100 * ((-1 + viewgroup
						.getChildCount()) - i))
						/ (-1 + viewgroup.getChildCount()));
				animation.setInterpolator(new AnticipateOvershootInterpolator(2F));
				inoutimagebutton.startAnimation(animation);
			}
		}
	}

	@Override
	protected void addShrinkAnimation(View[] views) {
		// TODO Auto-generated method stub
		MarginLayoutParams mlp = (MarginLayoutParams) views[0].
				getLayoutParams();
		addAnimation(new TranslateAnimation(
				xOffset + -mlp.leftMargin, 
				0F,yOffset + mlp.bottomMargin, 0F));
	}

	@Override
	protected void addEnlargeAnimation(View[] views) {
		// TODO Auto-generated method stub
		MarginLayoutParams mlp = (MarginLayoutParams) views[0].
				getLayoutParams();
		addAnimation(new TranslateAnimation(
				0F, xOffset + -mlp.leftMargin, 
				0F,yOffset + mlp.bottomMargin));
	}
}
