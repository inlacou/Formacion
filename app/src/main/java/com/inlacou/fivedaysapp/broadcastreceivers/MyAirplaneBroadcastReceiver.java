package com.inlacou.fivedaysapp.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

//https://developer.android.com/reference/android/content/Intent#ACTION_AIRPLANE_MODE_CHANGED
public class MyAirplaneBroadcastReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Timber.d("airplane mode changed to " + intent.getBooleanExtra("state", false));
	}
}
