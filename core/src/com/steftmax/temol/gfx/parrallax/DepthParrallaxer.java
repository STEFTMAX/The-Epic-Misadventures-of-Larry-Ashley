package com.steftmax.temol.gfx.parrallax;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * @author pieter3457
 *
 */
public class DepthParrallaxer {
	
	private int pixelMapWidth, pixelMapHeight;

	private List<ParrallaxLayer> layers = new ArrayList<ParrallaxLayer>();
	
	public DepthParrallaxer(int pixelMapWidth, int pixelMapHeight) {
		this.pixelMapHeight = pixelMapHeight;
		this.pixelMapWidth = pixelMapWidth;
	}

	
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < layers.size(); i ++) {
			layers.get(i).draw(batch);
		}
	}
}
