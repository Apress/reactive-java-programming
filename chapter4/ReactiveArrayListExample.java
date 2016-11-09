package com.andreamaglie.rxjava.chapter4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class ReactiveArrayListExample  {
	
	public static void main(String... args) {
		ReactiveArrayList<String> reactiveList = new ReactiveArrayList<String>();
		
		reactiveList.observeItemsAdded()
			.subscribe(new Subscriber<String>() {

				@Override
				public void onCompleted() {
				}

				@Override
				public void onError(Throwable e) {
				}

				@Override
				public void onNext(String item) {
					System.out.println("item added: " + item);
				}
				
			});
		
		reactiveList.observeItemsRemoved()
			.subscribe(new Subscriber<Object>() {
	
				@Override
				public void onCompleted() {
				}
	
				@Override
				public void onError(Throwable e) {
				}
	
				@Override
				public void onNext(Object item) {
					System.out.println("item removed: " + item);
				}
				
			});
		
		reactiveList.add("1");
		reactiveList.add("2");
		reactiveList.remove("1");
		reactiveList.addAll(Arrays.asList("4", "5", "6"));
		reactiveList.remove("5");

	}

	static class ReactiveArrayList<T> extends ArrayList<T> {

		private PublishSubject<T> addSubject = PublishSubject.create();
		private PublishSubject<Object> removeSubject = PublishSubject.create();

		@Override
		public boolean add(T item) {
			boolean result = super.add(item);
			if (result) {
				addSubject.onNext(item);
			}
			return result;
		}

		@Override
		public void add(int index, T item) {
			super.add(index, item);
			addSubject.onNext(item);
		}

		@Override
		public T remove(int index) {
			T removedItem = super.remove(index);
			removeSubject.onNext(removedItem);
			return removedItem;
		}

		@Override
		public boolean remove(Object object) {
			boolean result = super.remove(object);
			if (result) {
				removeSubject.onNext(object);
			}
			return result;
		}

		@Override
		public boolean addAll(Collection<? extends T> c) {
			boolean result = super.addAll(c);
			if (result) {
				for (T t : c) {
					addSubject.onNext(t);
				}
			}
			return result;
		}

		@Override
		public boolean addAll(int index, Collection<? extends T> c) {
			boolean result = super.addAll(index, c);
			if (result) {
				for (T t : c) {
					addSubject.onNext(t);
				}
			}
			return result;
		}

		public Observable<T> observeItemsAdded() {
			return addSubject.asObservable();
		}

		public Observable<Object> observeItemsRemoved() {
			return removeSubject.asObservable();
		}
	}
}
