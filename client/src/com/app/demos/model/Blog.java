package com.app.demos.model;

import com.app.demos.base.BaseModel;

public class Blog extends BaseModel {
	
	// model columns
	public final static String COL_ID = "id";
	public final static String COL_FACE = "face";
	public final static String COL_DESC = "desc";
	public final static String COL_TITLE = "title";
	public final static String COL_AUTHOR = "author";
	public final static String COL_CONTENT = "content";
	public final static String COL_COMMENT = "comment";
	public final static String COL_UPTIME = "uptime";
	
	private String id;
	private String face;
	private String desc;
	private String title;
	private String author;
	private String content;
	private String comment;
	private String uptime;
	
	public Blog () {}
	
	public String getId () {
		return this.id;
	}
	
	public void setId (String id) {
		this.id = id;
	}
	
	public String getFace () {
		return this.face;
	}
	
	public void setFace (String face) {
		this.face = face;
	}
	
	public String getDesc () {
		return this.desc;
	}
	
	public void setDesc (String desc) {
		this.desc = desc;
	}
	
	public String getTitle () {
		return this.title;
	}
	
	public void setTitle (String title) {
		this.title = title;
	}
	
	public String getAuthor () {
		return this.author;
	}
	
	public void setAuthor (String author) {
		this.author = author;
	}
	
	public String getContent () {
		return this.content;
	}
	
	public void setContent (String content) {
		this.content = content;
	}
	
	public String getComment () {
		return this.comment;
	}
	
	public void setComment (String comment) {
		this.comment = comment;
	}
	
	public String getUptime () {
		return this.uptime;
	}
	
	public void setUptime (String uptime) {
		this.uptime = uptime;
	}
}