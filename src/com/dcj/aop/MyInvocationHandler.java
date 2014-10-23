package com.dcj.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
	//需要被代理的对象
	private Object target;
	
	public void setTarget(Object target){
		this.target = target;
	}
	
	/**
	 * 该方法将会作为代理对象的方法实现
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		DogUtils util = new DogUtils();
		util.method01();
		Object result = method.invoke(target, args);
		util.method02();
		return result;
	}

}
