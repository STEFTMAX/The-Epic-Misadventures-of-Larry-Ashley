package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.steftmax.temol.component.ControllerComponent;
import com.steftmax.temol.component.PhysicsComponent;

/**
 * @author pieter3457
 *
 */
public class BodyControllerSystem extends IteratingSystem {

	private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);
	
	public BodyControllerSystem() {
		super(Family.all(ControllerComponent.class, PhysicsComponent.class).get());
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

		
		final PhysicsComponent pc = pm.get(entity);
		
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			pc.body.setLinearVelocity(-1, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			pc.body.setLinearVelocity(1, 0);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			pc.body.setLinearVelocity(0, 1);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			pc.body.setLinearVelocity(0, -1);
		}
	}
}
