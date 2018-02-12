package com.gdx.game.core.model;

import com.badlogic.gdx.math.Rectangle;

public class GDXTriggerArea extends GDXEventHandler {
	private Rectangle boundaries;

	@Override
	public void reset() {
		super.reset();

		boundaries = null;
	}

	public GDXTriggerArea withBoundaries(Rectangle boundaries) {
		setBoundaries(boundaries);

		return this;
	}

	public void setBoundaries(Rectangle boundaries) {
		this.boundaries = boundaries;
	}

	public Rectangle getBoundaries() {
		return boundaries;
	}
}
