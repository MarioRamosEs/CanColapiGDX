package com.gdx.masamune.model;

import com.gdx.game.core.model.GDXGameObject;

public class Unit extends GDXGameObject {
	private Player	owner;
	private float	life;

	@Override
	public void reset() {
		super.reset();

		owner = null;
	}

	public Unit withOwner(Player owner) {
		setOwner(owner);

		return this;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public Unit withLife(float life) {
		setLife(life);

		return this;
	}

	public void setLife(float life) {
		this.life = life;
	}

	public float getLife() {
		return life;
	}

	@Override
	public boolean isRemovable() {
		return life <= 0;
	}

	@Override
	public float getStateTime() {
		if (getCurrentAction() == null) {
			return 0;
		}
		return super.getStateTime();
	}
}
