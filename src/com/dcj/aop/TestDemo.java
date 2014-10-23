package com.dcj.aop;

import org.junit.Test;

public class TestDemo {
	@Test
	public void test(){
		IDog target = new Dog();
		IDog dog = (IDog) MyProxyFactory.getProxy(target);
		dog.run();
		dog.say();
	}
}
