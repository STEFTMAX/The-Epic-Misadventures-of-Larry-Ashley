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
	
	
	public Parrallaxer(Camera cam, float scale) {
		this.cam = cam;
		this.scale = scale;
	}
	
	public void draw(SpriteBatch batch) {
		for (int i = 0; i < layers.size(); i ++) {
			layers.get(i).draw(batch);
		}
	}

	//Adds a new layer. The later the layer is added, the more to the foreground it will be.
	public void addLayer(TextureRegion layer, float speed) {
		layers.add(new ParrallaxLayer(speed, speed, layer, cam, scale));
	}
	
	public void addLayer(TextureRegion layer, float xSpeed, float ySpeed) {
		layers.add(new ParrallaxLayer(xSpeed, ySpeed, layer, cam,scale));
	}
}
