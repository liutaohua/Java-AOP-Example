package com.lenovo.test;

import com.lenovo.aop.AopUser;
import com.lenovo.aop.IAopUser;
import com.lenovo.aop.ProxyHandler;

public class Tester {

	public static void main(String[] args) {
		IAopUser userI = new ProxyHandler<IAopUser>().getTargetObject(new AopUser());
		userI.beforTest();
		userI.afterTest();
	}
}
