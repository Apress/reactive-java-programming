package com.andreamaglie.rxjava.chapter2;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * Created by andrea on 16/06/16.
 */
public class RxJavaDefer {

	public static void main(String... args) {
		// create a new instance of Person
		final Person person = new Person();

		Observable<String> nameObservable = Observable.defer(new Func0<Observable<String>>() {
			@Override
			public Observable<String> call() {
				return Observable.just(person.getName());
			}
		});

		Observable<Integer> ageObservable = Observable.defer(new Func0<Observable<Integer>>() {
			@Override
			public Observable<Integer> call() {
				return Observable.just(person.getAge());
			}
		});

		// set age and name
		person.setName("Bob");
		person.setAge(35);

		ageObservable.subscribe(new Subscriber<Integer>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(Integer age) {
				System.out.println("age is: " + age);
			}
		});

		nameObservable.subscribe(new Subscriber<String>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(String name) {
				System.out.println("name is: " + name);
			}
		});
	}


}

class Person {
	private String name;
	private int age;

	public void setAge(int age) {
		this.age = age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}
}