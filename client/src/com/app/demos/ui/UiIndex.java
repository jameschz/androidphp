package com.app.demos.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.demos.R;
import com.app.demos.base.BaseHandler;
import com.app.demos.base.BaseMessage;
import com.app.demos.base.BaseTask;
import com.app.demos.base.BaseUi;
import com.app.demos.base.BaseUiAuth;
import com.app.demos.base.C;
import com.app.demos.list.BlogList;
import com.app.demos.model.Blog;
import com.app.demos.sqlite.BlogSqlite;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class UiIndex extends BaseUiAuth {

	private ListView blogListView;
	private BlogList blogListAdapter;
	private BlogSqlite blogSqlite;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);
		
		// set handler
		this.setHandler(new IndexHandler(this));
		
		// tab button
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_1);
		ib.setImageResource(R.drawable.tab_blog_2);
		
		// init sqlite
		blogSqlite = new BlogSqlite(this);
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		// show all blog list
		HashMap<String, String> blogParams = new HashMap<String, String>();
		blogParams.put("typeId", "0");
		blogParams.put("pageId", "0");
		this.doTaskAsync(C.task.blogList, C.api.blogList, blogParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);

		switch (taskId) {
			case C.task.blogList:
				try {
					@SuppressWarnings("unchecked")
					final ArrayList<Blog> blogList = (ArrayList<Blog>) message.getResultList("Blog");
					// load face image
					for (Blog blog : blogList) {
						loadImage(blog.getFace());
						blogSqlite.updateBlog(blog);
					}
					// show text
					blogListView = (ListView) this.findViewById(R.id.app_index_list_view);
					blogListAdapter = new BlogList(this, blogList);
					blogListView.setAdapter(blogListAdapter);
					blogListView.setOnItemClickListener(new OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
							Bundle params = new Bundle();
							params.putString("blogId", blogList.get(pos).getId());
							overlay(UiBlog.class, params);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
		}
	}
	
	@Override
	public void onNetworkError (int taskId) {
		super.onNetworkError(taskId);
		toast(C.err.network);
		switch (taskId) {
			case C.task.blogList:
				try {
					final ArrayList<Blog> blogList = blogSqlite.getAllBlogs();
					// load face image
					for (Blog blog : blogList) {
						loadImage(blog.getFace());
						blogSqlite.updateBlog(blog);
					}
					// show text
					blogListView = (ListView) this.findViewById(R.id.app_index_list_view);
					blogListAdapter = new BlogList(this, blogList);
					blogListView.setAdapter(blogListAdapter);
					blogListView.setOnItemClickListener(new OnItemClickListener(){
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
							Bundle params = new Bundle();
							params.putString("blogId", blogList.get(pos).getId());
							overlay(UiBlog.class, params);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// inner classes
	
	private class IndexHandler extends BaseHandler {
		public IndexHandler(BaseUi ui) {
			super(ui);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
					case BaseTask.LOAD_IMAGE:
						blogListAdapter.notifyDataSetChanged();
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ui.toast(e.getMessage());
			}
		}
	}
}