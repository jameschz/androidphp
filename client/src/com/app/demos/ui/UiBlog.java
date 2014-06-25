package com.app.demos.ui;

import java.util.ArrayList;
import java.util.HashMap;

import com.app.demos.R;
import com.app.demos.base.BaseUi;
import com.app.demos.base.BaseUiAuth;
import com.app.demos.base.BaseHandler;
import com.app.demos.base.BaseMessage;
import com.app.demos.base.BaseTask;
import com.app.demos.base.C;
import com.app.demos.list.ExpandList;
import com.app.demos.model.Blog;
import com.app.demos.model.Comment;
import com.app.demos.model.Customer;
import com.app.demos.util.AppCache;
import com.app.demos.util.AppUtil;
import com.app.demos.util.UIUtil;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UiBlog extends BaseUiAuth {
	
	private String blogId = null;
	private String customerId = null;
	private Button addfansBtn = null;
	private Button commentBtn = null;
	private ImageView faceImage = null;
	private String faceImageUrl = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_blog);
		
		// set handler
		this.setHandler(new BlogHandler(this));
		
		// get params
		Bundle params = this.getIntent().getExtras();
		blogId = params.getString("blogId");
		
		// do add fans
		addfansBtn = (Button) this.findViewById(R.id.app_blog_btn_addfans);
		addfansBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// prepare blog data
				HashMap<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("customerId", customerId);
				doTaskAsync(C.task.fansAdd, C.api.fansAdd, urlParams);
			}
		});
		
		// do add comment
		commentBtn = (Button) this.findViewById(R.id.app_blog_btn_comment);
		commentBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle data = new Bundle();
				data.putInt("action", C.action.edittext.COMMENT);
				data.putString("blogId", blogId);
				doEditText(data);
			}
		});
		
		// prepare blog data
		HashMap<String, String> blogParams = new HashMap<String, String>();
		blogParams.put("blogId", blogId);
		this.doTaskAsync(C.task.blogView, C.api.blogView, blogParams);
	}
	
	@Override
	public void onStart () {
		super.onStart();
		
		// prepare comment data
		HashMap<String, String> commentParams = new HashMap<String, String>();
		commentParams.put("blogId", blogId);
		commentParams.put("pageId", "0");
		this.doTaskAsync(C.task.commentList, C.api.commentList, commentParams);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// async task callback methods
	
	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		super.onTaskComplete(taskId, message);
		
		switch (taskId) {
			case C.task.blogView:
				try {
					Blog blog = (Blog) message.getResult("Blog");
					TextView textUptime = (TextView) this.findViewById(R.id.app_blog_text_uptime);
					TextView textContent = (TextView) this.findViewById(R.id.app_blog_text_content);
					textUptime.setText(blog.getUptime());
					textContent.setText(blog.getContent());
					Customer customer = (Customer) message.getResult("Customer");
					TextView textCustomerName = (TextView) this.findViewById(R.id.app_blog_text_customer_name);
					TextView testCustomerInfo = (TextView) this.findViewById(R.id.app_blog_text_customer_info);
					textCustomerName.setText(customer.getName());
					testCustomerInfo.setText(UIUtil.getCustomerInfo(this, customer));
					// set customer id
					customerId = customer.getId();
					// load face image async
					faceImage = (ImageView) this.findViewById(R.id.app_blog_image_face);
					faceImageUrl = customer.getFaceurl();
					loadImage(faceImageUrl);
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
			case C.task.commentList:
				try {
					@SuppressWarnings("unchecked")
					ArrayList<Comment> commentList = (ArrayList<Comment>) message.getResultList("Comment");
					String[] from = {
						Comment.COL_CONTENT,
						Comment.COL_UPTIME
					};
					int[] to = {
						R.id.tpl_list_comment_content,
						R.id.tpl_list_comment_uptime,
					};
					ExpandList el = new ExpandList(this, AppUtil.dataToList(commentList, from), R.layout.tpl_list_comment, from, to);
					LinearLayout layout = (LinearLayout) this.findViewById(R.id.app_blog_list_comment);
					layout.removeAllViews(); // clean first
					el.render(layout);
				} catch (Exception e) {
					e.printStackTrace();
					toast(e.getMessage());
				}
				break;
			case C.task.fansAdd:
				if (message.getCode().equals("10000")) {
					toast("Add fans ok");
					// refresh customer data
					HashMap<String, String> cvParams = new HashMap<String, String>();
					cvParams.put("customerId", customerId);
					this.doTaskAsync(C.task.customerView, C.api.customerView, cvParams);
				} else {
					toast("Add fans fail");
				}
				break;
			case C.task.customerView:
				try {
					// update customer info
					final Customer customer = (Customer) message.getResult("Customer");
					TextView textInfo = (TextView) this.findViewById(R.id.app_blog_text_customer_info);
					textInfo.setText(UIUtil.getCustomerInfo(this, customer));
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
			doFinish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// inner classes
	
	private class BlogHandler extends BaseHandler {
		public BlogHandler(BaseUi ui) {
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