package com.bdldw.asmlearn;

import com.bdldw.asmlearn.annotation.After;
import com.bdldw.asmlearn.annotation.Before;

public class Tester {

	@Before
	public void beforTest() {
		System.out.println("beforTest called!");
	}

	@After
	public void afterTest() {
		System.out.println("afterTest called!");
	}

}
