package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * @author pieter3457
 *
 */
public class PhysicsComponent implements Component {
	
	public Body body;
	public Fixture[] fixtures;
	
	public PhysicsComponent(Body body, Fixture... fixtures) {
		this.body = body;
		this.fixtures = fixtures;
	}
	
}
