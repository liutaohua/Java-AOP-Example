package com.bdldw.asmlearn;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.internal.verification.VerificationModeFactory;
import org.objectweb.asm.MethodVisitor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bdldw.asmlearn.chain.AfterHandler;
import com.bdldw.asmlearn.chain.Handler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
public class HandlerTest {
	private static Handler handler;
	private static AfterHandler testHandler;

	@BeforeClass
	public static void buildHandler() {
		handler = new Handler() {
			@Override
			public void handleRequest(MethodVisitor mv, String clazzName) {
				// TODO Auto-generated method stub
			}
		};
		testHandler = new AfterHandler();
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		handler = null;
		testHandler = null;
	}

	@Test
	public void test001_SetSuccessor() throws IllegalArgumentException, IllegalAccessException {
		Handler spy = PowerMockito.spy(handler);
		handler.setSuccessor(testHandler);
		Field field = PowerMockito.field(spy.getClass(), "nextHandler");
		Object object = field.get(handler);
		Assert.assertEquals(object, testHandler);
	}

	@Test
	public void test002_GetSuccessor() {
		Assert.assertEquals(handler.getSuccessor(), testHandler);
	}

	@Test
	public void test003_nextExecute() throws Exception {
		Handler spy = PowerMockito.spy(handler);
		int times = 1;
		Handler countTmp = handler;
		for (countTmp = handler; countTmp.getSuccessor() != null; countTmp = countTmp
				.getSuccessor()) {
			times++;
		}
		Method[] methods = PowerMockito.methods(spy.getClass(), new String[] { "nextExecute" });
		for (Method m : methods) {
			if (m.getName().equals("nextExecute")) {
				m.invoke(spy, null, "");
			}
		}
		PowerMockito.verifyPrivate(spy, VerificationModeFactory.times(times)).invoke("nextExecute",
				new Object[] { null, "" });
	}
}
