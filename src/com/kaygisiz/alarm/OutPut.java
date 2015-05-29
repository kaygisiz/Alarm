package com.kaygisiz.alarm;

import java.util.Random;

import com.manish.alarmmanager.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OutPut extends Activity implements OnClickListener {
	TextView textmessage;
	EditText editex;
	Button buton;
	String sonucString, cevap, sonuc2;
	int sonuc=0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_output);

		Random generator = new Random();
		int a = (int)(2+generator.nextInt(4));
		int b = (int)(2+generator.nextInt(4));
		int c = (int)(2+generator.nextInt(4));
		int d = (int)(2+generator.nextInt(4));
		int e = (int)(2+generator.nextInt(4));
		int op = (int)(generator.nextInt(2));
		
		switch(op){
		case 0:
			sonuc=a+b-c+d*e;
			sonucString= Integer.toString(a)+"+"+Integer.toString(b)+"-"+Integer.toString(c)+"+"+Integer.toString(d)+"*"+Integer.toString(e);
			break;
		case 1:
			sonuc=a-b*c+d-e;
			sonucString= Integer.toString(a)+"-"+Integer.toString(b)+"*"+Integer.toString(c)+"+"+Integer.toString(d)+"-"+Integer.toString(e);
			break;
		case 2:
			sonuc=a*b+c-d*e;
			sonucString= Integer.toString(a)+"*"+Integer.toString(b)+"+"+Integer.toString(c)+"-"+Integer.toString(d)+"*"+Integer.toString(e);
			break;
		}
		textmessage = (TextView) findViewById(R.id.textView1);
		editex = (EditText) findViewById(R.id.editText1);
		buton = (Button) findViewById(R.id.button4);
		
		buton.setOnClickListener(this);
		
		textmessage.setText(sonucString+ " = ?");
		
		}

	@Override
	public void onClick(View v) {
		sonuc2 = Integer.toString(sonuc);
		cevap = editex.getText().toString();
		// TODO Auto-generated method stub
		if(cevap.equalsIgnoreCase(sonuc2)){
			Toast.makeText(OutPut.this, "doðru", Toast.LENGTH_LONG).show();
			MainActivity.alarmManager.cancel(MainActivity.pendingIntent);
			AlarmReceiver.notificationManager.cancelAll();
			Intent myIntent = new Intent(v.getContext(), MainActivity.class);
            startActivityForResult(myIntent, 0);
            this.finish();
		}
		else
			Toast.makeText(OutPut.this, "yanlýþ", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onBackPressed() {
	}
	@Override
	public void onAttachedToWindow() {
	    this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
	    super.onAttachedToWindow();
	}

	/*
	@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
        case KeyEvent.KEYCODE_VOLUME_UP:
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            return true;
        default:
            return super.dispatchKeyEvent(event);
        }
    }
        */
}