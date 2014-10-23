package com.dcj.aop;

public class Dog implements IDog {

	@Override
	public void run() {
		System.out.println("run---------");

	}

	@Override
	public void say() {
		System.out.println("say---------");

	}

}
