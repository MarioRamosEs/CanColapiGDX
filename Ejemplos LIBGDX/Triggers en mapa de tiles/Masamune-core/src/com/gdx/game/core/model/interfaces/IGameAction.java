package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface IGameAction extends Poolable {
	public IGameAction withTarget(IGameObject target);

	public void start();

	public void update(float deltaTime);

	public boolean isFinished();
}
