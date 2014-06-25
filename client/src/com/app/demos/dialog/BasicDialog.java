package com.app.demos.dialog;

import com.app.demos.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class BasicDialog {

	private Dialog mDialog;
	private TextView mTextMessage;

	public BasicDialog(Context context, Bundle params) {
		mDialog = new Dialog(context, R.style.com_app_weibo_theme_dialog);
		mDialog.setContentView(R.layout.main_dialog);
		mDialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
		
		Window window = mDialog.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = 0;
		window.setAttributes(wl);
//		window.setGravity(Gravity.CENTER);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		window.setLayout(200, ViewGroup.LayoutParams.WRAP_CONTENT);
		
		mTextMessage = (TextView) mDialog.findViewById(R.id.cs_main_dialog_text);
		mTextMessage.setTextColor(context.getResources().getColor(R.color.gray));
		mTextMessage.setText(params.getString("text"));
	}

	public void show() {
		mDialog.show();
	}

}
