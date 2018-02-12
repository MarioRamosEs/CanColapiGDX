package com.gdx.masamune.model;

import com.gdx.game.core.model.GDXGameObject;

public class Effect extends GDXGameObject {
	private float lifespan;

	@Override
	public void reset() {
		super.reset();

		lifespan = 0;
		setLoopAnimation(false);
	}

	public Effect withLifeSpan(float lifespan) {
		this.lifespan = lifespan;

		return this;
	}

	@Override
	public boolean isRemovable() {
		return getStateTime() >= lifespan;
	}
}
