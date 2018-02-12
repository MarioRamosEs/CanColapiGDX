package com.gdx.game.core;

import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.game.core.interfaces.IGameState;
import com.gdx.game.core.model.interfaces.IGameObject;

public abstract class GDXGameState implements IGameState {
	protected final IGame				game;
	protected final Array<IGameObject>	renderObjects;

	public GDXGameState(IGame game) {
		this.game = game;
		this.renderObjects = new Array<IGameObject>(100);
	}

	@Override
	public Array<IGameObject> getRenderObjects() {
		return renderObjects;
	}
}
