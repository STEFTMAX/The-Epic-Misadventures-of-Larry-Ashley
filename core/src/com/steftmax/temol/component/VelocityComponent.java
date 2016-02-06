package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;

/**
 * @author pieter3457
 *
 */
public class VelocityComponent implements Component {
	public float x;
	public float y;

	public VelocityComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}

}
