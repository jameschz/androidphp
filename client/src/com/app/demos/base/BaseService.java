package com.app.demos.base;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.app.demos.util.AppClient;
import com.app.demos.util.AppUtil;
import com.app.demos.util.HttpUtil;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;

public class BaseService extends Service {

	public static final String ACTION_START = ".ACTION_START";
	public static final String ACTION_STOP = ".ACTION_STOP";
	public static final String ACTION_PING = ".ACTION_PING";
	public static final String HTTP_TYPE = ".HTTP_TYPE";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	public void doTaskAsync (final int taskId, final String taskUrl) {
		SharedPreferences sp = AppUtil.getSharedPreferences(this);
		final int httpType = sp.getInt(HTTP_TYPE, 0);
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new Runnable(){
			@Override
			public void run() {
				try {
					AppClient client = new AppClient(taskUrl);
					if (httpType == HttpUtil.WAP_INT) {
						client.useWap();
					}
					String httpResult = client.get();
					onTaskComplete(taskId, AppUtil.getMessage(httpResult));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void doTaskAsync (final int taskId, final String taskUrl, final HashMap<String, String> taskArgs) {
		SharedPreferences sp = AppUtil.getSharedPreferences(this);
		final int httpType = sp.getInt(HTTP_TYPE, 0);
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new Runnable(){
			@Override
			public void run() {
				try {
					AppClient client = new AppClient(taskUrl);
					if (httpType == HttpUtil.WAP_INT) {
						client.useWap();
					}
					String httpResult = client.post(taskArgs);
					onTaskComplete(taskId, AppUtil.getMessage(httpResult));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void onTaskComplete (int taskId, BaseMessage message) {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// static functions
	
	public static void start(Context ctx, Class<? extends Service> sc) {
		// get some global data
		SharedPreferences sp = AppUtil.getSharedPreferences(ctx);
		Editor editor = sp.edit();
		editor.putInt(HTTP_TYPE, HttpUtil.getNetType(ctx));
		editor.commit();
		// start service
		String actionName = sc.getName() + ACTION_START;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.startService(i);
	}

	public static void stop(Context ctx, Class<? extends Service> sc) {
		String actionName = sc.getName() + ACTION_STOP;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.startService(i);
	}
	
	public static void ping(Context ctx, Class<? extends Service> sc) {
		String actionName = sc.getName() + ACTION_PING;
		Intent i = new Intent(ctx, sc);
		i.setAction(actionName);
		ctx.startService(i);
	}
}