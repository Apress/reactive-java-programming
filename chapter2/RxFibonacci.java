package com.andreamaglie.rxjava.chapter2;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class RxFibonacci {

	public static void main(String... args) throws InterruptedException {
		rxFibonacci(10);
	}

	public static void rxFibonacci(int n) {
		final int[] tmp = {0, 0};

		Observable.range(1, n)
		.map(new Func1<Integer, Integer>() {
			@Override
			public Integer call(Integer x) {
				if (x < 3) {
					tmp[0] = 1;
					tmp[1] = 1;
					return 1;
				} else {
					int item = tmp[0] + tmp[1];
					tmp[0] = tmp[1];
					tmp[1] = item;
					return item;
				}
			}
		})
		.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {
				System.out.println("sequence completed!");
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer item) {
				System.out.println("next item: " + item);
			}
		});
	}
}
