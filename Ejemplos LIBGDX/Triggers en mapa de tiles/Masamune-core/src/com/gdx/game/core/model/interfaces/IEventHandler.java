package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.utils.Pool.Poolable;

public interface IEventHandler extends Poolable {
	public void addEventListener(IEventListener listener);

	public void removeEventListener(IEventListener listener);

	public void fireEvent(IEvent event);
}
