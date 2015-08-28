package com.jbs.rh2;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class BossHealthBar extends Group {
	Image icon;
	Image empty, bar;
	Boss boss;

	boolean active;
	float maxBarWidth;

	public BossHealthBar(Boss boss) {
		this.boss = boss;
		icon = new Image(Assets.getTex("Maps/" + boss.levelName + "/Boss/Icon.png"));
		empty = new Image(Assets.getTex("Maps/" + boss.levelName + "/Boss/Barempty.png"));
		bar = new Image(Assets.getTex("Maps/" + boss.levelName + "/Boss/HPBar.png"));
		maxBarWidth = bar.getWidth();

		empty.setPosition(icon.getWidth() - 50f, icon.getHeight() / 2 - empty.getHeight() / 2);
		bar.setPosition(empty.getX() + 20f, empty.getY() + empty.getHeight() / 2 - bar.getHeight()
				/ 2 + 5f);

		addActor(empty);
		addActor(bar);
		addActor(icon);
	}

	@Override
	public float getHeight() {
		return icon.getHeight();
	}

	Action activeAction = new Action() {
		@Override
		public boolean act(float delta) {
			active = true;
			return true;
		}

	};

	public void show() {
		if (!active) {
			addAction(Actions.sequence(Actions.alpha(0), activeAction, Actions.fadeIn(0.4f)));
		}
	}

	public void fade() {
		active = false;
		addAction(Actions.fadeOut(0.4f));
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (bar.getActions().size == 0) {
			bar.addAction(Actions.sizeTo(15 + Math.max(boss.health, 0) / boss.maxHealth * (maxBarWidth - 15), bar.getHeight(), 0.4f));
		}

		setVisible(active);
	}

}
