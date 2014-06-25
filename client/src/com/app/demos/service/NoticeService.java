package com.app.demos.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.app.demos.ui.UiBlogs;
import com.app.demos.base.BaseMessage;
import com.app.demos.base.BaseService;
import com.app.demos.base.C;
import com.app.demos.model.Notice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.IBinder;

public class NoticeService extends BaseService {

	private static final int ID = 1000;
	private static final String NAME = NoticeService.class.getName();
	
	// Notification manager to displaying arrived push notifications 
	private NotificationManager	notiManager;
	
	// Thread Pool Executors
	private ExecutorService execService;
	
	// Loop getting notice
	private boolean runLoop = true;
	
	@Override
	public IBinder onBind(Intent intent) {
		return super.onBind(intent);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		notiManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		execService = Executors.newSingleThreadExecutor();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		if (intent.getAction().equals(NAME + BaseService.ACTION_START)) {
			startService();
		}
	}
	
	@Override
	public void onDestroy() {
		runLoop = false;
	}
	
	public void startService () {
		execService.execute(new Runnable(){
			@Override
			public void run() {
				while (runLoop) {
					try {
						// get notice
						doTaskAsync(C.task.notice, C.api.notice);
						// sleep 30 seconds
						Thread.sleep(30 * 1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@Override
	public void onTaskComplete (int taskId, BaseMessage message) {
		try {
			Notice notice = (Notice) message.getResult("Notice");
			showNotification(notice.getMessage());
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	private void showNotification(String text) {
		try {
			Notification n = new Notification();
			n.flags |= Notification.FLAG_SHOW_LIGHTS;
	      	n.flags |= Notification.FLAG_AUTO_CANCEL;
	        n.defaults = Notification.DEFAULT_ALL;
			n.icon = com.app.demos.R.drawable.icon;
			n.when = System.currentTimeMillis();
			// Simply open the parent activity
			PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, UiBlogs.class), 0);
			// Change the name of the notification here
			n.setLatestEventInfo(this, "demos Notice", text, pi);
			// show notification
			notiManager.notify(ID, n);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}