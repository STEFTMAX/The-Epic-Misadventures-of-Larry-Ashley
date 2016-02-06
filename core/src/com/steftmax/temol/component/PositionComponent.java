package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;

/**
 * @author pieter3457
 *
 */
public class PositionComponent implements Component {
	public float x;
	public float y;

	public PositionComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}

}
