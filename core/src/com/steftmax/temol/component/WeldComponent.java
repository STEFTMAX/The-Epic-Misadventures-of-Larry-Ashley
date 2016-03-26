package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

/**
 * @author pieter3457
 *
 */
public class WeldComponent extends TransformComponent implements Component{

	public Entity parent;
	
	public WeldComponent(Entity parent, float x, float y) {
		super(x, y);
		this.parent = parent;
	}
}
