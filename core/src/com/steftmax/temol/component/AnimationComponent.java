package com.steftmax.temol.component;

import java.util.HashMap;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * @author pieter3457
 *
 */
public class AnimationComponent implements Component {

	public final HashMap<String, Animation> animations;
	public boolean isPaused = false;
	public String state = "";
	public float time = 0f;
	public boolean isLooping = false;
	
	public float speedMod = 1f;
	
	public AnimationComponent() {
		animations = new HashMap<String, Animation>();
	}
	
	public AnimationComponent(HashMap<String, Animation> animations) {
		this.animations = animations;
	}
	
	public void setState(String state) {
		if (!this.state.equals(state)) {
			time = 0f;
			this.state = state;
			speedMod = 1f;
		}
	}
}
