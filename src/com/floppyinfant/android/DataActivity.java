package com.floppyinfant.android;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class DataActivity extends ListActivity {
	
	private DBManager mDBM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mDBM = new DBManager(this);
		SQLiteDatabase db = mDBM.getReadableDatabase();
		
		/*
		 * To query the database you can use query(), rawQuery() 
		 * or the SQLiteQueryBuilder class.
		 */
		Cursor cursor = db.query(
				false, 							// distinct
				DBSchema.DATABASE_TABLE, 			// table
				DBSchema.SEARCH_COLUMNS, 		// columns
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
		//SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, DBSchema.RESULT_COLUMNS, widgetKeys);
		SimpleCursorAdapter adapter = new DataAdapter(this, android.R.layout.simple_list_item_2, cursor, DBSchema.RESULT_COLUMNS, widgetKeys);
		setListAdapter(adapter);
	}
	
}
