package com.steftmax.temol.notifier;

import com.steftmax.temol.listener.ResolutionListener;

/**
 * @author pieter3457
 *
 */
public class ResolutionNotifier extends Notifier <ResolutionListener> {
	
	public void notify(int width, int height) {
		for (ResolutionListener l : listeners) {
			l.resize(width, height);
			System.out.println("yo");
		}
	}

}
