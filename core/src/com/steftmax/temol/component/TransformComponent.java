package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;

/**
 * @author pieter3457
 *
 */
public class TransformComponent implements Component {
	public final Vector2 position = new Vector2();
	public final Vector2 origin = new Vector2();
	public final Vector2 scale = new Vector2(1f, 1f);
	public float rotation = 0f;
	public final Affine2 transform = new Affine2();
	public TransformComponent(float x, float y) {
		position.x = x;
		position.y = y;
	}
}
