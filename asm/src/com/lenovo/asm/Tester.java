package com.lenovo.asm;

import com.lenovo.asm.annotation.After;
import com.lenovo.asm.annotation.Before;

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
