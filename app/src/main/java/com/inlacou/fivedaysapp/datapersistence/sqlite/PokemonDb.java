package com.inlacou.fivedaysapp.datapersistence.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.inlacou.fivedaysapp.business.Pokemon;

import java.util.ArrayList;

public class PokemonDb {
	
	/**
	 * Inserts a new pokemon in the database
	 * @param context
	 * @param pokemon to insert
	 * @return associated table row id
	 */
	public static long insertLikedPokemon(Context context, Pokemon pokemon) {
		SqliteCtrl dbHelper = new SqliteCtrl(context);
		
		// Gets the data repository in write mode
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(PokemonContract.COLUMN_NAME_ID, pokemon.id);
		values.put(PokemonContract.COLUMN_NAME_NAME, pokemon.name);
		values.put(PokemonContract.COLUMN_NAME_HEIGHT, pokemon.height);

		// Insert the new row, returning the primary key value of the new row
		return db.insert(PokemonContract.TABLE_NAME, null, values);
	}
	
	/**
	 * Finds all pokemon on database.
	 * @param context
	 * @return found pokemon (if any)
	 */
	public static ArrayList<Pokemon> retrieveLikedPokemon(Context context) {
		SqliteCtrl dbHelper = new SqliteCtrl(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				PokemonContract.COLUMN_NAME_ID,
				PokemonContract.COLUMN_NAME_NAME,
				PokemonContract.COLUMN_NAME_HEIGHT
		};

		// Filter results WHERE "id" =  'provided id'

		// How you want the results sorted in the resulting Cursor
		String sortOrder = PokemonContract.COLUMN_NAME_ID + " DESC";
		
		Cursor cursor = db.query(
				PokemonContract.TABLE_NAME,     // The table to query
				projection,                     // The array of columns to return (pass null to get all)
				null,                  // The columns for the WHERE clause
				null,               // The values for the WHERE clause
				null,                   // don't group the rows
				null,                    // don't filter by row groups
				sortOrder                       // The sort order
		);
		
		ArrayList<Pokemon> pokemons = new ArrayList<>();
		while(cursor.moveToNext()) {
			Pokemon pokemon = new Pokemon(
					cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_ID)),
					cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_NAME)),
					cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_HEIGHT))
			);
			pokemons.add(pokemon);
		}
		cursor.close();
		
		return pokemons; //Get only first because we are looking for it with the unique id
	}
	
	/**
	 * Finds the pokemon with given id. Only one or zero matches are possible.
	 * @param context
	 * @param id to find
	 * @return found pokemon (if any)
	 */
	public static Pokemon retrieveLikedPokemon(Context context, int id) {
		SqliteCtrl dbHelper = new SqliteCtrl(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				PokemonContract.COLUMN_NAME_ID,
				PokemonContract.COLUMN_NAME_NAME,
				PokemonContract.COLUMN_NAME_HEIGHT
		};

		// Filter results WHERE "id" =  'provided id'
		String selection = PokemonContract.COLUMN_NAME_ID + " = ?";
		String[] selectionArgs = { id+"" };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = PokemonContract.COLUMN_NAME_ID + " DESC";
		
		Cursor cursor = db.query(
				PokemonContract.TABLE_NAME,     // The table to query
				projection,                     // The array of columns to return (pass null to get all)
				selection,                      // The columns for the WHERE clause
				selectionArgs,                  // The values for the WHERE clause
				null,                   // don't group the rows
				null,                    // don't filter by row groups
				sortOrder                       // The sort order
		);
		
		ArrayList<Pokemon> pokemons = new ArrayList<>();
		while(cursor.moveToNext()) {
			Pokemon pokemon = new Pokemon(
					cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_ID)),
					cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_NAME)),
					cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_HEIGHT))
			);
			pokemons.add(pokemon);
		}
		cursor.close();
		
		if(pokemons.isEmpty()) return null;
		return pokemons.get(0); //Get only first because we are looking for it with the unique id
	}
	
	/**
	 * Get all pokemon that match given name. For example, saur should give us at least Bulbasaur, Ivysaur and Venusaur.
	 * @param context
	 * @param name text to match on name
	 * @return arraylist with all pokemon that matched
	 */
	public static ArrayList<Pokemon> retrieveLikedPokemon(Context context, String name) {
		SqliteCtrl dbHelper = new SqliteCtrl(context);
		SQLiteDatabase db = dbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
				PokemonContract.COLUMN_NAME_ID,
				PokemonContract.COLUMN_NAME_NAME,
				PokemonContract.COLUMN_NAME_HEIGHT
		};

		// Filter results WHERE "id" =  'provided id'
		String selection = PokemonContract.COLUMN_NAME_ID + " LIKE ?"; //We are doing a filtering to get multiple matches
		String[] selectionArgs = { name };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = PokemonContract.COLUMN_NAME_ID + " DESC";
		
		Cursor cursor = db.query(
				PokemonContract.TABLE_NAME,     // The table to query
				projection,                     // The array of columns to return (pass null to get all)
				selection,                      // The columns for the WHERE clause
				selectionArgs,                  // The values for the WHERE clause
				null,                   // don't group the rows
				null,                    // don't filter by row groups
				sortOrder                       // The sort order
		);
		
		ArrayList<Pokemon> pokemons = new ArrayList<>();
		while(cursor.moveToNext()) {
			Pokemon pokemon = new Pokemon(
					cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_ID)),
					cursor.getString(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_NAME)),
					cursor.getInt(cursor.getColumnIndexOrThrow(PokemonContract.COLUMN_NAME_HEIGHT))
			);
			pokemons.add(pokemon);
		}
		cursor.close();
		
		return pokemons;
	}
	
	/**
	 * Deletes pokemon that matches with the given id. Should be one or zero.
	 * @param context
	 * @param id to look for
	 * @return number of rows deleted (should be one or zero)
	 */
	public static int deleteLikedPokemon(Context context, int id) {
		SQLiteDatabase db = new SqliteCtrl(context).getWritableDatabase();
		// Define 'where' part of query.
		String selection = PokemonContract.COLUMN_NAME_ID + " = ?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { id+"" };
		// Issue SQL statement.
		return db.delete(PokemonContract.TABLE_NAME, selection, selectionArgs);
	}
	
	/**
	 * Deletes all pokemon on database.
	 * @param context
	 * @return number of rows deleted
	 */
	public static int deleteLikedPokemon(Context context) {
		SQLiteDatabase db = new SqliteCtrl(context).getWritableDatabase();
		return db.delete(PokemonContract.TABLE_NAME, null, null);
	}
	
}
