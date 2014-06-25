package com.app.demos.opengl;

import com.app.demos.opengl.R;
import com.app.demos.opengl.demo.DemoGL2d;
import com.app.demos.opengl.demo.DemoGL3d;
import com.app.demos.opengl.demo.DemoGL3dTexture;
import com.app.demos.opengl.util.GLImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OpenglActivity extends Activity {

	Button btnDemo2d = null;
	Button btnDemo3d = null;
	Button btnDemo3dTexture = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		GLImage.load(this.getResources());
		
		btnDemo2d = (Button) this.findViewById(R.id.btn_demo_2d);
		btnDemo2d.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setClass(OpenglActivity.this, DemoGL2d.class);
				startActivity(intent);
			}
		});

		btnDemo3d = (Button) this.findViewById(R.id.btn_demo_3d);
		btnDemo3d.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setClass(OpenglActivity.this, DemoGL3d.class);
				startActivity(intent);
			}
		});
		
		btnDemo3dTexture = (Button) this.findViewById(R.id.btn_demo_3d_texture);
		btnDemo3dTexture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setClass(OpenglActivity.this, DemoGL3dTexture.class);
				startActivity(intent);
			}
		});
	}
}