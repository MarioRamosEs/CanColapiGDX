package com.gdx.game.core.model;

import com.badlogic.gdx.utils.ObjectMap;
import com.gdx.game.core.model.interfaces.IGameObjectType;

public class GDXGameObjectType implements IGameObjectType {
	private final String					type;
	private final ObjectMap<String, String>	properties;

	public GDXGameObjectType(String type, ObjectMap<String, String> properties) {
		this.type = type;
		this.properties = properties;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getProperty(String property) {
		return properties.get(property);
	}

	@Override
	public String toString() {
		return "Type: " + type + " " + properties.toString();
	}
}
