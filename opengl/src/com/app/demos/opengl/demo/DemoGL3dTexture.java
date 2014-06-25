package com.app.demos.opengl.demo;

import com.app.demos.opengl.R;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;

public class DemoGL3dTexture extends Activity {

	private GLSurfaceView glSurfaceView;
	private Renderer renderer;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Resources res = this.getResources();
		Bitmap texture = BitmapFactory.decodeResource(res, R.drawable.skin);
		
		renderer = new CubeTextureRenderer(true, texture);
		glSurfaceView = new GLSurfaceView(this);
		glSurfaceView.setRenderer(renderer);
		setContentView(glSurfaceView);
	}
}