package com.app.demos.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.app.demos.base.C;

import android.util.Log;

@SuppressWarnings("rawtypes")
public class AppClient {
	
	// compress strategy
	final private static int CS_NONE = 0;
	final private static int CS_GZIP = 1;
	
	// logic variables
	private String apiUrl;
	private HttpParams httpParams;
	private HttpClient httpClient;
	private int timeoutConnection = 10000;
	private int timeoutSocket = 10000;
	private int compress = CS_NONE;
	
	// charset default utf8
	private String charset = HTTP.UTF_8;
	
	public AppClient (String url) {
		initClient(url);
	}
	
	public AppClient (String url, String charset, int compress) {
		initClient(url);
		this.charset = charset; // set charset
		this.compress = compress; // set strategy
	}
	
	private void initClient (String url) {
		// initialize API URL
		this.apiUrl = C.api.base + url;
		String apiSid = AppUtil.getSessionId();
		if (apiSid != null && apiSid.length() > 0) {
			this.apiUrl += "?sid=" + apiSid;
		}
		// set client timeout
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
		HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
		// init client
		httpClient = new DefaultHttpClient(httpParams);
	}
	
	public void useWap () {
		HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}
	
	public String get () throws Exception {
		try {
			HttpGet httpGet = headerFilter(new HttpGet(this.apiUrl));
			Log.w("AppClient.get.url", this.apiUrl);
			// send get request
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String httpResult = resultFilter(httpResponse.getEntity());
				Log.w("AppClient.get.result", httpResult);
				return httpResult;
			} else {
				return null;
			}
		} catch (ConnectTimeoutException e) {
			throw new Exception(C.err.network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String post (HashMap urlParams) throws Exception {
		try {
			HttpPost httpPost = headerFilter(new HttpPost(this.apiUrl));
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			// get post parameters
			Iterator it = urlParams.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				postParams.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
			}
			// set data charset
			if (this.charset != null) {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams, this.charset));
			} else {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
			}
			Log.w("AppClient.post.url", this.apiUrl);
			Log.w("AppClient.post.data", postParams.toString());
			// send post request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String httpResult = resultFilter(httpResponse.getEntity());
				Log.w("AppClient.post.result", httpResult);
				return httpResult;
			} else {
				return null;
			}
		} catch (ConnectTimeoutException e) {
			throw new Exception(C.err.network);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private HttpGet headerFilter (HttpGet httpGet) {
		switch (this.compress) {
			case CS_GZIP:
				httpGet.addHeader("Accept-Encoding", "gzip");
				break;
			default :
				break;
		}
		return httpGet;
	}
	
	private HttpPost headerFilter (HttpPost httpPost) {
		switch (this.compress) {
			case CS_GZIP:
				httpPost.addHeader("Accept-Encoding", "gzip");
				break;
			default :
				break;
		}
		return httpPost;
	}
	
	private String resultFilter(HttpEntity entity){
		String result = null;
		try {
			switch (this.compress) {
				case CS_GZIP:
					result = AppUtil.gzipToString(entity);
					break;
				default :
					result = EntityUtils.toString(entity);
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}