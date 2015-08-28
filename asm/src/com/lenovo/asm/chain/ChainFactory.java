package com.lenovo.asm.chain;

public class ChainFactory {

	public static Handler getChainHandler() {
		AfterHandler ah = new AfterHandler();
		BeforeHandler bh = new BeforeHandler();
		ah.setSuccessor(bh);
		return ah;
	}
}
