package com.bdldw.aoplearn.aop;

import com.bdldw.aoplearn.annotation.After;
import com.bdldw.aoplearn.annotation.Before;

public interface IAopUser {
	@Before
	public void beforTest();

	@After
	public void afterTest();
}
