package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.GravityComponent;
import com.steftmax.temol.component.VelocityComponent;

/**
 * @author pieter3457
 */
public class GravitySystem extends IteratingSystem {

	private ComponentMapper<GravityComponent> sm = ComponentMapper.getFor(GravityComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	private Vector2 gravity;

	public GravitySystem(Vector2 gravity) {
		super(Family.all(GravityComponent.class, VelocityComponent.class).get());
		this.gravity = gravity;
	}

	public void setGravity(Vector2 gravity) {
		this.gravity = gravity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.
	 * ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		final GravityComponent sc = sm.get(entity);

		if (!sc.isGrounded) {
			vm.get(entity).velocity.mulAdd(gravity, deltaTime);
		}
		// System.out.println("State " + sc.state.name());
	}
}
