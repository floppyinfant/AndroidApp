package com.floppyinfant.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * 
 * @see {@link /ApiDemos/src/com/example/android/apis/os/Sensors.java}
 * @author TM
 *
 */
public class SensorGraphView extends View {
	
	private Bitmap mBitmap;
	private Paint mPaint = new Paint();
	private Canvas mCanvas = new Canvas();

	public SensorGraphView(Context context) {
		super(context);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		synchronized(this) {
			if (mBitmap != null) {
				final Paint paint = mPaint;
				// TODO drawing: read about DoubbleBuffering, Java2D Tutorial
				
				canvas.drawBitmap(mBitmap, 0, 0, null);
			}
		}
		
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
		mCanvas.setBitmap(mBitmap);
		mCanvas.drawColor(0xFFFFFFFF);
		// TODO scaling, offset, width, height
		
		super.onSizeChanged(w, h, oldw, oldh);
	}

	
}
