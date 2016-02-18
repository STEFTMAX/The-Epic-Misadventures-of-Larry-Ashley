package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.CollisionComponent;
import com.steftmax.temol.component.PositionComponent;
import com.steftmax.temol.component.VelocityComponent;

/**
 * @author pieter3457
 *
 */
public class CollisionSystem extends IteratingSystem{

	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<CollisionComponent> bm = ComponentMapper.getFor(CollisionComponent.class);
	
	private TiledMap map;
	private TiledMapTileLayer layer;
	
	public CollisionSystem(TiledMap map) {
		super(null);//TODO
		
		this.map = map;
		layer = (TiledMapTileLayer) map.getLayers().get("tiles");
	}

	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		final Rectangle bounds = bm.get(entity).bounds;
		
		final Vector2 position = pm.get(entity).position;
		final Vector2 velocity = vm.get(entity).velocity;
		
		
		
		
		bounds.setCenter(position);
	}

}
