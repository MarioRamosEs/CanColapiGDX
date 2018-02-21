package com.gdx.game.core.model.gameactions;

import com.badlogic.gdx.math.MathUtils;
import com.gdx.game.core.model.GDXGameAction;

public class ActionRotation extends GDXGameAction {
	private float	targetDegree;
	private float	time;
	private float	progress;
	private float	sourceDegree;

	public ActionRotation withRotation(float targetDegree, float time) {
		this.targetDegree = targetDegree;
		this.time = time;

		return this;
	}

	@Override
	public void start() {
		sourceDegree = target.getRotation();
	}

	@Override
	public void update(float deltaTime) {
		progress += (deltaTime / time);
		target.setRotation(MathUtils.lerp(sourceDegree, targetDegree, progress));
	}

	@Override
	public boolean isFinished() {
		return progress >= 1;
	}

	@Override
	public void reset() {
		progress = 0;
	}
}
