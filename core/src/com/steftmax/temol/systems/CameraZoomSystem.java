package com.steftmax.temol.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * @author pieter3457
 *
 */
public class CameraZoomSystem extends EntitySystem implements InputProcessor {

	private float minZoom, maxZoom, zoomSpeed;
	private boolean upIn;
	private OrthographicCamera cam;

	public CameraZoomSystem(InputMultiplexer im, OrthographicCamera cam, float minZoom, float maxZoom, float zoomSpeed,
			boolean upIn) {

		im.addProcessor(this);

		this.cam = cam;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.zoomSpeed = zoomSpeed;
		this.upIn = upIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		if (upIn) {
			cam.zoom += amount * zoomSpeed;
		} else {
			cam.zoom -= amount * zoomSpeed;
		}
		
		if (cam.zoom > maxZoom) {
			cam.zoom = maxZoom;
		}
		if (cam.zoom < minZoom) {
			cam.zoom = minZoom;
		}
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
