package com.floppyinfant.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

/**
 * Render a cube.
 */
public class GLRenderer implements GLSurfaceView.Renderer {

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
