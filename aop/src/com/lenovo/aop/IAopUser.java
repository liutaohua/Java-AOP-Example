package com.lenovo.aop;

import com.lenovo.annotation.After;
import com.lenovo.annotation.Before;

public interface IAopUser {
	@Before
	public void beforTest();

	@After
	public void afterTest();
}
