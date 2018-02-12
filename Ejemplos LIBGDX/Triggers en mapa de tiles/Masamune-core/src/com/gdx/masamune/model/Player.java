package com.gdx.masamune.model;

import com.gdx.masamune.enums.PlayerType;

public class Player {
	private String				name;
	private final PlayerType	type;

	public Player(PlayerType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PlayerType getType() {
		return type;
	}
}
