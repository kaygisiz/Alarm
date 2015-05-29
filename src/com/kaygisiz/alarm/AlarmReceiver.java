package com.kaygisiz.alarm;

import com.manish.alarmmanager.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
	static NotificationManager notificationManager;
	private final String SOMEACTION = "com.kaygisiz.alarm.ACTION";

	@Override
	public void onReceive(Context context, Intent intent) {
		generateNotification(context,"Alarm!");
		String action = intent.getAction();
		if (SOMEACTION.equals(action)) {
			//do what you want here
			generateNotification(context,"Alarm!");
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private void generateNotification(Context context, String message) {
		  System.out.println(message+"++++++++++2");
		  int icon = R.drawable.ic_launcher;
		  long when = System.currentTimeMillis();
		  notificationManager = (NotificationManager) context
		    .getSystemService(Context.NOTIFICATION_SERVICE);
		  Notification notification = new Notification(icon, message, when);
		  String title = context.getString(R.string.app_name);
		  String subTitle = context.getString(R.string.app_name);
		  Intent notificationIntent = new Intent(context, OutPut.class);
		  notificationIntent.putExtra("content", message);
		  PendingIntent intent = PendingIntent.getActivity(context, 111,notificationIntent, 0);
		  notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
		    | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		 
		  notification.setLatestEventInfo(context, title, subTitle, intent);
		  //To play the default sound with your notification:
		  notification.sound = Uri.parse(MainActivity.path);
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  notificationManager.notify(0, notification);
		  context.startActivity(notificationIntent);
    }

}