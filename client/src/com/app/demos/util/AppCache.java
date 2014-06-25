package com.app.demos.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

public class AppCache {
	
	// tag for log
	private static String TAG = AppCache.class.getSimpleName();
	
	public static Bitmap getCachedImage (Context ctx, String url) {
		String cacheKey = AppUtil.md5(url);
		Bitmap cachedImage = SDUtil.getImage(cacheKey);
		if (cachedImage != null) {
			Log.w(TAG, "get cached image");
			return cachedImage;
		} else {
			Bitmap newImage = IOUtil.getBitmapRemote(ctx, url);
			SDUtil.saveImage(newImage, cacheKey);
			return newImage;
		}
	}
	
	public static Bitmap getImage (String url) {
		String cacheKey = AppUtil.md5(url);
		return SDUtil.getImage(cacheKey);
	}
}