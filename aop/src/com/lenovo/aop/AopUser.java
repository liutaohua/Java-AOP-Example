package com.lenovo.aop;

public class AopUser implements IAopUser {

	public void beforTest() {
		System.out.println("beforTest called!");
	}

	public void afterTest() {
		System.out.println("afterTest called!");
	}
}
