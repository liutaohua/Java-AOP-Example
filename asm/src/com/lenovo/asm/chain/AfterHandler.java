package com.lenovo.asm.chain;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.lenovo.asm.annotation.After;

public class AfterHandler extends Handler {
	@Override
	public void handleRequest(MethodVisitor mv, String clazzName) {
		if (clazzName.equals(After.class.getName())) {
			execute(mv);
			return;
		}
		super.nextExecute(mv, clazzName);
	}

	private void execute(MethodVisitor mv) {
		mv.visitEnd();
		mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
		mv.visitLdcInsn("after handler called!!");
		mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
	}
}
