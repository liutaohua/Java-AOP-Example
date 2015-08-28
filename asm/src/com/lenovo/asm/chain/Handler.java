package com.lenovo.asm.chain;

import org.objectweb.asm.MethodVisitor;

public abstract class Handler {
	protected Handler nextHandler;

	public abstract void handleRequest(MethodVisitor mv, String clazzName);

	public Handler getSuccessor() {
		return nextHandler;
	}

	public void setSuccessor(Handler successor) {
		this.nextHandler = successor;
	}

	protected void nextExecute(MethodVisitor mv, String clazzName) {
		if (this.nextHandler != null) {
			this.nextHandler.handleRequest(mv, clazzName);
		}
	}
}
