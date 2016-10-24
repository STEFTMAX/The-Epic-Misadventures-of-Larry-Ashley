package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.steftmax.temol.component.AnimationComponent;
import com.steftmax.temol.component.GravityComponent;
import com.steftmax.temol.component.PlayerComponent;
import com.steftmax.temol.component.RotationComponent;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.VelocityComponent;

/**
 * @author pieter3457
 *
 */
public class PlayerControllerSystem extends IteratingSystem {

	private static final float MAXVELOCITY = 80f;
	private static final float JUMP = 100f;
	private static final float MOVEACCELERATION = 80f;
	private static final float STANDACCELERATION = 400f;
	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<GravityComponent> gm = ComponentMapper.getFor(GravityComponent.class);
	private ComponentMapper<RotationComponent> rm = ComponentMapper.getFor(RotationComponent.class);
	private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
	
	public PlayerControllerSystem() {
		super(Family.all(PlayerComponent.class, VelocityComponent.class, GravityComponent.class, AnimationComponent.class).get());
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
		final VelocityComponent vc = vm.get(entity);
		final GravityComponent gc = gm.get(entity);
		
		final RotationComponent rc = rm.get(entity);

		final AnimationComponent ac = am.get(entity);
		
		if (gc.isGrounded) {
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				vc.velocity.y = JUMP;
				gc.isGrounded = false;

			} else {
				if (Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
					
					
					if (vc.velocity.x > 0) {
						ac.setState("stand");
						brake(vc, deltaTime);
					} else {
						ac.setState("walk");
						tc.scale.x = -1; // head left
						vc.velocity.x -= MOVEACCELERATION * deltaTime;
						ac.speedMod = Math.abs(vc.velocity.x/MAXVELOCITY);
					}
				}

				if (Gdx.input.isKeyPressed(Input.Keys.D) && !Gdx.input.isKeyPressed(Input.Keys.A)) {
					
					
					if (vc.velocity.x < 0) {
						ac.setState("stand");
						brake(vc, deltaTime);
					} else {
						ac.setState("walk");
						tc.scale.x = 1; // head right
						vc.velocity.x += MOVEACCELERATION * deltaTime;
						ac.speedMod = Math.abs(vc.velocity.x/MAXVELOCITY);
						
					}
				}

				if (!Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.D)) {
					
					ac.setState("stand");
					
					brake(vc, deltaTime);
					
				}

				if (Math.abs(vc.velocity.x) > MAXVELOCITY) {
					vc.velocity.x = Math.signum(vc.velocity.x) * MAXVELOCITY;
				}
			}
		}
		
		if (rc != null) {
			if (Gdx.input.isKeyPressed(Input.Keys.J)) {
				tc.rotation -= .05f *deltaTime;
			}
			
			if (Gdx.input.isKeyPressed(Input.Keys.K)) {
				tc.rotation += .05f *deltaTime;
			}
		}
	}

	private void brake(VelocityComponent vc, float deltaTime) {
		float decrease = Math.signum(vc.velocity.x) * STANDACCELERATION * deltaTime;
		
		if (Math.abs(decrease) > Math.abs(vc.velocity.x)) {
			vc.velocity.x = 0;
		} else {
			vc.velocity.x -= decrease;
		}
	}
}
