package com.gdx.game.core.triggeractions;

import com.badlogic.gdx.Gdx;
import com.gdx.game.core.model.interfaces.IEvent;
import com.gdx.game.core.model.interfaces.IGameObject;
import com.gdx.game.core.model.interfaces.ITriggerAction;
import com.gdx.game.core.triggerevents.TriggerEventEnter;

public class TriggerActionSetProperty implements ITriggerAction {
	private String	propertyName;
	private String	propertyValue;

	public TriggerActionSetProperty withProperty(String propertyName, String propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;

		return this;
	}

	private String getSetterMethodByProperty() {
		if (propertyName.length() > 1) {
			return "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
		}

		return "set" + propertyName.toUpperCase();
	}

	@Override
	public void execute(IEvent event) {
		if (event instanceof TriggerEventEnter) {
			IGameObject eventObject = ((TriggerEventEnter) event).getTriggerObject();

			try {
				if ("true".equals(propertyValue) || "false".equals(propertyValue)) {
					// set boolean value
					eventObject.getClass().getMethod(getSetterMethodByProperty(), boolean.class).invoke(eventObject, Boolean.parseBoolean(propertyValue));
				} else {

					try {
						int parsedInt = Integer.parseInt(propertyValue);

						// set int value
						eventObject.getClass().getMethod(getSetterMethodByProperty(), int.class).invoke(eventObject, parsedInt);
					} catch (NumberFormatException e) {
						try {
							float parsedFloat = Float.parseFloat(propertyValue);

							// set float value
							eventObject.getClass().getMethod(getSetterMethodByProperty(), float.class).invoke(eventObject, parsedFloat);
						} catch (NumberFormatException e2) {

							// set string value
							eventObject.getClass().getMethod(getSetterMethodByProperty(), String.class).invoke(eventObject, propertyValue);
						}
					}
				}
			} catch (Exception e) {
				Gdx.app.log("ERROR", "Could not set property " + propertyName + " with value " + propertyValue, e);
			}
		}
	}

	@Override
	public void reset() {
		propertyName = propertyValue = null;
	}
}
