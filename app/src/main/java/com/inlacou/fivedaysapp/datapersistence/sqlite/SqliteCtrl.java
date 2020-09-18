package com.inlacou.fivedaysapp.datapersistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteCtrl extends SQLiteOpenHelper {
	
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Pokemon.db";
	
	public SqliteCtrl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_POKEMON);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy is
		// to simply to discard the data and start over
		db.execSQL(SQL_DELETE_TABLE_POKEMON);
		onCreate(db);
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	private static final String SQL_CREATE_TABLE_POKEMON =
			"CREATE TABLE " + PokemonContract.TABLE_NAME + " (" +
					PokemonContract.COLUMN_NAME_ID + " INTEGER PRIMARY KEY ON CONFLICT REPLACE," +
					PokemonContract.COLUMN_NAME_NAME + " TEXT," +
					PokemonContract.COLUMN_NAME_HEIGHT + " INTEGER)";
	
	private static final String SQL_DELETE_TABLE_POKEMON =
			"DROP TABLE IF EXISTS " + PokemonContract.TABLE_NAME;
	
	
}
