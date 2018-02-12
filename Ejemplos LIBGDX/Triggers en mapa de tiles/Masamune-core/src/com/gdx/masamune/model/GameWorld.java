package com.gdx.masamune.model;

import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.game.core.model.GDXGameWorld;
import com.gdx.game.core.model.interfaces.IGameObjectType;
import com.gdx.masamune.enums.PlayerType;

public class GameWorld extends GDXGameWorld {
	private Array<Player> players;

	public GameWorld(IGame game) {
		super(game);

		players = new Array<Player>();
		for (PlayerType type : PlayerType.values()) {
			players.add(new Player(type));
		}
	}

	public Unit spawnUnit(String typeID, PlayerType owner, float x, float y, int z) {
		IGameObjectType type = game.getGameObjectType(typeID);
		return (Unit) super.spawnObject(Unit.class, typeID, x, y, z) // new unit
				.withOwner(players.get(owner.ordinal())) // owner
				.withLife(Float.parseFloat(type.getProperty("life"))) // life
				.withSpeed(Float.parseFloat(type.getProperty("speedX")), Float.parseFloat(type.getProperty("speedY"))) // speed
				.withLoopAnimation(true) // loop animation for units
				;
	}

	public Effect spawnEffect(String typeID, float x, float y, int z) {
		IGameObjectType type = game.getGameObjectType(typeID);
		return (Effect) super.spawnObject(Effect.class, typeID, x, y, z).withLifeSpan(Float.parseFloat(type.getProperty("lifespan"))).withLoopAnimation(false);
	}
}
