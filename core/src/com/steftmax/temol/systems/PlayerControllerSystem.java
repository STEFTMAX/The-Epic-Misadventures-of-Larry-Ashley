package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.GroundedComponent;
import com.steftmax.temol.component.PhysicsComponent;
import com.steftmax.temol.component.PlayerComponent;

/**
 * @author pieter3457
 *
 */
public class PlayerControllerSystem extends IteratingSystem {

	private static final float JUMPIMPULSE = 4;
	private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);
	private ComponentMapper<GroundedComponent> gm = ComponentMapper.getFor(GroundedComponent.class);

	public PlayerControllerSystem() {
		super(Family.all(PlayerComponent.class, PhysicsComponent.class, GroundedComponent.class).get());
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
		final GroundedComponent gc = gm.get(entity);

		final Vector2 position = pc.body.getPosition();
		
		if (gc.isGrounded) {
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				
				//The minuscule boost to help overcome jumping twice or even more often
				final float y = position.y + .01f;
				
				
				pc.body.setTransform(position.x, y, pc.body.getAngle());
				pc.body.applyLinearImpulse(0, JUMPIMPULSE, position.x, y, true);
				gc.isGrounded = false;
				
			} else {
				if (Gdx.input.isKeyPressed(Input.Keys.A)) {
					pc.body.setLinearVelocity(-1, 0);
				}

				if (Gdx.input.isKeyPressed(Input.Keys.D)) {
					pc.body.setLinearVelocity(1, 0);
				}
			}
		}

	}
}
