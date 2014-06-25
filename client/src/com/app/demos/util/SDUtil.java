package com.app.demos.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

import com.app.demos.base.C;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class SDUtil {

	private static String TAG = SDUtil.class.getSimpleName();
	
	private static double MB = 1024;
	private static double FREE_SD_SPACE_NEEDED_TO_CACHE = 10;
	private static double IMAGE_EXPIRE_TIME = 10;

	public static Bitmap getImage(String fileName) {
		// check image file exists
		String realFileName = C.dir.faces + "/" + fileName;
		File file = new File(realFileName);
		if (!file.exists()) {
			return null;
		}
		// get original image
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(realFileName, options);
	}
	
	public static Bitmap getSample(String fileName) {
		// check image file exists
		String realFileName = C.dir.faces + "/" + fileName;
		File file = new File(realFileName);
		if (!file.exists()) {
			return null;
		}
		// get original size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(realFileName, options);
		int zoom = (int) (options.outHeight / (float) 50);
		if (zoom < 0) zoom = 1;
		// get resized image
		options.inSampleSize = zoom;
		options.inJustDecodeBounds = false;
		bitmap = BitmapFactory.decodeFile(realFileName, options);
		return bitmap;
	}
	
	public static void saveImage(Bitmap bitmap, String fileName) {
		if (bitmap == null) {
			Log.w(TAG, " trying to save null bitmap");
			return;
		}
		// 判断sdcard上的空间
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > getFreeSpace()) {
			Log.w(TAG, "Low free space onsd, do not cache");
			return;
		}
		// 不存在则创建目录
		File dir = new File(C.dir.faces);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 保存图片
		try {
			String realFileName = C.dir.faces + "/" + fileName;
			File file = new File(realFileName);
			file.createNewFile();
			OutputStream outStream = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
			outStream.flush();
			outStream.close();
			Log.i(TAG, "Image saved tosd");
		} catch (FileNotFoundException e) {
			Log.w(TAG, "FileNotFoundException");
		} catch (IOException e) {
			Log.w(TAG, "IOException");
		}
	}

	protected static void updateTime(String fileName) {
		File file = new File(fileName);
		long newModifiedTime = System.currentTimeMillis();
		file.setLastModified(newModifiedTime);
	}

	/**
	 * 计算sdcard上的剩余空间
	 * 
	 * @return
	 */
	public static int getFreeSpace() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	public static void removeExpiredCache(String dirPath, String filename) {
		File file = new File(dirPath, filename);
		if (System.currentTimeMillis() - file.lastModified() > IMAGE_EXPIRE_TIME) {
			Log.i(TAG, "Clear some expiredcache files ");
			file.delete();
		}
	}

	public static void removeCache(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		if (FREE_SD_SPACE_NEEDED_TO_CACHE > getFreeSpace()) {
			int removeFactor = (int) ((0.4 * files.length) + 1);
			Arrays.sort(files, new FileLastModifSort());
			Log.i(TAG, "Clear some expiredcache files ");
			for (int i = 0; i < removeFactor; i++) {
				files[i].delete();
			}

		}

	}

	private static class FileLastModifSort implements Comparator<File> {
		@Override
		public int compare(File arg0, File arg1) {
			if (arg0.lastModified() > arg1.lastModified()) {
				return 1;
			} else if (arg0.lastModified() == arg1.lastModified()) {
				return 0;
			} else {
				return -1;
			}
		}
	}
}