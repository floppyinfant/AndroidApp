package com.floppyinfant.android;

import android.app.Activity;
import android.os.Bundle;

/**
 * 
 * @author TM
 * @see http://developer.android.com/resources/tutorials/views/hello-tabwidget.html
 */
public class TabHost extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabhost);
    }
	
}
