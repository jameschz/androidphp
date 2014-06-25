package com.app.demos.special;

import com.app.demos.special.R;
import com.app.demos.special.demo.DemoCamera;
import com.app.demos.special.demo.DemoLbs;
import com.app.demos.special.demo.DemoMap;
import com.app.demos.special.demo.DemoMedia;
import com.app.demos.special.demo.DemoSensor;
import com.app.demos.special.demo.DemoVoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SpecialActivity extends Activity {

	private Button btnDemoMap;
	private Button btnDemoLbs;
	private Button btnDemoSensor;
	private Button btnDemoCamera;
	private Button btnDemoMedia;
	private Button btnDemoVoice;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		btnDemoMap = (Button) this.findViewById(R.id.btn_demo_map);
		btnDemoMap.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoMap.class);
		        startActivity(intent);
			}
		});
		
		btnDemoLbs = (Button) this.findViewById(R.id.btn_demo_lbs);
		btnDemoLbs.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoLbs.class);
		        startActivity(intent);
			}
		});
		
		btnDemoSensor = (Button) this.findViewById(R.id.btn_demo_sensor);
		btnDemoSensor.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoSensor.class);
		        startActivity(intent);
			}
		});
		
		btnDemoCamera = (Button) this.findViewById(R.id.btn_demo_camera);
		btnDemoCamera.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoCamera.class);
		        startActivity(intent);
			}
		});
		
		btnDemoMedia = (Button) this.findViewById(R.id.btn_demo_media);
		btnDemoMedia.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoMedia.class);
		        startActivity(intent);
			}
		});
		
		btnDemoVoice = (Button) this.findViewById(R.id.btn_demo_voice);
		btnDemoVoice.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
		        Intent intent = new Intent(Intent.ACTION_VIEW);
		        intent.setClass(SpecialActivity.this, DemoVoice.class);
		        startActivity(intent);
			}
		});
	}
}