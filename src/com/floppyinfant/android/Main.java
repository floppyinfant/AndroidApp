package com.floppyinfant.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.floppyinfant.android.data.DataListActivity;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class Main extends Activity {

	/** Logging */
	private static final String TAG = Main.class.getSimpleName();
	
	public static boolean DEBUG = true;
	
	GoogleAnalyticsTracker tracker;

	/* ************************************************************************
	 * Lifecycle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		registerForContextMenu(findViewById(R.id.sf_action));

		tracker = GoogleAnalyticsTracker.getInstance();
	    String trackingId = getResources().getString(R.string.tracking_id);
	    tracker.startNewSession(trackingId, this);	// manual dispatch mode
	    
		Log.v(TAG, "onCreate");
		Log.v(TAG, "Google Analytics Tracking-ID: " + trackingId);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		tracker.stopSession();
	}

	/* ************************************************************************
	 * Actions
	 */
	/** 
	 * Button onClick
	 */
	public void onClickAction(final View view) {
		switch (view.getId()) {
		case R.id.sf_action:
			// Seite wechseln
			startActivity(new Intent(this, Form.class));
			tracker.trackPageView("/FormScreen");	// Google Analytics
			tracker.dispatch();
			break;
		case R.id.sf_tabhost:
			startActivity(new Intent(this, Tabs.class));
			tracker.trackPageView("/TabHost");
			tracker.dispatch();
			break;
		case R.id.sf_list:
			// ListAdapter | ListView
			startActivity(new Intent(this, List.class));
			break;
		case R.id.sf_intent:
			// implicite Intent
			String uri = "";
			
			// CALL
			//uri = "tel:(+49) 641 2503192";
			//Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));
			
			// Website
			//uri = "http://www.floppyinfant.com";
			
			// geo
			String coords = "50.1,7.1";
			String zoom = "6";		// Zoomlevel 0 - 23
			//uri = "geo:" + coords + "?z=" + zoom;
			
			String address = "Ludwigstrasse%2036,35390%20Gießen";
			uri = "geo:0,0?q=" + address;
			
			// StreetView
			//uri = "google.streetview:cbll=" + coords;
			
			// route
			String saddr = "";
			String daddr = "";
			//uri = "http://maps.google.com/maps?saddr=" + saddr + "&daddr=" + daddr;
			
			
			Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			if (isAvailable(i)) {
				Log.v(TAG, "Intent with URI: " + uri);
				startActivity(i);
			} else {
				Log.w(TAG, "Intent not available; URI: " + uri);
			}
			break;
		case R.id.sf_dialog:
			// Dialog
			String title1 = getString(R.string.dia1_title);
			String message1 = getString(R.string.dia1_message);
			
			// TODO Builder
			
			break;
		case R.id.sf_progress:
			// Dialog
			String title = getString(R.string.dia_title);
			String message = getString(R.string.dia_message);
			
			final ProgressDialog progress = ProgressDialog.show(this, title, message, true, false);
			
			new Thread() {
				public void run() {
					// execute blocking code in a new Thread
					synchronized (this) {
						try {
							Thread.sleep(4000);
							//wait(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						progress.dismiss();		// close ProgressDialog
					}
				}
			}.start();
			
			break;
		case R.id.sf_toast:
			// Toast
			String msg = getString(R.string.tx_hello) 
				+ " " + Preferences.getPreferences(this).getString("username", "Welt!");
			Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
			toast.show();
			break;
		case R.id.sf_service:
			startActivity(new Intent(this, WifiServiceController.class));
			break;
		case R.id.sf_canvas:
			startActivity(new Intent(this, Graphics.class));
			break;
		case R.id.sf_opengl:
			startActivity(new Intent(this, GLActivity.class));
			break;
		case R.id.sf_db:
			startActivity(new Intent(this, DataListActivity.class));
			break;
		case R.id.sf_sensor:
			startActivity(new Intent(this, SensorActivity.class));
			break;
		}
	}

	/**
	 * check if intent can be resolved to avoid runtime errors (ActivityNotFoundException)
	 * 
	 * @param i Intent
	 * @return true if Intent is available on device, false if Intent is not available on device
	 */
	private boolean isAvailable(Intent i) {
		java.util.List<ResolveInfo> list = getPackageManager().queryIntentActivities(i, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	/* ************************************************************************
	 * Menus
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);
		// Alternative:
		// menu.add(0,id,0,str);
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_preferences:
			startActivity(new Intent(this, Preferences.class));
			return true;
		case R.id.opt_help:
			startActivity(new Intent(this, Help.class));
			return true;
		case R.id.opt_close:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
//		menu.findItem(R.id.opt_help).setEnabled(false);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		if (v.getId() == R.id.sf_action) {
			getMenuInflater().inflate(R.menu.contextmenu_main, menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ctx_help:
			startActivity(new Intent(this, Help.class));
			return true;	// zeigt dem Framework an, dass das Ereignis verbraucht ist
		default:
			// return true;
		}
		return super.onContextItemSelected(item);   
	}
}