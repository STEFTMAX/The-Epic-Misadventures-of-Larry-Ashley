package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.VelocityComponent;

/**
 * @author pieter3457
 *	TODO merge this with collsionsystem into PhysicsSystem
 */
public class MovementSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

	public MovementSystem() {
		super(Family.all(TransformComponent.class, VelocityComponent.class).get());
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
		final TransformComponent pc = pm.get(entity);
//		pc.lastPosition.set(pc.position); TODO last positioning
		pc.position.add(velocity.x * deltaTime, velocity.y * deltaTime);
		
//		pc.rotation += .1f*deltaTime;
	}
}
