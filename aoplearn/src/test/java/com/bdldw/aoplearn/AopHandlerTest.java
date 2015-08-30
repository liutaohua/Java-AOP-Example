package com.bdldw.aoplearn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.bdldw.aoplearn.aop.AopUser;
import com.bdldw.aoplearn.aop.IAopUser;
import com.bdldw.aoplearn.aop.ProxyHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AopHandlerTest {

	private static ProxyHandler<IAopUser> ph;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@BeforeClass
	public static void buildProxyHandler() {
		ph = new ProxyHandler<IAopUser>();
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		ph = null;
	}

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	@Test
	public void test001_GetTargetObject() {
		IAopUser o = ph.getTargetObject(new AopUser());
		Assert.assertNotNull(o);
	}

	@Test
	public void test002_BeforeAOP() {
		ph.getTargetObject(new AopUser()).beforTest();
		String target = "before handler method call !!\r\nbeforTest called!\r\n";
		Assert.assertEquals(target, outContent.toString());
	}

	@Test
	public void test003_AfterAOP() {
		ph.getTargetObject(new AopUser()).afterTest();
		String target = "afterTest called!\r\nafter handler method call !!\r\n";
		Assert.assertEquals(target, outContent.toString());
	}
}
