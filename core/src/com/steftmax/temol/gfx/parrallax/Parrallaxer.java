package com.steftmax.temol.gfx.parrallax;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author pieter3457
 *
 */
public class Parrallaxer {

	private Camera cam;
	private float scale;

	private List<ParrallaxLayer> layers = new ArrayList<ParrallaxLayer>();

	private int pixelMapWidth, pixelMapHeight;

	public Parrallaxer(Camera cam, float scale, int pixelMapWidth, int pixelMapHeight) {
		this.pixelMapHeight = pixelMapHeight;
		this.pixelMapWidth = pixelMapWidth;
		this.cam = cam;
		this.scale = scale;
	}

	public void draw(SpriteBatch batch) {
		for (int i = 0; i < layers.size(); i++) {
			layers.get(i).draw(batch);
		}
	}

	// Adds a new layer. The later the layer is added, the more to the
	// foreground it will be.
	public void addLayer(TextureRegion layer, float speed) {
		layers.add(new ParrallaxLayer(speed, speed, layer, cam, scale));
	}

	public void addLayer(TextureRegion layer) {
		layers.add(new ParrallaxLayer((float) layer.getRegionWidth() / pixelMapWidth,
				(float) layer.getRegionHeight() / pixelMapHeight, layer, cam, scale));
	}
}
