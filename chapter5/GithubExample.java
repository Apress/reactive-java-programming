package com.andreamaglie.rxjava.chapter5;

import java.util.List;

import com.andreamaglie.rxjava.chapter5.services.GitHubService;
import com.andreamaglie.rxjava.chapter5.services.Repo;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GithubExample {

	public static void main(String... args) throws InterruptedException {
		listReposWithMaxIssues("octocat", 2);		
	}
	
	public static void listRepos(String user) {
		Retrofit retrofit = new Retrofit.Builder()
			    .baseUrl("https://api.github.com/")
			    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();

		GitHubService service = retrofit.create(GitHubService.class);
		
		service.listRepos(user)
			.subscribe(new Subscriber<List<Repo>>() {

				@Override
				public void onCompleted() {
					System.out.println("sequence completed");
				}

				@Override
				public void onError(Throwable e) {
					e.printStackTrace();
				}

				@Override
				public void onNext(List<Repo> repos) {
					for (Repo repo : repos) {
						System.out.println("Repo: " + repo.getName());
					}
				}
			});
	}
	
	public static void listReposWithMaxIssues(String user, final int maxOpenIssues) {
		Retrofit retrofit = new Retrofit.Builder()
			    .baseUrl("https://api.github.com/")
			    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();

		GitHubService service = retrofit.create(GitHubService.class);
		
		service.listRepos(user)
			.flatMap(new Func1<List<Repo>, Observable<Repo>>() {

				@Override
				public Observable<Repo> call(List<Repo> repos) {
					return Observable.from(repos);
				}
				
			})
			.filter(new Func1<Repo, Boolean>() {

				@Override
				public Boolean call(Repo repo) {
					return repo.getOpenIssueCount() <= maxOpenIssues;
				}
				
			})
			.subscribe(new Subscriber<Repo>() {

				@Override
				public void onCompleted() {
					System.out.println("sequence completed");
				}

				@Override
				public void onError(Throwable e) {
					e.printStackTrace();
				}

				@Override
				public void onNext(Repo repo) {
					System.out.println("Repo: " + repo.getName());
				}
			});
	}
}
