package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author pieter3457
 *
 */
public class VelocityComponent implements Component {
	
	public Vector2 velocity;
	
	public VelocityComponent(float x, float y) {
		this(new Vector2(x,y));
	}

	public VelocityComponent(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public VelocityComponent() {
		this(new Vector2());
	}

}
