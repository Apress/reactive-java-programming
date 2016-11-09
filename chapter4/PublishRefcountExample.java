package com.andreamaglie.rxjava.chapter4;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

public class PublishRefcountExample {

	public static void main(String... args) {
		
		ConnectableObservable<String> observable = 
				Observable.range(0, 5)
					.map(new Func1<Integer, String>() {

						@Override
						public String call(Integer t) {
							return String.valueOf(t);
						}
						
					}).publish();
		
		observable.subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				System.out.println("first: sequence completed");
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onNext(String item) {
				System.out.println("first: next item is " + item);
			
			}
		});
		
		observable.subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				System.out.println("second: sequence completed");
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onNext(String item) {
				System.out.println("second: next item is " + item);
			
			}
		});
		
		observable.connect();
	
	}
	
	static void simulateWork() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
