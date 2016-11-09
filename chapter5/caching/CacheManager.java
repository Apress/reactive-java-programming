package com.andreamaglie.rxjava.chapter5.caching;

import rx.Observable;

abstract class CacheManager<T> {

	private T mMemoryCache;

	public Observable<T> getData() {
		return Observable.concat(fromMemory(), fromDisk(), fromNetwork())
				.first(response -> isValid(response))
				.onErrorResumeNext(t -> fallbackToDiskCache());
	}


	protected Observable<T> fromNetwork() {
		return doNetworkCall()
				.doOnNext(response -> {
					cacheInMemory(response);
					cacheOnDisk(response);
				})
				.compose(logSource("network"));
	}

	private Observable<T> fallbackToDiskCache() {
		System.out.println("fallback to disk cache");
		return fromDisk();
	}

	protected void cacheOnDisk(T response) {
		System.out.println("saving data on disk: " + response);
		persistOnDisk(response);
	}

	protected Observable<T> fromDisk() {
		Observable<T> observable = Observable.create(subscriber -> {
			T response = readFromDisk();
			if (response != null) {
				subscriber.onNext(response);
				cacheInMemory(response);
			}
			subscriber.onCompleted();
		});

		return observable.compose(logSource("disk"));
	}


	protected void cacheInMemory(T response) {
		mMemoryCache = response;
	}


	protected Observable<T> fromMemory() {
		Observable<T> observable = Observable.create(subscriber -> {
			subscriber.onNext(mMemoryCache);
			subscriber.onCompleted();
		});

		return observable.compose(logSource("memory"));
	}

	public void deleteAll() {
		mMemoryCache = null;
		deleteFromDisk();
	}

	private Observable.Transformer<T, T> logSource(final String source) {
		return dataObservable -> dataObservable.doOnNext(data -> {
			if (data == null) {
				System.out.println(source + " does not have any data.");
			} else if (!isValid(data)) {
				System.out.println(source + " has stale data.");
			} else {
				System.out.println(source + " has the data you are looking for!");
			}
		});
	}

	/**
	 * Use this method to check if data is valid or should be discarded
	 * @param data
	 * @return true if data is valid
	 */
	abstract boolean isValid(T data);

	/**
	 * Implement your api call logic here
	 */
	abstract Observable<T> doNetworkCall();

	/**
	 * Implement here the logic to save response data on disk
	 * @param response
	 */
	abstract void persistOnDisk(T response);

	/**
	 * Implement here the logic to read response data from disk
	 * @param response
	 */
	abstract T readFromDisk();

	/**
	 * Implement here the logic to delete response data from disk
	 * @param response
	 */
	abstract void deleteFromDisk();
}