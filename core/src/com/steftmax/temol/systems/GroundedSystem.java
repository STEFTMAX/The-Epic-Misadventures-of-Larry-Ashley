package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.steftmax.temol.component.GroundedComponent;
import com.steftmax.temol.component.PhysicsComponent;

/**
 * @author pieter3457
 * This has two tasks:
 * 1. Separating a fixture that is the sensor of the body for ground
 * test
 * 2. Testing of state and setting it
 */
public class GroundedSystem extends IteratingSystem {

	private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);
	private ComponentMapper<GroundedComponent> sm = ComponentMapper.getFor(GroundedComponent.class);
	private World w;

	public GroundedSystem(World w) {
		super(Family.all(GroundedComponent.class, PhysicsComponent.class).get());

		this.w = w;
	}

	private EntityListener listener = new EntityListener() {

		@Override
		public void entityAdded(Entity entity) {
			GroundedComponent sc = sm.get(entity);
			PhysicsComponent pc = pm.get(entity);

			sc.sensorFixture = pc.fixtures[sc.feetFixtureIndex];

		}

		@Override
		public void entityRemoved(Entity entity) {
			sm.get(entity).sensorFixture = null;
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.ashley.systems.IteratingSystem#addedToEngine(com.badlogic.
	 * ashley.core.Engine)
	 */
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addEntityListener(Family.all(GroundedComponent.class, PhysicsComponent.class).get(), listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.systems.IteratingSystem#removedFromEngine(com.
	 * badlogic.ashley.core.Engine)
	 */
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeEntityListener(listener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.systems.IteratingSystem#update(float)
	 */

	private Array<Contact> contacts;

	@Override
	public void update(float deltaTime) {
		contacts = w.getContactList();
		super.update(deltaTime);
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

		final GroundedComponent sc = sm.get(entity);

		sc.isGrounded = isGrounded(sc, pm.get(entity));
		
//		System.out.println("State " + sc.state.name());
	}
	
	private boolean isGrounded(GroundedComponent sc, PhysicsComponent pc) {
		for (Contact contact : contacts) {
			
			if (contact.isTouching()
					&& (contact.getFixtureA() == sc.sensorFixture || contact.getFixtureB() == sc.sensorFixture)) {
				final Vector2[] contactPoints = contact.getWorldManifold().getPoints();
				final Vector2 position = pc.body.getPosition();
				boolean below = true;

				for (int i = 0; i < contact.getWorldManifold().getNumberOfContactPoints(); i++) {
					below &= contactPoints[i].y <= position.y + sc.relativeFeetHeight;
				}

				
				if (below)
					return true;
			}
		}
		
		return false;
	}

}
