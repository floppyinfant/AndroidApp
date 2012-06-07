package com.floppyinfant.android;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class DataActivity extends ListActivity {
	
	private DatabaseManager mDBM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDBM = new DatabaseManager(this);
		SQLiteDatabase db = mDBM.getReadableDatabase();
		
		Cursor cursor = db.query(
				false, 							// distinct
				DatabaseSchema.TABLE_NAME, 		// table
				DatabaseSchema.SEARCH_COLUMNS, 	// columns
				null, 							// selection
				null, 							// selectionArgs
				null, 							// groupBy
				null, 							// having
				null, 							// orderBy
				null							// limit
		);
		startManagingCursor(cursor);
		
		int[] widgetKeys = new int[] {
				android.R.id.text1,
				android.R.id.text2
		};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, DatabaseSchema.RESULT_COLUMNS, widgetKeys);
		setListAdapter(adapter);
	}
	
}
