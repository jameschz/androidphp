package com.app.demos.base;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.NameValuePair;

import android.content.Context;

import com.app.demos.util.HttpUtil;
import com.app.demos.util.AppClient;

public class BaseTaskPool {
	
	// task thread pool
	static private ExecutorService taskPool;
	
	// for HttpUtil.getNetType
	private Context context;
	
	public BaseTaskPool (BaseUi ui) {
		this.context = ui.getContext();
		taskPool = Executors.newCachedThreadPool();
	}
	
	// http post task with params and files
	public void addTask (int taskId, String taskUrl, HashMap<String, String> taskArgs, List<NameValuePair> taskFiles, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(context, taskUrl, taskArgs, taskFiles, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	// http post task with params
	public void addTask (int taskId, String taskUrl, HashMap<String, String> taskArgs, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(context, taskUrl, taskArgs, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	// http post task without params
	public void addTask (int taskId, String taskUrl, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(context, taskUrl, null, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	// custom task
	public void addTask (int taskId, BaseTask baseTask, int delayTime) {
		baseTask.setId(taskId);
		try {
			taskPool.execute(new TaskThread(context, null, null, null, baseTask, delayTime));
		} catch (Exception e) {
			taskPool.shutdown();
		}
	}
	
	// task thread logic
	private class TaskThread implements Runnable {
		private Context context;
		private String taskUrl;
		private HashMap<String, String> taskArgs;
		private List<NameValuePair> taskFiles;
		private BaseTask baseTask;
		private int delayTime = 0;
		public TaskThread(Context context, String taskUrl, HashMap<String, String> taskArgs, List<NameValuePair> taskFiles, BaseTask baseTask, int delayTime) {
			this.context = context;
			this.taskUrl = taskUrl;
			this.taskArgs = taskArgs;
			this.taskFiles = taskFiles;
			this.baseTask = baseTask;
			this.delayTime = delayTime;
		}
		@Override
		public void run() {
			try {
				baseTask.onStart();
				String httpResult = null;
				// set delay time
				if (this.delayTime > 0) {
					Thread.sleep(this.delayTime);
				}
				try {
					// remote task
					if (this.taskUrl != null) {
						// init app client
						AppClient client = new AppClient(this.taskUrl);
						if (HttpUtil.WAP_INT == HttpUtil.getNetType(context)) {
							client.useWap();
						}
						// http get
						if (taskArgs == null) {
							httpResult = client.get();
						// http post
						} else {
							if (taskFiles != null) {
								httpResult = client.post(this.taskArgs, this.taskFiles);
							} else {
								httpResult = client.post(this.taskArgs);
							}
						}
					}
					// remote task
					if (httpResult != null) {
						baseTask.onComplete(httpResult);
					// local task
					} else {
						baseTask.onComplete();
					}
				} catch (Exception e) {
					e.printStackTrace(); // debug
					baseTask.onError(e.getMessage());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					baseTask.onStop();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}