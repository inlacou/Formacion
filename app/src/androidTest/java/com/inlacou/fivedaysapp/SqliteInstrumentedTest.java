package com.inlacou.fivedaysapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.datapersistence.sharedpreferences.SharedPrefManager;
import com.inlacou.fivedaysapp.datapersistence.sqlite.PokemonDb;
import com.inlacou.fivedaysapp.datapersistence.sqlite.SqliteCtrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SqliteInstrumentedTest {
	
	Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", 67);
	Pokemon ivysaur = new Pokemon(2, "Ivysaur", 77);
	Pokemon venusaur = new Pokemon(3, "Venusaur", 107);
	
	@Before
	public void clean() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		PokemonDb.deleteLikedPokemon(appContext);
	}
	
	@Test
	public void test() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		//Initial state
		assertEquals(new ArrayList<>(), PokemonDb.retrieveLikedPokemon(appContext));
		//Bulbasaur
		assertEquals(null, PokemonDb.retrieveLikedPokemon(appContext, 1));
		PokemonDb.insertLikedPokemon(appContext, bulbasaur);
		assertEquals(1, PokemonDb.retrieveLikedPokemon(appContext).size());
		assertEquals(bulbasaur, PokemonDb.retrieveLikedPokemon(appContext, 1));
		//Ivysaur
		assertEquals(null, PokemonDb.retrieveLikedPokemon(appContext, 2));
		PokemonDb.insertLikedPokemon(appContext, ivysaur);
		assertEquals(2, PokemonDb.retrieveLikedPokemon(appContext).size());
		assertEquals(ivysaur, PokemonDb.retrieveLikedPokemon(appContext, 2));
	}
	
}