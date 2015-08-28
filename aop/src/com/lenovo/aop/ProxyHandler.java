package com.lenovo.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.lenovo.annotation.After;
import com.lenovo.annotation.Before;

public class ProxyHandler<T> implements InvocationHandler {
	private Object targetObj;

	@SuppressWarnings("unchecked")
	public T getTargetObject(T obj) {
		this.targetObj = obj;
		return (T) Proxy.newProxyInstance(targetObj.getClass().getClassLoader(), targetObj.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args) throws Throwable {
		if (method.getAnnotation(Before.class) != null) {
			System.out.println("after handler method call !!");
		}
		Object result = method.invoke(targetObj, args);
		if (method.getAnnotation(After.class) != null) {
			System.out.println("after handler method call !!");
		}
		return result;
	}

}
