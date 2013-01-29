package com.floppyinfant.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

// imports for Renderer
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;

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
	
	/* *************************************************************************
	 * Event Listener Callbacks
	 ************************************************************************ */

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
			
			/*
			 * Code from Triangle 2D OpenGL Tutorial
			 * http://developer.android.com/guide/topics/graphics/opengl.html
			 */
			/*
			// reverse direction of rotation above the mid-line
			if (y > getHeight() / 2) {
				dx = dx * -1;
			}
			// reverse direction of rotation to left of the mid-line
			if (x < getWidth() / 2) {
				dy = dy * -1;
			}
			*/
			
			mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
			mRenderer.mAngleY += dy * TOUCH_SCALE_FACTOR;
			requestRender();
		}

		mPreviousX = x;
		mPreviousY = y;
		return true;
	}

	/* *************************************************************************
	 * Renderer
	 ************************************************************************ */
	
	class GLRenderer implements GLSurfaceView.Renderer {

		private GLObject mObject;

		public float mAngleX;
		public float mAngleY;

		public GLRenderer() {
			mObject = new GLObject();
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			
			/*
			 * By default, OpenGL enables features that improve quality but reduce
			 * performance. One might want to tweak that especially on software
			 * renderer.
			 */
			gl.glDisable(GL10.GL_DITHER);

			/*
			 * Some one-time OpenGL initialization can be made here probably based
			 * on features of this particular context
			 */
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

			gl.glClearColor(0f, 0f, 0f, 1f);	// background color
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			
			// Initialize the objects to be drawn here
			// initShapes();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			
			gl.glViewport(0, 0, width, height);

			/*
			 * Set our projection matrix. This doesn't have to be done each time we
			 * draw, but usually a new projection needs to be set when the viewport
			 * is resized.
			 */
			float ratio = (float) width / height;
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		}

		public void onDrawFrame(GL10 gl) {
			
			/*
			 * Usually, the first thing one might want to do is to clear the screen.
			 * The most efficient way of doing this is to use glClear().
			 */
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			
			/*
			 * Now we're ready to draw some 3D objects
			 */
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			// When using GL_MODELVIEW, you must set the view point
			//GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0f);
			
			// Add Motion
			gl.glTranslatef(0, 0, -3.0f);
			gl.glRotatef(mAngleX, 0, 1, 0);
			gl.glRotatef(mAngleY, 1, 0, 0);
			
			// Draw
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			
			mObject.draw(gl);
		}
	}
}
