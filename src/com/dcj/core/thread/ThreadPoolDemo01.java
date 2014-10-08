package com.dcj.core.thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class ThreadPoolDemo01 implements Runnable{
	
	@Test
	public void test(){
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.submit(new ThreadPoolDemo01());
		pool.submit(new ThreadPoolDemo01());
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdown();
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.out.println(Thread.currentThread().getName()+"--->i的值为："+i);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
