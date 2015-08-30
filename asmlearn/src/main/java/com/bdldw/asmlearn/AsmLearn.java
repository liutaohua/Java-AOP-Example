package com.bdldw.asmlearn;

import java.io.FileOutputStream;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import com.bdldw.asmlearn.chain.ChainFactory;
import com.bdldw.asmlearn.chain.Handler;

public class AsmLearn {

	public static void main(final String[] args) throws Exception {
		final Handler chainHandler = ChainFactory.getChainHandler();
		final String n = Tester.class.getName();
		final ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassReader cr = new ClassReader(n);
		cr.accept(new ClassVisitor(Opcodes.ASM4, cw) {
			@Override
			public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
				final MethodVisitor v = cv.visitMethod(access, name, desc, signature, exceptions);
				return new MethodVisitor(Opcodes.ASM5, v) {
					@Override
					public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
						final String clazzName = Type.getType(desc).getClassName();
						chainHandler.handleRequest(v, clazzName);
						return super.visitAnnotation(desc, visible);
					}
				};
			}
		}, 0);

		byte[] code = cw.toByteArray();
		FileOutputStream fos = new FileOutputStream(Tester.class.getSimpleName() + ".class");
		fos.write(code);
		fos.close();
	}
}
