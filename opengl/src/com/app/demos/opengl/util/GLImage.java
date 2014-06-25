package com.app.demos.opengl.util;

import com.app.demos.opengl.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GLImage  
{     
	public static Bitmap mImageSkin;
	public static void load(Resources resources)  
	{
		mImageSkin = BitmapFactory.decodeResource(resources, R.drawable.skin);  
	}  
}  