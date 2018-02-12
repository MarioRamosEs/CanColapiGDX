package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface IEvent extends Poolable {
	default IEvent withGameWorld(IGameWorld world) {
		setGameWorld(world);

		return this;
	}

	public void setGameWorld(IGameWorld world);

	public IGameWorld getGameWorld();

	default IEvent withTriggerObject(IGameObject triggerObj) {
		setTriggerObject(triggerObj);

		return this;
	}

	public void setTriggerObject(IGameObject triggerObj);

	public IGameObject getTriggerObject();
}
