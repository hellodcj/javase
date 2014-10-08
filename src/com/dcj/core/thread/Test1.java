package com.dcj.core.thread;

import org.junit.Test;

public class Test1 {
	
	@Test
	public void startListOff(){
		for (int i=1;i<=5;i++){
			ListOff lo = new ListOff();
			Thread t = new Thread (lo);
			t.start();
		}
	}
}


class ListOff implements Runnable{
	protected int countDown=10;
	private static int tastCount=0;
	private final int id = tastCount++;
	
	public String status(){
		return "#"+id+"--"+countDown;
	}

	@Override
	public void run(){
		while (countDown-->0){
			System.out.println(status());
			//Thread.sleep(1000);
		}
	}
}
	
