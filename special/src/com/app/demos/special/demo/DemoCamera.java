package com.app.demos.special.demo;

import java.io.File;
import java.io.FileOutputStream;

import com.app.demos.special.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class DemoCamera extends Activity {
	
	Camera camera;
	SurfaceView viewCamera;
	Button btnTakePhoto;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Window window = this.getWindow();
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo_camera);
		
		viewCamera = (SurfaceView) this.findViewById(R.id.view_camera);
//		viewCamera.getHolder().setFixedSize(800, 480);
		viewCamera.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		viewCamera.getHolder().addCallback(new CameraSurfaceCallback());
		
		btnTakePhoto = (Button) this.findViewById(R.id.btn_take_photo);
		btnTakePhoto.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				camera.takePicture(null, null, new TakePhotoCallback());
			}
		});
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (camera != null) {
			if (event.getRepeatCount() == 0) {
				switch (keyCode) {
					case KeyEvent.KEYCODE_DPAD_CENTER:
						camera.takePicture(null, null, new TakePhotoCallback());
						break;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private final class CameraSurfaceCallback implements SurfaceHolder.Callback {
		
		private boolean isPreview;
		
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			try {
				camera = Camera.open();
				WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
				Display display = wm.getDefaultDisplay();
				int displayWidth = display.getWidth();
				int displayHeight = display.getHeight();
				
				Camera.Parameters params = camera.getParameters();
				params.setPreviewSize(displayWidth, displayHeight);
				params.setPreviewFrameRate(3);
				params.setPictureFormat(PixelFormat.JPEG);
				params.setJpegQuality(80);
				params.setPictureSize(displayWidth, displayHeight);
				
				camera.setParameters(params);
				camera.setPreviewDisplay(viewCamera.getHolder());
				camera.startPreview();
				
				isPreview = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// TODO Auto-generated method stub
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (camera != null) {
				if (isPreview) {
					camera.stopPreview();
					camera.release();
					camera = null;
				}
			}
		}
	}
	
	private final class TakePhotoCallback implements PictureCallback {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				// stop preview
				camera.stopPreview();
				// save photo image
				String pictureName = "demo_camera_" + System.currentTimeMillis() + ".jpg";
				Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
				File file = new File(Environment.getExternalStorageDirectory(), pictureName);
				FileOutputStream outputStream = new FileOutputStream(file);
				bitmap.compress(CompressFormat.JPEG, 100, outputStream);
				outputStream.close();
				// start preview
				camera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}