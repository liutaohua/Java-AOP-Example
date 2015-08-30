package com.bdldw.aoplearn.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.bdldw.aoplearn.annotation.After;
import com.bdldw.aoplearn.annotation.Before;
import com.bdldw.aoplearn.executor.Executor;

public class ProxyHandler<T> implements InvocationHandler {
	private T targetObj;

	@SuppressWarnings("unchecked")
	public T getTargetObject(T obj) {
		this.targetObj = obj;
		return (T) Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), targetObj.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Executor executor = new Executor();
		if (method.getAnnotation(Before.class) != null) {
			executor.beforeExecute();
		}
		Object result = method.invoke(targetObj, args);
		if (method.getAnnotation(After.class) != null) {
			executor.afterExecute();
		}
		return result;
	}

}
