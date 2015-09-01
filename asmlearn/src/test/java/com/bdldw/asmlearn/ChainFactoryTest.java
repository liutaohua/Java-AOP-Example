package com.bdldw.asmlearn;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bdldw.asmlearn.chain.ChainFactory;
import com.bdldw.asmlearn.chain.Handler;

public class ChainFactoryTest {

	@Test
	public void test001_GetChainHandler() {
		Handler chainHandler = ChainFactory.getChainHandler();
		Handler successor = chainHandler.getSuccessor();
		assertNotNull(chainHandler);
		assertNotNull(successor);
		assertNull(successor.getSuccessor());
	}

}
