package com.andreamaglie.rxjava.chapter4;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class SubjectExamples {

	public static void main(String... args) throws InterruptedException {
		subjectAsObservableAndSubscriber();
		
		Thread.sleep(10000);
	}
	
	public static void subjectAsObservableExample() {
		Subject<String, String> subject = PublishSubject.create();
		subject.subscribe(new Subscriber<String>() {

			@Override
			public void onCompleted() {
				System.out.println("sequence completed");
			}

			@Override
			public void onError(Throwable e) {
				
			}

			@Override
			public void onNext(String item) {
				System.out.println(item);
			}
			
		});
		
		subject.onNext("first item");
		subject.onNext("second item");
		subject.onNext("third item");
		subject.onCompleted();
	}
	
	public static void subjectAsObservableAndSubscriber() {
		Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);
		
		Subject<Long, Long> subject = PublishSubject.create();
		interval.subscribe(subject);
		
		subject.subscribe(new Subscriber<Long>() {

			@Override
			public void onCompleted() {
				System.out.println("first sequence completed");
			}

			@Override
			public void onError(Throwable e) {
			}

			@Override
			public void onNext(Long item) {
				System.out.println("first sequence, item: " + item);
				
			}
		});
		
		subject.subscribe(new Subscriber<Long>() {

			@Override
			public void onCompleted() {
				System.out.println("second sequence completed");
			}

			@Override
			public void onError(Throwable e) {				
			}

			@Override
			public void onNext(Long item) {
				System.out.println("second sequence, item: " + item);
				
			}
		});
		
	}
}
