package com.inlacou.fivedaysapp.datapersistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteCtrl extends SQLiteOpenHelper {
	
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 2;
	public static final String DATABASE_NAME = "Pokemon.db";
	
	public SqliteCtrl(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_TABLE_POKEMON);
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(oldVersion==1) {
			db.execSQL(MIGRATION_FROM_1_to_2_first);
			db.execSQL(MIGRATION_FROM_1_to_2_second);
		} else {
			//Usual default behaviour, using the db as some sort of cache
			db.execSQL(SQL_DELETE_TABLE_POKEMON);
			onCreate(db);
		}
	}
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	
	private static final String MIGRATION_FROM_1_to_2_first = "ALTER TABLE " + PokemonContract.TABLE_NAME + " ADD COLUMN " + PokemonContract.COLUMN_NAME_FRONT_IMAGE + " TEXT";
	private static final String MIGRATION_FROM_1_to_2_second = "ALTER TABLE " + PokemonContract.TABLE_NAME + " ADD COLUMN " + PokemonContract.COLUMN_NAME_BACK_IMAGE + " TEXT";
	
	private static final String SQL_CREATE_TABLE_POKEMON =
			"CREATE TABLE " + PokemonContract.TABLE_NAME + " (" +
					PokemonContract.COLUMN_NAME_ID + " INTEGER PRIMARY KEY ON CONFLICT REPLACE," +
					PokemonContract.COLUMN_NAME_NAME + " TEXT," +
					PokemonContract.COLUMN_NAME_FRONT_IMAGE + " TEXT," +
					PokemonContract.COLUMN_NAME_BACK_IMAGE + " TEXT," +
					PokemonContract.COLUMN_NAME_HEIGHT + " INTEGER)";
	
	private static final String SQL_DELETE_TABLE_POKEMON =
			"DROP TABLE IF EXISTS " + PokemonContract.TABLE_NAME;
	
	
}
