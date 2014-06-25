package com.app.demos.model;

import com.app.demos.base.BaseModel;

public class Comment extends BaseModel {
	
	// model columns
	public final static String COL_ID = "id";
	public final static String COL_CONTENT = "content";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String content;
	private String uptime;
	
	public Comment () {}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getContent () {
		return this.content;
	}
	
	public void setContent (String content) {
		this.content = content;
	}
	
	public String getUptime () {
		return this.uptime;
	}
	
	public void setUptime (String uptime) {
		this.uptime = uptime;
	}
}