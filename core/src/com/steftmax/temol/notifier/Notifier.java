package com.steftmax.temol.notifier;

import java.util.ArrayList;
import java.util.List;

import com.steftmax.temol.listener.Listener;

/**
 * @author pieter3457
 *
 */
public abstract class Notifier <T extends Listener>{
	
	
	protected List<T> listeners = new ArrayList<T>();
	
	
	public void addListeners(T... listener) {
		listeners.addAll(listeners);
	}
	public void removeListeners(T... listener) {
		listeners.removeAll(listeners);
	}
	public void clearListeners() {
		listeners.clear();
	}
}
