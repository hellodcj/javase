package com.dcj.core.net;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup{
	public ThreadPool(String name) {
		super(name);
	}
	private boolean isClosed =false; //线程是否关闭
	private LinkedList<Runnable> workQueue ; //工作队列
	private static int threadPoolId ; //表示线程Id
	
	public ThreadPool(int size){
		super(""+size);
		setDaemon(true);
		workQueue = new LinkedList<Runnable>();
		for (int i = 0; i < size; i++) {
			new WorkThread().start();
		}
		
	}
	
	protected Runnable getTask() throws Exception {
		while(workQueue.size()==0){
			if (isClosed) return null;
			wait();
		}
		return workQueue.removeFirst();
	}
	
	private class WorkThread extends Thread{
		@Override
		public void run() {
			while(!isInterrupted()){
				Runnable task = null;
				task = getTask();
			}
		}

	}
}


