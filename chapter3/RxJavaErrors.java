package com.andreamaglie.rxjava.chapter3;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class RxJavaErrors {

	public static void main(String... args) {
		retryExample();
	}

	public static void errorExample() {
		Observable.just("1", "2", "a", "3", "4")
		.map(new Func1<String, Integer>() {
			@Override
			public Integer call(String s) {
				return Integer.parseInt(s);
			}

		})
		.onErrorResumeNext(Observable.<Integer>empty())
		.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {
				System.err.println("error! " + e.toString());
			}

			@Override
			public void onNext(Integer item) {
				System.out.println("next item is: " + item);
			}
		});
	}

	public static void retryExample() {
		Observable.just("1", "2", "a", "3", "4")
		.map(new Func1<String, Integer>() {
			@Override
			public Integer call(String s) {
				return Integer.parseInt(s);
			}
		})
		.retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
			@Override
			public Observable<?> call(Observable<? extends Throwable> observable) {
				return observable.zipWith(Observable.range(1, 3), new Func2<Throwable, Integer, Integer>() {
					@Override
					public Integer call(Throwable throwable, Integer integer) {
						System.out.println("retry #" + integer);
						return integer;
					}
				}).flatMap(new Func1<Integer, Observable<?>>() {
					@Override
					public Observable<?> call(Integer integer) {
						return Observable.timer(5, TimeUnit.SECONDS);
					}
				});
			}
		})
		.toBlocking().forEach(new Action1<Integer>() {
			@Override
			public void call(Integer integer) {
				System.out.println("next item is: " + integer);

			}
		});
	}
}
