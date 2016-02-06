package com.steftmax.temol.tool;

import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * @author pieter3457
 *
 */
public class ChainableBodyDef extends BodyDef{
	
	
	public ChainableBodyDef setAngularDamping(float angularDamping) {
		this.angularDamping = angularDamping;
		return this;
	}
	
	public ChainableBodyDef setAngularVelocity(float angularVelocity) {
		this.angularVelocity = angularVelocity;
		return this;
	}
	
	public ChainableBodyDef setType(BodyDef.BodyType type) {
		this.type = type;
		return this;
	}
	
	public ChainableBodyDef setPosition(float x, float y) {
		this.position.set(x, y);
		return this;
	}
	
	public ChainableBodyDef setLinearVelocity(float x, float y) {
		this.linearVelocity.set(x, y);
		return this;
	}
	
	public ChainableBodyDef linearDamping(float linearDamping) {
		this.linearDamping = linearDamping;
		return this;
	}
	
	public ChainableBodyDef setGravityScale(float gravityScale) {
		this.gravityScale = gravityScale;
		return this;
	}
	
	public ChainableBodyDef setFixedRotation(boolean fixedRotation) {
		this.fixedRotation = fixedRotation;
		return this;
	}
	
	public ChainableBodyDef setBullet(boolean bullet) {
		this.bullet = bullet;
		return this;
	}
	
	public ChainableBodyDef setAwake(boolean awake) {
		this.awake = awake;
		return this;
	}
	
	public ChainableBodyDef setAllowSleep(boolean allowSleep) {
		this.allowSleep = allowSleep;
		return this;
	}
	
	public ChainableBodyDef setAngle(float angle) {
		this.angle = angle;
		return this;
	}
}
