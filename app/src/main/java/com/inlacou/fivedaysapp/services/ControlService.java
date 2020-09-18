package com.inlacou.fivedaysapp.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import timber.log.Timber;

public class ControlService extends IntentService {
	
	public ControlService(){
		super("ControlService");
	}
	
	@Override
	protected void onHandleIntent(@Nullable Intent intent) {
		Timber.d("onHandleIntent: " + intent.getAction());
		if(intent.getAction()==Action.START.name()) {
			WorkService.start(this);
		} else if(intent.getAction()==Action.DO_SOMETHING_MAYBE_END.name()) {
			//TODO do something
			//Yeah, end
		} else if(intent.getAction()==Action.END.name()) {
			WorkService.stop(this);
		}
	}
	
	public static void start(Context context) {
		Intent intent = new Intent(context, ControlService.class);
		intent.setAction(Action.START.name());
		context.startService(intent);
	}
	
	public static void stop(Context context) {
		Intent intent = new Intent(context, ControlService.class);
		intent.setAction(Action.END.name());
		context.startService(intent);
	}
	
	public enum Action {
		START, DO_SOMETHING_MAYBE_END, END
	}
}
