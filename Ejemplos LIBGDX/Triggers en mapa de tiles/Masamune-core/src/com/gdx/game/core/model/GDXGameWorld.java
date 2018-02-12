package com.gdx.game.core.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.game.core.model.interfaces.IGameAction;
import com.gdx.game.core.model.interfaces.IGameObject;
import com.gdx.game.core.model.interfaces.IGameObjectType;
import com.gdx.game.core.model.interfaces.IGameWorld;
import com.gdx.game.core.triggeractions.TriggerActionCreateObject;
import com.gdx.game.core.triggeractions.TriggerActionPlayMusic;
import com.gdx.game.core.triggeractions.TriggerActionPlaySound;
import com.gdx.game.core.triggeractions.TriggerActionSetProperty;
import com.gdx.game.core.triggerevents.TriggerEvent;
import com.gdx.masamune.model.Effect;
import com.gdx.masamune.model.Unit;

public abstract class GDXGameWorld implements IGameWorld {
	protected final IGame						game;
	protected Array<IGameObject>				objects;
	protected Array<GDXTriggerArea>				triggerAreas;
	protected Array<GDXTriggerTimeout>			triggerTimeouts;
	protected String							currentMap;
	protected Map<Integer, Array<Rectangle>>	mapCollisionRects;
	private float								width;
	private float								height;

	public GDXGameWorld(IGame game) {
		this.game = game;
		objects = new Array<IGameObject>();
		mapCollisionRects = new HashMap<Integer, Array<Rectangle>>();
		triggerAreas = new Array<GDXTriggerArea>();
		triggerTimeouts = new Array<GDXTriggerTimeout>();
	}

	@Override
	public void update(float deltaTime) {
		Iterator<GDXTriggerTimeout> triggerTimeoutIter = triggerTimeouts.iterator();
		while (triggerTimeoutIter.hasNext()) {
			GDXTriggerTimeout triggerTimeout = triggerTimeoutIter.next();

			if (triggerTimeout.update(deltaTime)) {
				triggerTimeout.fireEvent(Pools.obtain(TriggerEvent.class).withGameWorld(this));
				triggerTimeoutIter.remove();
				Pools.free(triggerTimeout);
			}
		}

		Iterator<IGameObject> iterator = objects.iterator();
		while (iterator.hasNext()) {
			IGameObject gameObject = iterator.next();
			if (gameObject.isRemovable()) {
				Pools.free(gameObject);
				iterator.remove();
			} else {
				gameObject.update(deltaTime);
			}
		}
	}

	@Override
	public void setMap(String mapPath) {
		TiledMap map = game.loadMap(mapPath);

		this.currentMap = mapPath;
		parseMapCollisionRects(map);
		parseTrigger(map);

		MapProperties properties = map.getProperties();
		width = properties.get("width", Integer.class) * properties.get("tilewidth", Integer.class);
		height = properties.get("height", Integer.class) * properties.get("tileheight", Integer.class);
	}

	private void parseTrigger(TiledMap map) {
		triggerAreas.clear();

		for (MapLayer layer : map.getLayers()) {
			if (layer instanceof MapLayer && layer.getName().contains("Trigger")) {
				for (MapObject object : layer.getObjects()) {
					if (object instanceof RectangleMapObject) {
						MapProperties properties = object.getProperties();
						String type = "" + properties.get("type");

						final GDXTrigger trigger = Pools.obtain(GDXTrigger.class);
						parseTriggerActions(trigger, properties);

						switch (type) {
							case "TriggerEnter":
								GDXTriggerArea area = Pools.obtain(GDXTriggerArea.class).withBoundaries(((RectangleMapObject) object).getRectangle());
								area.addEventListener(trigger);
								triggerAreas.add(area);
								break;
							case "TriggerMapLoad":
								trigger.onEvent(Pools.obtain(TriggerEvent.class).withGameWorld(this));
								break;
							case "TriggerTimeout":
								GDXTriggerTimeout triggerTimeout = Pools.obtain(GDXTriggerTimeout.class).withDelay(Float.parseFloat((String) properties.get("timeout")));
								triggerTimeout.addEventListener(trigger);
								triggerTimeouts.add(triggerTimeout);
								break;
							default:
								Gdx.app.log("ERROR", "Unsupported Triggertype '" + type + "' for map " + currentMap, null);
								break;
						}
					}
				}
			}
		}
	}

	private void parseTriggerActions(GDXTrigger trigger, MapProperties properties) {
		Iterator<String> keys = properties.getKeys();
		while (keys.hasNext()) {
			String actionType = keys.next();
			if ("type".equals(actionType) || "x".equals(actionType) || "y".equals(actionType) || "width".equals(actionType) || "height".equals(actionType) || "timeout".equals(actionType)) {
				continue;
			}

			final String[] keyValuePair;
			final String[] keyValuePair2;
			switch (actionType) {
				case "setProperty":
					keyValuePair = ((String) properties.get(actionType)).split("=");
					if (keyValuePair.length == 2) {
						trigger.addAction(Pools.obtain(TriggerActionSetProperty.class).withProperty(keyValuePair[0], keyValuePair[1]));
					} else {
						Gdx.app.log("ERROR", "Incorrect format type for setProperty action for map " + currentMap + ". Format must be PROPERTYNAME=PROPERTYVALUE", null);
					}
					break;
				case "playSound":
					trigger.addAction(Pools.obtain(TriggerActionPlaySound.class).withFile((String) properties.get(actionType)));
					break;
				case "playMusic":
					trigger.addAction(Pools.obtain(TriggerActionPlayMusic.class).withFile((String) properties.get(actionType)));
					break;
				case "createObject":
					// class=Effect,type=LIGHTNING
					//TODO
					String[] classTypePair = ((String) properties.get(actionType)).split(",");
					keyValuePair = classTypePair[0].split("=");
					keyValuePair2 = classTypePair[1].split("=");
					if (keyValuePair.length == 2 && keyValuePair2.length == 2) {
						if ("Effect".equals(keyValuePair[1])) {
							trigger.addAction(Pools.obtain(TriggerActionCreateObject.class).withType(Effect.class, keyValuePair2[1]));
						} else if ("Unit".equals(keyValuePair[1])) {
							trigger.addAction(Pools.obtain(TriggerActionCreateObject.class).withType(Unit.class, keyValuePair2[1]));
						} else {
							Gdx.app.log("ERROR", "Unsupported class type '" + keyValuePair[1] + "' for createObject action for map " + currentMap + ".", null);
						}
					} else {
						Gdx.app.log("ERROR", "Incorrect format type for createObject action for map " + currentMap + ". Format must be ENUMCLASS=ENUMCONSTANT", null);
					}

					break;
				default:
					Gdx.app.log("ERROR", "Unsupported Actiontype '" + actionType + "' for map " + currentMap, null);
					break;
			}
		}
	}

	private void parseMapCollisionRects(TiledMap map) {
		mapCollisionRects.clear();

		for (MapLayer layer : map.getLayers()) {
			if (layer instanceof MapLayer && layer.getName().contains("Collision")) {
				final int z = Integer.parseInt(layer.getName().substring("Collision".length() + 1).trim());
				final Array<Rectangle> collisionRects;

				if (mapCollisionRects.containsKey(z)) {
					collisionRects = mapCollisionRects.get(z);
				} else {
					collisionRects = new Array<Rectangle>();
					mapCollisionRects.put(z, collisionRects);
				}

				for (MapObject object : layer.getObjects()) {
					if (object instanceof RectangleMapObject) {
						collisionRects.add(((RectangleMapObject) object).getRectangle());
					}
				}
			}
		}
	}

	@Override
	public Array<GDXTriggerArea> getTriggerAreas() {
		return triggerAreas;
	}

	@Override
	public Array<Rectangle> getCollisionRectsForLayer(int layer) {
		return mapCollisionRects.get(layer);
	}

	@Override
	public <T extends IGameAction> T obtainAction(Class<T> actionType) {
		return Pools.obtain(actionType);
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void playSound(String filePath) {
		// TODO Auto-generated method stub

		/*
		 * 
		 * Music music = Gdx.audio.newMusic(Gdx.files.internal(filePath));
		 * 
		 * music.setLooping(true);
		 * music.play();
		 * 
		 * // TODO music dispose
		 * 
		 */

	}

	@Override
	public void playMusic(String filePath) {
		// TODO Auto-generated method stub

	}

	@Override
	public Array<IGameObject> getObjects() {
		return objects;
	}

	@Override
	public <T extends IGameObject> T spawnObject(Class<T> classType, String typeID, float x, float y, int z) {
		IGameObjectType type = game.getGameObjectType(typeID);
		T newObject = classType.cast(Pools.obtain(classType) // default params
				.withTexture(type.getProperty("textureID")) // texture
				.withSize(Float.parseFloat(type.getProperty("width")), Float.parseFloat(type.getProperty("height"))) // size
				.withPosition(x, y, z) // position
		);

		IGameObjectType textureType = game.getGameObjectType(type.getProperty("textureID"));
		String fps = textureType.getProperty("fps");
		if (fps != null) {
			newObject.withAnimationSpeed(1).withFramesPerSecond(Float.parseFloat(fps));
		} else {
			newObject.withAnimationSpeed(0).withFramesPerSecond(0);
		}

		objects.add(newObject);
		return newObject;
	}
}
