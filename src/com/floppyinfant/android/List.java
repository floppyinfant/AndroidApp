package com.floppyinfant.android;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class List extends ListActivity implements AdapterView.OnItemSelectedListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		// this implements AdapterView.OnItemSelectedListener
		// or create inner class for Listener (stored in a member variable)
		// or create anonymous class
		((Spinner) this.findViewById(R.id.sp_order_by)).setOnItemSelectedListener(this);
		
		// ArrayAdapter
		setListAdapter(
			new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.list)
			)
		);
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
		Cursor c = null;
		Toast toast;
		switch (position) {
		case 0:
			//c = mKontaktSpeicher.ladeGeoKontaktListe(Sortierung.STANDARD, null);
			toast = Toast.makeText(this, "case 0", Toast.LENGTH_LONG);
			toast.show();
			break;
		case 1:
			//c = mKontaktSpeicher.ladeGeoKontaktListe(Sortierung.NAME, null);
			toast = Toast.makeText(this, "case 1", Toast.LENGTH_LONG);
			toast.show();
			break;        
		default:
			//c = mKontaktSpeicher.ladeGeoKontaktListe(null);
			toast = Toast.makeText(this, "case default", Toast.LENGTH_LONG);
			toast.show();
			
		}

		final ListView view = getListView();
//		final SimpleCursorAdapter cursorAdapter = ((SimpleCursorAdapter) view.getAdapter());
//		cursorAdapter.changeCursor(c);
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}
}
