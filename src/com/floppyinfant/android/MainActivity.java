package com.floppyinfant.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;

public class MainActivity extends Activity {

	/** 
	 * Logging
	 */
	private static final String TAG = MainActivity.class.getSimpleName();

	/* ************************************************************************
	 * Lifecycle
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		registerForContextMenu(findViewById(R.id.sf_action));

		Log.v(TAG, "onCreate");
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
			startActivity(new Intent(this, SubActivity.class));
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
			startActivity(new Intent(this, HelpActivity.class));
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
			startActivity(new Intent(this, HelpActivity.class));
			return true;	// zeigt dem Framework an, dass das Ereignis verbraucht ist
		default:
			// return true;
		}
		
		return super.onContextItemSelected(item);   
	}
}