package com.andreamaglie.rxjava.chapter3;

import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class SchedulersExamples {

	static void workerSchedulerExample() {
		Worker worker = Schedulers.newThread().createWorker();
		worker.schedule(new Action0() {
			
			@Override
			public void call() {
				// do your work here
			}
		});
	}
	
}
