package com.gdx.game.core;

import java.util.Iterator;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.game.core.interfaces.IGameRenderer;
import com.gdx.game.core.model.interfaces.IGameObject;

public class GDXRenderer implements IGameRenderer {
	private final OrthographicCamera				camera;
	private final SpriteBatch						spriteBatch;
	private final ObjectMap<String, AtlasRegion>	staticAtlasRegions;
	private final ObjectMap<String, Animation>		animations;
	private OrthogonalTiledMapRenderer				mapRenderer;
	private Array<TiledMapTileLayer>				mapLayers;

	public GDXRenderer(IGame game, ObjectMap<String, Animation> animations, ObjectMap<String, AtlasRegion> staticAtlasRegions) {
		this.spriteBatch = game.getSpriteBatch();
		this.camera = game.getCamera();
		this.animations = animations;
		this.staticAtlasRegions = staticAtlasRegions;
	}

	@Override
	public void render(Array<IGameObject> renderableObjects) {
		if (mapRenderer != null) {
			mapRenderer.setView(camera);

			AnimatedTiledMapTile.updateAnimationBaseTime();
			spriteBatch.begin();

			for (int z = 1; z <= mapLayers.size; ++z) {
				spriteBatch.setColor(1, 1, 1, 1);
				mapRenderer.renderTileLayer(mapLayers.get(z - 1));

				Iterator<IGameObject> objIter = renderableObjects.iterator();
				while (objIter.hasNext()) {
					IGameObject gameObj = objIter.next();

					if (gameObj.getZ() == z) {
						final TextureRegion regionToRender;
						if (gameObj.getFramesPerSecond() > 0) {
							regionToRender = animations.get(gameObj.getTexture() + gameObj.getFacing()).getKeyFrame(gameObj.getStateTime(), gameObj.getLoopAnimation());
						} else {
							regionToRender = staticAtlasRegions.get(gameObj.getTexture());
						}

						spriteBatch.setColor(gameObj.getRed(), gameObj.getGreen(), gameObj.getBlue(), gameObj.getAlpha());
						spriteBatch.draw( // params
								regionToRender,     // textureregion
								gameObj.getX(), gameObj.getY(),     // position
								gameObj.getOriginX(), gameObj.getOriginY(),     // rotation origin
								gameObj.getWidth(), gameObj.getHeight(),     // size
								gameObj.getScaleX(), gameObj.getScaleY(),     // scale
								gameObj.getRotation()); // rotation

						objIter.remove();
					} else {
						break;
					}
				}
			}

			spriteBatch.end();
		}
	}

	@Override
	public void setMap(TiledMap map) {
		if (mapRenderer != null) {
			mapRenderer.dispose();
			mapLayers.clear();
		}

		if (mapLayers == null) {
			mapLayers = new Array<TiledMapTileLayer>();
		}

		mapRenderer = new OrthogonalTiledMapRenderer(map, spriteBatch);
		for (MapLayer layer : mapRenderer.getMap().getLayers()) {
			if (layer instanceof TiledMapTileLayer) {
				mapLayers.add((TiledMapTileLayer) layer);
			}
		}
	}

	@Override
	public void dispose() {
		if (mapRenderer != null) {
			mapRenderer.dispose();
		}
	}
}
