package com.gdx.masamune;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.gdx.game.core.GDXGame;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.masamune.gamestates.GSGame;

public class Masamune implements ApplicationListener {
	private IGame game;

	@Override
	public void create() {
		game = new GDXGame();
		game.setGameState(GSGame.class, false);
		Gdx.input.setInputProcessor(game);
	}

	@Override
	public void render() {
		game.update(Gdx.graphics.getDeltaTime());
		game.render();
	}

	@Override
	public void dispose() {
		game.dispose();
	}

	@Override
	public void resize(int width, int height) {
		game.resize(width, height);
	}

	@Override
	public void pause() {
		game.pause();
	}

	@Override
	public void resume() {
		game.resume();
	}
}
