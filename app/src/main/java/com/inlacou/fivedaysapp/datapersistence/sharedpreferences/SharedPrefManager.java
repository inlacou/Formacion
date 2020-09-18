package com.inlacou.fivedaysapp.datapersistence.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefManager {
	
	enum Ids {
		AUTH_TOKEN, FAVORITE_POKEMON_ID
	}
	
	public static void setAuthToken(Context context, String value) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(Ids.AUTH_TOKEN.name(), value);
		editor.commit();
	}
	
	public static String getAuthToken(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		String result = sharedPreferences.getString(Ids.AUTH_TOKEN.name(), "");
		if(result.equals("")) {
			return null;
		} else {
			return result;
		}
	}
	
	public static void setFavoritePokemonId(Context context, Integer value) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		if(value==null){
			editor.putInt(Ids.FAVORITE_POKEMON_ID.name(), -1);
		} else {
			editor.putInt(Ids.FAVORITE_POKEMON_ID.name(), value);
		}
		editor.commit();
	}
	
	public static Integer getFavoritePokemonId(Context context) {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		int result = sharedPreferences.getInt(Ids.FAVORITE_POKEMON_ID.name(), -1);
		if(result==-1) {
			return null;
		} else {
			return result;
		}
	}
	
}
