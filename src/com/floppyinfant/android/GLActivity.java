package com.floppyinfant.android;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

/**
 * Wrapper activity demonstrating the use of {@link GLSurfaceView}, a view that
 * uses OpenGL drawing into a dedicated surface.
 * 
 * Shows: + How to redraw in response to user input.
 * 
 * Example from Android_SDK/samples/ApiDemos/TouchRotateActivity.java
 * 
 */
public class GLActivity extends Activity {
	
	private GLSurfaceView mGLSurfaceView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create our Preview view and set it as the content of our Activity
		mGLSurfaceView = new GLView(this);
		setContentView(mGLSurfaceView);
		
		mGLSurfaceView.requestFocus();
		mGLSurfaceView.setFocusableInTouchMode(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mGLSurfaceView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGLSurfaceView.onPause();
	}

}
