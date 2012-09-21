package com.floppyinfant.android;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Zugriff auf Datenbank:
 * SQLiteDatabase db = new DBManager(getContext()).getReadableDatabase();
 * bzw.
 * SQLiteDatabase db = new DBManager(getContext()).getWriteableDatabase();
 * 
 * @author TM
 *
 */
public class DBManager extends SQLiteOpenHelper {
	
	private static final String TAG = "DBManager";
	private Context context;
	private SQLiteDatabase db;
	
	/**
	 * Database-Schema
	 * 
	 * @author TM
	 *
	 */
	public static class Schema {
		
		public static final String DATABASE_NAME = "androidapp.db";
		public static final int DATABASE_VERSION = 1;
		public static final String DATABASE_TABLE = "table";
		
		/*
		 * Columns | Keys
		 */
		public static final String KEY_ROWID = "_id";
		public static final String KEY_NAME = "name";
		public static final String KEY_TEXT = "text";
		
		public static final String[] ALL_COLUMNS = new String[] {
			KEY_ROWID,
			KEY_NAME,
			KEY_TEXT
		};
		
		public static final String[] SEARCH_COLUMNS = new String[] {
			KEY_ROWID,
			KEY_NAME,
			KEY_TEXT
		};
		
		public static final String[] RESULT_COLUMNS = new String[] {
			KEY_NAME,
			KEY_TEXT
		};
		
		/*
		 * DCL
		 */
		public static final String SQL_CREATE = 
			"CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
				KEY_ROWID + " INTEGER PRIMARY KEY AUTOINKREMENT, " + 
				KEY_NAME + " TEXT NOT NULL, " + 
				KEY_TEXT + " TEXT" + 
			");";
		
		public static final String SQL_DROP = 
			"DROP TABLE IF EXISTS " + DATABASE_TABLE;
		
		/*
		 * DML
		 */
		public static final String STM_INSERT = 
			"INSERT INTO " + DATABASE_TABLE + 
				" (" + KEY_NAME + ") " + 
				"VALUES (?)";
		
	} // end Schema
	
	
	/* *************************************************************************
	 * copy prepopulated database from assets to phone
	 **************************************************************************/
	
	/**
	 * DBManager extends SQLiteOpenHelper<br/>
	 * is the abstraction layer to manage the underlying SQLite Database<br/>
	 * the database schema and the operations are encapsulated in this class.
	 * 
	 * @param context
	 */
	public DBManager(Context context) {
		super(context, Schema.DATABASE_NAME, null, Schema.DATABASE_VERSION);
		
		this.context = context;
		copy();
	}
	
	/**
	 * copy the prepopulated database from resources, 
	 * if it does not exist on the phone
	 */
	public void copy() {
		String destPath = "/data/data/" + context.getPackageName() + "/databases/" + Schema.DATABASE_NAME;
		File f = new File(destPath);
		try {
			if (!f.exists()) {
				// copy sqlite-DB from Assets to Phone
				InputStream in = context.getAssets().open(Schema.DATABASE_NAME);
				OutputStream out = new FileOutputStream(destPath);
				
				// copy 1k bytes at a time
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			}
			Log.d(TAG, "copy() file to " + destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/* *************************************************************************
	 * SQLiteOpenHelper
	 **************************************************************************/
	
	/**
	 * Opens the database with the name and version passed to the super constructor<br/>
	 * from the path "/data/data/<package_name>/databases/"
	 * 
	 * @return this (method chaining)
	 */
	public DBManager open() {
		db = getWritableDatabase();
		return this; 	// Method-Chaining
	}
	
	public void close() {
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate() with statement " + Schema.SQL_CREATE);
		// CREATE
		try {
			db.execSQL(Schema.SQL_CREATE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading Database '" + Schema.DATABASE_NAME + "' from Version '" + oldVersion + "' to Version '" + newVersion + "'.");
		// backup (export), modify, copy ...
		
		// DROP
//		db.execSQL(DBSchema.SQL_DROP);
		
		// CREATE
//		onCreate(db);
	}
	
	
	/* *************************************************************************
	 * SQL-Statement Wrapper
	 **************************************************************************/
	
	public void insertRecord(String title, String text) {
		
	}
	
	public void updateRecord(long rowId, String title, String text) {
		
	}
	
	public void deleteRecord(long rowId) {
		
	}
	
	public Cursor getRecord(long rowId) {
		Cursor c = null;
		
		return c;
	}
	
	public Cursor getAllRecords() {
		Cursor c = db.query(Schema.DATABASE_TABLE, Schema.SEARCH_COLUMNS, null, null, null, null, Schema.KEY_NAME);
		return c;
	}
	
	
}
