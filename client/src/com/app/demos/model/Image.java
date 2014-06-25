package com.app.demos.model;

import com.app.demos.base.BaseModel;

public class Image extends BaseModel {
	
	// model columns
	public final static String COL_ID = "id";
	public final static String COL_URL = "url";
	public final static String COL_TYPE = "type";
	
	private String id;
	private String url;
	private String type;
	
	public Image () {}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getUrl () {
		return this.url;
	}
	
	public void setUrl (String url) {
		this.url = url;
	}
	
	public String getType () {
		return this.type;
	}
	
	public void setType (String type) {
		this.type = type;
	}
}