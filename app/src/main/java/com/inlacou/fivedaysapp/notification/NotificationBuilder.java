package com.inlacou.fivedaysapp.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationChannel;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.inlacou.fivedaysapp.R;
import com.inlacou.fivedaysapp.services.ControlService;
import com.inlacou.fivedaysapp.ui.activities.main.MainAct;

public class NotificationBuilder {
	
	public static Notification createOngoingNotification(Context context, String title, String body) {
		String mTitle = title;
		String mBody = body;
		if (mTitle == null) mTitle = context.getString(R.string.app_name);
		if (mBody == null) mBody = context.getString(R.string.app_name);
		
		Intent serviceIntent = new Intent(context, ControlService.class);
		serviceIntent.setAction(ControlService.Action.DO_SOMETHING_MAYBE_END.name());
		serviceIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		PendingIntent doSomethingPendingIntent = PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_ONE_SHOT);
		
		Intent openAppIntent = new Intent(context, MainAct.class);
		openAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_ONE_SHOT);
		
		NotificationCompat.Builder notificationBuilder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			notificationBuilder = new NotificationCompat.Builder(context, alertChannel(context).getId());
		} else {
			notificationBuilder = new NotificationCompat.Builder(context, "ongoing_alert");
		}
		notificationBuilder
				.setSmallIcon(android.R.drawable.ic_media_next)
				//kotlin .setColor(context.resources.getColorCompat(R.color.irontec_red))
				.setColor(getColorCompat(context, R.color.red))
				.setContentTitle(mTitle)
				.setContentText(mBody)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(mBody))
				.setOngoing(true)
				.setContentIntent(openAppPendingIntent)
				.addAction(android.R.drawable.star_on, context.getString(R.string.Some_action), doSomethingPendingIntent);
		// (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(998 /* ID of notification */, notificationBuilder.build())
		return notificationBuilder.build();
	}
	
	private static int getColorCompat(Context context, int resId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return context.getResources().getColor(resId, null);
		}else{
			return context.getResources().getColor(resId);
		}
	}
	
	public static void destroyOngoingNotification(Context context) {
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(998);
	}
	
	@RequiresApi(Build.VERSION_CODES.O)
	private static NotificationChannel alertChannel(Context context) {
		String id = "ongoing_alert";
		String name = context.getString(R.string.ongoing_notification_channel_title);
		int importance = NotificationManager.IMPORTANCE_MIN;
		String desc = context.getString(R.string.ongoing_notification_channel_description);
		
		NotificationChannel channel = new NotificationChannel(id, name, importance);
		channel.setDescription(desc);
		channel.setSound(null, null);
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
		return channel;
	}
	
}
