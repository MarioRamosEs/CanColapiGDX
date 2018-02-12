package com.gdx.game.core.triggeractions;

import com.badlogic.gdx.math.Rectangle;
import com.gdx.game.core.model.interfaces.IEvent;
import com.gdx.game.core.model.interfaces.IGameObject;
import com.gdx.game.core.model.interfaces.ITriggerAction;
import com.gdx.game.core.triggerevents.TriggerEventEnter;
import com.gdx.masamune.model.GameWorld;

public class TriggerActionCreateObject implements ITriggerAction {
	private Class<? extends IGameObject>	classType;
	private String							type;

	public TriggerActionCreateObject withType(Class<? extends IGameObject> classType, String type) {
		this.classType = classType;
		this.type = type;

		return this;
	}

	@Override
	public void execute(IEvent event) {
		if (event instanceof TriggerEventEnter) {
			TriggerEventEnter enterEvent = (TriggerEventEnter) event;
			Rectangle boundaries = enterEvent.getTriggerArea().getBoundaries();

			//TODO
			((GameWorld)enterEvent.getGameWorld()).spawnEffect(type, boundaries.x, boundaries.y, enterEvent.getTriggerObject().getZ());
			/*enterEvent.getGameWorld().spawnObject( // params
					classType,    // object class type
					type,    // object type id
					boundaries.x,    // pos-x
					boundaries.y,    // pos-y
					enterEvent.getTriggerObject().getZ() // pos-z
			);*/
		}
	}

	@Override
	public void reset() {
		type = null;
	}
}
