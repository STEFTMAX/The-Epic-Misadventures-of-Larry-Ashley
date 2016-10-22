package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author pieter3457
 *
 */
public class CollisionComponent implements Component {

	public Rectangle bounds;
	
	public CollisionComponent(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	public CollisionComponent() {
		bounds = new Rectangle();
	}
}
