package com.dcj.core.net;

import java.util.LinkedList;

import org.junit.Test;



public class ThreadPool extends ThreadGroup{
	public ThreadPool(String name) {
		super(name);
	}
	private boolean isClosed = false; //线程是否关闭
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
	
	//向工作队列中加入一个新的任务，由工作线程执行任务
	public synchronized void execute (Runnable task){
//System.out.println(activeCount());
		if(isClosed) throw new IllegalStateException();
		if (task!= null){
			workQueue.add(task);
			notify(); //唤醒正在getTask()中等待任务的线程
		}
	}
	
	public synchronized void close(){
		if (!isClosed){
			isClosed = true;
			workQueue.clear();
			//ThreadGroup中的方法，interrupt方法相当于调用线程组组中所有活着的线程的interrupt方法
			interrupt(); //中断所有的工作线程
		}
	}
	
	/**
	 * 与close的区别是，等待所有任务执行完毕再关闭
	 */
	public void join(){
		synchronized (this) {
			isClosed = true;
			notifyAll();
		}
		
		//activecount方法是threadgroup提供的。返回现在还存活的数量
		Thread [] threads = new Thread[activeCount()];
		//enumerate方法返回的数量包括了子线程
		int count = enumerate(threads);
//System.out.println(count+"count====");
		for (int i = 0; i < count; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 逻辑：
	 *  1.如果队列为空，并且线程池已经关闭，则返回null表示已经没有任务可以执行了
		2.如果队列为空，并且线程池没有关闭，那么等待，直到其他的线程唤醒或者中断。
		3.如果队列中有任务，就返回第一个任务。
	 * @return
	 * @throws InterruptedException 
	 * @throws Exception
	 */
	protected synchronized Runnable getTask() throws InterruptedException {
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
				try {
					task = getTask();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (task == null) return;
				
				task.run();
			}
		}
	}
	
	public Runnable createTask(final int taskId){
		return new Runnable() {
			@Override
			public void run() {
				System.out.println("start：--》"+taskId);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("end：--》"+taskId);
			}
		};
	}
	
}


