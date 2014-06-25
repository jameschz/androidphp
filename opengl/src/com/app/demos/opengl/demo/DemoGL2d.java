package com.app.demos.opengl.demo;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public class DemoGL2d extends Activity {

	private GLSurfaceView glSurfaceView;
	private Renderer renderer;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		renderer = new DemoGL2dRenderer();
		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setRenderer(renderer);
		setContentView(glSurfaceView);
	}
}