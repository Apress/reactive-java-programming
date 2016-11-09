package com.andreamaglie.rxjava.chapter5.services;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubService {
	@GET("users/{user}/repos")
    Observable<List<Repo>> listRepos(@Path("user") String user);
	
	@GET("users/{user}/gists")
    Observable<List<Gist>> listGists(@Path("user") String user);
}
