package com.steftmax.temol.systems;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.steftmax.temol.component.CollisionComponent;
import com.steftmax.temol.component.GravityComponent;
import com.steftmax.temol.component.RenderableComponent;
import com.steftmax.temol.component.TransformComponent;
import com.steftmax.temol.component.VelocityComponent;
import com.steftmax.temol.tool.Constants;

/**
 * @author pieter3457
 *
 */
public class CollisionSystem extends IteratingSystem {

	private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);
	private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
	private ComponentMapper<CollisionComponent> bm = ComponentMapper.getFor(CollisionComponent.class);
	private ComponentMapper<GravityComponent> gc = ComponentMapper.getFor(GravityComponent.class);
	private ComponentMapper<RenderableComponent> rm = ComponentMapper.getFor(RenderableComponent.class);

	private TiledMap map;
	private TiledMapTileLayer layer;

	public CollisionSystem(TiledMap map) {
		super(Family.all(TransformComponent.class, VelocityComponent.class, CollisionComponent.class).get());// TODO

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

	private void setBoundsPosition(CollisionComponent cc, TransformComponent tc) {
		cc.bounds.setPosition(tc.position.x - tc.origin.x, tc.position.y - tc.origin.y);
	}

	private void updateBounds(CollisionComponent cc, RenderableComponent rc) {
		cc.bounds.setSize(rc.region.getRegionWidth(), rc.region.getRegionHeight());
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {

		RenderableComponent rc = rm.get(entity);
		if (rc != null)
			updateBounds(bm.get(entity), rc);
		solveCollision(entity);
	}

	/**
	 * @param bounds
	 * @return
	 */
	private Set<Cell> getCollidingCells(Rectangle bounds) {

		final Set<Cell> cells = new HashSet<Cell>();
		final Set<Rectangle> rectangles = new HashSet<Rectangle>();

		final float tileWidth = layer.getTileWidth();
		final float tileHeight = layer.getTileHeight();

		// final float ox = bounds.x % tileWidth;
		// final float oy = bounds.y % tileHeight;

		final int ixa = (int) (bounds.x / tileWidth);
		final int ixb = (int) ((bounds.x + bounds.width) / tileWidth);

		final int iya = (int) (bounds.y / tileHeight);
		final int iyb = (int) ((bounds.y + bounds.height) / tileHeight);

		for (int x = 0; x <= ixb - ixa; x++) {
			for (int y = 0; y <= iyb - iya; y++) {

				// if (layer.getCell(x + ixa, y + iya) != null)
				// cells.add(layer.getCell(x + ixa, y + iya));

				final Cell currentCell = layer.getCell(x + ixa, y + iya);
				if (currentCell != null) {

					final float cellPositionAX = (x + ixa) * tileHeight;
					final float cellPositionAY = (y + iya) * tileHeight;

					Rectangle cellBounds = new Rectangle(cellPositionAX, cellPositionAY, tileWidth, tileHeight);
					if (cellBounds.overlaps(bounds)) {
						rectangles.add(cellBounds);
					}

				}

			}
		}

		return cells;

	}

	private void solveCollision(Entity entity) {

		final Rectangle bounds = bm.get(entity).bounds;

		final Vector2 position = pm.get(entity).position;
		final Vector2 velocity = vm.get(entity).velocity;

		setBoundsPosition(bm.get(entity), pm.get(entity));// Sync bounds
															// position
															// beforehand...

		final float tileWidth = layer.getTileWidth();
		final float tileHeight = layer.getTileHeight();

		final int ixa = (int) (bounds.x / tileWidth);
		final int ixb = (int) ((bounds.x + bounds.width) / tileWidth);

		final int iya = (int) (bounds.y / tileHeight);
		final int iyb = (int) ((bounds.y + bounds.height) / tileHeight);

		for (int x = 0; x <= ixb - ixa; x++) {
			for (int y = 0; y <= iyb - iya; y++) {

				final Cell currentCell = layer.getCell(x + ixa, y + iya);
				if (currentCell != null) {

					final float cellPositionAX = (x + ixa) * tileHeight;
					final float cellPositionAY = (y + iya) * tileHeight;

					Rectangle cellBounds = Rectangle.tmp.set(cellPositionAX, cellPositionAY, tileWidth, tileHeight);

					if (cellBounds.overlaps(bounds)) {/// actual collision

						float Xpenetration = ((cellBounds.width / 2 + bounds.width / 2)
								- (bounds.width / 2 + bounds.x - (cellBounds.width / 2 + cellBounds.x)));

						float Ypenetration = ((cellBounds.height / 2 + bounds.height / 2)
								- (bounds.height / 2 + bounds.y - (cellBounds.height / 2 + cellBounds.y)));
						System.out.println("Xpen" + Xpenetration + "Ypen" + Ypenetration);
						float deltaTimeForX = Float.MIN_VALUE;

						if (velocity.x > 0f) {
							deltaTimeForX = (cellBounds.x - (bounds.x + bounds.width)) / velocity.x;
						} else if (velocity.x < 0f) {
							deltaTimeForX = ((cellBounds.x + cellBounds.width) - bounds.x) / velocity.x;
						}

						float deltaTimeForY = Float.MIN_VALUE;

						if (velocity.y > 0f) {
							deltaTimeForY = (cellBounds.y - (bounds.y + bounds.height)) / velocity.y;
						} else if (velocity.y < 0f) {
							deltaTimeForY = ((cellBounds.y + cellBounds.height) - bounds.y) / velocity.y;
						}

						System.out.println("deltaX: " + deltaTimeForX + " deltaY: " + deltaTimeForY);

						boolean corrected = false;
						if (deltaTimeForX < 0f) {
							GravityComponent g = gc.get(entity);
							if (g != null) {
								// System.out.println("grounded");
								g.isGrounded = true;
							}
							position.x += velocity.x * deltaTimeForX;
							velocity.x = 0f;
							corrected = true;
						}

						if (deltaTimeForY < 0f) {
							GravityComponent g = gc.get(entity);
							if (g != null) {
								// System.out.println("grounded");
								g.isGrounded = true;
							}
							position.y += velocity.y * deltaTimeForY;
							velocity.y = 0f;
							corrected = true;
						}

						if (corrected)
							return;
					}

					// position.set(pm.get(entity).lastPosition);
					// velocity.set(0f, 0f);
					//

				}

			}
		}

		setBoundsPosition(bm.get(entity), pm.get(entity));// ...and sync
															// afterwards
	}

}
