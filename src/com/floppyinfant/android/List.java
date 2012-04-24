package com.floppyinfant.android;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class List extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		// ArrayAdapter
		setListAdapter(
			new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1, 
				getResources().getStringArray(R.array.list)
			)
		);
		
	}
}
