package com.gdx.game.core.model.gameactions;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.gdx.game.core.model.GDXGameAction;
import com.gdx.game.core.model.interfaces.IGameAction;
import com.gdx.game.core.model.interfaces.IGameObject;

public class ActionParallel extends GDXGameAction {
	private Array<IGameAction> actions;

	public ActionParallel addAction(GDXGameAction action) {
		if (actions == null) {
			actions = new Array<IGameAction>();
		}

		actions.add(action);

		return this;
	}

	@Override
	public void start() {
		for (IGameAction action : actions) {
			action.start();
		}
	}

	@Override
	public void update(float deltaTime) {
		for (int i = actions.size - 1; i >= 0; --i) {
			final IGameAction action = actions.get(i);
			if (!action.isFinished()) {
				action.update(deltaTime);
			}
		}
	}

	@Override
	public boolean isFinished() {
		for (int i = actions.size - 1; i >= 0; --i) {
			if (!actions.get(i).isFinished()) {
				return false;
			}
		}

		return true;
	}

	@Override
	public void reset() {
		super.reset();

		if (actions != null) {
			for (IGameAction action : actions) {
				Pools.free(action);
			}
			actions.clear();
		}
	}

	@Override
	public IGameAction withTarget(IGameObject target) {
		for (IGameAction action : actions) {
			action.withTarget(target);
		}

		return this;
	}
}
