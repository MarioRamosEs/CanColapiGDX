package com.gdx.game.core.triggeractions;

import com.gdx.game.core.model.interfaces.IEvent;
import com.gdx.game.core.model.interfaces.ITriggerAction;

public class TriggerActionPlaySound implements ITriggerAction {
	private String filePath;

	public TriggerActionPlaySound withFile(String filePath) {
		this.filePath = filePath;

		return this;
	}

	@Override
	public void execute(IEvent event) {
		event.getGameWorld().playSound(filePath);
	}

	@Override
	public void reset() {
		filePath = null;
	}
}
