package com.gdx.game.core.model.gameactions;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.model.GDXGameAction;
import com.gdx.game.core.model.interfaces.IGameObject;

public class ActionPatrol extends GDXGameAction {
	private Array<ActionTranslation>	translations;
	private int							currentPatrolIndex;
	private int							patrolDirection;

	public ActionPatrol addPatrolPoint(float targetX, float targetY) {
		if (translations == null) {
			translations = new Array<ActionTranslation>();
		}

		if (translations.size == 0) {
			// this position will be replaced with the target's actual position
			// once the action is started
			translations.add(Pools.obtain(ActionTranslation.class).withTargetPosition(0, 0));
		}

		translations.add(Pools.obtain(ActionTranslation.class).withTargetPosition(targetX, targetY));

		return this;
	}

	@Override
	public GDXGameAction withTarget(IGameObject target) {
		this.target = target;
		if (translations != null) {
			for (ActionTranslation action : translations) {
				action.withTarget(target);
			}
		}

		return this;
	}

	@Override
	public void start() {
		translations.get(0).withTargetPosition(target.getX(), target.getY());
		translations.get(1).start();
		currentPatrolIndex = 1;
		patrolDirection = 1;
	}

	@Override
	public void update(float deltaTime) {
		translations.get(currentPatrolIndex).update(deltaTime);

		if (translations.get(currentPatrolIndex).isFinished()) {
			currentPatrolIndex = currentPatrolIndex + patrolDirection;
			if (currentPatrolIndex >= translations.size) {
				currentPatrolIndex = translations.size - 2;
				patrolDirection = -1;
			} else if (currentPatrolIndex < 0) {
				currentPatrolIndex = 0;
				patrolDirection = 1;
			}

			translations.get(currentPatrolIndex).start();
		}
	}

	@Override
	public void reset() {
		super.reset();

		if (translations != null) {
			for (ActionTranslation action : translations) {
				Pools.free(action);
			}
			translations.clear();
		}

		currentPatrolIndex = 1;
		patrolDirection = 1;
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
