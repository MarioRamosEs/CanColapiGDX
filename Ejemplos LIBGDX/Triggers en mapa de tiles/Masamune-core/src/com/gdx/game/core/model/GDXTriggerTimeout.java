package com.gdx.game.core.model;

public class GDXTriggerTimeout extends GDXEventHandler {
	private float delay;

	@Override
	public void reset() {
		super.reset();

		delay = 0;
	}

	public GDXTriggerTimeout withDelay(float delay) {
		this.delay = delay;

		return this;
	}

	public boolean update(float deltaTime) {
		delay -= deltaTime;

		return delay <= 0;
	}
}
