package com.gdx.game.core.model;

import com.gdx.game.core.model.interfaces.IGameAction;
import com.gdx.game.core.model.interfaces.IGameObject;

public abstract class GDXGameAction implements IGameAction {
	protected IGameObject target;

	@Override
	public void reset() {
		target = null;
	}

	@Override
	public IGameAction withTarget(IGameObject target) {
		this.target = target;

		return this;
	}
}
