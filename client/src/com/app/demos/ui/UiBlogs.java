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
import com.app.demos.list.ExpandList;
import com.app.demos.model.Blog;
import com.app.demos.model.Customer;
import com.app.demos.util.AppCache;
import com.app.demos.util.AppUtil;
import com.app.demos.util.UIUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UiBlogs extends BaseUiAuth {
	
//	private ListView blogListView;
	private ImageView faceImage;
	private String faceImageUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_blogs);
		
		// set handler
		this.setHandler(new BlogsHandler(this));
		
		// tab button
		ImageButton ib = (ImageButton) this.findViewById(R.id.main_tab_2);
		ib.setImageResource(R.drawable.tab_heart_2);		
	}
	
	@Override
	public void onStart () {
		super.onStart();
		
		// prepare customer data
		HashMap<String, String> cvParams = new HashMap<String, String>();
		cvParams.put("customerId", customer.getId());
		this.doTaskAsync(C.task.customerView, C.api.customerView, cvParams);
		
		// prepare blog data
		HashMap<String, String> blogParams = new HashMap<String, String>();
		blogParams.put("typeId", "1");
		blogParams.put("pageId", "0");
		this.doTaskAsync(C.task.blogList, C.api.blogList, blogParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	@SuppressWarnings("unchecked")
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		
		switch (taskId) {
			case C.task.customerView:
				try {
					final Customer customer = (Customer) message.getResult("Customer");
					TextView textName = (TextView) this.findViewById(R.id.app_blogs_text_customer_name);
					TextView textInfo = (TextView) this.findViewById(R.id.app_blogs_text_customer_info);
					textName.setText(customer.getSign());
					textInfo.setText(UIUtil.getCustomerInfo(this, customer));
					// load face image async
					faceImage = (ImageView) this.findViewById(R.id.app_blogs_image_face);
					faceImageUrl = customer.getFaceurl();
					loadImage(faceImageUrl);
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
			case C.task.blogList:
				try {
					final ArrayList<Blog> blogList = (ArrayList<Blog>) message.getResultList("Blog");
					String[] from = {
						Blog.COL_CONTENT,
						Blog.COL_UPTIME,
						Blog.COL_COMMENT
					};
					int[] to = {
						R.id.tpl_list_blog_text_content,
						R.id.tpl_list_blog_text_uptime,
						R.id.tpl_list_blog_text_comment
					};
					// can not use listview under scrollview
//					blogListView = (ListView) this.findViewById(R.id.app_blogs_list_view);
//					blogListView.setAdapter(new SimpleList(this, AppUtil.dataToList(blogList, from), R.layout.tpl_list_blogs, from, to));
//					blogListView.setOnItemClickListener(new OnItemClickListener(){
//						@Override
//						public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
//							Bundle params = new Bundle();
//							params.putString("blogId", blogList.get(pos).getId());
//							overlay(AppBlog.class, params);
//						}
//					});
					// use expandlist to do this
					ExpandList el = new ExpandList(this, AppUtil.dataToList(blogList, from), R.layout.tpl_list_blogs, from, to);
					LinearLayout layout = (LinearLayout) this.findViewById(R.id.app_blogs_list_view);
					layout.removeAllViews(); // clean first
					el.setDivider(R.color.divider3);
					el.setOnItemClickListener(new ExpandList.OnItemClickListener() {
						@Override
						public void onItemClick(View view, int pos) {
							Bundle params = new Bundle();
							params.putString("blogId", blogList.get(pos).getId());
							overlay(UiBlog.class, params);
						}
					});
					el.render(layout);
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
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// other methods
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			this.forward(UiIndex.class);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// inner classes
	
	private class BlogsHandler extends BaseHandler {
		public BlogsHandler(BaseUi ui) {
			super(ui);
		}
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				switch (msg.what) {
					case BaseTask.LOAD_IMAGE:
						Bitmap face = AppCache.getImage(faceImageUrl);
						faceImage.setImageBitmap(face);
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				ui.toast(e.getMessage());
			}
		}
	}
}