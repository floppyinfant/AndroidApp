package com.floppyinfant.android;

public class DatabaseSchema {
	
	public static final String DB_NAME = "androidapp.db";
	public static final int DB_VERSION = 1;
	public static final String TABLE_NAME = "";
	
	/*
	 * Columns
	 */
	public static final String COL_ID = "_id";
	public static final String COL_NAME = "name";
	public static final String COL_TEXT = "text";
	
	public static final String[] ALL_COLUMNS = new String[] {
		COL_ID,
		COL_NAME,
		COL_TEXT
	};
	
	public static final String[] SEARCH_COLUMNS = new String[] {
		COL_ID,
		COL_NAME,
		COL_TEXT
	};
	
	public static final String[] RESULT_COLUMNS = new String[] {
		COL_NAME,
		COL_TEXT
	};
	
	/*
	 * DCL
	 */
	public static final String SQL_CREATE = 
			"CREATE TABLE " + TABLE_NAME + "(" +
			"	" + 
			"	" + 
			"	" + 
			");";
	
	public static final String SQL_DROP = 
			"DROP TABLE IF EXISTS " + TABLE_NAME;
	
	/*
	 * DML
	 */
	public static final String STM_INSERT = 
			"INSERT INTO " + TABLE_NAME + 
			" (" + COL_NAME + ") " + 
			"VALUES (?)";
	
	
}
