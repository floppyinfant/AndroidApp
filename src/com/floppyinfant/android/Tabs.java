package com.floppyinfant.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * 
 * @author TM
 * @see http://developer.android.com/resources/tutorials/views/hello-tabwidget.html
 * 
 */
public class Tabs extends TabActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        
        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        
        // do foreach Tab...
        intent = new Intent().setClass(this, Tab1.class);
        spec = tabHost.newTabSpec("1")
        		.setIndicator("Hello", getResources().getDrawable(R.drawable.ic_tab_1))
        		.setContent(intent);
        tabHost.addTab(spec);
        
        // Tab 2
        intent = new Intent().setClass(this, Tab2.class);
        spec = tabHost.newTabSpec("2")
        		.setIndicator("Tabs", getResources().getDrawable(R.drawable.ic_tab_2))
        		.setContent(intent);
        tabHost.addTab(spec);
        
        // Tab 3 MapView
        intent = new Intent().setClass(this, Maps.class);
        spec = tabHost.newTabSpec("3")
        		.setIndicator("Maps", getResources().getDrawable(R.drawable.ic_tab_1))
        		.setContent(intent);
        tabHost.addTab(spec);
        
        
        tabHost.setCurrentTab(0);
    }
	
}
