package com.gdx.masamune.gamestates;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.core.GDXGameState;
import com.gdx.game.core.GDXStage;
import com.gdx.game.core.interfaces.IGame;
import com.gdx.game.core.model.gameactions.ActionPatrol;
import com.gdx.game.core.model.gameactions.ActionTranslation;
import com.gdx.game.core.model.interfaces.IGameObject;
import com.gdx.masamune.enums.PlayerType;
import com.gdx.masamune.model.GameWorld;
import com.gdx.masamune.model.Unit;

public class GSGame extends GDXGameState {
	private GameWorld			gameWorld;
	private Unit				player;
	private Unit				cameraUnit;
	private Array<Boolean>		keyHold;
	private OrthographicCamera	camera;
	private GDXStage			uiStage;

	public GSGame(IGame game) {
		super(game);

		camera = game.getCamera();
		uiStage = game.getUIStage();

		keyHold = new Array<Boolean>();
		for (int i = 0; i < 4; ++i) {
			keyHold.add(false);
		}

		loadHUD();

		gameWorld = new GameWorld(game);
		gameWorld.setMap("maps/test.tmx");

		player = gameWorld.spawnUnit("SOLDIER", PlayerType.HUMAN, 350, 50, 2);
		gameWorld.spawnEffect("LIGHTNING", 280, 30, 2);
		player.setAction(gameWorld.obtainAction(ActionPatrol.class).addPatrolPoint(500, 100).addPatrolPoint(500, 250).addPatrolPoint(350, 250));

		lockCameraTo(player);
	}

	private void lockCameraTo(Unit unit) {
		cameraUnit = unit;
	}

	private void loadHUD() {
		Skin skin = new Skin();
		TextButtonStyle style = new TextButton.TextButtonStyle();
		skin.add("default", new BitmapFont());
		style.font = skin.getFont("default");
		skin.add("default", style);

		TextButton button1 = new TextButton("HUD-Label1", skin);
		uiStage.addActor(button1);
		button1.setPosition(camera.viewportWidth / 2 - 100, 0);
		button1.setBounds(button1.getX(), button1.getY(), button1.getWidth(), button1.getHeight());

		uiStage.addActor(new TextButton("HUD-Label2", skin));
	}

	@Override
	public void update(float deltaTime) {
		gameWorld.update(deltaTime);

		if (cameraUnit != null) {
			camera.position.set( // position
					Math.max(camera.viewportWidth * camera.zoom * 0.5f, Math.min(gameWorld.getWidth() - camera.viewportWidth * camera.zoom * 0.5f, cameraUnit.getX() + cameraUnit.getWidth() * 0.5f)),                                                        // x
					Math.max(camera.viewportHeight * camera.zoom * 0.5f, Math.min(gameWorld.getHeight() - camera.viewportHeight * camera.zoom * 0.5f, cameraUnit.getY() + cameraUnit.getHeight() * 0.5f)),                                                        // y
					camera.position.z); // z
		}
	}

	@Override
	public Array<IGameObject> getRenderObjects() {
		renderObjects.clear();

		Array<IGameObject> objects = gameWorld.getObjects();
		for (int i = objects.size - 1; i >= 0; --i) {
			IGameObject obj = objects.get(i);

			// filter out objects that are not within the camera view
			final float left = obj.getX();
			final float bottom = obj.getY();
			final float right = left + obj.getWidth();
			final float top = bottom + obj.getHeight();

			if (camera.frustum.pointInFrustum(left, bottom, 0) // bottom left within view or
					|| camera.frustum.pointInFrustum(right, bottom, 0) // or bottom right within view
					|| camera.frustum.pointInFrustum(right, top, 0) // or top right within view
					|| camera.frustum.pointInFrustum(left, top, 0) // or bottom left within view
			) {
				renderObjects.add(obj);
			}
		}

		// sort objects by y-coordinate
		renderObjects.sort();

		return renderObjects;
	}

	private void updatePlayerMovement() {
		boolean moveUp = keyHold.get(0);
		boolean moveDown = keyHold.get(1);
		boolean moveLeft = keyHold.get(2);
		boolean moveRight = keyHold.get(3);

		if (moveUp && moveLeft && !moveRight && !moveDown) {
			// top left
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(0, gameWorld.getHeight()).withCollisionDetection(gameWorld, true));
		} else if (moveUp && moveRight && !moveLeft && !moveDown) {
			// top right
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(gameWorld.getWidth(), gameWorld.getHeight()).withCollisionDetection(gameWorld, true));
		} else if (moveUp && !moveRight && !moveLeft && !moveDown) {
			// top
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(player.getX(), gameWorld.getHeight()).withCollisionDetection(gameWorld, true));
		} else if (moveDown && moveLeft && !moveRight && !moveUp) {
			// bottom left
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(0, 0).withCollisionDetection(gameWorld, true));
		} else if (moveDown && moveRight && !moveLeft && !moveUp) {
			// bottom right
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(gameWorld.getWidth(), 0).withCollisionDetection(gameWorld, true));
		} else if (moveDown && !moveRight && !moveLeft && !moveUp) {
			// bottom
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(player.getX(), 0).withCollisionDetection(gameWorld, true));
		} else if (!moveDown && !moveRight && moveLeft && !moveUp) {
			// left
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(0, player.getY()).withCollisionDetection(gameWorld, true));
		} else if (!moveDown && moveRight && !moveLeft && !moveUp) {
			// right
			player.setAction(gameWorld.obtainAction(ActionTranslation.class).withTargetPosition(gameWorld.getWidth(), player.getY()).withCollisionDetection(gameWorld, true));
		} else {
			// don't move
			player.setAction(null);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
			case Input.Keys.RIGHT:
			case Input.Keys.LEFT:
			case Input.Keys.DOWN:
			case Input.Keys.UP:
				keyHold.set(keycode - 19, true);
				updatePlayerMovement();
				break;
		}

		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.RIGHT:
			case Input.Keys.LEFT:
			case Input.Keys.DOWN:
			case Input.Keys.UP:
				keyHold.set(keycode - 19, false);
				updatePlayerMovement();
				break;
		}

		return true;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
}
