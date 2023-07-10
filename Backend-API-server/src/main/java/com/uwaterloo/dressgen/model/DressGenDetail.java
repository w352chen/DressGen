package com.uwaterloo.dressgen.model;

import java.io.Serializable;

public class DressGenDetail implements Serializable {
	private int code = 0;
	private String name = "";
	private String picUrl = "";
	
	public DressGenDetail() {}

	public DressGenDetail(int code, String name, String picUrl) {
		super();
		this.code = code;
		this.name = name;
		this.picUrl = picUrl;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public String toString() {
		return "DressGenDetail [code=" + code + ", name=" + name + ", picUrl=" + picUrl + "]";
	}
	
	
}
