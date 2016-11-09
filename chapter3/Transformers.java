package com.andreamaglie.rxjava.chapter3;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Transformers {

    public static <T> Observable.Transformer<T, T> applySchedulers() {
    	return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread());
            }
        };
    }
}
