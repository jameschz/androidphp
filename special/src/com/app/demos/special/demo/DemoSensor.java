package com.app.demos.special.demo;

import com.app.demos.special.R;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class DemoSensor extends Activity {
	
	private Sensor sensor;
	private SensorManager sm;
	private TextView textResult;
	
	private float x, y, z;
	private String result = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_sensor);
		
		textResult = (TextView) this.findViewById(R.id.demo_sensor_text_result);
		
		try {
			sm = (SensorManager) this.getSystemService(SENSOR_SERVICE);
			sensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			SensorEventListener sel = new SensorEventListener() {
				@Override
				public void onSensorChanged(SensorEvent event) {
					x = event.values[SensorManager.DATA_X];
					y = event.values[SensorManager.DATA_Y];
					z = event.values[SensorManager.DATA_Z];
					result = "x : " + x + " , y : " + y + " , z : " + z;
					textResult.setText(result);
				}
				@Override
				public void onAccuracyChanged(Sensor sensor, int accuracy) {
					// TODO Auto-generated method stub
				}
			};
			sm.registerListener(sel, sensor, SensorManager.SENSOR_DELAY_GAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}