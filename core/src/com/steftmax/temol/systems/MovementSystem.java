package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.PositionComponent;
import com.steftmax.temol.component.VelocityComponent;

/**
 * @author pieter3457
 *	TODO merge this with collsionsystem into PhysicsSystem
 */
public class MovementSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	public MovementSystem() {
		super(Family.all(PositionComponent.class, VelocityComponent.class).get());
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

		final Vector2 velocity = vm.get(entity).velocity;
		final PositionComponent pc = pm.get(entity);
		pc.lastPosition.set(pc.position);
		pm.get(entity).position.add(velocity.x * deltaTime, velocity.y * deltaTime);
	}
}
