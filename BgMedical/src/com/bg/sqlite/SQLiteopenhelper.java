package com.bg.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteopenhelper extends SQLiteOpenHelper {

	private static String name = "mydata.db";
	private static int version = 1;

	public SQLiteopenhelper(Context context) {
		super(context, name, null, version);
	}

	public SQLiteopenhelper(Context context, String name,
			CursorFactory factory, int version,
			DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql_baby = "CREATE TABLE babys (" +
				"babyname VARCHAR( 30 )  PRIMARY KEY ASC NOT NULL," +
				"sex INTEGER( 1 )  NOT NULL," + //0 为男宝  1为女宝
				"parity INTEGER( 2 )," +
				"birthday  DATETIME( 20 ) NOT NULL," +
				"height REAL( 10 )," +
				"weight REAL( 10 )," +
				"mark   VARCHAR( 10 ))"; //是否为默认 1 为默认， 0 为非默认
		
		String sql_datas = "CREATE TABLE datas (" +
				"id integer PRIMARY KEY autoincrement," +
				"babyname VARCHAR( 30 ) NOT NULL," + 
				"date DATETIME( 20 ) NOT NULL," + //测量日期
				"height REAL( 20 )," +
				"weight REAL( 20 ), " +
				"bmi REAL( 20 )," +
				"height_weight REAL( 10 )," +
				"head REAL( 10 )," +
				"seat REAL( 10 )," +
				"bust REAL( 10 ))";
		db.execSQL(sql_baby);
		db.execSQL(sql_datas);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "alter table login add addr varchar(50)";
		db.execSQL(sql);
	}

}
