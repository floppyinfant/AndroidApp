package com.floppyinfant.android;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {
	
	private SensorManager mSensorManager;
	private Sensor mSensor;
	
	private HashMap<Integer, String> mSensorTypes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.sensor);
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		// Initialize the map of sensor type values and names
		mSensorTypes = new HashMap<Integer, String>();
		mSensorTypes.put(Sensor.TYPE_ACCELEROMETER, "TYPE_ACCELEROMETER"); // 1
		mSensorTypes.put(Sensor.TYPE_MAGNETIC_FIELD, "TYPE_MAGNETIC_FIELD"); // 2
		mSensorTypes.put(Sensor.TYPE_ORIENTATION, "TYPE_ORIENTATION"); // 3
		mSensorTypes.put(Sensor.TYPE_GYROSCOPE, "TYPE_GYROSCOPE"); // 4
		mSensorTypes.put(Sensor.TYPE_LIGHT, "TYPE_LIGHT"); // 5
		mSensorTypes.put(Sensor.TYPE_PRESSURE, "TYPE_PRESSURE"); // 6
		mSensorTypes.put(Sensor.TYPE_TEMPERATURE, "TYPE_TEMPERATURE"); // 7
		mSensorTypes.put(Sensor.TYPE_PROXIMITY, "TYPE_PROXIMITY"); // 8
		mSensorTypes.put(Sensor.TYPE_GRAVITY, "TYPE_GRAVITY"); // 9
		mSensorTypes.put(Sensor.TYPE_LINEAR_ACCELERATION, "TYPE_LINEAR_ACCELERATION"); // 10
		mSensorTypes.put(Sensor.TYPE_ROTATION_VECTOR, "TYPE_ROTATION_VECTOR"); // 11
		mSensorTypes.put(Sensor.TYPE_RELATIVE_HUMIDITY, "TYPE_RELATIVE_HUMIDITY"); // 12
		mSensorTypes.put(Sensor.TYPE_AMBIENT_TEMPERATURE, "TYPE_AMBIENT_TEMPERATURE"); // 13
		
		List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		StringBuilder sb = new StringBuilder();
		sb.append("Available sensors on this device:\n");
		int i = 1;
		for (Sensor sensor : sensors) {
			sb.append(i + ".)\n");
			sb.append("Name: \t" + sensor.getName() + "\n");
			sb.append("Type: \t" + mSensorTypes.get(sensor.getType()) + "\n");
			sb.append("Max. Range: \t" + sensor.getMaximumRange() + "\n");
			// append more parameters of sensor
			i++;
		}
		String msg = sb.toString();
		TextView text = (TextView)findViewById(R.id.sensor_text);
		text.setText(msg);
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
		// register more Sensors
	}

	@Override
	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// not used
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			// TODO
			break;
		default:
			break;
		}

	}
}
