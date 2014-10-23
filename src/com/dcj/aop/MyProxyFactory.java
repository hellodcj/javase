package com.dcj.aop;

import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.InvocationHandler;

/**
 * 为指定的target生成动态代理对象
 * @author dcj
 *
 */
public class MyProxyFactory {
	public static Object getProxy(Object target){
		MyInvocationHandler handler = new MyInvocationHandler();
		handler.setTarget(target);
		//创建返回一个动态代理
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
	}
}	
