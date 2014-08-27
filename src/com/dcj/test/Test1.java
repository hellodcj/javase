package com.dcj.test;

import org.junit.Test;
/*
 * 测试多线程成员变量的共享
 */

public class Test1 {
	
	@Test
	public void test(){
		Utils u = new UtilsImp();
		MyThread mt = new MyThread(u);
		Thread t1 = new Thread(mt);
		Thread t2 = new Thread(mt);
		Thread t3 = new Thread(mt);
		t1.start();
		t2.start();
		t3.start();
	}
}

abstract class Utils{
	private int i=10;

	public int getI() {
		return i;
	}
	
	public void delI(){
		i--;
	}
	
}

class MyThread implements Runnable{
	private Utils u;
	
	
	public MyThread(Utils u) {
		super();
		this.u = u;
	}

	@Override
	public void run() {
			u.delI();
			System.out.println(Thread.currentThread().getName()+"-->"+u.getI());
	}
}

class UtilsImp extends Utils{
	
}