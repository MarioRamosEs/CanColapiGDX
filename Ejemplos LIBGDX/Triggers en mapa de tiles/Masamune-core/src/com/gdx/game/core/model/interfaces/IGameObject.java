package com.gdx.game.core.model.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface IGameObject extends IEventHandler, Comparable<IGameObject> {
	public enum Facing {
		SOUTH,
		WEST,
		EAST,
		NORTH;
	}

	public void update(float deltaTime);

	default IGameObject withTexture(String textureID) {
		setTexture(textureID);

		return this;
	}

	public void setTexture(String textureID);

	public String getTexture();

	default IGameObject withFramesPerSecond(float fps) {
		setFramesPerSecond(fps);

		return this;
	}

	public void setFramesPerSecond(float fps);

	public float getFramesPerSecond();

	default IGameObject withAnimationSpeed(float speed) {
		setAnimationSpeed(speed);

		return this;
	}

	public void setAnimationSpeed(float speed);

	public float getAnimationSpeed();

	public float getStateTime();

	default IGameObject withLoopAnimation(boolean loopAnimation) {
		setLoopAnimation(loopAnimation);

		return this;
	}

	public void setLoopAnimation(boolean loopAnimation);

	public boolean getLoopAnimation();

	default IGameObject withPosition(float x, float y, int z) {
		setPosition(x, y);
		setZ(z);

		return this;
	}

	default void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}

	public void setX(float x);

	public float getX();

	public void setY(float y);

	public float getY();

	public void setZ(int z);

	public int getZ();

	default IGameObject withFacing(Facing facing) {
		setFacing(facing);

		return this;
	}

	public void setFacing(Facing facing);

	public Facing getFacing();

	default IGameObject withSpeed(float speedX, float speedY) {
		setSpeed(speedX, speedY);

		return this;
	}

	default void setSpeed(float speedX, float speedY) {
		setSpeedX(speedX);
		setSpeedY(speedY);
	}

	public void setSpeedX(float speedX);

	public float getSpeedX();

	public void setSpeedY(float speedY);

	public float getSpeedY();

	default Rectangle getBoundaries() {
		return Rectangle.tmp.set(getX(), getY(), getWidth(), getHeight());
	}

	default IGameObject withSize(float width, float height) {
		setSize(width, height);

		return this;
	}

	default void setSize(float width, float height) {
		setWidth(width);
		setHeight(height);
	}

	public void setWidth(float width);

	public float getWidth();

	public void setHeight(float height);

	public float getHeight();

	default IGameObject withScale(float scaleX, float scaleY) {
		setScale(scaleX, scaleY);

		return this;
	}

	default void setScale(float scaleX, float scaleY) {
		setScaleX(scaleX);
		setScaleY(scaleY);
	}

	public void setScaleX(float scaleX);

	public float getScaleX();

	public void setScaleY(float scaleY);

	public float getScaleY();

	public float getOriginX();

	public float getOriginY();

	default IGameObject withRotation(float rotation) {
		setRotation(rotation);

		return this;
	}

	public void setRotation(float rotation);

	public float getRotation();

	default IGameObject withColor(float red, float green, float blue, float alpha) {
		setColor(red, green, blue, alpha);

		return this;
	}

	default void setColor(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}

	default void setColor(float red, float green, float blue) {
		setColor(red, green, blue, getAlpha());
	}

	public void setRed(float red);

	public float getRed();

	public void setGreen(float green);

	public float getGreen();

	public void setBlue(float blue);

	public float getBlue();

	public void setAlpha(float alpha);

	public float getAlpha();

	public void setAction(IGameAction action);

	public void addAction(IGameAction action);

	public IGameAction getCurrentAction();

	public boolean isRemovable();

	@Override
	default int compareTo(IGameObject o) {
		// first sort by z
		int deltaZ = Integer.compare(getZ(), o.getZ());

		if (deltaZ == 0) {
			// if z is equal -> order by y
			return Float.compare(getY(), o.getY());
		}

		return deltaZ;
	}
}
