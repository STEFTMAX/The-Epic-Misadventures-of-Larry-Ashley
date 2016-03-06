package com.steftmax.temol.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;

/**
 * @author pieter3457
 *
 */
public class CameraZoomSystem extends EntitySystem implements InputProcessor {

	private float minZoom, maxZoom, zoomSpeed, zoomFadeTime;
	private float startZoom, endZoom, actualZoom;
	private float timeZooming = 0f;
	private boolean upIn;
	private OrthographicCamera cam;
//	private boolean zooming = true;

	public CameraZoomSystem(InputMultiplexer im, OrthographicCamera cam, float minZoom, float maxZoom, float zoomSpeed,
			float zoomFadeTime, boolean upIn) {

		im.addProcessor(this);

		this.cam = cam;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.zoomSpeed = zoomSpeed;
		this.upIn = upIn;
		this.zoomFadeTime = zoomFadeTime;
		this.actualZoom = this.startZoom = this.endZoom = cam.zoom;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.core.EntitySystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {

		timeZooming += deltaTime / zoomFadeTime;
		timeZooming = Math.min(timeZooming, 1f);
		actualZoom = Interpolation.linear.apply(startZoom, endZoom, timeZooming);
		
		cam.zoom = actualZoom;
		
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public synchronized boolean scrolled(int amount) {
		timeZooming = 0f;
		startZoom = actualZoom;
		
		if (upIn) {
			endZoom += amount * zoomSpeed;
		} else {
			endZoom -= amount * zoomSpeed;
		}
		

		endZoom = Math.max(endZoom, minZoom);
		endZoom = Math.min(endZoom, maxZoom);

		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
}
