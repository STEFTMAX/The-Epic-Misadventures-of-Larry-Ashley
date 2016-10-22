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

	final Vector2 correctedPosition = new Vector2(), correctedWeldPosition = new Vector2();
	float t;

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		final TransformComponent tc = tm.get(entity);
		final WeldComponent wc = wm.get(entity);

		// position correction mechanism for the rotation shader to work
		// correctly on normal objects
		float offset = 3f / 8f;

		correctedPosition.set(((float) Math.round(tc.position.x)) + offset,
				((float) (Math.round(tc.position.y))) + offset);
		// correctedPosition.set(tc.position);

		calculateAffine2(tc.transform, correctedPosition, tc.origin, tc.scale, tc.rotation);
		// TODO check if it works too for the weld
		if (wc != null) {
			correctedWeldPosition.set(((float) Math.round(wc.position.x)) + offset,
					((float) (Math.round(wc.position.y))) + offset);
			calculateAffine2(wc.transform, correctedWeldPosition, wc.origin, wc.scale, wc.rotation);
		}
	}

	private void calculateAffine2(Affine2 affine2, Vector2 position, Vector2 origin, Vector2 scale, float rotation) {

		affine2.setToTrnRotRadScl(position.x, position.y, rotation, scale.x, scale.y).translate(-origin.x, -origin.y);
	}
}
