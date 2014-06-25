package com.app.demos.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DBUtil {
	
	public static byte[] bitmap2byte (Bitmap bitmap) {
		int size = bitmap.getWidth() * bitmap.getHeight() * 4;
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);
		try {
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	public static Bitmap getBitmapByCursor (Cursor c, int index) {
		byte[] data = c.getBlob(index);
		try {
			return BitmapFactory.decodeByteArray(data, 0, data.length);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getTimeFromString (String timestamp) {
		try {
			DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
			String timeString = dateFormat.format(new Date(Long.parseLong(timestamp)));
			return timeString;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String getTimeByCursor (Cursor c, int index) {
		try {
			String timestamp = c.getString(index);
			return DBUtil.getTimeFromString(timestamp);
		} catch (Exception e) {
			return null;
		}
	}
}