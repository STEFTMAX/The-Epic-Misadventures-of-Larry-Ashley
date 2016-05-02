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
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
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
	private ShaderProgram rotShader;

	private OrthographicCamera camera, fboCam = new OrthographicCamera();

	private int width, height;

	public RenderSystem(OrthographicCamera cam, float gameScale, ResolutionNotifier notifier) {
		super(Family.all(RenderableComponent.class, TransformComponent.class).get());
		notifier.addListeners(this);
		this.camera = cam;
		this.gameScale = gameScale;
		rotShader = new ShaderProgram(Gdx.files.internal("shader/default.vert"),
				Gdx.files.internal("shader/default.frag"));
		System.out.println(rotShader.getLog());
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
		camera.zoom = 1f;
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		fb.begin();
		batch.setShader(rotShader);
		batch.begin();
		Gdx.gl.glViewport(0, 0, fb.getWidth(), fb.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.update(deltaTime);
		batch.end();
		fb.end();

		drawToScreen();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.steftmax.temol.listener.ResolutionListener#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {

		fboCam.viewportWidth = width;
		fboCam.viewportHeight = height;
		fboCam.update();

		this.width = width;
		this.height = height;

		if (fb != null)
			fb.dispose();

		final int standardWidth = 640;
		final int standardHeight = 480;
		fb = new FrameBuffer(Format.RGBA8888, standardWidth, standardHeight, false);

		float widthScale = (float) Math.ceil((float) width / standardWidth);
		float heightScale = (float) Math.ceil((float) height / standardHeight);

		fboCam.position.set(Math.round(fb.getWidth() / 2f), Math.round(fb.getHeight() / 2f), 0f);
		float scale = Math.max(widthScale, heightScale);
		fboCam.zoom = 1f / scale;
		fboCam.update();

		fb.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		System.out.println(width % scale == 0 ? "true" : "untrue");// TODO force
																	// same
																	// thingy
																	// you know
																	// what to
	}

	private void drawToScreen() {

		// fboCam.zoom = 1f/ (float)Math.sqrt( (width * (float) height)/
		// (fb.getWidth() * fb.getHeight()));
		// fboCam.update();
		// Gdx.gl.glClearColor(0, 0, 1, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//
		// final int standardWidth = 640;
		// final int standardHeight = 480;
		//
		// final int widthScale = (int) Math.ceil((float) width /
		// standardWidth);
		// final int heightScale = (int) Math.ceil((float) height /
		// standardHeight);
		// final int scale = Integer.max(widthScale, heightScale);
		//
		// final int drawWidth = fb.getWidth() * scale;
		// final int drawHeight = fb.getHeight() * scale;
		//
		// fboCam.position.set(drawWidth / 2, drawHeight / 2, 0);
		// fboCam.update();

		fboBatch.setProjectionMatrix(fboCam.combined);
		fboBatch.begin();
		fboBatch.draw(fb.getColorBufferTexture(), 0, 0, fb.getWidth(), fb.getHeight(), 0, 0, 1, 1);
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
