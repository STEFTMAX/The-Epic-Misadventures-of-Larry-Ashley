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
	
	
	public void addListeners(T... listeners) {
		for (T listener : listeners) {
			this.listeners.add(listener);
		}
	}
	public void removeListeners(T... listeners) {
		for (T listener : listeners) {
			this.listeners.remove(listener);
		}
	}
	public void clearListeners() {
		listeners.clear();
	}
}
