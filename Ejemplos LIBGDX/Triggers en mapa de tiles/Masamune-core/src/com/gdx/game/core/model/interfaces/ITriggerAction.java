package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface ITriggerAction extends Poolable {
	public void execute(IEvent event);
}
