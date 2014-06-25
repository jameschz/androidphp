package com.app.demos.demo;

import android.util.Log;
import android.webkit.WebView;

import com.app.demos.R;
import com.app.demos.base.BaseUiWeb;
import com.app.demos.base.C;

public class DemoWeb extends BaseUiWeb {
	
	private WebView mWebView;
	
	@Override
	public void onStart() {
		super.onStart();
		
		// start loading webview
		setContentView(R.layout.demo_web);
		mWebView = (WebView) findViewById(R.id.web_form);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.loadUrl(C.web.index);
		
		// add js interface
		mWebView.addJavascriptInterface(new DemoJs(), "demo");
		
		this.setWebView(mWebView);
		this.startWebView();
	}
	
	protected class DemoJs {
		public void testCallBack(String testParam) {
			Log.w("DemoJs", testParam);
		}
	}
}