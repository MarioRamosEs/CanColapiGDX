package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.model.GDXTriggerArea;

public interface IGameWorld {
	public void update(float deltaTime);

	public Array<IGameObject> getObjects();

	public Array<Rectangle> getCollisionRectsForLayer(int layer);

	public Array<GDXTriggerArea> getTriggerAreas();

	public float getWidth();

	public float getHeight();

	public void playSound(String filePath);

	public void playMusic(String filePath);

	public <T extends IGameAction> T obtainAction(Class<T> actionType);

	public void setMap(String mapPath);

	public <T extends IGameObject> T spawnObject(Class<T> classType, String typeID, float x, float y, int z);
}
