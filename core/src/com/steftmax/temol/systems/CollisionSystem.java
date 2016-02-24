package com.steftmax.temol.systems;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.CollisionComponent;
import com.steftmax.temol.component.PositionComponent;
import com.steftmax.temol.component.VelocityComponent;
import com.steftmax.temol.tool.Constants;

/**
 * @author pieter3457
 *
 */
public class CollisionSystem extends IteratingSystem {

	private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<CollisionComponent> bm = ComponentMapper.getFor(CollisionComponent.class);

	private TiledMap map;
	private TiledMapTileLayer layer;

	public CollisionSystem(TiledMap map) {
		super(Family.all(PositionComponent.class, VelocityComponent.class, CollisionComponent.class).get());// TODO

		this.map = map;
		layer = (TiledMapTileLayer) map.getLayers().get("Tile Layer 1");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.
	 * ashley.core.Entity, float)
	 */
	TextureRegion test = new TextureRegion(new Texture("unnamed.png"));

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		final Rectangle bounds = bm.get(entity).bounds;

		final Vector2 position = pm.get(entity).position;
		final Vector2 velocity = vm.get(entity).velocity;
		bounds.setCenter(position);// Sync bounds position beforehand...

		Set<Cell> cells = getCollidingCells(bounds);

		System.out.println("round");
		for (Cell cell : cells) {

			if (cell != null)
				cell.getTile().setTextureRegion(test);

		}

		bounds.setCenter(position);// ...and sync afterwards
	}

	/**
	 * @param bounds
	 * @return
	 */
	private Set<Cell> getCollidingCells(Rectangle bounds) {

		final Set<Cell> cells = new HashSet<Cell>();

		final float tileWidth = layer.getTileWidth();
		final float tileHeight = layer.getTileHeight();

		// final float ox = bounds.x % tileWidth;
		// final float oy = bounds.y % tileHeight;

		final int ixa = (int) (bounds.x * Constants.METERSTOPIXELS / tileWidth);
		final int ixb = (int) ((bounds.x + bounds.width) * Constants.METERSTOPIXELS / tileWidth);

		final int iya = (int) (bounds.y * Constants.METERSTOPIXELS / tileHeight);
		final int iyb = (int) ((bounds.y + bounds.height) * Constants.METERSTOPIXELS / tileHeight);

		for (int x = 0; x <= ixb - ixa; x++) {
			for (int y = 0; y <= iyb - iya; y++) {
				cells.add(layer.getCell(x + ixa, y + iya));

				if (layer.getCell(x + ixa, y + iya) == null)
					System.out.print("null: ");

				System.out.println("x" + (x + ixa) + "y" + (y + iya));
				
			}
		}

		return cells;

	}

}
