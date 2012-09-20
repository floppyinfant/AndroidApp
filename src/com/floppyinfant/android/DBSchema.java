package com.floppyinfant.android;

public class DBSchema {
	
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
			"CREATE TABLE " + DATABASE_TABLE + "(" +
			"	" + 
			"	" + 
			"	" + 
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
	
	
}
