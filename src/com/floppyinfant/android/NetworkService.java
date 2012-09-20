package com.floppyinfant.android;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class NetworkService extends Service {
	
	private IBinder mBinder = new ServiceBinder();
	/** Callback Handler */
	private Handler mHandler;
	
	private volatile boolean isBusy;
	
	/**
	 * Service Binder to communicate with the Service
	 * @author TM
	 *
	 */
	public class ServiceBinder extends Binder {
		
		public void setCallback(Handler handler) {
			mHandler = handler;
		}
		
		public void doInBackground() {
			if (isBusy) {
				return;
			}
			new Thread() {
				@Override
				public void run() {
					isBusy  = true;
					
					// do something
					long res = execute();
					
					// send result back to the starting Activity
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putLong("result", res);
					msg.setData(bundle);
					mHandler.sendMessage(msg);
					
					isBusy = false;
				}

			}.start();
		}
		
		public void send() {
			String url = "http://www.floppyinfant.com:80/skate";
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			
			
		}
		
		public void receive() {
			// TODO do something
			
		}
	} // end Binder
	

	/**
	 * called by a Thread
	 * @return result
	 */
	private long execute() {
		// TODO calculation of result
		
		return -777;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

}
