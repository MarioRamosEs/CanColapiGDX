package com.gdx.game.core.model.gameactions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.model.GDXGameWorld;
import com.gdx.game.core.model.GDXTriggerArea;
import com.gdx.game.core.model.GDXGameAction;
import com.gdx.game.core.model.interfaces.IGameObject.Facing;
import com.gdx.game.core.triggerevents.TriggerEventEnter;

public class ActionTranslation extends GDXGameAction {
	private float					targetX;
	private float					targetY;
	private float					transX;
	private float					transY;
	private float					lastDistance;
	private boolean					collisionDetection;
	private GDXGameWorld			gameworld;
	private static GDXTriggerArea	lastArea;

	public ActionTranslation withTargetPosition(float targetX, float targetY) {
		this.targetX = targetX;
		this.targetY = targetY;

		return this;
	}

	public ActionTranslation withCollisionDetection(GDXGameWorld gameworld, boolean doDetection) {
		this.gameworld = gameworld;
		this.collisionDetection = doDetection;

		return this;
	}

	@Override
	public void start() {
		float sourceX = target.getX();
		float sourceY = target.getY();
		float diffX = targetX - sourceX;
		float diffY = targetY - sourceY;

		float angle = MathUtils.atan2(diffY, diffX);

		transY = target.getSpeedY() * MathUtils.sin(angle);
		transX = target.getSpeedX() * MathUtils.cos(angle);
		lastDistance = diffX * diffX + diffY * diffY;

		if (Math.abs(diffX) > Math.abs(diffY)) {
			if (diffX > 0) {
				target.setFacing(Facing.EAST);
			} else {
				target.setFacing(Facing.WEST);
			}
		} else {
			if (diffY > 0) {
				target.setFacing(Facing.NORTH);
			} else {
				target.setFacing(Facing.SOUTH);
			}
		}
	}

	@Override
	public void update(float deltaTime) {
		float newX = target.getX() + transX * deltaTime;
		float newY = target.getY() + transY * deltaTime;

		if (collisionDetection) {
			Rectangle targetCollisionRect = target.getBoundaries();
			targetCollisionRect.x = newX;
			targetCollisionRect.y = newY;

			Array<GDXTriggerArea> triggerAreas = gameworld.getTriggerAreas();
			if (triggerAreas != null) {
				for (int i = triggerAreas.size - 1; i >= 0; --i) {
					if (lastArea != null && lastArea.equals(triggerAreas.get(i)) && !triggerAreas.get(i).getBoundaries().overlaps(targetCollisionRect)) {
						lastArea = null;
					}
					if (!triggerAreas.get(i).equals(lastArea) && triggerAreas.get(i).getBoundaries().overlaps(targetCollisionRect)) {
						lastArea = triggerAreas.get(i);
						triggerAreas.get(i).fireEvent(Pools.obtain(TriggerEventEnter.class).withTriggerArea(lastArea).withTriggerObject(target).withGameWorld(gameworld));
					}
				}
			}

			Array<Rectangle> collisionRects = gameworld.getCollisionRectsForLayer((int) target.getZ());
			if (collisionRects != null) {
				for (int i = collisionRects.size - 1; i >= 0; --i) {
					if (collisionRects.get(i).overlaps(targetCollisionRect)) {
						// collision -> do not move target
						return;
					}
				}
			}
		}

		target.setPosition(newX, newY);
	}

	@Override
	public boolean isFinished() {
		float diffX = targetX - target.getX();
		float diffY = targetY - target.getY();
		float currentDistance = diffX * diffX + diffY * diffY;

		if (currentDistance > lastDistance) {
			target.setPosition(targetX, targetY);
			return true;
		}

		lastDistance = currentDistance;
		return false;
	}

	@Override
	public void reset() {
		super.reset();

		this.collisionDetection = false;
		this.gameworld = null;
	}
}
