package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author pieter3457
 *
 */
public class PositionComponent implements Component {
	public Vector2 position;

	public PositionComponent(float x, float y) {
		this(new Vector2(x, y));
	}

	public PositionComponent(Vector2 position) {
		this.position = position;
	}

	public PositionComponent() {
		this(new Vector2());
	}
}
