package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.steftmax.temol.component.RenderableComponent;
import com.steftmax.temol.component.TransformComponent;

/**
 * @author pieter3457
 *
 */
public class RenderSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

	private final SpriteBatch batch = new SpriteBatch(), fboBatch = new SpriteBatch(1);
	private final float gameScale;

	private OrthographicCamera camera;

	private FrameBuffer fbo;

	public RenderSystem(OrthographicCamera cam, float gameScale) {
		super(Family.all(RenderableComponent.class, TransformComponent.class).get());
		this.camera = cam;
		this.gameScale = gameScale;
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 8, false);
		fbo.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.ashley.systems.IteratingSystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {
//		fbo.begin();
//		Gdx.gl.glClearColor(0, 0, 0, 0);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.update(deltaTime);
		batch.end();
//
//		fbo.end();
//
//		fboBatch.begin();
//		fboBatch.draw(fbo.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth(), 0, 0, 1, 1);
//		fboBatch.end();
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
		batch.draw(tr, tr.getRegionWidth() * gameScale, tr.getRegionHeight() * gameScale, tm.get(entity).transform);

	}

}
