package com.steftmax.temol.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.steftmax.temol.component.CameraTargetComponent;
import com.steftmax.temol.component.CollisionComponent;
import com.steftmax.temol.component.GravityComponent;
import com.steftmax.temol.component.PlayerComponent;
import com.steftmax.temol.component.PositionComponent;
import com.steftmax.temol.component.VelocityComponent;
import com.steftmax.temol.systems.CameraTrackingSystem;
import com.steftmax.temol.systems.GravitySystem;
import com.steftmax.temol.systems.MovementSystem;
import com.steftmax.temol.systems.PlayerControllerSystem;
import com.steftmax.temol.tool.Box2DMapObjectParser;

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
	public static final float SCALE = LARRYHEIGHT / 32f;// in meters per pixel

	public GameScreen(Game g) {
		this.g = g;

		// -----Tiled Map-----
		map = new TmxMapLoader().load("maps/testmap.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map, SCALE);

		// -----Camera-----
		camera = new OrthographicCamera();
		camera.zoom = SCALE / 2;
		camera.update();

		// -----Ashley-----

		entityEngine = new Engine();

		entityEngine.addSystem(new PlayerControllerSystem());
		entityEngine.addSystem(new GravitySystem(new Vector2(0,-10)));
		entityEngine.addSystem(new MovementSystem());
		entityEngine.addSystem(new CameraTrackingSystem(camera));

		Entity ent = new Entity();

		ent.add(new CollisionComponent(new Rectangle(0, 0, 10, 10)));
		ent.add(new PositionComponent());
		ent.add(new VelocityComponent());
		ent.add(new CameraTargetComponent());
		ent.add(new GravityComponent());
		ent.add(new PlayerComponent());

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
		mapRenderer.setView(camera);
		mapRenderer.render();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.badlogic.gdx.ScreenAdapter#show()
	 */
	@Override
	public void show() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
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
