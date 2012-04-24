package com.floppyinfant.android;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Toast;

public class Main extends Activity {

	/** Logging */
	private static final String TAG = Main.class.getSimpleName();
	
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
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
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
			tracker.trackPageView("/FormScreen");
			tracker.dispatch();
			break;
		case R.id.sf_list:
			// Seite wechseln zu ListAdapter | ListView
			startActivity(new Intent(this, List.class));
			break;
		case R.id.sf_dialog:
			// Dialog
			String title = getString(R.string.dia_title);
			String message = getString(R.string.dia_message);
			
			final ProgressDialog progress = ProgressDialog.show(this, title, message, true, false);
			
			// TODO: Error: java.lang.IllegalMonitorStateException: object not locked by thread before wait()
			new Thread() {
				public void run() {
					// execute blocking code in a new Thread
					try {
						wait(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					progress.dismiss();		// close ProgressDialog
				}
			}.start();
			
			break;
		case R.id.sf_toast:
			// Toast
			Toast.makeText(this, R.string.tx_hello, Toast.LENGTH_LONG).show();
			break;
		}
	}

	/* ************************************************************************
	 * Menues(non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.main, menu);    
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_preferences:
//          startActivity(new Intent(this, EinstellungenBearbeiten.class));
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
			getMenuInflater().inflate(R.menu.main_contextmenue, menu);
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.ctx_help:
//          final Intent intent = new Intent(this, HilfeAnzeigen.class);
//          intent.putExtra(CONTEXTMENUE_HILFE, R.raw.hilfe_startseite_geokontakte);
//          startActivity(intent);
			startActivity(new Intent(this, Help.class));
			return true;	// zeigt dem Framework an, dass das Ereignis verbraucht ist
		default:
			// return true;
		}
		
		return super.onContextItemSelected(item);   
	}
}