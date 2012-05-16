package com.floppyinfant.android;

import android.app.Activity;
import android.os.Bundle;

public class Graphics extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GraphicsView(this));
	}

}
