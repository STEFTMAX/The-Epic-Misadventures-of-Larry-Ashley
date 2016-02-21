package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.steftmax.temol.component.CollisionComponent;

/**
 * @author pieter3457
 *
 */
public class BoundsRenderer extends IteratingSystem {
	
	private ComponentMapper<CollisionComponent> bm = ComponentMapper.getFor(CollisionComponent.class);
	private ShapeRenderer sr = new ShapeRenderer();
	private Camera cam;
	
	public BoundsRenderer(Camera cam){
		super(Family.all(CollisionComponent.class).get());
		this.cam = cam;
	}
	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final Rectangle bounds = bm.get(entity).bounds;
		sr.setProjectionMatrix(cam.combined);
		sr.begin(ShapeType.Line);
		sr.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		sr.end();
	}

}
