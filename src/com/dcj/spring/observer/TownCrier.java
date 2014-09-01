package com.dcj.spring.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TownCrier implements Subject {
	private List townResident = new ArrayList();
	private String messageText;

	// this message is added so I can give
	// this class a reason to call notifyListener.
	public void setMessage(String message) {
		System.out.println("I'm the Town Crier and " + "I've got a message: "
				+ message);
		this.messageText = message;
		this.notifyListeners();
	}

	public void addListener(Observer o) {
		this.townResident.add(o);
	}

	public void removeListener(Observer o) {
		if (this.townResident.contains(o)) {
			this.townResident.remove(o);
		}
	}

	// call the update method on
	// each observer (town resident)
	public void notifyListeners() {
		for (Iterator iter = townResident.iterator(); iter.hasNext();) {
			Observer listener = (Observer) iter.next();
			listener.update(messageText);
		}
	}
}
