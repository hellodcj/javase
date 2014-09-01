package com.dcj.spring.observer;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ExampleRun {
	@Test
	public void main() {
		// launch the spring frame work.
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"/config/ObserverContext.xml");
		// grab the Town Crier out of the spring
		// framework and send a message too all observers
		TownCrier crier = (TownCrier) ctx.getBean("townCrier");
		crier.setMessage("It is 1 O'Clock and all is well!");
	}
}
