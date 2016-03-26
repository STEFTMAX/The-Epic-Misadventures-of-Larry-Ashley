package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.WeldComponent;

/**
 * @author pieter3457
 *
 */
public class WeldSystem extends IteratingSystem{
	

	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<WeldComponent> wm = ComponentMapper.getFor(WeldComponent.class);
	
	public WeldSystem() {
		super(Family.all(TransformComponent.class, WeldComponent.class).get());
		
	}
	
	
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		
		WeldComponent wc = wm.get(entity);
		
		
		
		
		TransformComponent parentTransform = tm.get(wc.parent);
		TransformComponent intrinsicTransform = tm.get(entity);
		
		intrinsicTransform.transform.setToProduct(wc.transform, parentTransform.transform);
	}

}
