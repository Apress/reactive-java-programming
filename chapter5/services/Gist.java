package com.andreamaglie.rxjava.chapter5.services;

import com.google.gson.annotations.SerializedName;

public class Gist {

	@SerializedName("id")
	private int id;
	
	@SerializedName("html_url")
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
