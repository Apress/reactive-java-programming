package com.andreamaglie.rxjava.chapter4;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class PublishSubjectExample {

	public static void main(String... args) {
		publishSubjectExample();
	}
	
	private static void publishSubjectExample() {
		PublishSubject<Integer> subject = PublishSubject.create();
		Observable<Integer> subjectAsObservable = subject.asObservable();
		
		// subscribe the first Subscriber
		subjectAsObservable.subscribe(new Subscriber<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("first: sequence completed");
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("first: next item is " + item);
			
			}
		});
		
		subject.onNext(1);
		subject.onNext(2);
		
		// subscribe the second Subscriber
		subjectAsObservable.subscribe(new Subscriber<Integer>() {

			@Override
			public void onCompleted() {
				System.out.println("second: sequence completed");
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("second: next item is " + item);
			
			}
		});
		
		subject.onNext(3);
		subject.onNext(4);
		subject.onNext(5);
		subject.onCompleted();
	}
}
