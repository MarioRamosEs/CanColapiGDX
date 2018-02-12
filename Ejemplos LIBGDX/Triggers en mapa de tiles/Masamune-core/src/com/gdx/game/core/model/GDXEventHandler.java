package com.gdx.game.core.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.model.interfaces.IEvent;
import com.gdx.game.core.model.interfaces.IEventHandler;
import com.gdx.game.core.model.interfaces.IEventListener;

public abstract class GDXEventHandler implements IEventHandler {
	private Array<IEventListener> listeners;

	@Override
	public void addEventListener(IEventListener listener) {
		getListeners().add(listener);
	}

	@Override
	public void removeEventListener(IEventListener listener) {
		getListeners().removeValue(listener, false);
	}

	@Override
	public void fireEvent(IEvent event) {
		for (int i = getListeners().size - 1; i >= 0; --i) {
			getListeners().get(i).onEvent(event);
		}
	}

	private Array<IEventListener> getListeners() {
		if (listeners == null) {
			listeners = new Array<IEventListener>();
		}

		return listeners;
	}

	@Override
	public void reset() {
		if (listeners != null) {
			for (IEventListener listener : listeners) {
				Pools.free(listener);
			}
			listeners.clear();
		}
	}
}
