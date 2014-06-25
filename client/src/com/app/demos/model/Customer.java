package com.app.demos.model;

import com.app.demos.base.BaseModel;

public class Customer extends BaseModel {
	
	// model columns
	public final static String COL_ID = "id";
	public final static String COL_SID = "sid";
	public final static String COL_NAME = "name";
	public final static String COL_PASS = "pass";
	public final static String COL_SIGN = "sign";
	public final static String COL_FACE = "face";
	public final static String COL_FACEURL = "faceurl";
	public final static String COL_BLOGCOUNT = "blogcount";
	public final static String COL_FANSCOUNT = "fanscount";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String sid;
	private String name;
	private String pass;
	private String sign;
	private String face;
	private String faceurl;
	private String blogcount;
	private String fanscount;
	private String uptime;
	
	// default is no login
	private boolean isLogin = false;
	
	// single instance for login
	static private Customer customer = null;
	
	static public Customer getInstance () {
		if (Customer.customer == null) {
			Customer.customer = new Customer();
		}
		return Customer.customer;
	}
	
	public Customer () {}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getSid () {
		return this.sid;
	}
	
	public void setSid (String sid) {
		this.sid = sid;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getPass () {
		return this.pass;
	}
	
	public void setPass (String pass) {
		this.pass = pass;
	}
	
	public String getSign () {
		return this.sign;
	}
	
	public void setSign (String sign) {
		this.sign = sign;
	}
	
	public String getFace () {
		return this.face;
	}
	
	public void setFace (String face) {
		this.face = face;
	}
	
	public String getFaceurl () {
		return this.faceurl;
	}
	
	public void setFaceurl (String faceurl) {
		this.faceurl = faceurl;
	}
	
	public String getUptime () {
		return this.uptime;
	}
	
	public void setUptime (String uptime) {
		this.uptime = uptime;
	}
	
	public String getBlogcount () {
		return this.blogcount;
	}
	
	public void setBlogcount (String blogcount) {
		this.blogcount = blogcount;
	}
	
	public String getFanscount () {
		return this.fanscount;
	}
	
	public void setFanscount (String fanscount) {
		this.fanscount = fanscount;
	}
	
	public Boolean getLogin () {
		return this.isLogin;
	}
	
	public void setLogin (boolean isLogin) {
		this.isLogin = isLogin;
	}
}