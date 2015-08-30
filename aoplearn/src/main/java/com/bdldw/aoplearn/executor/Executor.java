package com.bdldw.aoplearn.executor;

public class Executor {
	public void beforeExecute() {
		System.out.println("before handler method call !!");
	}

	public void afterExecute() {
		System.out.println("after handler method call !!");
	}
}
