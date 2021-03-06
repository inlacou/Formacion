package com.inlacou.fivedaysapp;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.inlacou.fivedaysapp.parse.json.JsonParseExample;
import com.inlacou.fivedaysapp.parse.xml.XmlParseExample;

import net.jodah.xsylum.XsylumException;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ParseInstrumentedTests {
	@Test
	public void BulbasaurFromJson() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("bulbasaur", JsonParseExample.parse().name);
	}
	@Test
	public void BulbasaurFromXml() throws XsylumException {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("Bulbasaur", XmlParseExample.parse().name);
	}
}