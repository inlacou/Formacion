package com.inlacou.fivedaysapp.general;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class AppCtrl extends Application {
	
	public static Context instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		Timber.plant(new Timber.DebugTree());
	}
	
}
