package com.dcj.core.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * 任务的终结
 * 统计多个大门中通过的人数
 * @author chengjun
 *
 */

public class Test3 {
	@Test
	public void main() throws InterruptedException{
		ExecutorService exec = Executors.newCachedThreadPool();
	    for(int i = 0; i < 5; i++)
	      exec.execute(new Door());
	    try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    exec.shutdown();
	    
	    System.out.println("Total: " + Door.totalCount());
	    System.out.println("Sum of Entrances: " + Door.countSum());
	}
}

/**
 * 计数类
 * @author chengjun
 *
 */
class Count{
	private int count=0;
	public synchronized void add(){
		int temp = count;
		Thread.yield();
		this.count=++temp;
	}
	
	public synchronized int value(){
		return count;
	}
}

/**
 * 大门，统计人数
 */
class Door implements Runnable{
	private static Count count= new Count();
	private int num=0;
	private static volatile boolean canceled = false;
	//保存一个静态的自身对象的列表
	private static List<Door> list = new ArrayList<Door>();
	
	public synchronized int getNum() {
		return num;
	}

	public Door() {
		list.add(this);
	}
	
	//累加list中的人数
	public static int countSum(){
		int sum =0;
		for (Door d :list){
			sum+=d.getNum();
		}
		return sum;
	}
	
	public static int totalCount(){
		return count.value();
	}

	@Override
	public void run() {
		while(!canceled){
			synchronized (this) {
				num++;
			}
			count.add();
			System.out.println(Thread.currentThread().getName()+"totalcount==="+Door.totalCount());
		}
	}
	
}
