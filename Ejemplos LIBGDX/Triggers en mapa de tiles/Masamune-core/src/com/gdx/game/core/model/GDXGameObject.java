package com.gdx.game.core.model;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.model.interfaces.IGameAction;
import com.gdx.game.core.model.interfaces.IGameObject;

public abstract class GDXGameObject extends GDXEventHandler implements IGameObject {
	private String				textureID;
	private float				stateTime;
	private boolean				loopAnimation;
	private float				fps;
	private float				animationSpeed;
	private float				x;
	private float				y;
	private int					z;
	private Facing				facing;
	private float				speedX;
	private float				speedY;
	private float				width;
	private float				height;
	private float				scaleX;
	private float				scaleY;
	private float				originX;
	private float				originY;
	private float				rotation;
	private float				red;
	private float				green;
	private float				blue;
	private float				alpha;
	private Array<IGameAction>	actions;
	private IGameAction			currentAction;

	public GDXGameObject() {
		facing = Facing.SOUTH;
		scaleX = scaleY = 1;
		red = blue = green = alpha = 1;
	}

	private void resetActions() {
		if (actions != null) {
			for (IGameAction action : actions) {
				Pools.free(action);
			}
			actions.clear();
		}
		currentAction = null;
	}

	@Override
	public void reset() {
		super.reset();

		resetActions();

		textureID = null;
		stateTime = 0;

		facing = Facing.SOUTH;
		scaleX = scaleY = 1;
		red = blue = green = alpha = 1;
		rotation = 0;
	}

	@Override
	public void update(float deltaTime) {
		if (currentAction != null) {
			currentAction.update(deltaTime);
			if (currentAction.isFinished()) {
				Pools.free(actions.removeIndex(0));
				if (actions.size > 0) {
					currentAction = actions.get(0);
					currentAction.start();
				}
			}
		}

		if (fps > 0) {
			stateTime += (deltaTime * animationSpeed);
		}
	}

	@Override
	public void setTexture(String textureID) {
		this.textureID = textureID;
	}

	@Override
	public String getTexture() {
		return textureID;
	}

	@Override
	public void setFramesPerSecond(float fps) {
		this.fps = fps;
	}

	@Override
	public float getFramesPerSecond() {
		return fps;
	}

	@Override
	public void setAnimationSpeed(float speed) {
		this.animationSpeed = speed;
	}

	@Override
	public float getAnimationSpeed() {
		return animationSpeed;
	}

	@Override
	public float getStateTime() {
		return stateTime;
	}

	@Override
	public void setLoopAnimation(boolean loopAnimation) {
		this.loopAnimation = loopAnimation;
	}

	@Override
	public boolean getLoopAnimation() {
		return loopAnimation;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public void setFacing(Facing facing) {
		this.facing = facing;
		stateTime = 0;
	}

	@Override
	public Facing getFacing() {
		return facing;
	}

	@Override
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	@Override
	public float getSpeedX() {
		return speedX;
	}

	@Override
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	@Override
	public float getSpeedY() {
		return speedY;
	}

	@Override
	public void setWidth(float width) {
		this.width = width * scaleX;
		this.originX = this.width * 0.5f;
	}

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public void setHeight(float height) {
		this.height = height * scaleY;
		this.originY = this.height * 0.5f;
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getOriginX() {
		return originX;
	}

	@Override
	public float getOriginY() {
		return originY;
	}

	@Override
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public float getRotation() {
		return rotation;
	}

	@Override
	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
		setWidth(width);
	}

	@Override
	public float getScaleX() {
		return scaleX;
	}

	@Override
	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
		setHeight(height);
	}

	@Override
	public float getScaleY() {
		return scaleY;
	}

	@Override
	public void setRed(float red) {
		this.red = Math.max(0, Math.min(1, red));
	}

	@Override
	public float getRed() {
		return red;
	}

	@Override
	public void setGreen(float green) {
		this.green = Math.max(0, Math.min(1, green));
	}

	@Override
	public float getGreen() {
		return green;
	}

	@Override
	public void setBlue(float blue) {
		this.blue = Math.max(0, Math.min(1, blue));
	}

	@Override
	public float getBlue() {
		return blue;
	}

	@Override
	public void setAlpha(float alpha) {
		this.alpha = Math.max(0, Math.min(1, alpha));
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	@Override
	public void addAction(IGameAction action) {
		if (action != null) {
			if (actions == null) {
				actions = new Array<IGameAction>();
			}

			actions.add(action.withTarget(this));
			if (actions.size == 1) {
				// first action is added to the object -> start it
				currentAction = actions.get(0);
				currentAction.start();
			}
		}
	}

	@Override
	public void setAction(IGameAction action) {
		resetActions();
		addAction(action);
	}

	@Override
	public IGameAction getCurrentAction() {
		return currentAction;
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}
