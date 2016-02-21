package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.steftmax.temol.component.GravityComponent;
import com.steftmax.temol.component.PlayerComponent;
import com.steftmax.temol.component.PositionComponent;
import com.steftmax.temol.component.VelocityComponent;

/**
 * @author pieter3457
 *
 */
public class PlayerControllerSystem extends IteratingSystem {

	private static final float MAXVELOCITY = 4f;
	private static final float JUMP = 4f;
	private static final float MOVEACCELERATION = 4f;
	private static final float STANDACCELERATION = 10f;
//	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<GravityComponent> gm = ComponentMapper.getFor(GravityComponent.class);

	public PlayerControllerSystem() {
		super(Family.all(PlayerComponent.class, PositionComponent.class, VelocityComponent.class).get());
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

//		final PositionComponent pc = pm.get(entity);
		final VelocityComponent vc = vm.get(entity);
		final GravityComponent gc = gm.get(entity);

		if (gc.isGrounded) {
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				vc.velocity.y = JUMP;
				gc.isGrounded = false;

			} else {
				if (Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
					// pc.body.applyForceToCenter(-10, 0, true);
					vc.velocity.x -= MOVEACCELERATION * deltaTime;
				}

				if (Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
					// pc.body.applyForceToCenter(10, 0, true);
					vc.velocity.x += MOVEACCELERATION * deltaTime;
				}

				if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
					
					float decrease = Math.signum(vc.velocity.x) * STANDACCELERATION * deltaTime;
					
					if (Math.abs(decrease) > Math.abs(vc.velocity.x)) {
						vc.velocity.x = 0;
					} else {
						vc.velocity.x -= decrease;
					}
				}

				if (Math.abs(vc.velocity.x) > MAXVELOCITY) {
					vc.velocity.x = Math.signum(vc.velocity.x) * MAXVELOCITY;
				}
			}
		}
	}

}
