package com.lenovo.asm.chain;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.lenovo.asm.annotation.Before;

public class BeforeHandler extends Handler {

	@Override
	public void handleRequest(MethodVisitor mv, String clazzName) {
		if (clazzName.equals(Before.class.getName())) {
			execute(mv);
			return;
		}
		super.nextExecute(mv, clazzName);
	}

	private void execute(MethodVisitor mv) {
		mv.visitCode();
		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn("before handler called!!");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
	}
}
