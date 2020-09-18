package com.inlacou.fivedaysapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import androidx.annotation.Nullable;

import com.inlacou.fivedaysapp.notification.NotificationBuilder;

public class WorkService extends Service {
	
	private PowerManager.WakeLock wakeLock = null;
	
	public static void start(Context context) {
		Intent intent = new Intent(context, WorkService.class);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			context.startForegroundService(intent);
		}else{
			context.startService(intent);
		}
	}
	
	public static void stop(Context context) {
		Intent intent = new Intent(context, WorkService.class);
		context.stopService(intent);
	}
	
	@Override
	public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		//Do some continuous work here
		startForeground(2, NotificationBuilder.createOngoingNotification(this, "title", "body"));
	}
	
	@Override
	public void onDestroy() {
		if(wakeLock!=null) wakeLock.release();
		super.onDestroy();
	}
	
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
