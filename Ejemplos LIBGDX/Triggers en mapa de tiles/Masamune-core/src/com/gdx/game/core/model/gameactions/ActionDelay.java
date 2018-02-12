package com.gdx.game.core.model.gameactions;

import com.gdx.game.core.model.GDXGameAction;

public class ActionDelay extends GDXGameAction {
	private float delay;

	@Override
	public void update(float deltaTime) {
		delay -= deltaTime;
	}

	@Override
	public boolean isFinished() {
		return delay <= 0;
	}

	public ActionDelay withDelay(float delay) {
		this.delay = delay;

		return this;
	}

	@Override
	public void start() {
	}
}
