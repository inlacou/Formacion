package com.inlacou.fivedaysapp.general;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.inlacou.fivedaysapp.broadcastreceivers.MyAirplaneBroadcastReceiver;

import timber.log.Timber;

public class AppCtrl extends Application {
	
	public static Context instance;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		Timber.plant(new Timber.DebugTree());
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		filter.addAction(getPackageName()+".CUSTOM_INTENT");
		this.registerReceiver(new MyAirplaneBroadcastReceiver(), filter);
		
	}
	
}
