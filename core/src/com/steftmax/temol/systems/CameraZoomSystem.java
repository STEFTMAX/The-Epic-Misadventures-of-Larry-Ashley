package com.steftmax.temol.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;

/**
 * @author pieter3457
 *
 */
public class CameraZoomSystem extends EntitySystem implements InputProcessor {

	private float minZoom, maxZoom, zoomSpeed, zoomFadeSpeed;
	private float startZoom, endZoom;
	private float timeZooming = 0f;
	private boolean upIn;
	private OrthographicCamera cam;
	private boolean zooming = true;

	public CameraZoomSystem(InputMultiplexer im, OrthographicCamera cam, float minZoom, float maxZoom, float zoomSpeed,
			float zoomFadeSpeed, boolean upIn) {

		im.addProcessor(this);

		this.cam = cam;
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
		this.zoomSpeed = zoomSpeed;
		this.upIn = upIn;
		this.zoomFadeSpeed = zoomFadeSpeed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.core.EntitySystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		if (zooming) {
			timeZooming += deltaTime;
			cam.zoom = Interpolation.linear.apply(startZoom, endZoom, timeZooming);
		}
		if (timeZooming > 1f) {
			timeZooming = 0f;
			zooming = false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.InputProcessor#scrolled(int)
	 */
	@Override
	public boolean scrolled(int amount) {
		if (zooming) {

			if (upIn) {
				startZoom -= amount * zoomSpeed;
			} else {

				startZoom += amount * zoomSpeed;
			}

		} else {
			timeZooming = 0f;
			zooming = true;
			startZoom = cam.zoom;
		}
		if (upIn) {
			endZoom += amount * zoomSpeed;
		} else {
			endZoom -= amount * zoomSpeed;
		}

		if (endZoom > maxZoom) {
			endZoom = maxZoom;
		}

		if (endZoom < minZoom) {
			endZoom = minZoom;
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
