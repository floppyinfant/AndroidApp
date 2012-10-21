package com.floppyinfant.android.data;

import com.floppyinfant.android.Help;
import com.floppyinfant.android.Preferences;
import com.floppyinfant.android.R;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DataListActivity extends ListActivity {
	
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    private static final int DELETE_ID = Menu.FIRST + 1;	// used by context-menu
    
	private DataDBAdapter dbm;
	private Cursor mCursor;
	
	private int mNoteNumber = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_list);
		
		// use the DBAdapter to handle the database
		dbm = new DataDBAdapter(this);
		dbm.open();
		fillData();
		
		registerForContextMenu(getListView());
	}
	
	private void fillData() {
		// query the database for results (=cursor)
		mCursor = dbm.fetchAllRecords();
		startManagingCursor(mCursor);
		
		// show the result in an AdapterView using a CursorAdapter, mapping the data 'from' 'to'
		String[] from = new String[] {DataDBAdapter.KEY_NAME, DataDBAdapter.KEY_TEXT};	// specify database fields
		int [] to = new int[] {android.R.id.text1, android.R.id.text2};			// to bind to views
		SimpleCursorAdapter adapter = new DataCursorAdapter(this, android.R.layout.simple_list_item_2, mCursor, from, to);
		setListAdapter(adapter);
	}
	
	/* ************************************************************************
	 * Menus
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.db, menu);
		// Alternative:
		// menu.add(0,id,0,str);
		return result;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.opt_create:
			//startActivity(new Intent(this, DataInputActivity.class));
			createRecord();
			return true;
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

	private void createRecord() {
		dbm.insertRecord("Note " + mNoteNumber++, "");	// TODO: Subactivity for insert
		fillData();
		
		/*
		Intent i = new Intent(this, DataEditActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE);
		*/
	}
	
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.opt_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                dbm.deleteRecord(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }
	
	/* *************************************************************************
	 * Callbacks
	 **************************************************************************/
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, DataEditActivity.class);
        i.putExtra(DataDBAdapter.KEY_ROWID, id);
        i.putExtra(DataDBAdapter.KEY_NAME, c.getString(c.getColumnIndexOrThrow(DataDBAdapter.KEY_NAME)));
        i.putExtra(DataDBAdapter.KEY_TEXT, c.getString(c.getColumnIndexOrThrow(DataDBAdapter.KEY_TEXT)));
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Bundle extras = intent.getExtras();
        switch(requestCode) {
            case ACTIVITY_CREATE:
                String title = extras.getString(DataDBAdapter.KEY_NAME);
                String text = extras.getString(DataDBAdapter.KEY_TEXT);
                dbm.insertRecord(title, text);
                fillData();
                break;
            case ACTIVITY_EDIT:
                Long rowId = extras.getLong(DataDBAdapter.KEY_ROWID);
                if (rowId != null) {
                    String editTitle = extras.getString(DataDBAdapter.KEY_NAME);
                    String editText = extras.getString(DataDBAdapter.KEY_TEXT);
                    dbm.updateRecord(rowId, editTitle, editText);
                }
                fillData();
                break;
        }
    }

}
