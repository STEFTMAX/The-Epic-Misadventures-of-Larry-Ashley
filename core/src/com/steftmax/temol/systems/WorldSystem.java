package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.steftmax.temol.component.PhysicsComponent;
import com.steftmax.temol.component.PhysicsDefComponent;

/**
 * @author pieter3457
 *
 */
public class WorldSystem extends EntitySystem {

	private float timeStep;
	private int velocityIterations;
	private int positionIterations;
	private final World w;

	private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);
	private ComponentMapper<PhysicsDefComponent> pdm = ComponentMapper.getFor(PhysicsDefComponent.class);

	public WorldSystem(World w, float timeStep, int velocityIterations, int positionIterations) {
		this.w = w;

		this.timeStep = timeStep;
		this.velocityIterations = velocityIterations;
		this.positionIterations = positionIterations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.ashley.core.EntitySystem#addedToEngine(com.badlogic.ashley.
	 * core.Engine)
	 */

	EntityListener la = new EntityListener() {

		@Override
		public void entityAdded(Entity entity) {
			// Step 1: create the parts from the definition

			// Step 2: remove the definitions, because these are no longer
			// needed after creation

			// Step 1
			final PhysicsDefComponent pdc = pdm.get(entity);

			// create the body
			final Body body = w.createBody(pdc.bodyDef);

			// create the fixtures
			final Fixture[] fixtures = new Fixture[pdc.fixtureDefs.length];
			for (int i = 0; i < pdc.fixtureDefs.length; i++) {
				fixtures[i] = body.createFixture(pdc.fixtureDefs[i]);
			}

			entity.add(new PhysicsComponent(body, fixtures));

			// Step 2

			for (int i = 0; i < pdc.fixtureDefs.length; i++) {
				// dispose each individual shape because they arent cleaned
				// otherwise
				pdc.fixtureDefs[i].shape.dispose();
			}

			entity.remove(PhysicsDefComponent.class);
		}

		@Override
		public void entityRemoved(Entity entity) {
		}
	}, lb = new EntityListener() {

		@Override
		public void entityRemoved(Entity entity) {
			w.destroyBody(pm.get(entity).body);
		}

		@Override
		public void entityAdded(Entity entity) {
		}
	};

	@Override
	public void addedToEngine(Engine engine) {

		// PERFORMANCE: When many physical entities are spawned a lot of physics
		// definitions are instanced and dereferenced.
		engine.addEntityListener(Family.all(PhysicsDefComponent.class).get(), la);

		// Tested and this works!
		engine.addEntityListener(Family.all(PhysicsComponent.class).get(), lb);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.ashley.core.EntitySystem#removedFromEngine(com.badlogic.
	 * ashley.core.Engine)
	 */
	@Override
	public void removedFromEngine(Engine engine) {
		engine.removeEntityListener(la);
		engine.removeEntityListener(lb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.core.EntitySystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		w.step(timeStep, velocityIterations, positionIterations);
	}

	// getters and setters

	/**
	 * @return the world
	 */
	public World getWorld() {
		return w;
	}

	/**
	 * @return the positionIterations
	 */
	public int getPositionIterations() {
		return positionIterations;
	}

	/**
	 * @return the velocityIterations
	 */
	public int getVelocityIterations() {
		return velocityIterations;
	}

	/**
	 * @param timeStep
	 *            the timeStep to set
	 */
	public void setTimeStep(float timeStep) {
		this.timeStep = timeStep;
	}

	/**
	 * @param velocityIterations
	 *            the velocityIterations to set
	 */
	public void setVelocityIterations(int velocityIterations) {
		this.velocityIterations = velocityIterations;
	}

	/**
	 * @param positionIterations
	 *            the positionIterations to set
	 */
	public void setPositionIterations(int positionIterations) {
		this.positionIterations = positionIterations;
	}
}
