package com.dcj.core.thread;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

public class Test4 {
	
	@Test
	public void timeScedule(){
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("bomb");
			}
		}, 1000, 3000);
		
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
