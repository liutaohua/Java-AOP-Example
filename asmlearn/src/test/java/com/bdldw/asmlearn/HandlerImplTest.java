package com.bdldw.asmlearn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.bdldw.asmlearn.annotation.After;
import com.bdldw.asmlearn.chain.AfterHandler;
import com.bdldw.asmlearn.chain.BeforeHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(PowerMockRunner.class)
public class HandlerImplTest {
	private static final String n = Tester.class.getName();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	@Test
	public void test001_AfterHandlerExecute() throws Exception {
		AfterHandler spy = PowerMockito.spy(new AfterHandler());
		Method method = PowerMockito.method(spy.getClass(), "execute",
				Class.forName("org.objectweb.asm.MethodVisitor"));

		ClassWriter cw = publicMethodCall(After.class.getName(), method, spy);
		getLastOutPut("afterTest", cw.toByteArray());

		Assert.assertTrue(outContent.toString().contains("after handler called!!"));
	}

	@Test
	public void test002_BeforeHandlerExecute() throws Exception {
		BeforeHandler spy = PowerMockito.spy(new BeforeHandler());
		Method method = PowerMockito.method(spy.getClass(), "execute",
				Class.forName("org.objectweb.asm.MethodVisitor"));

		ClassWriter cw = publicMethodCall(com.bdldw.asmlearn.annotation.Before.class.getName(),
				method, spy);
		getLastOutPut("beforTest", cw.toByteArray());

		Assert.assertTrue(outContent.toString().contains("before handler called!!"));
	}

	private ClassWriter publicMethodCall(final String anntationName, final Method m,
			final Object obj) throws IOException {

		final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassReader cr = new ClassReader(n);
		cr.accept(new ClassVisitor(Opcodes.ASM4, cw) {
			@Override
			public MethodVisitor visitMethod(int access, String name, String desc,
					String signature, String[] exceptions) {
				final MethodVisitor v = cv.visitMethod(access, name, desc, signature, exceptions);
				return new MethodVisitor(Opcodes.ASM5, v) {
					@Override
					public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
						if (Type.getType(desc).getClassName().equals(anntationName)) {
							try {
								m.invoke(obj, v);
							} catch (Exception e) {
								e.printStackTrace(System.out);
							}
						}
						return super.visitAnnotation(desc, visible);
					}
				};
			}
		}, 0);
		return cw;
	}

	private void getLastOutPut(String methodName, final byte[] b) throws Exception {
		Class<?> c = new ClassLoader() {
			@Override
			public Class<?> loadClass(final String name) throws ClassNotFoundException {
				if (name.equals(n)) {
					return defineClass(name, b, 0, b.length);
				}
				return super.loadClass(name);
			}
		}.loadClass(n);

		Method m = c.getMethod(methodName, new Class[] {});
		m.invoke(c.newInstance(), new Object[] {});
	}
}
