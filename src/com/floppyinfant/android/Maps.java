package com.floppyinfant.android;

import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

/**
 * Google Maps API
 * 
 * Create layout com.google.android.maps.MapView
 * with a Google Maps API Key from 
 * {@link https://developers.google.com/maps/documentation/android/mapkey?hl=de-DE}
 * 
 * Build against Google API.
 * 
 * Set Permissions in AndroidManifest.xml<br>
 * android.permission.INTERNET<br>
 * android.permission.ACCESS_COARSE_LOCATION<br>
 * android.permission.ACCESS_FINE_LOCATION<br>
 * android.permission.ACCESS_MOCK_LOCATION for Emulator faked location data<br>
 * <application><uses-library android:name="com.google.android.maps" /></application>
 * 
 * @see {@link http://developer.android.com/guide/topics/location/index.html}
 * @see {@link http://developer.android.com/reference/android/location/LocationManager.html}
 * @see {@link file:///C:/android-sdk/add-ons/addon-google_apis-google_inc_-8/docs/reference/index.html}
 * @author TM
 *
 */
public class Maps extends MapActivity {
	
	MapView mMapView;
	MapController mMapController;
	GeoPoint mGeoPoint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maps);
		
		mMapView = (MapView) findViewById(R.id.mapview);
		mMapView.setBuiltInZoomControls(true);
		mMapView.setSatellite(true);
		
		MyLocationOverlay mlo = new MyLocationOverlay(this, mMapView);
		//mGeoPoint = new GeoPoint(mlo.getMyLocation().getLatitudeE6(), mlo.getMyLocation().getLongitudeE6());
		mGeoPoint = new GeoPoint(-7000000, 50000000);
		
		mMapController = mMapView.getController();
		mMapController.setCenter(mGeoPoint);
		mMapController.setZoom(5); // max. 23
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
}
