package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.steftmax.temol.component.AnimationComponent;
import com.steftmax.temol.component.RenderableComponent;

/**
 * @author pieter3457
 *         Yaww bitch this is where real shite happens bro
 */
public class AnimationSystem extends IteratingSystem {

	private final ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
	private final ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

	public interface State {
		public void update(float delta);
	}

	public AnimationSystem() {
		super(Family.all( AnimationComponent.class, RenderableComponent.class).get());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		AnimationComponent ac = am.get(entity);
		RenderableComponent rc = rm.get(entity);

		if (ac.animations.containsKey(ac.state)) {
			rc.region = ac.animations.get(ac.state).getKeyFrame(ac.time, ac.isLooping);
		}

		if (!ac.isPaused) {
			ac.time += deltaTime * ac.speedMod;
		}

	}

}
