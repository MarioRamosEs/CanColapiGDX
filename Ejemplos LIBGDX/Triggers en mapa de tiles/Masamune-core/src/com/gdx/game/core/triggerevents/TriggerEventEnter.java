package com.gdx.game.core.triggerevents;

import com.gdx.game.core.model.GDXTriggerArea;

public class TriggerEventEnter extends TriggerEvent {
	private GDXTriggerArea triggerArea;

	public TriggerEventEnter withTriggerArea(GDXTriggerArea area) {
		setTriggerArea(area);

		return this;
	}

	public void setTriggerArea(GDXTriggerArea triggerArea) {
		this.triggerArea = triggerArea;
	}

	public GDXTriggerArea getTriggerArea() {
		return triggerArea;
	}
}
