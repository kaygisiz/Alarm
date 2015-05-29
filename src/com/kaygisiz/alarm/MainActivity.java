package com.kaygisiz.alarm;

import java.util.Calendar;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.manish.alarmmanager.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{

	MediaPlayer mp;
	Button btnStartAlarm,btnStopAlarm,btnSong;
	Context context;
	TimePicker tp;
	Integer sure;
	Calendar c; 
	int seconds;
	int minutes;
	int hours;
	int ct, finalarm;
	static String path;
	static PendingIntent pendingIntent;
	static AlarmManager alarmManager;
	private static final String TAG = "FileChooserExampleActivity";

    private static final int REQUEST_CODE = 6384;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context=MainActivity.this;		

		Intent intentsOpen = new Intent(this, AlarmReceiver.class);
		intentsOpen.setAction("com.manish.alarm.ACTION");
		pendingIntent = PendingIntent.getBroadcast(this,111, intentsOpen, 0);
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


		btnStartAlarm=(Button)findViewById(R.id.button1);
		btnStopAlarm=(Button)findViewById(R.id.button2);
		btnSong=(Button)findViewById(R.id.button3);
		
		tp=(TimePicker)findViewById(R.id.timePicker1);
		tp.setIs24HourView(true);
		
		btnStartAlarm.setOnClickListener(this);
		btnStopAlarm.setOnClickListener(this);
		btnSong.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnStartAlarm){
			fireAlarm();
		}
		if(v==btnStopAlarm){
			stopAlarm();
		}
		if(v==btnSong){
			showChooser();
		}
	}
	public void fireAlarm() {
		c = Calendar.getInstance(); 
		seconds = c.get(Calendar.SECOND)*1000;
		minutes = c.get(Calendar.MINUTE)*60*1000;
		hours = c.get(Calendar.HOUR)*3600*1000;
		ct = seconds + minutes + hours;
		sure = (tp.getCurrentMinute()*60*1000) + (tp.getCurrentHour()*3600*1000);
		finalarm = sure - ct;
		Toast.makeText(MainActivity.this, "Alarm " + finalarm/1000 + " Saniye sonra çalacak", Toast.LENGTH_LONG).show();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		alarmManager.set( AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+finalarm , pendingIntent );
		
		
	}
	public static void stopAlarm(){
				alarmManager.cancel(pendingIntent);
				AlarmReceiver.notificationManager.cancelAll();
		
	}
	
	private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            path = FileUtils.getPath(this, uri);
                            Toast.makeText(MainActivity.this,
                                    "Dosya seçildi: " + path, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Log.e("DosyaSeçici", "Dosya seçerken bir hata oluþtu", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        mp = MediaPlayer.create(this, Uri.parse(path));
        mp.setLooping(true);
    }
	@Override
	public void onBackPressed() {
		finish();
	}

}
