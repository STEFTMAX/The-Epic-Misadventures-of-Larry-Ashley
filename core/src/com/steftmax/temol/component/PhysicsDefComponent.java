package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * @author pieter3457
 *
 */
public class PhysicsDefComponent implements Component {
	public FixtureDef[] fixtureDefs;
	public BodyDef bodyDef;
	
	
	public PhysicsDefComponent(BodyDef bodyDef, FixtureDef... fixtureDefs) {
		this.bodyDef = bodyDef;
		this.fixtureDefs = fixtureDefs;
	}
}
