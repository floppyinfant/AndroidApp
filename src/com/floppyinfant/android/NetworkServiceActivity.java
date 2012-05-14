package com.floppyinfant.android;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class NetworkServiceActivity extends Activity {
	
	private NetworkService.ServiceBinder mBinder;
	
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			mBinder = (NetworkService.ServiceBinder) binder;
			// TODO Callback setzen
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Notification notification = new Notification(R.drawable.ic_android_logo, "Service gestartet", System.currentTimeMillis());
		PendingIntent p = PendingIntent.getActivity(this, 0, null, 0);
		notification.setLatestEventInfo(this, "Service", "gestartet", p);
		//TODO startForeground(77, notification);
		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		Intent service = new Intent(this, NetworkService.class);
		bindService(service, mServiceConnection, BIND_AUTO_CREATE);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unbindService(mServiceConnection);
		super.onPause();
	}
	
}
