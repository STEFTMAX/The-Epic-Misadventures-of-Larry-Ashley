package com.steftmax.temol.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ImmediateModeRenderer20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.steftmax.temol.component.RenderableComponent;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.listener.ResolutionListener;
import com.steftmax.temol.notifier.ResolutionNotifier;

/**
 * @author pieter3457
 *
 */
public class RenderSystem extends IteratingSystem implements ResolutionListener {

	private static final boolean DRAWGRID = false;
	private ComponentMapper<TransformComponent> tm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

	private final SpriteBatch fboBatch = new SpriteBatch(), // fboBatch is
															// responsible for
															// drawing on the
															// fbo
			screenBatch = new SpriteBatch(1); // screenBatch is responsible for
												// drawing the fbo on the screen
	private final float gameScale;
	private FrameBuffer fb = new FrameBuffer(Format.RGBA8888, 640, 480, false);
	private ShaderProgram rotShader;

	private OrthographicCamera screenCamera = new OrthographicCamera(), // responsible
																		// for
																		// drawing
																		// on
																		// the
																		// screen
			fboCam;// responsible for drawing on the fbo

	private int width, height;

	public RenderSystem(OrthographicCamera cam, float gameScale, ResolutionNotifier notifier) {
		super(Family.all(RenderableComponent.class, TransformComponent.class).get());
		notifier.addListeners(this);
		this.fboCam = cam;
		this.gameScale = gameScale;

		rotShader = new ShaderProgram(Gdx.files.internal("shader/scalex3rotation.vert"),
				Gdx.files.internal("shader/scalex3rotation.frag"));

		// Shader error checking
		if (!rotShader.isCompiled()) {
			rotShader.begin();
			System.out.println(rotShader.getLog());
			rotShader.end();
		}
	}

	/*
	 * (non-Javadoc)
	 * Procedure:
	 * Draw a low res version of the game to a fbo via the shader
	 *
	 * then
	 * 
	 * Draw the low res version (the fbo) on to the screen with the normal
	 * shader and the correct scale
	 * 
	 * @see com.badlogic.ashley.systems.IteratingSystem#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		// pass 1
		// fboBatch.setColor(0, 0, 1, 0.5f);
		fboCam.viewportWidth = fb.getWidth();
		fboCam.viewportHeight = fb.getHeight();
		fboCam.zoom = 1;
		fboCam.update();
		fboBatch.setProjectionMatrix(fboCam.combined);
		//fboBatch.setShader(rotShader);
		fb.begin();
		fboBatch.begin();
		 //rotShader.setUniformi("u_textureSize", 23, 40);// TODO make fboBatch
		// do
		Gdx.gl.glViewport(0, 0, fb.getWidth(), fb.getHeight());
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		super.update(deltaTime);
		fboBatch.end();
		fb.end();
		screenBatch.setProjectionMatrix(screenCamera.combined);
		screenBatch.setShader(null);
		screenBatch.setProjectionMatrix(screenCamera.combined);
		screenBatch.begin();
		screenBatch.draw(fb.getColorBufferTexture(), 0, 0, fb.getWidth(), fb.getHeight(), 0, 0, 1, 1);
		screenBatch.end();
		System.out.println(rotShader.getLog());

		if (DRAWGRID) {
			// draw raster
			lineRenderer.begin(fboCam.combined, GL30.GL_LINES);
			// ... lineRenderer works a lot
			grid(100, 100, .5f, .5f, .5f, .5f);
			// ... lineRenderer works a lot
			lineRenderer.end();
		}

	}

	static ImmediateModeRenderer20 lineRenderer = new ImmediateModeRenderer20(false, true, 0);

	// create atomic method for line
	public static void line(float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b,
			float a) {
		lineRenderer.color(r, g, b, a);
		lineRenderer.vertex(x1, y1, z1);
		lineRenderer.color(r, g, b, a);
		lineRenderer.vertex(x2, y2, z2);
	}

	// method for whole grid
	public static void grid(int width, int height, float r, float g, float b, float a) {
		for (int x = 0; x <= width; x++) {
			// draw vertical
			line(x, 0, 0, x, height, 0, r, g, b, a);

		}
		for (int y = 0; y <= height; y++) {
			// draw horizontal
			line(0, y, 0, width, y, 0, r, g, b, a);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.steftmax.temol.listener.ResolutionListener#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {

		screenCamera.viewportWidth = width;
		screenCamera.viewportHeight = height;
		screenCamera.update();

		this.width = width;
		this.height = height;

		int widthScale = (int) Math.ceil((float) width / fb.getWidth());
		int heightScale = (int) Math.ceil((float) height / fb.getHeight());

		screenCamera.position.set(Math.round(fb.getWidth() / 2f), Math.round(fb.getHeight() / 2f), 0f);
		float scale = Math.max(widthScale, heightScale);
		screenCamera.zoom = 1f / scale;
		screenCamera.update();

		fb.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		System.out.println(width % scale == 0 ? "true" : "untrue");// TODO force
																	// same
																	// thingy
																	// you know
																	// what to
		// rotShader.begin();
		// rotShader.setUniformi("u_resolution", width, height);
		// rotShader.end();
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
		fboBatch.draw(tr, tr.getRegionWidth(), tr.getRegionHeight(), tm.get(entity).transform);

	}

}
