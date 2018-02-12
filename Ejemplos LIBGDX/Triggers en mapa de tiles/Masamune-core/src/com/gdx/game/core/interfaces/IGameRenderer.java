package com.gdx.game.core.interfaces;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.model.interfaces.IGameObject;

public interface IGameRenderer {
	public void setMap(TiledMap map);

	public void render(Array<IGameObject> renderableObjects);

	public void dispose();
}
