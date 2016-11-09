package com.andreamaglie.rxjava.chapter2;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

public class RxJavaOperators {

	public static void mapExample() {
		Observable.just(1, 2, 3)
		.map(new Func1<Integer, Integer>() {
			@Override
			public Integer call(Integer x) {
				return x * 10;
			}
		})
		.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer integer) {
				System.out.println("next item is: " + integer);
			}
		});
	}

	public static void zipExample() {
		Observable<Integer> rangeMajor = Observable.range(1, 3);
		Observable<Integer> rangeMinor = Observable.range(5, 10);

		Observable.zip(rangeMajor, rangeMinor, new Func2<Integer, Integer, String>() {
			@Override
			public String call(Integer major, Integer minor) {
				return major + "." + minor;
			}
		}).subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String s) {
				System.out.println("next item is: " + s);
			}
		});
	}

	public static void concatExample() {
		Observable<String> first = Observable.just("one", "two");
		Observable<String> second = Observable.just("three", "four", "five");

		Observable.concat(first, second)
		.subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String s) {
				System.out.println("next item is: " + s);
			}
		});
	}

	public static void filterExample() {
		Observable.from(new Integer[]{2, 30, 22, 5, 60, 1})
		.filter(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer x) {
				return x > 10;
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer item) {
				System.out.println("next item is: " + item);
			}
		});
	}

	public static void firstExample() {
		Observable.from(new Integer[]{2, 30, 22, 5, 60, 1})
		.first(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer x) {
				return x > 10;
			}
		}).subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer item) {
				System.out.println("next item is: " + item);
			}
		});
	}

	public static void scanExample() {
		Observable<Integer> sourceObservable = Observable.range(1, 5);

		Observable<Integer> scanObservable = sourceObservable
				.scan(new Func2<Integer, Integer, Integer>() {
					@Override
					public Integer call(Integer i1, Integer i2) {
						return i1 + i2;
					}
				});

		scanObservable.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer item) {
				System.out.println("next item is: " + item);
			}
		});
	}
}
