package com.dcj.core.net;

import org.junit.Test;

public class ThreadPoolTest {
	@Test
	public void testss(){
		//定义一个大小为5的线程池
		ThreadPool threadPool = new ThreadPool(5);
		//用threadpool启动10个线程
		for (int i = 1; i <= 10; i++) {
			Runnable r = threadPool.createTask(i);
			threadPool.execute(r);
		}
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//threadPool.join();
		//threadPool.close();
	}
}
