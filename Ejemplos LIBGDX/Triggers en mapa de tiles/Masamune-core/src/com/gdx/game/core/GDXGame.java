package com.gdx.game.core;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.game.core.interfaces.IGameRenderer;
import com.gdx.game.core.interfaces.IGameState;
import com.gdx.game.core.model.GDXGameObjectType;
import com.gdx.game.core.model.interfaces.IGameObjectType;
import com.gdx.game.core.model.interfaces.IGameObject.Facing;

public class GDXGame implements IGame {
	private final SpriteBatch							spriteBatch;
	private final OrthographicCamera					camera;
	private final AssetManager							assetManager;
	private final GDXStage								uiStage;
	private final IGameRenderer							renderer;
	private final Array<IGameState>						gameStates;
	private IGameState									currentState;
	private final ObjectMap<String, AtlasRegion>		staticAtlasRegions;
	private final ObjectMap<String, Animation>			animations;
	private final ObjectMap<String, IGameObjectType>	gameObjectTypes;

	public GDXGame() {
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		assetManager = new AssetManager();
		uiStage = new GDXStage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), spriteBatch);
		gameStates = new Array<IGameState>();

		// TODO zoom can be replaced if we use edge padding for the tiledmap
		// also checkout tiledmap and textureatlas
		camera.zoom = 1.01f;
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		gameObjectTypes = new ObjectMap<String, IGameObjectType>();
		loadGameObjectTypes();

		staticAtlasRegions = new ObjectMap<String, AtlasRegion>();
		animations = new ObjectMap<String, Animation>();
		loadTextures();

		renderer = new GDXRenderer(this, animations, staticAtlasRegions);
	}

	private void loadGameObjectTypes() {
		try {
			Element typeFilesRoot = new XmlReader().parse(Gdx.files.internal("types/types.xml"));

			Array<Element> filesToLoad = typeFilesRoot.getChildrenByName("filepath");
			for (Element file : filesToLoad) {
				Element typeRoot = new XmlReader().parse(Gdx.files.internal("types/" + file.getText()));

				Array<Element> typesToLoad = typeRoot.getChildrenByName(file.getText().substring(0, file.getText().indexOf(".") - 1));
				for (Element type : typesToLoad) {
					String typeID = type.getChildByName("id").getText();
					if (gameObjectTypes.containsKey(typeID)) {
						Gdx.app.log("ERROR", "Game object type " + typeID + " already loaded. Type IDs must be unique!");
						Gdx.app.exit();
						return;
					}

					ObjectMap<String, String> properties = new ObjectMap<String, String>();
					for (int i = 0; i < type.getChildCount(); ++i) {
						Element attribute = type.getChild(i);

						properties.put(attribute.getName(), attribute.getText());
					}

					gameObjectTypes.put(typeID, new GDXGameObjectType(type.getName(), properties));
				}
			}
		} catch (IOException e) {
			Gdx.app.log("ERROR", "Could not load game object types", e);
			Gdx.app.exit();
		}
	}

	private void loadTextures() {
		Array<IGameObjectType> textures = getGameObjectTypesByName("texture");
		ObjectMap<String, TextureAtlas> textureAtlasMap = new ObjectMap<String, TextureAtlas>();

		for (IGameObjectType texture : textures) {
			String atlas = texture.getProperty("atlas");
			TextureAtlas textureAtlas = textureAtlasMap.get(atlas);
			if (textureAtlas == null) {
				// atlas not loaded yet -> load it!
				textureAtlas = loadTextureAtlas(assetManager, atlas);
				textureAtlasMap.put(atlas, textureAtlas);
			}

			String fps = texture.getProperty("fps");
			if (fps != null) {
				AtlasRegion region = textureAtlas.findRegion(texture.getProperty("atlasID"));
				final int framesX = Integer.parseInt(texture.getProperty("framesX"));
				final int framesY = Integer.parseInt(texture.getProperty("framesY"));

				final String framesMaxStr = texture.getProperty("framesMax");
				final int framesMax;
				if (framesMaxStr == null) {
					framesMax = framesX * framesY;
				} else {
					framesMax = Integer.parseInt(framesMaxStr);
				}
				TextureRegion[][] frames = region.split(region.getRegionWidth() / framesX, region.getRegionHeight() / framesY);

				Array<TextureRegion> framesForAni = new Array<TextureRegion>();
				int counter = 0;
				for (int row = 0; row < frames.length; ++row) {
					if (framesY == 4 && framesForAni.size > 0) {
						framesForAni = new Array<TextureRegion>();
					}
					for (int column = 0; column < frames[row].length; ++column) {
						++counter;
						if (counter > framesMax) {
							break;
						}
						framesForAni.add(frames[row][column]);
					}
					if (framesY == 4) {
						animations.put(texture.getProperty("id") + Facing.values()[row], new Animation(1.00f / Float.parseFloat(fps), framesForAni));
					}
				}

				if (framesY != 4) {
					animations.put(texture.getProperty("id") + Facing.SOUTH, new Animation(1.00f / Float.parseFloat(fps), framesForAni));
				}
			} else {
				staticAtlasRegions.put(texture.getProperty("id"), textureAtlas.findRegion(texture.getProperty("atlasID")));
			}
		}
	}

	private TextureAtlas loadTextureAtlas(AssetManager assetManager, String atlas) {
		final String atlasPath = "packedGraphics/" + atlas + ".atlas";
		assetManager.load(atlasPath, TextureAtlas.class);
		assetManager.finishLoading();

		return assetManager.get(atlasPath, TextureAtlas.class);
	}

	@Override
	public void setGameState(Class<? extends IGameState> gameStateClass, boolean disposeCurrentState) {
		// dipose resources of current gamestate if it exists
		if (disposeCurrentState && currentState != null) {
			currentState.dispose();
			gameStates.removeValue(currentState, false);
			currentState = null;
		}

		// check if an undisposed gamestate of the given type already exists
		// if yes -> use it instead of creating a new one
		for (IGameState state : gameStates) {
			if (gameStateClass.isInstance(state)) {
				currentState = state;
				break;
			}
		}

		if (currentState == null) {
			// no gamestate of given type found -> createa a new instance
			try {
				currentState = (IGameState) gameStateClass.getConstructors()[0].newInstance(this);
			} catch (Exception e) {
				Gdx.app.log("ERROR", "Could not create new gamestate instance of type " + gameStateClass, e);
				Gdx.app.exit();
				return;
			}

			// remember the newly created gamestate if we want to reused it later on
			gameStates.add(currentState);
		}
	}

	@Override
	public void update(float deltaTime) {
		currentState.update(deltaTime);
		camera.update();
		uiStage.act(deltaTime);

		// TODO remove debug
		Gdx.graphics.setTitle("Masamune: " + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw map and game objects
		renderer.render(currentState.getRenderObjects());

		// draw ui/hud
		spriteBatch.setColor(1, 1, 1, 1);
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		for (IGameState state : gameStates) {
			state.resize(width, height);
		}
		uiStage.getViewport().update(width, height, true);
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void dispose() {
		for (IGameState state : gameStates) {
			state.dispose();
		}
		spriteBatch.dispose();
		assetManager.dispose();
		uiStage.dispose();
		renderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return currentState.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		return currentState.keyUp(keycode);
	}

	@Override
	public TiledMap loadMap(String filePath) {
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		assetManager.load(filePath, TiledMap.class);
		assetManager.finishLoading();

		TiledMap map = assetManager.get(filePath);

		renderer.setMap(map);

		return map;
	}

	@Override
	public IGameObjectType getGameObjectType(String typeID) {
		return gameObjectTypes.get(typeID);
	}

	@Override
	public Array<IGameObjectType> getGameObjectTypesByName(String typeName) {
		Array<IGameObjectType> result = new Array<IGameObjectType>();

		for (IGameObjectType type : gameObjectTypes.values()) {
			if (typeName.equals(type.getType())) {
				result.add(type);
			}
		}

		return result;
	}

	@Override
	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	@Override
	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public GDXStage getUIStage() {
		return uiStage;
	}
}
