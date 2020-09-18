package com.inlacou.fivedaysapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.inlacou.fivedaysapp.datapersistence.sharedpreferences.SharedPrefManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SharedPrefInstrumentedTest {
	@Before
	public void clean() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		SharedPrefManager.setAuthToken(appContext, null);
		SharedPrefManager.setFavoritePokemonId(appContext, null);
	}
	
	@Test
	public void authToken() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals(null, SharedPrefManager.getAuthToken(appContext));
		SharedPrefManager.setAuthToken(appContext, "my auth token");
		assertEquals("my auth token", SharedPrefManager.getAuthToken(appContext));
	}
	
	@Test
	public void favoritePokemonId() {
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals(null, SharedPrefManager.getFavoritePokemonId(appContext));
		SharedPrefManager.setFavoritePokemonId(appContext, 1);
		assertEquals(1L, SharedPrefManager.getFavoritePokemonId(appContext).longValue());
	}
}