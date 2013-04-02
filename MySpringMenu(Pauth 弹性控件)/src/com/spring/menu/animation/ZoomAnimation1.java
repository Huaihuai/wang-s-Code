package com.spring.menu.animation;

import android.view.View;
import android.view.animation.AnimationSet;

/**
 * �Ŵ���С������
 * @Description: �Ŵ���С������

 * @File: ZoomAnimation.java

 * @Package com.spring.menu.animation

 * @Author Hanyonglu

 * @Date 2012-10-25 ����11:37:52

 * @Version V1.0
 */
public abstract class ZoomAnimation1 extends AnimationSet {
	public Direction direction;

	public enum Direction {
		HIDE, SHOW;
	}

	public ZoomAnimation1(Direction direction, long duration, View[] views) {
		super(true);
		this.direction = direction;
		
		switch (this.direction) {
		case HIDE:
			addShrinkAnimation(views);
			break;
		case SHOW:
			addEnlargeAnimation(views);
			break;
		}
		
		setDuration(duration);
	}

	protected abstract void addShrinkAnimation(View[] views);
	
	protected abstract void addEnlargeAnimation(View[] views);
}
