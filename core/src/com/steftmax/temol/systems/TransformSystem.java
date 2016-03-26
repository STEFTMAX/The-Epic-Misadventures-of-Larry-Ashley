package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.WeldComponent;

/**
 * @author pieter3457
 *
 */
public class TransformSystem extends IteratingSystem {

	ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<WeldComponent> wm = ComponentMapper.getFor(WeldComponent.class);

	public TransformSystem() {
		super(Family.all(TransformComponent.class).get());
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
		final TransformComponent tc = tm.get(entity);
		final WeldComponent wc = wm.get(entity);
		calculateAffine2(tc.transform, tc.position, tc.origin, tc.scale, tc.rotation);
		if (wc != null)
			calculateAffine2(wc.transform, wc.position, wc.origin, wc.scale, wc.rotation);
	}

	private void calculateAffine2(Affine2 affine2, Vector2 position, Vector2 origin, Vector2 scale, float rotation) {

		affine2.setToTrnRotRadScl(position.x, position.y - origin.y, rotation, scale.x, scale.y).translate(-origin.x,
				-origin.y);
	}
}
