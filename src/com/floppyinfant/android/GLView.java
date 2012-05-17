package com.floppyinfant.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Implement a simple rotation control.
 * 
 */
public class GLView extends GLSurfaceView {

	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private final float TRACKBALL_SCALE_FACTOR = 36.0f;

	private GLRenderer mRenderer;

	private float mPreviousX;
	private float mPreviousY;

	public GLView(Context context) {
		super(context);
		mRenderer = new GLRenderer();
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent e) {
		mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
		mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
		requestRender();
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mPreviousX;
			float dy = y - mPreviousY;
			mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
			mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
			requestRender();
		}
		mPreviousX = x;
		mPreviousY = y;
		return true;
	}

}
