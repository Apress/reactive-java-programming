package com.andreamaglie.rxjava.chapter5.services;

import com.google.gson.annotations.SerializedName;

/**
 * Model used to map the result json from GitHub API.
 * For semplicity only some fileds are mapped.
 * You can see a full json example:
 * <ul>
 *   <li>into your browser, opening this link: https://api.github.com/users/octocat/repos</li>
 *   <li>into a shell, running the command: curl https://api.github.com/users/octocat/repos</li>
 * </ul>
 */
public class Repo {

	@SerializedName("id")
	private int id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("url")
	private String url;
	
	@SerializedName("watchers_count")
	private int watchersCount;
	
	@SerializedName("open_issues_count")
	private int openIssueCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWatchersCount() {
		return watchersCount;
	}

	public void setWatchersCount(int watchersCount) {
		this.watchersCount = watchersCount;
	}

	public int getOpenIssueCount() {
		return openIssueCount;
	}

	public void setOpenIssueCount(int openIssueCount) {
		this.openIssueCount = openIssueCount;
	}
	
	
}
