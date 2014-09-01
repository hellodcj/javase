package com.dcj.spring.observer;

public class TownResident implements Observer {
	public void update(String messageText) {
		  System.out.println("Greetings my name is: " + this);
		  System.out.println("I heard: " + messageText);
		 }
}
