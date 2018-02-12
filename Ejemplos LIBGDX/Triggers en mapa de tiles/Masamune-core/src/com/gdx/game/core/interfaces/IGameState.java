package com.gdx.game.core.interfaces;

import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.model.interfaces.IGameObject;

public interface IGameState {
	public void update(float deltaTime);

	public void resize(int width, int height);

	public boolean keyDown(int keycode);

	public boolean keyUp(int keycode);

	public void dispose();

	public Array<IGameObject> getRenderObjects();
}
