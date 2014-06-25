package com.app.demos.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.app.demos.util.AppUtil;

public class TestProxy implements InvocationHandler {

	Object testObj;
	
	public TestProxy (Object obj) {
		testObj = obj;
	}
	
	public static Object init (Object obj) {
		return Proxy.newProxyInstance(
			obj.getClass().getClassLoader(), 
			obj.getClass().getInterfaces(), 
			new TestProxy(obj));
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object methodResult;
		try {
			System.out.println("method name : " + method.getName());
			long startTime = AppUtil.getTimeMillis();
			methodResult = method.invoke(testObj, args);
			long endTime = AppUtil.getTimeMillis();
			System.out.println("method time : " + (endTime - startTime) + "ms");
		} catch (Exception e) {
			throw new RuntimeException("TestHandler Exception : " + e.getMessage());
		}
		return methodResult;
	}
}