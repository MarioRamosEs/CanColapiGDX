package com.gdx.game.core.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.model.interfaces.IEvent;
import com.gdx.game.core.model.interfaces.IEventListener;
import com.gdx.game.core.model.interfaces.ITriggerAction;

public class GDXTrigger implements IEventListener {
	private Array<ITriggerAction> actions;

	@Override
	public void reset() {
		if (actions != null) {
			for (ITriggerAction action : actions) {
				Pools.free(action);
			}
			actions.clear();
		}
	}

	public GDXTrigger addAction(ITriggerAction action) {
		getActions().add(action);

		return this;
	}

	private Array<ITriggerAction> getActions() {
		if (actions == null) {
			actions = new Array<ITriggerAction>();
		}

		return actions;
	}

	@Override
	public void onEvent(IEvent event) {
		for (ITriggerAction action : getActions()) {
			action.execute(event);
		}
	}
}
