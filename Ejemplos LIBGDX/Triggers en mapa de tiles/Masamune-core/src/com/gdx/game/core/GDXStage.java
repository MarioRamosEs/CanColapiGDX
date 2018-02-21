package com.gdx.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GDXStage extends Stage {
	private final Table table;

	public GDXStage(Viewport viewport, SpriteBatch spriteBatch) {
		super(viewport, spriteBatch);

		table = new Table();
		table.setFillParent(true);
		addActor(table);
	}
}
