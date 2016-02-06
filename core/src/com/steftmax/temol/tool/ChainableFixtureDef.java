package com.steftmax.temol.tool;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * @author pieter3457
 *
 */
public class ChainableFixtureDef extends FixtureDef{
	public ChainableFixtureDef setFilter(short categoryBits, short groupIndex, short maskBits) {
		
		this.filter.categoryBits = categoryBits;
		this.filter.groupIndex = groupIndex;
		this.filter.maskBits = maskBits;
		
		return this;
	}
	
	public ChainableFixtureDef setDensity(float density) {
		this.density = density;
		return this;
	}
	
	public ChainableFixtureDef setFriction(float friction) {
		this.friction = friction;
		return this;
	}
	
	public ChainableFixtureDef setSensor(boolean sensor) {
		this.isSensor = sensor;
		return this;
	}
	
	public ChainableFixtureDef setRestitution(float restitution) {
		this.restitution = restitution;
		return this;
	}
	
	public ChainableFixtureDef setShape(Shape shape) {
		this.shape = shape;
		return this;
	}
	
	
}
