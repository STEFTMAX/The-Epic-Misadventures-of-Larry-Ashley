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
public class DebugRenderSystem extends IteratingSystem {

	private ComponentMapper<CollisionComponent> cm = ComponentMapper.getFor(CollisionComponent.class);
	private ShapeRenderer renderer= new ShapeRenderer();
	private Camera cam;
	/**
	 * @param family
	 */
	public DebugRenderSystem(Camera cam) {
		super(Family.all(CollisionComponent.class).get());
		this.cam = cam;
	}

	
	
	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		renderer.setColor(1f,1f,1f,1f);
		renderer.setProjectionMatrix(cam.combined);
		renderer.begin(ShapeType.Line);
		super.update(deltaTime);
		renderer.end();
	}
	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		Rectangle bounds = cm.get(entity).bounds;
		renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
	}

}
