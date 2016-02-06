package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.steftmax.temol.component.PositionComponent;

/**
 * @author pieter3457
 *
 */
public class PositionLoggerSystem extends IteratingSystem{


	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	
	/**
	 * @param family
	 */
	public PositionLoggerSystem() {
		super(Family.all(PositionComponent.class).get());
	}

	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		PositionComponent pc = pm.get(entity);
		Gdx.app.log(getClass().getName(),"Entity "+ entity +" position: x: " + pc.x + " y: "+ pc.y);
	}
	
	

}
