package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.steftmax.temol.component.TransformComponent;

/**
 * @author pieter3457
 *
 */
public class TransformSystem extends IteratingSystem {

	ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);

	public TransformSystem() {
		super(Family.all(TransformComponent.class).get());
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
		final TransformComponent tc = tm.get(entity);
		tc.transform.idt().translate(tc.origin).scale(tc.scale).rotateRad(tc.rotation)
				.translate(-tc.origin.x + tc.position.x, -tc.origin.y + tc.position.y);
	}
}