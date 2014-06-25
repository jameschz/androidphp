package com.app.demos.demo;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.demos.R;
import com.app.demos.base.BaseUiWeb;
import com.app.demos.base.C;

public class DemoMap extends BaseUiWeb {
	
	private WebView mWebViewMap;
	
	@Override
	public void onStart() {
		super.onStart();
		
		setContentView(R.layout.demo_map);
		
		mWebViewMap = (WebView) findViewById(R.id.web_map);
		mWebViewMap.getSettings().setJavaScriptEnabled(true);
		mWebViewMap.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url){
				mWebViewMap.loadUrl("javascript:centerAt(39.907325,116.391455);");
			}
		});
		mWebViewMap.loadUrl(C.web.gomap);
		
		this.setWebView(mWebViewMap);
		this.startWebView();
	}
}