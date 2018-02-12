package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface IEventListener extends Poolable {
	public void onEvent(IEvent event);
}
