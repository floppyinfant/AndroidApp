package com.floppyinfant.android;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class NetworkService extends Service {
	
	private IBinder mBinder = new ServiceBinder();
	private volatile boolean isBusy;
	
	public class ServiceBinder extends Binder {
		
		public void send() {
			if (isBusy) {
				return;
			}
			new Thread() {
				@Override
				public void run() {
					isBusy  = true;
					// TODO do something
					
					isBusy = false;
				}
			}.start();
		}
		
		public void receive() {
			// TODO do something
			
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

}
