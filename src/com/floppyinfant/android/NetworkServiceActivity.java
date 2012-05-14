package com.floppyinfant.android;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class NetworkServiceActivity extends Activity {
	
	private NetworkService.ServiceBinder mBinder;
	
	/*
	 * Override the Handlers handleMessage() method in the Activity
	 * 
	 * ... or create a Runnable class and pass it to the Binder: setRunnable(r)
	 * the Runnable stores the result in a member variable and 
	 * can access views of the Activity in its run() method to show the result.
	 * 
	 * in the Service implementation the Runnable is executed by the Handler:
	 * Handler.post(Runnable);
	 */
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			Bundle bundle = msg.getData();
			long res = bundle.getLong("result", -1);
			// TODO show result in a View
			
			super.handleMessage(msg);
		}
	};
	
	/*
	 * Establish the connection to the service in the onResume() method:
	 * bindService(intent, conn, int)
	 */
	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder binder) {
			mBinder = (NetworkService.ServiceBinder) binder;
			mBinder.setCallback(mHandler);
			mBinder.doInBackground();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		/*
		// Service with higher priority
		Notification notification = new Notification(R.drawable.ic_android_logo, "Service gestartet", System.currentTimeMillis());
		PendingIntent p = PendingIntent.getActivity(this, 0, null, 0);
		notification.setLatestEventInfo(this, "Service", "gestartet", p);
		//TODO startForeground(77, notification); // setForeground(true);
		// @see samples/ApiDemos/src/com/example/android/apis/app/ForegroundService.java
		*/
		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		Intent service = new Intent(this, NetworkService.class);
		// startService(intent) is implicitly called by BIND_AUTO_CREATE
		bindService(service, mServiceConnection, BIND_AUTO_CREATE);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		unbindService(mServiceConnection);
		//stopService(new Intent(this, NetworkService.class)); // or stopSelf() in Service class, when finished
		super.onPause();
	}
	
}
