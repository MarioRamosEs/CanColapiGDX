package com.gdx.game.core.interfaces;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.GDXStage;
import com.gdx.game.core.model.interfaces.IGameObjectType;

public interface IGame extends InputProcessor {
	public void setGameState(Class<? extends IGameState> gameStateClass, boolean disposeCurrentState);

	public void update(float deltaTime);

	public void render();

	public void resize(int width, int height);

	public void dispose();

	default void pause() {
		// this is only necessary for android games; we don't use it
		// pause happens when the application moves to the background
		// when pressing the home button for example
	}

	default void resume() {
		// this is only necessary for android games; we don't use it
		// resume happens when the application gets the focus again
	}

	@Override
	public boolean keyDown(int keycode);

	@Override
	public boolean keyUp(int keycode);

	@Override
	default boolean keyTyped(char character) {
		// this is not used yet, because keyDown and keyUp are fulfilling everything?
		// if this is not sufficient -> forward call to currentState
		return false;
	}

	@Override
	default boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// this is not used yet, because the rpg will be a keyboard/gamepad only game
		// if you want to use it, forward the call to the current gamestate
		return false;
	}

	@Override
	default boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// this is not used yet, because the rpg will be a keyboard/gamepad only game
		// if you want to use it, forward the call to the current gamestate
		return false;
	}

	@Override
	default boolean touchDragged(int screenX, int screenY, int pointer) {
		// this is not used yet, because the rpg will be a keyboard/gamepad only game
		// if you want to use it, forward the call to the current gamestate
		return false;
	}

	@Override
	default boolean mouseMoved(int screenX, int screenY) {
		// this is not used yet, because the rpg will be a keyboard/gamepad only game
		// if you want to use it, forward the call to the current gamestate
		return false;
	}

	@Override
	default boolean scrolled(int amount) {
		// this is not used yet, because the rpg will be a keyboard/gamepad only game
		// if you want to use it, forward the call to the current gamestate
		return false;
	}

	public TiledMap loadMap(String filePath);

	public IGameObjectType getGameObjectType(String typeID);

	public Array<IGameObjectType> getGameObjectTypesByName(String typeName);

	public SpriteBatch getSpriteBatch();

	public AssetManager getAssetManager();

	public OrthographicCamera getCamera();

	public GDXStage getUIStage();
}
