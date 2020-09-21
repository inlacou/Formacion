package com.inlacou.fivedaysapp;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.inlacou.fivedaysapp.business.Pokemon;
import com.inlacou.fivedaysapp.datapersistence.sharedpreferences.SharedPrefManager;
import com.inlacou.fivedaysapp.ui.activities.pokemon.detail.PokemonDetailAct;
import com.inlacou.fivedaysapp.ui.activities.pokemon.detail.PokemonDetailActMdl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInstrumentedUiTests {
	
	Pokemon bulbasaur = new Pokemon(
			1,
			"bulbasaur",
			67,
			"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
			"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png"
	);
	
	Integer favoriteCache = null;
	
	@Before
	public void prepare() {
		favoriteCache = SharedPrefManager.getFavoritePokemonId(ApplicationProvider.getApplicationContext());
		SharedPrefManager.setFavoritePokemonId(ApplicationProvider.getApplicationContext(), null);
	}
	
	@After
	public void restore() {
		SharedPrefManager.setFavoritePokemonId(ApplicationProvider.getApplicationContext(), favoriteCache);
	}
	
	@Test
	public void launchActivity() {
		Intent intent = PokemonDetailAct.intent(ApplicationProvider.getApplicationContext(), new PokemonDetailActMdl(bulbasaur));
		ActivityScenario.launch(intent).onActivity(activity -> {
			// do something with your activity instance
		});
	}
	
	@Test
	public void checkName() {
		Intent intent = PokemonDetailAct.intent(ApplicationProvider.getApplicationContext(), new PokemonDetailActMdl(bulbasaur));
		ActivityScenario.launch(intent).onActivity(activity -> {
			Assert.assertEquals("names should be equal", bulbasaur.name, ((PokemonDetailAct) activity).model.pokemon.name);
		});
	}
	
	@Test
	public void isNotFavorite() {
		SharedPrefManager.setFavoritePokemonId(ApplicationProvider.getApplicationContext(), null);
		Intent intent = PokemonDetailAct.intent(ApplicationProvider.getApplicationContext(), new PokemonDetailActMdl(bulbasaur));
		ActivityScenario.launch(intent).onActivity(activity -> {
			Assert.assertFalse("should not be favorite", ((PokemonDetailAct) activity).model.isFavorite);
		});
	}
	
	@Test
	public void makeFavoriteFromUi() {
		SharedPrefManager.setFavoritePokemonId(ApplicationProvider.getApplicationContext(), null);
		Intent intent = PokemonDetailAct.intent(ApplicationProvider.getApplicationContext(), new PokemonDetailActMdl(bulbasaur));
		ActivityScenario.launch(intent).onActivity(activity -> {
			Assert.assertFalse("favorite cb should not be checked", ((PokemonDetailAct) activity).cbFavorited.isChecked());
			Assert.assertFalse("should be not favorite", ((PokemonDetailAct) activity).model.isFavorite);
			((PokemonDetailAct) activity).cbFavorited.performClick();
			Assert.assertTrue("favorite cb should be checked", ((PokemonDetailAct) activity).cbFavorited.isChecked());
			Assert.assertTrue("should be favorite", ((PokemonDetailAct) activity).model.isFavorite);
		});
	}
	
	@Test
	public void makeFavoriteFromOther() {
		SharedPrefManager.setFavoritePokemonId(ApplicationProvider.getApplicationContext(), null);
		Intent intent = PokemonDetailAct.intent(ApplicationProvider.getApplicationContext(), new PokemonDetailActMdl(bulbasaur));
		ActivityScenario.launch(intent).onActivity(activity -> {
			Assert.assertFalse("favorite cb should not be checked", ((PokemonDetailAct) activity).cbFavorited.isChecked());
			Assert.assertFalse("should be not favorite", ((PokemonDetailAct) activity).model.isFavorite);
			SharedPrefManager.setFavoritePokemonId(ApplicationProvider.getApplicationContext(), bulbasaur.id);
			((PokemonDetailAct) activity).populate();
			Assert.assertTrue("favorite cb should be checked", ((PokemonDetailAct) activity).cbFavorited.isChecked());
			Assert.assertTrue("should be favorite", ((PokemonDetailAct) activity).model.isFavorite);
		});
	}
	
}