package com.floppyinfant.android;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Performanter ListAdapter
 * 
 * Quelle: Arno Becker, Marcus Pant, Android 2, Grundlagen und Programmierung, dpunkt.verlag, 2010
 *         Kapitel 6.4, pp. 120ff.
 *         
 * @author TM
 *
 */
public class DataAdapter extends SimpleCursorAdapter {
	
	private int index1 = -1;
	private int index2 = -1;
	
	/*
	 * ViewHolder-Pattern
	 */
	static class ViewHolder {
		private TextView text1;
		private TextView text2;
	}

	public DataAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
		super(context, layout, c, from, to);
		
		index1 = c.getColumnIndex(DBSchema.KEY_NAME);
		index2 = c.getColumnIndex(DBSchema.KEY_TEXT);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = super.newView(context, cursor, parent); 
		
		final ViewHolder viewHolder = new ViewHolder();
		viewHolder.text1 = (TextView) view.findViewById(android.R.id.text1);
		viewHolder.text2 = (TextView) view.findViewById(android.R.id.text2);
		
		view.setTag(viewHolder);
		
		return view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder viewHolder = (ViewHolder) view.getTag();
		
		viewHolder.text1.setText(cursor.getString(index1));
		viewHolder.text2.setText(cursor.getString(index2));
		
		super.bindView(view, context, cursor);
	}
	
	
	
}
