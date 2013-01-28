package com.floppyinfant.android.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database Adapter<br />
 * a Database Manager
 * 
 * @author TM
 *
 */
public class DatabaseManager extends SQLiteOpenHelper {
	
	/* *************************************************************************
	 * Database-Schema
	 **************************************************************************/
	public static final String DATABASE_NAME = "androidapp.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_TABLE = "notes";
	
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
	
	private static final String TAG = "DBAdapter";
	private Context mContext;
	private SQLiteDatabase mDb;
	
	
	/* *************************************************************************
	 * copy prepopulated database from assets to phone
	 **************************************************************************/
	
	/**
	 * DBAdapter extends SQLiteOpenHelper<br/>
	 * is the abstraction layer to manage the underlying SQLite Database<br/>
	 * the database schema and the operations are encapsulated in this class.
	 * 
	 * @param context
	 */
	public DatabaseManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		this.mContext = context;
		copyFile();
	}
	
	/**
	 * copy the prepopulated database from resources, 
	 * if it does not exist on the phone
	 */
	private void copyFile() {
		String destPath = "/data/data/" + mContext.getPackageName() + "/databases/" + DATABASE_NAME;
		File f = new File(destPath);
		try {
			if (!f.exists()) {
				// copy sqlite-DB from Assets to Phone
				InputStream in = mContext.getAssets().open(DATABASE_NAME);
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
	
	
	/**
	 * read a text file
	 * 
	 * FileInputStream fin = new FileInputStream(new File(directory, filename));
	 * 
	 * @param in
	 * @return String with the file contents
	 * @throws IOException
	 */
	public static String readTextFile(FileInputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} finally {
			reader.close();
		}
		
		return sb.toString();
	}
	
	/**
	 * write a text file
	 * 
	 * @param out
	 * @param str
	 * @throws IOException
	 */
	public static void writeTextFile(FileOutputStream out, String str) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(out);
		try {
			writer.write(str);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	/* *************************************************************************
	 * SQLiteOpenHelper
	 **************************************************************************/
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// copy() or create ???
		
		Log.d(TAG, "" + SQL_CREATE);
		// CREATE
		try {
			db.execSQL(SQL_CREATE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading Database '" + DATABASE_NAME + "' from Version '" + oldVersion + "' to Version '" + newVersion + "'.");
		// backup (export), modify, copy ...
		
		// DROP
//		db.execSQL(DBSchema.SQL_DROP);
		
		// CREATE
//		onCreate(db);
	}
	
	/**
	 * Opens the database with the name and version passed to the super constructor<br/>
	 * from the path "/data/data/<package_name>/databases/"
	 * 
	 * @return this (method chaining)
	 */
	public DatabaseManager open() throws SQLException {
		mDb = getWritableDatabase();
		return this; 	// Method-Chaining, self reference
	}
	
	public void close() {
		super.close();
	}

	
	/* *************************************************************************
	 * SQL-Statement Wrapper
	 **************************************************************************/
	
	/**
	 * 
	 * @param title
	 * @param text
	 * @return rowId or -1 if failed
	 */
	public long insertRecord(String title, String text) {
		// INSERT INTO <table> VALUES (NULL, title, text);
		
		ContentValues values = new ContentValues();
        values.put(KEY_NAME, title);
        values.put(KEY_TEXT, text);

		return mDb.insert(DATABASE_TABLE, null, values);
	}
	
	public boolean updateRecord(long rowId, String title, String text) {
		// UPDATE <table> SET <col> = <value> WHERE <condition>
		
		ContentValues args = new ContentValues();
        args.put(KEY_NAME, title);
        args.put(KEY_TEXT, text);

        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public boolean deleteRecord(long rowId) {
		// DELETE FROM <table> WHERE <condition>
		
		return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	public Cursor fetchRecord(long rowId) {
		Cursor c = mDb.query(true, DATABASE_TABLE, 
				new String[] {KEY_ROWID, KEY_NAME, KEY_TEXT}, 
	            KEY_ROWID + "=" + rowId, 
	            null, null, null, null, null);
		
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public Cursor fetchAllRecords() {
		Cursor c = mDb.query(DATABASE_TABLE, SEARCH_COLUMNS, null, null, null, null, KEY_NAME);
		return c;
	}
	
	
}
