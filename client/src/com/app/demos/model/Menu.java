package com.app.demos.model;

import com.app.demos.base.BaseModel;

public class Menu extends BaseModel{
	
	// model columns
	public final static String COL_NAME = "name";
	public final static String COL_CLASS = "clazz";
	
	private String name;
	private Class<?> clazz;
	
	public Menu (String name, Class<?> clazz) {
		this.name = name;
		this.clazz = clazz;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public Class<?> getClazz () {
		return this.clazz;
	}
	
	public void setClazz (Class<?> clazz) {
		this.clazz = clazz;
	}
}