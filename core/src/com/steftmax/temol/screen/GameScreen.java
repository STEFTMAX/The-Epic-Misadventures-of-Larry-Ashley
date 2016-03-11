package com.steftmax.temol.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.steftmax.temol.component.CameraTargetComponent;
import com.steftmax.temol.component.GroundedComponent;
import com.steftmax.temol.component.PhysicsDefComponent;
import com.steftmax.temol.component.PlayerComponent;
import com.steftmax.temol.gfx.parrallax.Parrallaxer;
import com.steftmax.temol.component.PositionComponent;
import com.steftmax.temol.component.VelocityComponent;
import com.steftmax.temol.systems.BoundsRenderer;
import com.steftmax.temol.systems.CameraTrackingSystem;
import com.steftmax.temol.systems.CollisionSystem;
import com.steftmax.temol.systems.GravitySystem;
import com.steftmax.temol.systems.MovementSystem;
import com.steftmax.temol.systems.PlayerControllerSystem;
import com.steftmax.temol.systems.CameraTrackingSystem;
import com.steftmax.temol.systems.CameraZoomSystem;
import com.steftmax.temol.systems.GroundedSystem;
import com.steftmax.temol.systems.PlayerControllerSystem;
import com.steftmax.temol.systems.WorldSystem;
import com.steftmax.temol.tool.Box2DMapObjectParser;
import com.steftmax.temol.tool.ChainableBodyDef;
import com.steftmax.temol.tool.ChainableFixtureDef;
import com.steftmax.temol.tool.Constants;

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

	public static final float LARRYHEIGHT = 1.5f; // Meters
	public static final float SCALE = LARRYHEIGHT / 32f;// in meters per pixe

	private SpriteBatch batch = new SpriteBatch(10);

	private Parrallaxer para;

	private InputMultiplexer inputMultiplexer = new InputMultiplexer();

	public GameScreen(Game g) {
		this.g = g;

		// -----Tiled Map-----
		map = new TmxMapLoader().load("maps/testmap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, SCALE);  
		MapProperties prop = map.getProperties();

		int mapWidth = prop.get("width", Integer.class);
		int mapHeight = prop.get("height", Integer.class);
		int tilePixelWidth = prop.get("tilewidth", Integer.class);
		int tilePixelHeight = prop.get("tileheight", Integer.class);

		int mapPixelWidth = mapWidth * tilePixelWidth;
		int mapPixelHeight = mapHeight * tilePixelHeight;
		// -----Box2D-----
		w = new World(new Vector2(0, -9.81f), true);
		new Box2DMapObjectParser(SCALE).load(w, map);
		b2dr = new Box2DDebugRenderer();

		// -----Camera-----
		camera = new OrthographicCamera();
		camera.zoom = SCALE / 2;
		camera.update();

		// -----Ashley-----

		entityEngine = new Engine();

		entityEngine.addSystem(new PlayerControllerSystem());
		entityEngine.addSystem(new GravitySystem(new Vector2(0, -10)));
		entityEngine.addSystem(new MovementSystem());
		entityEngine.addSystem(new CollisionSystem(map));

		entityEngine.addSystem(new CameraZoomSystem(inputMultiplexer, camera, SCALE / 5f, SCALE /2f, .008f, .19f, true));
		entityEngine.addSystem(new CameraTrackingSystem(camera));

		Entity ent = new Entity();


		ent.add(new CollisionComponent(new Rectangle(0, 0, 2, 2)));
		ent.add(new PositionComponent(5, 15));
		ent.add(new VelocityComponent());
		ent.add(new CameraTargetComponent());
		ent.add(new GravityComponent());
		ent.add(new PlayerComponent());

		entityEngine.addEntity(ent);

		para = new Parrallaxer(camera,SCALE*4, mapPixelWidth,mapPixelHeight);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer1.png")), .4f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer2.png")), .5f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer3.png")), .75f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer4.png")), .8f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer5.png")), .9f);
		para.addLayer(new TextureRegion(new Texture("gfx/Layer6.png")), 2);

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

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		para.draw(batch);
		batch.end();
		// mapRenderer.setView(camera);
		// mapRenderer.render();
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
		w.dispose();
		map.dispose();

	}
}
