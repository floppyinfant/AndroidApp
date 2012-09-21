package com.floppyinfant.android;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class DataActivity extends ListActivity {
	
	private DBManager dbm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// setContentView...
		
		
		// use the DBManager to handle the database
		dbm = new DBManager(this);
		
		
		// actions triggered by a button or menu item (move to OnClickListener)...
		
		// query the database for results (=cursor)
		dbm.open();
		Cursor cursor = dbm.getAllRecords();
		startManagingCursor(cursor);
		// show the result in an AdapterView using a CursorAdapter
		int[] widgetKeys = new int[] {
				android.R.id.text1,
				android.R.id.text2
		};
		SimpleCursorAdapter adapter = new DataAdapter(this, 
				android.R.layout.simple_list_item_2, 
				cursor, 
				DBManager.Schema.RESULT_COLUMNS, 
				widgetKeys);
		setListAdapter(adapter);
	}
	
}
