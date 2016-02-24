package com.steftmax.temol.gfx.parrallax;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * @author pieter3457
 *
 */
public class ParrallaxLayer {
	private float xSpeed, ySpeed;
	private Sprite layer;
	// public float xOffset = 0, yOffset = 0;
	private Camera cam;
	private float scale;
	
	
	public ParrallaxLayer(float xSpeed, float ySpeed, TextureRegion layer, Camera cam, float scale) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.layer = new Sprite(layer);
		this.cam = cam;
		this.scale = scale;
		
		this.layer.setOrigin(0f, 0f);
	}

	public void draw(SpriteBatch batch) {
		layer.setPosition(cam.position.x * (1f-xSpeed), cam.position.y * (1f - ySpeed));
		layer.setScale(scale);
		
		
		layer.draw(batch);
	}

}
