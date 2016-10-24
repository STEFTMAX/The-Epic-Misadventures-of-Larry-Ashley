package com.steftmax.temol.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.steftmax.temol.component.AnimationComponent;
import com.steftmax.temol.component.CameraTargetComponent;
import com.steftmax.temol.component.CollisionComponent;
import com.steftmax.temol.component.GravityComponent;
import com.steftmax.temol.component.PlayerComponent;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.RenderableComponent;
import com.steftmax.temol.component.RotationComponent;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.VelocityComponent;
import com.steftmax.temol.component.WeldComponent;
import com.steftmax.temol.gfx.parrallax.Parrallaxer;
import com.steftmax.temol.notifier.ResolutionNotifier;
import com.steftmax.temol.systems.AnimationSystem;
import com.steftmax.temol.systems.CameraTrackingSystem;
import com.steftmax.temol.systems.CameraZoomSystem;
import com.steftmax.temol.systems.CollisionSystem;
import com.steftmax.temol.systems.DebugRenderSystem;
import com.steftmax.temol.systems.GravitySystem;
import com.steftmax.temol.systems.MovementSystem;
import com.steftmax.temol.systems.PlayerControllerSystem;
import com.steftmax.temol.systems.RenderSystem;
import com.steftmax.temol.systems.TransformSystem;
import com.steftmax.temol.systems.WeldSystem;
import com.steftmax.temol.tool.Constants;
import com.steftmax.temol.tool.PixelArtScaler;
import com.sun.corba.se.impl.orbutil.closure.Constant;

import sun.net.www.content.text.plain;

/**
 * @author pieter3457
 *
 */
public class GameScreen extends ScreenAdapter {

	private Engine entityEngine;
	private Game g;
	private TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	private OrthographicCamera camera;

	private SpriteBatch batch = new SpriteBatch(10);

	private Parrallaxer para;

	private InputMultiplexer inputMultiplexer = new InputMultiplexer();

	ResolutionNotifier rn = new ResolutionNotifier();

	public GameScreen(Game g) {

		this.g = g;
		// -----Parrallaxer

		para = new Parrallaxer(camera, 4, 1, 1);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer1.png")), .4f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer2.png")), .5f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer3.png")), .75f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer4.png")), .8f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer5.png")), .9f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer6.png")), 2);

		// -----Tiled Map-----
		map = new TmxMapLoader().load("maps/testmap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, 1);
		MapProperties prop = map.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		int mapPixelWidth = mapWidth * tilePixelWidth;
		int mapPixelHeight = mapHeight * tilePixelHeight;

		// -----Camera & Viewport-----
		camera = new OrthographicCamera();
		// camera.zoom = Constants.SCALE / 2;
		camera.update();
		// -----Ashley-----

		entityEngine = new Engine();

		entityEngine.addSystem(new PlayerControllerSystem());
		entityEngine.addSystem(new GravitySystem(new Vector2(0, -10 * 21)));
		entityEngine.addSystem(new MovementSystem());
		entityEngine.addSystem(new TransformSystem());
		entityEngine.addSystem(new WeldSystem());
		entityEngine.addSystem(new CollisionSystem(map));
		entityEngine.addSystem(new AnimationSystem());

		// entityEngine.addSystem(new CameraZoomSystem(inputMultiplexer, camera,
		// Constants.SCALE / 5f,
		// Constants.SCALE / 2f, .008f, .19f, true));
		entityEngine.addSystem(new CameraTrackingSystem(camera));

		entityEngine.addSystem(new DebugRenderSystem(camera));
		entityEngine.addSystem(new RenderSystem(camera, rn, mapRenderer, para));

		Entity ent = new Entity();

		ent.add(new CollisionComponent());
		ent.add(new VelocityComponent());
		ent.add(new CameraTargetComponent());
		ent.add(new GravityComponent());
		ent.add(new PlayerComponent());
		TransformComponent trnsfrm = new TransformComponent(5f, 30 * 9);
		trnsfrm.origin.set(23 * .5f, 26);
		ent.add(trnsfrm);
		ent.add(new RenderableComponent());

		AnimationComponent ac = new AnimationComponent();

		ac.state = "stand";
		ac.animations.put("stand", new Animation(1, new TextureRegion(new Texture("gfx/larry/standing.png"))));

		TextureRegion[] frames = new TextureRegion[8];
		for (int i = 0; i < frames.length; i++) {
			frames[i] = new TextureRegion(new Texture("gfx/larry/larry_walking/" + (i+1) + ".png"));
		}

		Animation anim = new Animation(.06f, frames);
		anim.setPlayMode(PlayMode.LOOP);
		ac.animations.put("walk", anim);

		ent.add(ac);

		entityEngine.addEntity(ent);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#resize(int, int)
	 */
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
		camera.update();
		rn.notify(width, height);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#render(float)
	 */
	@Override
	public void render(float delta) {

		// da updating
		// System.out.println("loop'd");
		entityEngine.update(delta);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#show()
	 */
	@Override
	public void show() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#dispose()
	 */
	@Override
	public void dispose() {
		mapRenderer.dispose();
		map.dispose();

	}
}
