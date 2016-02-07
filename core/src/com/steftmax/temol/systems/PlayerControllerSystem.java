package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.steftmax.temol.component.GroundedComponent;
import com.steftmax.temol.component.PhysicsComponent;
import com.steftmax.temol.component.PlayerComponent;

import sun.management.Sensor;

/**
 * @author pieter3457
 *
 */
public class PlayerControllerSystem extends IteratingSystem {

	private static final float MAXVELOCITY = 2f;
	private static final float JUMPIMPULSE = 4f;
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

		final Body b = pc.body;
		final Vector2 pos = pc.body.getPosition();

		final Fixture feetFixture = gc.feetFixture;
		if (!gc.isGrounded) {
			feetFixture.setFriction(0f);
		} else {
			if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
				gc.groundContact.setFriction(1f); //TODO change actual values in the contact!
			} else {
				gc.groundContact.setFriction(.2f);

			}
		}

		if (gc.isGrounded) {
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {

				// The minuscule boost to help overcome jumping twice or
				// even
				// more often
				final float y = pos.y + .01f;

				pc.body.setTransform(pos.x, y, pc.body.getAngle());
				pc.body.applyLinearImpulse(0, JUMPIMPULSE, pos.x, y, true);
				gc.isGrounded = false;

			} else {
				if (Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
					// pc.body.applyForceToCenter(-10, 0, true);
					pc.body.applyLinearImpulse(-1, 0, pos.x, pos.y, true);
				}

				if (Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
					// pc.body.applyForceToCenter(10, 0, true);
					b.applyLinearImpulse(1, 0, pos.x, pos.y, true);
				}

				if (Math.abs(b.getLinearVelocity().x) > MAXVELOCITY) {
					b.setLinearVelocity(Math.signum(b.getLinearVelocity().x) * MAXVELOCITY, b.getLinearVelocity().y);
				}
			}
		}
	}

}
