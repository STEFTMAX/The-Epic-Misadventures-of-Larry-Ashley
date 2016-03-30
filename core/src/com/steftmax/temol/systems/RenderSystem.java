package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.steftmax.temol.component.RenderableComponent;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.listener.ResolutionListener;
import com.steftmax.temol.notifier.ResolutionNotifier;

/**
 * @author pieter3457
 *
 */
public class RenderSystem extends IteratingSystem implements ResolutionListener {

	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

	private final SpriteBatch batch = new SpriteBatch(), fboBatch = new SpriteBatch(1);
	private final float gameScale;
	private FrameBuffer fb;

	private OrthographicCamera camera;

	public RenderSystem(OrthographicCamera cam, float gameScale, ResolutionNotifier notifier) {
		super(Family.all(RenderableComponent.class, TransformComponent.class).get());
		notifier.addListeners(this);
		
		this.camera = cam;
		this.gameScale = gameScale;

		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.systems.IteratingSystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		camera.viewportWidth = fb.getWidth();
		camera.viewportHeight = fb.getHeight();
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		fb.begin();
		batch.begin();
		Gdx.gl.glViewport(0, 0, fb.getWidth(), fb.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		super.update(deltaTime);
		batch.end();
		fb.end();
	}
	
	
	/* (non-Javadoc)
	 * @see com.steftmax.temol.listener.ResolutionListener#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		float ratio = width / ((float) height);

		 System.out.println("ratio: " + ratio);

		final int size = 1000;
		width = (int) (size / ratio);
		height = (int) (size * ratio);

		System.out.println("width: " + width + " height: " + height);

		if (fb != null)
			fb.dispose();
		
		fb = new FrameBuffer(Format.RGBA8888, width, height, false);
		fb.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}
	
	private void drawToScreen() {

		
		
		fboBatch.begin();
		fboBatch.draw(fb.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
		fboBatch.end();
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.
	 * ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		TextureRegion tr = rm.get(entity).region;
		// Note: the scaling happens here, that is quite important to know
		batch.draw(tr, tr.getRegionWidth(), tr.getRegionHeight(), tm.get(entity).transform);

	}

}
