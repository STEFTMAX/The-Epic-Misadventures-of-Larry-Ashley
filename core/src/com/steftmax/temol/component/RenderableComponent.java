package com.steftmax.temol.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author pieter3457
 *
 */
public class RenderableComponent implements Component{

	public TextureRegion region;

	public RenderableComponent(TextureRegion region) {
		this.region = region;
	}

	public RenderableComponent() {

	}
}
