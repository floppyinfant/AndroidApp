package com.floppyinfant.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Zugriff auf Datenbank:
 * SQLiteDatabase db = new Database(getContext()).getReadableDatabase();
 * bzw.
 * SQLiteDatabase db = new Database(getContext()).getWriteableDatabase();
 * 
 * @author TM
 *
 */
public class DatabaseManager extends SQLiteOpenHelper {

	public DatabaseManager(Context context) {
		super(context, DatabaseSchema.DB_NAME, null, DatabaseSchema.DB_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// CREATE
		db.execSQL(DatabaseSchema.SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO: Backup (export), modify ...
		
		// DROP
//		db.execSQL(DatabaseSchema.SQL_DROP);
		
		// CREATE
//		onCreate(db);
	}

}
