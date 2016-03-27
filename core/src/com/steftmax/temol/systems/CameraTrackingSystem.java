package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.CameraTargetComponent;
import com.steftmax.temol.component.TransformComponent;

/**
 * @author pieter3457
 *
 */
public class CameraTrackingSystem extends IteratingSystem {

	private Camera camera;
	private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
	private boolean updateCam;

	public CameraTrackingSystem(Camera camera) {
		this(camera, true);
	}

	public CameraTrackingSystem(Camera camera, boolean updateCam) {
		super(Family.all(CameraTargetComponent.class, TransformComponent.class).get());
		this.camera = camera;
		this.updateCam = updateCam;
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
		
		TransformComponent tc = pm.get(entity);

		camera.position.set(tc.origin.x + tc.position.x, tc.origin.y + tc.position.y, 0f);

		if (updateCam)
			camera.update();

	}

}
