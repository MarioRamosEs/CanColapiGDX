package com.gdx.game.core.triggerevents;

import com.gdx.game.core.model.interfaces.IEvent;
import com.gdx.game.core.model.interfaces.IGameObject;
import com.gdx.game.core.model.interfaces.IGameWorld;

public class TriggerEvent implements IEvent {
	private IGameObject	triggerObject;
	private IGameWorld	world;

	@Override
	public void reset() {
		triggerObject = null;
		world = null;
	}

	@Override
	public void setGameWorld(IGameWorld world) {
		this.world = world;
	}

	@Override
	public IGameWorld getGameWorld() {
		return world;
	}

	@Override
	public void setTriggerObject(IGameObject triggerObj) {
		this.triggerObject = triggerObj;
	}

	@Override
	public IGameObject getTriggerObject() {
		return triggerObject;
	}
}
